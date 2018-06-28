package it.unibo.bls.spring.buttonService;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class MainButtonService {
    // NOTE: launch with -Djava.awt.headless=false
    public static void main(String[] args){
        new SpringApplicationBuilder().sources(MainButtonService.class).profiles("button").run(args);
    }
}
