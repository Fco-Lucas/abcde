package com.lcsz.abcde.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    static {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing() // evita erro se .env não existir
                .load();

        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}
