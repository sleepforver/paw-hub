package com.pet.manager.service;

import com.pet.manager.domain.dto.PredictionResultDto;

import java.io.IOException;

public interface IRoboflowService {
    PredictionResultDto runWorkflow(String image) throws IOException;
}
