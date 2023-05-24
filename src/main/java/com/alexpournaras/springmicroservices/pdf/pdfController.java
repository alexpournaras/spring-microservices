package com.alexpournaras.springmicroservices.pdf;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class pdfController {
	protected Logger logger = Logger.getLogger(pdfController.class.getName());

    @Value("${pdf.directory}")
    private String pdfDirectory;

	@PostMapping("/pdf")
    public ResponseEntity<Map<String, String>> doPdf(@RequestBody Map<String, String> payload) {
        String text = payload.get("text");

        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 700);
            contentStream.showText(text);
            contentStream.endText();
            contentStream.close();

            // Create or get the pdf directory from the service properties
            Files.createDirectories(Paths.get(pdfDirectory));

            // Get current timestamp to name the new pdf file
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

            // Create pdf file name
            String pdfFileName = "/" + timeStamp + "_redacted.pdf";

            // Save the pdf file
            String path = pdfDirectory + pdfFileName;
            document.save(path);
            document.close();

            // Create response Map object
            Map<String, String> response = new HashMap<>();
            response.put("filename", pdfFileName);

            return ResponseEntity.ok(response);

        } catch (IOException error) {
            throw new RuntimeException("Could not generate PDF", error);
        }
    }

    @GetMapping("/pdf/{filename:.+}")
    public ResponseEntity<InputStreamResource> getPdf(@PathVariable String filename) {
        try {
            // Get file from path
            File file = new File(pdfDirectory + "/" + filename);

            if (!file.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Create the pdf resource to return
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);

        } catch (Exception error) {
            throw new RuntimeException("Could not read file " + filename, error);
        }
    }

}
