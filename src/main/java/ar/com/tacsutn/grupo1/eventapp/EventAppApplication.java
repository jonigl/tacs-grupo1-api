package ar.com.tacsutn.grupo1.eventapp;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableMongoAuditing
@EnableMongoRepositories
@SpringBootApplication
public class EventAppApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(EventAppApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(
            new FileSystemResource("eventbrite.yml"),
            new FileSystemResource("telegram.yml"),
            new FileSystemResource("jwt.yml")
        );
        configurer.setProperties(yaml.getObject());
        return configurer;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // https://stackoverflow.com/a/47570835/2618907
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }

}
