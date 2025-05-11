package com.pet.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebhookPayload {
    private String finalResult;
    private Object predictions;
    private String visualization;
}
