/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

package com.dqops.rest.server.client;

import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.serialization.JsonSerializerImpl;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Helper class that upgrades the Swagger 2.x file generated from DQO REST Api model to Swagger 3.
 */
public class SwaggerFileUpgradeUtility {
    /**
     * Converts a swagger 2 file to swagger 3 file.
     * @param sourcedSwagger2FilePath Path to the Swagger 2.x file (the source file).
     * @param targetSwagger3FilePath Path to the target Swagger 3.x file that should be overwritten with a new content.
     */
    public static void convertSwagger2ToSwagger3(String sourcedSwagger2FilePath, String targetSwagger3FilePath) {
        Path pathToSourceSwaggerFile = Path.of(sourcedSwagger2FilePath);
        Path pathToTargetSwaggerFile = Path.of(targetSwagger3FilePath);

        try {
            OpenAPIParser openAPIParser = new OpenAPIParser();
            String convertedSwaggerContent = Files.readString(pathToSourceSwaggerFile);
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

            ArrayList<String> pathStrings = new ArrayList<>(openApi.getPaths().keySet());
            for (String pathStringOriginal : pathStrings) {
                if (pathStringOriginal.startsWith("/")) {
                    String correctPathString = pathStringOriginal.substring(1);
                    PathItem pathItem = openApi.getPaths().get(pathStringOriginal);
                    openApi.getPaths().remove(pathStringOriginal);
                    openApi.getPaths().put(correctPathString, pathItem);
                }
            }

            JsonSerializerImpl jsonSerializer = new JsonSerializerImpl();
            String upgradedSwaggerContent = jsonSerializer.serializePrettyPrint(openApi);

            Files.writeString(pathToTargetSwaggerFile, upgradedSwaggerContent);
        }
        catch (Exception ex) {
            throw new DqoRuntimeException("Cannot convert Swagger 2 to Swagger 3, error: " + ex.getMessage(), ex);
        }
    }
}
