package ar.com.tacsutn.grupo1.eventapp;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableJpaAuditing
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
            new FileSystemResource("telegram.yml")
        );
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}
