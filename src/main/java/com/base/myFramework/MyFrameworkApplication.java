package com.base.myFramework;

import com.spring4all.swagger.EnableSwagger2Doc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

@EnableAsync
@EnableSwagger2Doc
@Slf4j
@Controller
@SpringBootApplication
public class MyFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFrameworkApplication.class, args);
    }

}
