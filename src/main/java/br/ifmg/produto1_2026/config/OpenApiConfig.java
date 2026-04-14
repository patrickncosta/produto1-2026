package br.ifmg.produto1_2026.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI getOpenAPI(){
        return new OpenAPI().info(
                new Info()
                        .title("IFMG Produtos API")
                        .description("Essa API vai fornecer dados para o frontend de vendas da lojinha " +
                                "do grêmio do IFMG")
                        .version("1.0")
                        .summary("Loja do gremio IFMG")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://formiga.ifmg.edu.br/")
                        )

        );
    }
}
