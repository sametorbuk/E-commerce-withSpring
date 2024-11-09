package com.teknotik.ecommmerce_backend.config;
import com.teknotik.ecommmerce_backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
// you can always add (debug = true) if you have issues with spring security
@EnableWebSecurity(debug = true)
// it could be a good idea to split into AppSecurityConfig && AppConfig
public class AppConfig {
    private final AuthenticationService authenticationService;

    // example usage of the properties in spring
    // PS more modern way it to use yaml instead of properties
    // you could override it by using ECOM_CORS_DOMAINS="http://example.com,http://example1.com" environment variable in prod
    @Value("${ecom.cors.domains:}")
    String[] corsDomains;

    @Autowired
    public AppConfig(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(authenticationService);
        return new ProviderManager(authenticationProvider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // // https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html -
                // we should use defaults as stated in the docs
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                // goto http://localhost:8080/teknotik/swagger-ui/index.html
                // https://openapi-ts.dev/openapi-fetch/ to generate endpoints automatically in your Next.js app
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.OPTIONS).permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/health").permitAll();
                    auth.requestMatchers("/swagger-ui/*").permitAll();
                    auth.requestMatchers("/swagger-ui").permitAll();
                    auth.requestMatchers("/v3/api-docs").permitAll();
                    auth.requestMatchers("/v3/api-docs/*").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    // https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html
    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("https://e-commerce-project-teknotik.vercel.app", "http://localhost:8000"));
        configuration.setAllowedOrigins(Arrays.stream(corsDomains).toList());
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE"
        ));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}