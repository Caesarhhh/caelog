package com.ownblog.caelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CaelogApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaelogApplication.class, args);
    }

}
