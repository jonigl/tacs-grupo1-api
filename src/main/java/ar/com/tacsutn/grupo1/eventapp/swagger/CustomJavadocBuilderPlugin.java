package ar.com.tacsutn.grupo1.eventapp.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.javadoc.plugin.JavadocBuilderPlugin;

@Component
public class CustomJavadocBuilderPlugin extends JavadocBuilderPlugin {

    private static final String PERIOD = ".";
    private final Environment environment;

    @Autowired
    public CustomJavadocBuilderPlugin(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void apply(OperationContext context) {
        super.apply(context);

        String notes = context.requestMappingPattern() + PERIOD + context.httpMethod().toString() + ".notes";
        if (StringUtils.hasText(notes) && StringUtils.hasText(this.environment.getProperty(notes))) {
            String text = environment.getProperty(notes);
            context.operationBuilder().notes("<b>" + text + "</b>");
            context.operationBuilder().summary("");
        }

        String returnDescription = context.requestMappingPattern() + "." + context.httpMethod().toString() + ".return";
        if (StringUtils.hasText(returnDescription)
                && StringUtils.hasText(this.environment.getProperty(returnDescription))) {
            String text = environment.getProperty(returnDescription);
            context.operationBuilder().summary("returns " + text);
        } else {
            context.operationBuilder().summary("");
        }

        context.operationBuilder().codegenMethodNameStem(context.httpMethod().toString() + context.requestMappingPattern());
    }
}
