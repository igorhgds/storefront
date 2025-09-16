package henrique.igor.storefront.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API da Vitrini do E-commerce")
                        .version("1.0")
                        .description("Documentação da API da vitrini do e-commerce"));
    }
}
