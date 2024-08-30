package com.AlTaraf.Booking.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;


@Configuration
public class CorsConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://webwebweb-6e98c.web.app",
                        "https://env-5463350.fin.libyanspider.cloud"
                        )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        corsConfiguration.setAllowedOrigins(allowedOrigins);
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
//        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
//        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
//        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
//        corsConfiguration.addAllowedMethod(HttpMethod.POST);
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
}