package com.ricardotravez.cuentamovimientos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API SOFKA CUENTA-MOVIMIENTO", description = "Prueba Tecnica Microservicios", termsOfService = "www.programacion.com/terminos_y_condiciones", version = "1.0.0", contact = @Contact(name = "Ricardo Travez", url = "https://test-ejemplo.com", email = "travezvinueza@gmail.com"), license = @License(name = "Standard Software Use License for programador", url = "www.programacion.com/licence")),
        servers = {@Server(description = "DEV SERVER",
                url = "http://localhost:8081"),
                @Server(description = "PROD SERVER", url = "http://test-ejemplo:8081")})
public class CuentasMovimientosConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
