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
package ai.dqo.rest.server.openapi;

import ai.dqo.utils.serialization.JsonSerializerImpl;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Post processes a swagger file, upgrading it to swagger 3.
 */
public class PrepareOpenApiFileForPythonClientPostProcessor {
    /**
     * Main method of the build operation that upgrades swagger 2 file to swagger 3 file.
     * @param args Command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Swagger 2 to OpenAPI 3 upgrade utility");
            System.out.println("Missing required parameter: <path to the source swagger 2 yaml file> <path to the target swagger 3 yaml file>");
            return;
        }

        Path pathToSourceSwaggerFile = Path.of(args[0]);
        Path pathToTargetSwaggerFile = Path.of(args[1]);

        HttpRequest converterRequest = HttpRequest.newBuilder()
                .uri(new URI("https://converter.swagger.io/api/convert"))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(pathToSourceSwaggerFile))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        try {
            HttpResponse<String> convertResponse = httpClient.send(converterRequest, HttpResponse.BodyHandlers.ofString());
            String convertedSwaggerContent = convertResponse.body();

            OpenAPIParser openAPIParser = new OpenAPIParser();
            SwaggerParseResult swaggerParseResult = openAPIParser.readContents(convertedSwaggerContent, null, null);
            if (swaggerParseResult == null || swaggerParseResult.getOpenAPI() == null) {
                String message = "File " + pathToSourceSwaggerFile.toString() + " is invalid and was not parsed.";
                System.err.println(message);
                throw new RuntimeException(message);
            }
            OpenAPI openApi = swaggerParseResult.getOpenAPI();
            openApi.getServers().forEach(server -> {
                server.setUrl("http://localhost:8888/");  // set the url to a local DQO instance as the default
            });

            openApi.getPaths().forEach(((s, pathItem) -> {
                pathItem.readOperations().forEach(operation -> {
                    if (operation.getResponses().size() > 1) {
                        // remove additional responses, so the python client will be simpler and return values will be of a single type

                        Optional<String> firstResponseName = operation.getResponses().keySet().stream().findFirst();
                        ApiResponse firstApiResponse = operation.getResponses().get(firstResponseName.get());
                        operation.getResponses().clear();
                        operation.getResponses().addApiResponse(firstResponseName.get(), firstApiResponse);
                    }
                });
            }));

            JsonSerializerImpl jsonSerializer = new JsonSerializerImpl();
            String upgradedSwaggerContent = jsonSerializer.serializePrettyPrint(openApi);

            Files.writeString(pathToTargetSwaggerFile, upgradedSwaggerContent);
        }
        catch (Exception ex) {
            System.err.println("Cannot convert Swagger 2 to Swagger 3, error: " + ex.getMessage());
        }
    }
}
