package com.pet.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 在com.pet.manager.util包下新增SnowflakeIdGenerator.java
@Component
public class SnowflakeIdGenerator {
    // 从配置文件读取（application.yml）
    @Value("${snowflake.datacenter-id:0}")
    private long datacenterId;

    @Value("${snowflake.machine-id:0}")
    private long machineId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public synchronized String nextIdStr() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨异常");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & 4095;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long id = ((timestamp - 1685500000000L) << 22)
                | (datacenterId << 18)
                | (machineId << 12)
                | sequence;

        return String.valueOf(id);
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}

