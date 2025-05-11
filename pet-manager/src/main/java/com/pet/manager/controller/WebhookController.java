package com.pet.manager.controller;

import com.pet.manager.domain.PetHealthAnalysis;
import com.pet.manager.domain.PetHealthRecords;
import com.pet.manager.domain.WebhookPayload;
import com.pet.manager.mapper.PetHealthRecordsMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.manager.service.IPetHealthAnalysisService;
import com.pet.manager.service.IPetHealthRecordsService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("manager/webhook")
public class WebhookController {

    @Autowired
    private IPetHealthAnalysisService iPetHealthAnalysisService;

    @Autowired
    private IPetHealthRecordsService iPetHealthRecordsService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/receive")
    public ResponseEntity<String> receiveWebhook(
            @RequestHeader(value = "X-Webhook-Key", required = false) String webhookKey,
            @RequestBody WebhookPayload payload) {
        // 验证 Webhook 密钥（可选）
        if (webhookKey != null && !"YOUR_SECRET_KEY".equals(webhookKey)) {
            return ResponseEntity.status(403).body("Invalid webhook key");
        }

        try {
            // 解析 predictions
            Map<String, Object> predictions = (Map<String, Object>) payload.getPredictions();
            List<Map<String, Object>> predictionList = (List<Map<String, Object>>) predictions.get("predictions");
            Map<String, Object> topPrediction = predictionList.get(0); // 取置信度最高的预测
            String diseaseName = (String) topPrediction.get("class");
            Double confidenceScore = ((Number) topPrediction.get("confidence")).doubleValue();

            // 调用 DeepSeek API
            String deepSeekResponse = callDeepSeekApi(objectMapper.writeValueAsString(predictions));

            // 解析 DeepSeek 响应（假设返回 JSON）
            Map<String, String> deepSeekResult = objectMapper.readValue(deepSeekResponse, Map.class);
            String diagnosis = deepSeekResult.getOrDefault("diagnosis", "No diagnosis provided");
            String recommendations = deepSeekResult.getOrDefault("recommendations", "Consult a veterinarian");

            // 保存到 tb_pet_health_records
            PetHealthRecords record = new PetHealthRecords();
//            record.setPetId(1); // 假设 pet_id，从前端或上下文获取
//            record.setCheckDate(LocalDateTime.now());
//            record.setImageUrl(payload.getVisualization()); // 存储 Base64 或上传到云存储后存储 URL
//            record.setSymptoms("Detected by AI"); // 可从前端获取
//            record.setAiDiagnosis(diagnosis);
//            record.setSeverityLevel(confidenceScore > 0.9 ? 2 : confidenceScore > 0.7 ? 1 : 0);
//            record.setRecommendations(recommendations);
//            record.setStatus(confidenceScore > 0.5 ? 1 : 0);
//            record.setCreatedBy(1); // 假设用户 ID
//            record.setUpdatedBy(1);
            iPetHealthRecordsService.insertPetHealthRecords(record);

            // 保存到 tb_pet_health_ai_analysis
            PetHealthAnalysis analysis = new PetHealthAnalysis();
//            analysis.setRecordId(record.getRecordId());
//            analysis.setDiseaseName(diseaseName);
//            analysis.setConfidenceScore(confidenceScore);
//            analysis.setDetectedFeatures(objectMapper.writeValueAsString(predictions)); // 存储完整 predictions
//            analysis.setAnalysisDetails(diagnosis);
            iPetHealthAnalysisService.insertPetHealthAnalysis(analysis);

            // 返回成功响应
            return ResponseEntity.ok("Webhook processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing webhook: " + e.getMessage());
        }
    }

    private String callDeepSeekApi(String inputData) throws Exception {
        String apiKey = "sk-wvogtpgpjednzlzoongsawsgfqmkxoijxwskwphhldpgvpbd"; // 替换为您的 DeepSeek API 密钥
        String url = "https://api.deepseek.com/v1/chat/completions";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization", "Bearer " + apiKey);
        post.setHeader("Content-Type", "application/json");

        String prompt = "Analyze the following dog skin disease detection results and provide a diagnosis and recommendations: " + inputData;
        String jsonBody = String.format(
                "{\"model\": \"deepseek-reasoner\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"stream\": false}",
                prompt.replace("\"", "\\\"")
        );
        post.setEntity(new StringEntity(jsonBody));

        try (CloseableHttpResponse response = client.execute(post)) {
            String result = EntityUtils.toString(response.getEntity());
            // 解析 DeepSeek 响应
            Map<String, Object> responseMap = objectMapper.readValue(result, Map.class);
            List<Object> choices = (List<Object>) responseMap.get("choices");

            if (choices == null || choices.isEmpty()) {
                throw new RuntimeException("No choices found in DeepSeek API response");
            }

            // 强制类型转换并提取内容
            Map<String, Object> choice = (Map<String, Object>) choices.get(0);
            Map<String, Object> message = (Map<String, Object>) choice.get("message");
            if (message == null || !message.containsKey("content")) {
                throw new RuntimeException("Invalid message format in DeepSeek API response");
            }

            return (String) message.get("content");
        }
    }

}

