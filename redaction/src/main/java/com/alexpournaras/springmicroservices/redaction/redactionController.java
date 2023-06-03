package com.alexpournaras.springmicroservices.redaction;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class redactionController {
	protected Logger logger = Logger.getLogger(redactionController.class.getName());

	// Regular expressions for name, email, and street address patterns
    private static final String NAME_PATTERN = "\\b([A-Z][a-z]+(?: [A-Z][a-z]+)*)\\b";
    private static final String EMAIL_PATTERN = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
    private static final String STREET_ADDRESS_PATTERN = "\\b\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)\\s+([a-zA-Z]+\\s+)?[A-Za-z]{2,}\\s+\\d{5}(?:[-\\s]\\d{4})?\\b";

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
        Pattern streetAddressPattern = Pattern.compile(STREET_ADDRESS_PATTERN);
        Matcher streetAddressMatcher = streetAddressPattern.matcher(text);
        text = streetAddressMatcher.replaceAll("[REDACTED STREET ADDRESS]");

        // Create response map object
        Map<String, String> response = new HashMap<>();
        response.put("response", text);

        return ResponseEntity.ok(response);
    }

}
