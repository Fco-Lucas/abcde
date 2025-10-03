package com.lcsz.abcde;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class AbcdeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbcdeApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(AppProperties props, ImageCompressor compressor) {
        return args -> {
            if (args.length > 0 && "compressor".equalsIgnoreCase(args[0])) {
                File baseDir = new File(props.getBaseImagesPath());
                compressor.processarImagens(baseDir);
            }
        };
    }

}
