package com.ycyw.graphql.scalar;

import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.netflix.graphql.dgs.DgsComponent;

import graphql.scalars.ExtendedScalars;

/**
 * Add Date and DateTime extended scalars
 */
@DgsComponent
public class DateTimeScalar {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime);
    }
}