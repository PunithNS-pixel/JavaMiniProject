package com.example.cryptoapp.controller;

import com.example.cryptoapp.dto.EncryptRequest;
import com.example.cryptoapp.dto.SendRequest;
import com.example.cryptoapp.service.CipherService;
import com.example.cryptoapp.service.EmailService;
import com.example.cryptoapp.service.PdfService;
import com.example.cryptoapp.service.TokenStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CryptoController {
    private final CipherService cipherService;
    private final PdfService pdfService;
    private final EmailService emailService;
    private final TokenStore tokenStore;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public CryptoController(CipherService cipherService, PdfService pdfService, EmailService emailService) {
        this.cipherService = cipherService;
        this.pdfService = pdfService;
        this.emailService = emailService;
        this.tokenStore = new TokenStore();
    }

    @GetMapping("/encrypt")
    public String encryptPage(HttpSession session) {
        return session.getAttribute("username") == null ? "redirect:/login" : "encrypt";
    }

    @PostMapping("/encrypt")
    public String doEncrypt(@ModelAttribute EncryptRequest req, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) return "redirect:/login";
        String cipherType = req.getCipherType() == null ? "VIGENERE" : req.getCipherType().toUpperCase();
        String encrypted;
        switch (cipherType) {
            case "CAESAR":
                int shift = req.getShift() == null ? 3 : req.getShift();
                encrypted = cipherService.encryptCaesar(req.getText(), shift);
                break;
            case "ATBASH":
                encrypted = cipherService.encryptAtbash(req.getText());
                break;
            case "PLAYFAIR":
                String kw = deriveKeyword(req.getPhrase());
                encrypted = cipherService.encryptPlayfair(req.getText(), kw);
                break;
            default: // VIGENERE
                String keyword = deriveKeyword(req.getPhrase());
                encrypted = cipherService.encryptVigenere(req.getText(), keyword);
        }
        model.addAttribute("cipherType", cipherType);
        model.addAttribute("originalText", req.getText());
        model.addAttribute("encryptedText", encrypted);
        model.addAttribute("phrase", req.getPhrase());
        model.addAttribute("shift", req.getShift());
        model.addAttribute("pdfPassword", req.getPdfPassword());
        return "result";
    }

    @PostMapping("/send")
    public String send(@ModelAttribute SendRequest req, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) return "redirect:/login";
        try {
            String cipherType = Optional.ofNullable(req.getCipherType()).orElse("VIGENERE").toUpperCase();
            String phrase = req.getPhrase();
            Integer shift = req.getShift();
            String pdfPassword = req.getPdfPassword();
            if (pdfPassword == null || pdfPassword.isBlank()) {
                pdfPassword = "CAESAR".equals(cipherType) ? String.valueOf(shift) : deriveKeyword(phrase);
            }
            String token = tokenStore.put(cipherType, req.getEncryptedText(), phrase, shift);
            String qrUrl = baseUrl + "/decrypt?token=" + token;

            byte[] pdf = pdfService.buildInstructionPdf(cipherType, req.getEncryptedText(), phrase, shift, qrUrl, pdfPassword);

            String body = "You've received an encrypted message.\n\n" +
                    "PDF password: " + pdfPassword + "\n" +
                    (phrase != null ? "Phrase: " + phrase + "\n" : "") +
                    (shift != null ? "Shift: " + shift + "\n" : "") +
                    "Open the attached PDF for instructions or visit: " + qrUrl + "\n";

            boolean ok = emailService.sendEmailWithAttachment(req.getToEmail(),
                    "Encrypted message instructions",
                    body,
                    pdf,
                    "instructions.pdf",
                    "application/pdf");

            model.addAttribute("emailStatus", ok ? "Sent" : "Failed to send");
            model.addAttribute("toEmail", req.getToEmail());
            model.addAttribute("qrUrl", qrUrl);
            return "send";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "send";
        }
    }

    @GetMapping("/decrypt")
    public String decryptPage(@RequestParam(value = "token", required = false) String token, Model model) {
        if (token != null) {
            tokenStore.get(token).ifPresent(rec -> {
                model.addAttribute("token", token);
                model.addAttribute("cipherType", rec.cipherType);
                model.addAttribute("encryptedText", rec.encryptedText);
                model.addAttribute("phrase", rec.phrase);
                model.addAttribute("shift", rec.shift);
            });
        }
        return "decrypt";
    }

    @PostMapping("/decrypt")
    public String doDecrypt(@RequestParam String cipherType,
                            @RequestParam String encryptedText,
                            @RequestParam(required = false) String phrase,
                            @RequestParam(required = false) Integer shift,
                            Model model) {
        String plaintext;
        switch (cipherType.toUpperCase()) {
            case "CAESAR":
                int s = shift == null ? 3 : shift;
                plaintext = cipherService.decryptCaesar(encryptedText, s);
                break;
            case "ATBASH":
                plaintext = cipherService.decryptAtbash(encryptedText);
                break;
            case "PLAYFAIR":
                String kw = deriveKeyword(phrase);
                plaintext = cipherService.decryptPlayfair(encryptedText, kw);
                break;
            default: // VIGENERE
                String keyword = deriveKeyword(phrase);
                plaintext = cipherService.decryptVigenere(encryptedText, keyword);
        }
        model.addAttribute("cipherType", cipherType);
        model.addAttribute("encryptedText", encryptedText);
        model.addAttribute("decryptedText", plaintext);
        return "decrypt";
    }

    private static String deriveKeyword(String phrase) {
        if (phrase == null) return "";
        return phrase.replaceAll("[^A-Za-z]", "").toUpperCase();
    }
}
