package com.alexpournaras.springmicroservices.redaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedactionController {
	protected Logger logger = Logger.getLogger(RedactionController.class.getName());

	// Regular expressions for name, email, and street address patterns
    private static final String NAME_PATTERN = "\\b([A-Z][a-z]*\\s[A-Z][a-z]*)\\b";
    private static final String EMAIL_PATTERN = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
    private static final String ADDRESS_PATTERN = "\\b([A-Za-z]+\\s\\d+)\\b";

	@PostMapping("/redact")
    public ResponseEntity<Map<String, String>> doRedaction(@RequestBody Map<String, String> payload) {
        String text = payload.get("text");

		// Redact names
        Pattern namePattern = Pattern.compile(NAME_PATTERN);
        Matcher nameMatcher = namePattern.matcher(text);
        text = nameMatcher.replaceAll("[REDACTED NAME]");

        // Redact email addresses
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher emailMatcher = emailPattern.matcher(text);
        text = emailMatcher.replaceAll("[REDACTED EMAIL]");

        // Redact street addresses
        Pattern streetAddressPattern = Pattern.compile(ADDRESS_PATTERN);
        Matcher streetAddressMatcher = streetAddressPattern.matcher(text);
        text = streetAddressMatcher.replaceAll("[REDACTED ADDRESS]");

        // Create response map object
        Map<String, String> response = new HashMap<>();
        response.put("response", text);

        // Add with HATEOAS the hashing url to the response object
        Link hashingUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RedactionController.class).doHashing(payload)).withRel("hash");
        response.put("hashing_url", hashingUrl.getHref());

        return ResponseEntity.ok(response);
    }

	@PostMapping("/hash")
	public ResponseEntity<Map<String, String>> doHashing(@RequestBody Map<String, String> payload) {
        String text = payload.get("text");
        StringBuilder hashedText = new StringBuilder();

        try {
            MessageDigest message = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = message.digest(text.getBytes());

            for (byte b : hashedBytes) {
                hashedText.append(String.format("%02x", b));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        // Create response map object
        Map<String, String> response = new HashMap<>();
        response.put("response", hashedText.toString());

        // Add with HATEOAS the redaction url to the response object
        Link redactionUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RedactionController.class).doRedaction(payload)).withRel("redact");
        response.put("redaction_link", redactionUrl.getHref());

        return ResponseEntity.ok(response);
    }

}
