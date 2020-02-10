package com.galvanize.gmdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GmdbReviewSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmdbReviewSvcApplication.class, args);
    }

}
