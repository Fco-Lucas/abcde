package com.lcsz.abcde.controllers;

import com.lcsz.abcde.AppProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HealthCheckController {
    private final AppProperties appProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    HealthCheckController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping("/ping-spring")
    public ResponseEntity<String> healthCheckSpring() {
        return ResponseEntity.ok("true");
    }

    @GetMapping("/ping-python")
    public ResponseEntity<String> healthCheckPython() {
        String pythonUrl = appProperties.getApiPythonUrl() + "/ping";

        try {
            // Faz requisição GET para o Python
            String response = restTemplate.getForObject(pythonUrl, String.class);

            // Retorna exatamente o que o Python respondeu
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Em caso de erro, retorna 503
            return ResponseEntity.status(503).body("false");
        }
    }
}
