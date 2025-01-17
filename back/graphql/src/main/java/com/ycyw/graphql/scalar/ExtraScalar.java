package com.ycyw.graphql.scalar;

import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import graphql.scalars.ExtendedScalars;

/**
 * Add extra CountryCode, Date and DateTime extended scalars support
 */
@Component
public class ExtraScalar {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.CountryCode)
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime);
    }
}