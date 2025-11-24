package com.cryptocraft.backend;

import java.beans.BeanProperty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner testMyCode(EncryptionService service) {
		return args -> {
			try {
                System.out.println("\n\n-------------------------------------");

                // 1. Caesar Test
                System.out.println("1. CAESAR (Shift 1): " + service.encryptCaesar("HAL", 1)); 

                // 2. Substitution Test
                String subKey = "QWERTYUIOPASDFGHJKLZXCVBNM";
                System.out.println("2. SUBST (Hello):    " + service.encryptSubstitution("Hello", subKey));
                
                // 3. Playfair Test
                System.out.println("3. PLAYFAIR:         " + service.playfairEncrypt("BALLOON", "MONARCHY"));
                
                // 4. DES Test
                System.out.println("4. DES (Base64):     " + service.encryptDES("Secrets", "MyPassword"));

                System.out.println("-------------------------------------\n\n");
            } catch (Exception e) {
                System.err.println("Error during encryption tests: " + e.getMessage());
                e.printStackTrace();
            }
		};
	}
}