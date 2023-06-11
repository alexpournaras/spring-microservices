package com.alexpournaras.springmicroservices.pdf;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.logging.Logger;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PdfController {
	protected Logger logger = Logger.getLogger(PdfController.class.getName());

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> doPdf(@RequestBody Map<String, String> payload) {
        String text = payload.get("text");

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, result);

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(14);

        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=redacted.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(result.toByteArray());
    }


}
