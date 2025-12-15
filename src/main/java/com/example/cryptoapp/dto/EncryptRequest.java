package com.example.cryptoapp.dto;

public class EncryptRequest {
    private String text;
    private String cipherType; // VIGENERE, CAESAR, ATBASH, PLAYFAIR
    private String phrase; // for vigenere or playfair
    private Integer shift; // for caesar
    private String pdfPassword; // sender-provided PDF password

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getCipherType() { return cipherType; }
    public void setCipherType(String cipherType) { this.cipherType = cipherType; }
    public String getPhrase() { return phrase; }
    public void setPhrase(String phrase) { this.phrase = phrase; }
    public Integer getShift() { return shift; }
    public void setShift(Integer shift) { this.shift = shift; }
    public String getPdfPassword() { return pdfPassword; }
    public void setPdfPassword(String pdfPassword) { this.pdfPassword = pdfPassword; }
}
