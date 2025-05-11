package com.pet.manager.config;

import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "roboflow")
public class RoboflowConfig {
    private static final Logger logger = LoggerFactory.getLogger(RoboflowConfig.class);

    private String apiUrl;
    @Getter
    private String apiKey;
    @Getter
    private String workspaceName;
    @Getter
    private String workflowId;

    private double confidenceThreshold = 0.95; // 默认值
    private boolean returnHighestConfidenceOnly = true; // 默认值


}
