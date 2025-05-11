package com.pet.manager.controller;//package com.pet.manager.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.manager.domain.dto.ChatRequestDto;
import com.pet.manager.service.IAiService;
import com.pet.manager.service.IRoboflowService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Api(tags = "AI Controller")
@RestController
@Slf4j
@RequestMapping("/manager/ai")
public class AiController {
    @Autowired
    private IAiService aiService;

    @Autowired
    private IRoboflowService roboflowService;

    @PostMapping("/chat/{model}")
    public String chat(@PathVariable String model, @RequestBody ChatRequestDto request) {
        return aiService.chat(request.getMessage(), model, request.getChatId());
    }

    @PostMapping("/chat/stream/{model}")
    public Flux<String> chatStream(@PathVariable String model, @RequestBody ChatRequestDto request) {
        return aiService.chatStream(request.getMessage(), model, request.getChatId());
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeImage(@RequestBody ChatRequestDto chatRequestDto) {
        try {

            // 调用服务层，运行 Roboflow 工作流
            String result = String.valueOf(roboflowService.runWorkflow(chatRequestDto.getImage()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        } catch (IOException e) {
            // 返回错误信息为 JSON 格式
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("message", "Error processing image: " + e.getMessage());

            try {
                return ResponseEntity
                        .status(500)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(errorResponse));
            } catch (JsonProcessingException ex) {
                return ResponseEntity.status(500).body("{\"error\":true,\"message\":\"Error processing request\"}");
            }
        }
    }
}
