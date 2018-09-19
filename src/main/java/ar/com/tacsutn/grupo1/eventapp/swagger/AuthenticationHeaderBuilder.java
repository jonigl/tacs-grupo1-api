package ar.com.tacsutn.grupo1.eventapp.swagger;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Collections;
import java.util.List;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class AuthenticationHeaderBuilder implements OperationBuilderPlugin {

    private final String API_LOGIN_ENDPOINT = "/api/v1/login";
    private final String HEADER_PARAMETER_TYPE = "header";
    private final String HEADER_DESCRIPTION = "Authentication token";
    private final String STRING_TYPE = "string";

    private final List<String> PUBLIC_API_MAPPINGS = Collections.singletonList(API_LOGIN_ENDPOINT);

    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.SWAGGER_2.equals(documentationType);
    }

    @Override
    public void apply(OperationContext context) {
        String mapping = context.requestMappingPattern();
        if (!PUBLIC_API_MAPPINGS.contains(mapping)) {
            Parameter parameter = new ParameterBuilder()
                    .parameterType(HEADER_PARAMETER_TYPE)
                    .name("Authorization")
                    .modelRef(new ModelRef(STRING_TYPE))
                    .description(HEADER_DESCRIPTION)
                    .allowMultiple(false)
                    .required(true)
                    .scalarExample("Bearer <TOKEN>")
                    .build();

            context.operationBuilder()
                   .parameters(Collections.singletonList(parameter));
        }
    }
}
