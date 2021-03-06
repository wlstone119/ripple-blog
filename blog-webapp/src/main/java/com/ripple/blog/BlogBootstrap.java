package com.ripple.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:applicationContext.xml"})
@SpringBootApplication
@EnableCaching
public class BlogBootstrap {

    public static void main(String[] args) {
        //System.setProperty("logback.configurationFile", "/Users/cuiyongxu/logback.xml");
        System.setProperty("file.encoding", "UTF-8");
        SpringApplication.run(BlogBootstrap.class, args);
    }

}
