package com.pet.manager.service;

import org.springframework.ai.chat.model.Generation;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IAiService {
    String chat(String message, String modelName, String chatId);
    Flux<String> chatStream(String message, String modelName, String chatId);
}

