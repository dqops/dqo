package ai.dqo.rest.server;

import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Web filter that returns the default index.html for all urls that are not rest api calls and not requests
 * to retrieve other static resources (images, etc.). The purpose of this filter is to serve the index.html on all remaining
 * urls, enabling bookmarking all react urls.
 */
@Component
public class ServeIndexHtmlWebFilter implements WebFilter {
    /**
     * Filters http requests and returns a static index.html for all urls that are not request for static files (images) or rest api calls.
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        PathContainer pathContainer = request.getPath();
        String pathValue = pathContainer.value();
        List<PathContainer.Element> pathElements = pathContainer.elements();

        // if the last element of the path has a dot '.' then we assume that we are retrieving a file, such as logo.png
        if (pathValue.startsWith("/api") ||
                pathValue.startsWith("/v2/api-docs") ||
                pathValue.startsWith("/swagger-ui") ||
                pathValue.startsWith("/swagger-resources") ||
                (pathElements.size() > 0 && pathElements.get(pathElements.size() - 1).value().indexOf('.') > 0)  // the url points to a file (something.ext), so we will be serving a static resource
            ) {
            return chain.filter(exchange);
        }

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .path("/index.html")
                        .build())
                .build();

        return chain.filter(mutatedExchange);
    }
}
