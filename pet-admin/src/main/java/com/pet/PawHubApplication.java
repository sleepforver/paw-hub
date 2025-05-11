package com.pet;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@EnableFileStorage
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class } )
@EnableCaching
public class PawHubApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(PawHubApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  爪聚启动成功   ლ(´ڡ`ლ)ﾞ  \n");

    }
}
