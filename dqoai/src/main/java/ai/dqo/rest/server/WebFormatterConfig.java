package ai.dqo.rest.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Spring reactive (webflux) configuration for the formatters.
 */
@Configuration
@EnableWebFlux
public class WebFormatterConfig implements WebFluxConfigurer {
    /**
     * Configures the default format for date/time as an ISO format.
     * @param registry Formatter registry.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }
}
