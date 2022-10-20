package ai.dqo.rest.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Spring WebFlux configuration for serving the static content.
 */
@Configuration
public class StaticResourcesConfiguration implements WebFluxConfigurer {
    /**
     * Registers static resources with the production build of the DQO UI.
     * @param registry Resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = "";
        registry.addResourceHandler(baseUrl + "/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true);
    }

    /**
     * Sets up the default handler for any requests targeted to the root url "/" to be serving the index.html page.
     * @param indexHtml The resource file with the content of index.html
     * @return Routing function.
     */
    @Bean
    public RouterFunction<ServerResponse> indexRouter(@Value("classpath:/static/index.html") final Resource indexHtml) {
        return route(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml));
    }
}
