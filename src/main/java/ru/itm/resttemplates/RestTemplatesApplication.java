package ru.itm.resttemplates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestTemplatesApplication {
    
    public static void main(String[] args) {
        var context = SpringApplication.run(RestTemplatesApplication.class, args);
        RestConnection restConnection = context.getBean(RestConnection.class);
        System.out.println("Result: " + restConnection.getResult());
    }
}
