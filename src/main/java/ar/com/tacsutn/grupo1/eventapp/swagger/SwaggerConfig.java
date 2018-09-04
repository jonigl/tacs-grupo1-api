package ar.com.tacsutn.grupo1.eventapp.swagger;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.PathProvider;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(CustomJavadocPluginConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .apiInfo(getApiInfo())
                .pathProvider(getPathProvider());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Event App API Documentation")
                .description("Event App Service RESTful API")
                .version("v1.0.0")
                .build();
    }

    private PathProvider getPathProvider() {
        final String basePath = "/api/v1";
        final String docPath = "/";

        return new AbstractPathProvider() {

            @Override
            protected String applicationPath() {
                return basePath;
            }

            @Override
            protected String getDocumentationPath() {
                return docPath;
            }

            @Override
            public String getOperationPath(String operationPath) {
                UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
                String path = uriComponentsBuilder
                        .path(operationPath.replaceFirst(basePath, ""))
                        .build()
                        .toString();
                return Paths.removeAdjacentForwardSlashes(path);
            }
        };
    }
}
