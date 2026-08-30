package Todo_List.TodoApp.Config;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**") // Apply to all endpoints in the app
                        .allowedOrigins("http://localhost:4200") // Allow Angular dev server
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Allow all common HTTP verbs
                        .allowedHeaders("*") // Allow all request headers
                        .allowCredentials(true); // Allow cookies/auth tokens if needed later
            }
        };
    }
}
