package dev.kevinyu.service.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RestfulApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {

        String encryptedUser = new BCryptPasswordEncoder().encode("12345678");
        String encryptedAdmin = new BCryptPasswordEncoder().encode("87654321");
        System.out.println("encryptedUser:  " + encryptedUser);
        System.out.println("encryptedAdmin:  " + encryptedAdmin);

        SpringApplication.run(RestfulApplication.class, args);
    }

}
