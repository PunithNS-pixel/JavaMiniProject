package com.example.cryptoapp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {
    public byte[] buildInstructionPdf(String cipherType,
                                      String encryptedText,
                                      String phrase,
                                      Integer shift,
                                      String qrUrl,
                                      String pdfPassword) throws Exception {
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                float margin = 50;
                float y = page.getMediaBox().getHeight() - margin;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
                cs.newLineAtOffset(margin, y);
                cs.showText("Decryption Instructions");
                cs.endText();

                y -= 30;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(margin, y);
                cs.showText("Cipher: " + cipherType);
                cs.endText();

                y -= 18;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(margin, y);
                cs.showText("Encrypted Text: ");
                cs.endText();

                // Wrap encrypted text
                y -= 16;
                y = writeWrappedText(cs, encryptedText, margin, y, page.getMediaBox().getWidth() - 2 * margin, 11);

                y -= 10;
                String hint = cipherType.equalsIgnoreCase("VIGENERE")
                        ? "Use the provided phrase to derive the keyword."
                        : "Use the provided shift value.";
                y = writeWrappedText(cs, "Hints: " + hint, margin, y, page.getMediaBox().getWidth() - 2 * margin, 11);

                if (phrase != null) {
                    y -= 12;
                    y = writeWrappedText(cs, "Phrase (for keyword): " + phrase, margin, y, page.getMediaBox().getWidth() - 2 * margin, 11);
                }
                if (shift != null) {
                    y -= 12;
                    cs.beginText();
                    cs.setFont(PDType1Font.HELVETICA, 11);
                    cs.newLineAtOffset(margin, y);
                    cs.showText("Shift: " + shift);
                    cs.endText();
                }

                // Generate QR and draw
                BufferedImage qr = generateQr(qrUrl, 220, 220);
                PDImageXObject pdImage = LosslessFactory.createFromImage(doc, qr);
                y -= 240;
                cs.drawImage(pdImage, margin, Math.max(100, y));

                y = Math.max(100, y) - 10;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
                cs.newLineAtOffset(margin, y);
                cs.showText("Scan to open the decryption page: " + qrUrl);
                cs.endText();
            }

            // Protect the PDF with user password
            AccessPermission ap = new AccessPermission();
            String owner = pdfPassword + "_owner";
            StandardProtectionPolicy spp = new StandardProtectionPolicy(owner, pdfPassword, ap);
            spp.setEncryptionKeyLength(128);
            doc.protect(spp);

            doc.save(baos);
            return baos.toByteArray();
        }
    }

    private static BufferedImage generateQr(String data, int width, int height) throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    private static float writeWrappedText(PDPageContentStream cs, String text, float x, float y, float width, float fontSize) throws Exception {
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        float leading = 1.4f * fontSize;
        for (String w : words) {
            String test = (line.length() == 0 ? w : line + " " + w);
            float len = PDType1Font.HELVETICA.getStringWidth(test) / 1000 * fontSize;
            if (len > width) {
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, fontSize);
                cs.newLineAtOffset(x, y);
                cs.showText(line.toString());
                cs.endText();
                y -= leading;
                line = new StringBuilder(w);
            } else {
                line = new StringBuilder(test);
            }
        }
        if (line.length() > 0) {
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, fontSize);
            cs.newLineAtOffset(x, y);
            cs.showText(line.toString());
            cs.endText();
            y -= leading;
        }
        return y;
    }
}
