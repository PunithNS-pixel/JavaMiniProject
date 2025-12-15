package com.example.cryptoapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${resend.apiKey:}")
    private String apiKey;

    @Value("${resend.from:}")
    private String fromEmail;

    public boolean sendEmailWithAttachment(String to,
                                           String subject,
                                           String bodyText,
                                           byte[] attachmentBytes,
                                           String filename,
                                           String contentType) throws Exception {
        if (apiKey == null || apiKey.isBlank())
            throw new IllegalStateException("Resend API key missing. Set RESEND_API_KEY or resend.apiKey.");
        if (fromEmail == null || fromEmail.isBlank())
            throw new IllegalStateException("Resend FROM email missing. Set RESEND_FROM_EMAIL or resend.from.");

        Map<String, Object> payload = new HashMap<>();
        payload.put("from", fromEmail);
        payload.put("to", List.of(to));
        payload.put("subject", subject);
        payload.put("text", bodyText);
        if (attachmentBytes != null) {
            Map<String, String> att = new HashMap<>();
            att.put("filename", filename);
            att.put("content", Base64.getEncoder().encodeToString(attachmentBytes));
            att.put("content_type", contentType);
            payload.put("attachments", List.of(att));
        }

        String json = mapper.writeValueAsString(payload);
        Request request = new Request.Builder()
                .url("https://api.resend.com/emails")
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }
}
