package ecommerce_backend;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import javax.swing.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EcommerceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackendApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationStartupInfo(Environment environment) {
        return args -> {
            System.out.println("=".repeat(50));
            System.out.println("\u001B[1m\u001B[34mAPPLICATION STATUS\u001B[0m");
            System.out.println("=".repeat(50));
            System.out.println("Spring Boot Version: " + SpringBootVersion.getVersion());
            System.out.println("Java Version: " + JavaVersion.getJavaVersion());
            System.out.println("Database: " + environment.getProperty("spring.database"));
            System.out.println("Active Profiles: " + Arrays.toString(environment.getActiveProfiles()));
            System.out.println("Uptime: " + Date.from(Instant.now()));
            System.out.println("=".repeat(50));
            Runtime runTime=Runtime.getRuntime();
        };
    }
}
// cart to add products one by one  and add into a list of productscarts ,
// send that list of productcart to order and and calcualte total sum ,
// to confirm order let user to login or signup and add adress ,
// if signup confim then login and pay for order
// if payment succesfull then show ordr details else go back ,
// if order confimred create a database to add pending quantity of each product and store ,
// later add scaduale and create a query to update product databse with new numbers products
// USER SHOULD HAVE ORDER HISTORY
