package com.ycyw.graphql.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Private an public keys properties for JWT.
 */
@ConfigurationProperties(prefix = "application.security.rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {}
