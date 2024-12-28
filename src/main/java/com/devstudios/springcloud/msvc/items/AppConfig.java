package com.devstudios.springcloud.msvc.items;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;



@Configuration
public class AppConfig {

    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> customizerCircuitBreaker(){
        return (factory) -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id)
                // .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .circuitBreakerConfig(
                    CircuitBreakerConfig.custom()
                    .slidingWindowSize(10) // limite de peticiones para error
                    .failureRateThreshold(50) // el porcentaje de error
                    .waitDurationInOpenState(Duration.ofSeconds(10)) // segundos en espera
                    .permittedNumberOfCallsInHalfOpenState(5)
                    .slowCallDurationThreshold(Duration.ofSeconds(2L))
                    .slowCallRateThreshold(50) // porcentaje de falla
                    .build()
                )
                .timeLimiterConfig(
                    TimeLimiterConfig.custom()
                    .timeoutDuration(Duration.ofSeconds(4L)) // maximo de segundos de espera
                    .build()
                )
                .build();
        });
    }

}
