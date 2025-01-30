package com.examendos.examenp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ExamenP2Application {

    public static void main(String[] args) {
        SpringApplication.run(ExamenP2Application.class, args);
    }

}
