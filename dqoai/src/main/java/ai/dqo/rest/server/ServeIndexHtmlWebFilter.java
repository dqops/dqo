/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.rest.server;

import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Web filter that returns the default index.html for all urls that are not rest api calls and not requests
 * to retrieve other static resources (images, etc.). The purpose of this filter is to serve the index.html on all remaining
 * urls, enabling bookmarking all react urls.
 */
@Component
public class ServeIndexHtmlWebFilter implements WebFilter {
    private static final Map<String, String> URL_REWRITES = new LinkedHashMap<>() {{
        put("/", "/index.html");
        put("/swagger-ui/", "/swagger-ui/index.html");
        put("/v2/api-docs", "/dqo-ui-swagger.json");
        put("/swagger-ui/swagger-initializer.js", "/swagger-ui-override/swagger-initializer.js");
    }};

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

        if (URL_REWRITES.containsKey(pathValue)) {
            ServerWebExchange rewrittenExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .path(URL_REWRITES.get(pathValue))
                            .build())
                    .build();
            return chain.filter(rewrittenExchange);
        }

        // if the last element of the path has a dot '.' then we assume that we are retrieving a file, such as logo.png
        if (pathValue.startsWith("/api") ||
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
