package com.avensys.rts.usergroupservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class UserGroupServiceApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(UserGroupServiceApplication.class,args);
    }
}