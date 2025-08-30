package uz.tenzorsoft.fetch24.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "FETCH 24",
                version = "1.0",
                description = "API Documentation"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization"
)
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            Parameter languageParameter = new Parameter()
                    .in("header")
                    .name("Accept-Language")
                    .description("Language selection (en, uz, ru)")
                    .example("en")
                    .schema(new io.swagger.v3.oas.models.media.Schema().type("string")._enum(java.util.Arrays.asList("en", "uz", "ru")));

            operation.addParametersItem(languageParameter);
            return operation;
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}