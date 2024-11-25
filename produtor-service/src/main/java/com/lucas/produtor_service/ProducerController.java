package com.lucas.produtor_service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {


    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }


    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        producerService.sendMessage("my-topic", message);
        return "Mensagem enviada: " + message;
    }
}