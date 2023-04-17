package tr.nttdata.poc.minicommerce.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/**"));
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.disable());
        http.headers(headers -> headers.frameOptions().disable());

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        // Allow any origin
        corsConfig.addAllowedHeader("*");
        // Allow any header
        corsConfig.addAllowedMethod("*");
        // Allow any method // Disable CSRF (Cross-Site Request Forgery) protection
        http.csrf().disable().cors().configurationSource(request -> corsConfig).and().authorizeRequests().anyRequest().permitAll();

        return http.build();
    }
}
