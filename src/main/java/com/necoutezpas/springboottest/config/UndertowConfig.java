package com.necoutezpas.springboottest.config;

import io.undertow.UndertowOptions;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfig {

    @Bean
    public ReactiveWebServerFactory servletContainer() {
        UndertowReactiveWebServerFactory factory = new UndertowReactiveWebServerFactory();
        factory.addBuilderCustomizers((UndertowBuilderCustomizer) builder ->
            builder
                .addHttpListener(8080, "0.0.0.0")
                .setServerOption(UndertowOptions.HTTP2_SETTINGS_MAX_CONCURRENT_STREAMS, 1000));
        return factory;
    }
}
