package com.lcsz.abcde.controllers;

import com.lcsz.abcde.dtos.email.EmailSendResponseDto;
import com.lcsz.abcde.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.basepath}emails")
public class EmailController {
    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<List<EmailSendResponseDto>> sendEmails() {
        List<EmailSendResponseDto> responseDto = service.sendEmails();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
