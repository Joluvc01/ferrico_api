package rest.ferrico_api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude ={SecurityAutoConfiguration.class})
public class FerricoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FerricoApiApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info((new Info()
                        .title("Ferrico Api")));
    }


}
