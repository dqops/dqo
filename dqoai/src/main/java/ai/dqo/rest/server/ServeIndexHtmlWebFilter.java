package ai.dqo.rest.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * Web filter that returns the default index.html for all urls that are not rest api calls and not requests
 * to retrieve other static resources (images, etc.). The purpose of this filter is to serve the index.html on all remaining
 * urls, enabling bookmarking all react urls.
 */
@Component
@Order(-2)
public class ServeIndexHtmlWebFilter implements WebFilter {
    private Resource indexHtml;
    private byte[] indexFileBytes;

    /**
     * Dependency injection constructor.
     * @param indexHtml index.html file retrieved from the static resource, will be served for all requests.
     */
    @Autowired
    public ServeIndexHtmlWebFilter(@Value("classpath:/static/index.html") Resource indexHtml) {
        this.indexHtml = indexHtml;
        try {
            try (InputStream inputStream = indexHtml.getInputStream()) {
                this.indexFileBytes = inputStream.readAllBytes();
            }
        }
        catch (IOException ioException) {
            throw new RuntimeException("Failed to retrieve the index.html file from resources");
        }
    }

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
        List<PathContainer.Element> pathElements = pathContainer.elements();

        // if the last element of the path has a dot '.' then we assume that we are retrieving a file, such as logo.png
        if (pathElements.size() == 0 ||
                pathElements.get(pathElements.size() - 1).value().indexOf('.') > 0 ||  // the url points to a file (something.ext), so we will be serving a static resource
                (pathElements.size() >= 2 && Objects.equals("api", pathElements.get(1).value())) ||
                (pathElements.size() >= 4 && Objects.equals("v2", pathElements.get(1).value()) && Objects.equals("api-docs", pathElements.get(3).value())) ||
                (pathElements.size() >= 2 && (Objects.equals("swagger-ui", pathElements.get(1).value()) ||
                        Objects.equals("swagger-resources", pathElements.get(1).value())))) {
            return chain.filter(exchange);
        }

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.TEXT_HTML);
        response.setStatusCode(HttpStatus.OK);
        DataBuffer buffer = response.bufferFactory().wrap(this.indexFileBytes);
        return response.writeWith(Mono.just(buffer));
    }
}
