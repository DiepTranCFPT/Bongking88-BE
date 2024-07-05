package com.example.demo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class ImageController {

    // Example endpoint for image upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        // Logic to handle file upload
        // Save file to a location or cloud storage
        // Update the 'photo' field in the Location entity with the image path or URL

        // Example: Assume saving to filesystem and updating photo field
        String fileName = file.getOriginalFilename();
        // Logic to save file to filesystem or cloud storage
        // locationService.saveImage(fileName);
        return ResponseEntity.ok("Image uploaded: " + fileName);
    }
}
