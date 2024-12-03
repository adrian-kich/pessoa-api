package com.adriankich.pessoaapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public static final String SUCCESS_MESSAGE = "Operação bem sucedida";
    public static final String NOT_FOUND_MESSAGE = "Objeto não encontrado com informações enviadas";
    public static final String BAD_REQUEST_MESSAGE = "Dados enviados inválidos, confira documentação";

    @Bean
    OpenAPI apiInfo()
    {
        return new OpenAPI()
                .info( new Info()
                        .title( "API de Pessoas e Endereços" )
                        .description("Projeto backend para gerenciar Pessoas e Endereços.")
                        .contact( new Contact()
                                .name( "Adrian Kich" )
                                .url( "https://github.com/adrian-kich" ))
                        .version("1.0")
                        .license( new License() )
                        .termsOfService("") );
    }
}