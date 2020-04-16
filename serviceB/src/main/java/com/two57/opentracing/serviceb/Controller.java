package com.two57.opentracing.serviceb;

import feign.Client;
import feign.Feign;
import feign.RequestLine;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.opentracing.TracingClient;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private static final String template = "ServiceB says: %s";
    private ServiceAProxy serviceA;

    @Autowired
    public Controller(Decoder decoder, Encoder encoder, Client client, Tracer tracer) {
        this.serviceA = Feign.builder().client(new TracingClient(client, tracer))
                .encoder(encoder)
                .decoder(decoder)
                .requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
                .target(ServiceAProxy.class, "http://localhost:8080");
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(template, serviceA.greeting());
    }
}

interface ServiceAProxy {
    @RequestLine("GET /greeting")
    String greeting();
}
