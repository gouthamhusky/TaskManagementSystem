package edu.northeastern.csye.tms.config;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
public class JaegerConfig {

    @Bean
    public Tracer tracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
                .withType("const")
                .withParam(1);

        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                .withLogSpans(true);

        Configuration config = new Configuration("tms-app")
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);

        return config.getTracer();
    }
}

