package com.huo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/24 17:18
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class HuoGenBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(HuoGenBlogApplication.class,args);
    }
}
