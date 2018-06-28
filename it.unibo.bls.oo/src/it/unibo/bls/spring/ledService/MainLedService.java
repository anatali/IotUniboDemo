package it.unibo.bls.spring.ledService;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class MainLedService {
    public static void main(String[] args){
        new SpringApplicationBuilder().sources(MainLedService.class).profiles("led").run(args);
    }
}
