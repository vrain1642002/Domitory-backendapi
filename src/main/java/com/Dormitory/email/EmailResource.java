package com.Dormitory.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401","https://dormiotry-frontend-student-production.up.railway.app","https://dormitory-frontend-admin-production.up.railway.app"})
@RequestMapping("api/v1/email")
public class EmailResource {
    @Autowired
    private EmailService emailService;

    // @PostMapping("")
    // public ResponseEntity<?> sendEmail(@Valid @RequestBody Email email, @RequestParam String studentName) {
    //     emailService.sendEmail(email,studentName);
    //     return ResponseEntity.ok().build();
    // }    
}
