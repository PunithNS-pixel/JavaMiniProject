package com.example.cryptoapp.dto;

public class SendRequest {
    private String toEmail;
    private String cipherType;
    private String encryptedText;
    private String phrase; // vigenere or playfair
    private Integer shift; // caesar
    private String pdfPassword; // sender-provided password

    public String getToEmail() { return toEmail; }
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }
    public String getCipherType() { return cipherType; }
    public void setCipherType(String cipherType) { this.cipherType = cipherType; }
    public String getEncryptedText() { return encryptedText; }
    public void setEncryptedText(String encryptedText) { this.encryptedText = encryptedText; }
    public String getPhrase() { return phrase; }
    public void setPhrase(String phrase) { this.phrase = phrase; }
    public Integer getShift() { return shift; }
    public void setShift(Integer shift) { this.shift = shift; }
    public String getPdfPassword() { return pdfPassword; }
    public void setPdfPassword(String pdfPassword) { this.pdfPassword = pdfPassword; }
}
