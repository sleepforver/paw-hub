package com.pet.manager.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.manager.config.RoboflowConfig;
import com.pet.manager.domain.dto.PredictionResultDto;
import com.pet.manager.service.IRoboflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class RoboflowServiceImpl implements IRoboflowService {
    private static final Logger logger = LoggerFactory.getLogger(RoboflowServiceImpl.class);

    private final RoboflowConfig config;

    private final WebClient webClient;

    @Autowired
    public RoboflowServiceImpl(RoboflowConfig config, WebClient.Builder webClientBuilder) {
        this.config = config;
        this.webClient = webClientBuilder.baseUrl(config.getApiUrl()).build();
    }


    @Override
    public PredictionResultDto runWorkflow(String image) throws IOException {
        // 有效验证
        if (config.getApiUrl() == null || config.getApiUrl().isEmpty()) {
            logger.error("Roboflow API URL is not configured");
            throw new IllegalStateException("Roboflow API URL is not configured");
        }
        if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
            logger.error("Roboflow API key is not configured");
            throw new IllegalStateException("Roboflow API key is not configured");
        }

        // 构建请求体
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("api_key", config.getApiKey());

        // 添加置信度阈值配置（如果有）
        if (config.getConfidenceThreshold() > 0) {
            requestMap.put("confidence", config.getConfidenceThreshold());
        }

        Map<String, Object> imageMap = new HashMap<>();

        // 检查输入是否为URL
        if (image.startsWith("http://") || image.startsWith("https://")) {
            // 如果是URL，直接使用
            imageMap.put("type", "url");
            imageMap.put("value", image);
        } else if (image.startsWith("data:image")) {
            // 处理带有MIME类型前缀的Base64
            String base64Data = image.substring(image.indexOf(",") + 1);
            imageMap.put("type", "base64");
            imageMap.put("value", base64Data);
        } else {
            // 假设输入已经是纯Base64编码的字符串
            imageMap.put("type", "base64");
            imageMap.put("value", image);
        }

        Map<String, Object> inputsMap = new HashMap<>();
        inputsMap.put("image", imageMap);

        requestMap.put("inputs", inputsMap);

        // Convert map to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestMap);

        logger.debug("Request Body: {}", requestBody);

        try {
            // 确保使用正确的基础URL - 必须使用HTTPS
            String baseUrl = "https://detect.roboflow.com";
            if (!config.getApiUrl().startsWith("https://")) {
                logger.warn("Forcing HTTPS protocol for Roboflow API");
            }

            // 构建完整的URL
            String fullUrl = baseUrl + "/infer/workflows/" + config.getWorkspaceName() + "/" + config.getWorkflowId();
            logger.debug("Full URL: {}", fullUrl);

            // 创建自定义的SSLContext以处理TLS握手问题
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, new SecureRandom());

            // 创建HTTP客户端请求，增加超时设置和SSL配置
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .sslContext(sslContext)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 发送请求并获取响应
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new IOException("HTTP error: " + response.statusCode() + " - " + response.body());
            }

            String responseBody = response.body();
            logger.debug("Roboflow API response: {}", responseBody);

            // 解析完整的JSON响应
            JsonNode rootNode = objectMapper.readTree(responseBody);

            try {
                // 获取predictions数组
                JsonNode predictionsArray = rootNode.path("outputs").get(0).path("predictions").path("predictions");

                // 如果predictions数组为空或不存在，返回空结果
                if (predictionsArray == null || predictionsArray.isEmpty()) {
                    logger.warn("No predictions found in response");
                    return new PredictionResultDto(0.0, -1, "");
                }

                // 初始化变量来存储最高置信度的预测
                double highestConfidence = 0;
                int highestClassId = -1;
                String highestClass = "";

                // 遍历找出置信度最高的预测
                for (JsonNode prediction : predictionsArray) {
                    double confidence = prediction.path("confidence").asDouble();
                    if (confidence > highestConfidence) {
                        highestConfidence = confidence;
                        highestClassId = prediction.path("class_id").asInt();
                        highestClass = prediction.path("class").asText();
                    }
                }

                // 返回只包含所需字段的结果
                return new PredictionResultDto(highestConfidence, highestClassId, highestClass);

            } catch (Exception e) {
                logger.error("Error extracting prediction data: {}", e.getMessage(), e);
                // 如果提取失败，返回错误结果
                throw new IOException("Failed to extract prediction data: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error calling Roboflow API: {}", e.getMessage(), e);
            throw new IOException("Failed to run workflow: " + e.getMessage(), e);
        }
    }
}
