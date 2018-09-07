package ar.com.tacsutn.grupo1.eventapp.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.javadoc.plugin.JavadocBuilderPlugin;

@Configuration
@PropertySource(
        value = {"classpath:/META-INF/springfox.javadoc.properties"},
        ignoreResourceNotFound = true
)
public class CustomJavadocPluginConfiguration {

    private CustomJavadocBuilderPlugin javadocBuilderPlugin;

    @Autowired
    public CustomJavadocPluginConfiguration(CustomJavadocBuilderPlugin javadocBuilderPlugin) {
        this.javadocBuilderPlugin = javadocBuilderPlugin;
    }

    @Bean
    public JavadocBuilderPlugin javadocBuilder() {
        return this.javadocBuilderPlugin;
    }
}
