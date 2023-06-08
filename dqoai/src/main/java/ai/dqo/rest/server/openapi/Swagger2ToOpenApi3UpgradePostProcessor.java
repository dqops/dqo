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

import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.utils.serialization.YamlSerializerImpl;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Post processes a swagger file, upgrading it to swagger 3.
 */
public class Swagger2ToOpenApi3UpgradePostProcessor {
    /**
     * Main method of the build operation that upgrades swagger 2 file to swagger 3 file.
     * @param args Command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Swagger 2 to OpenAPI 3 upgrade utility");
            System.out.println("Missing required parameter: <path to the swagger yaml file that will be upgraded in place>");
            return;
        }

        Path pathToSwaggerFile = Path.of(args[0]);
        OpenAPIParser openAPIParser = new OpenAPIParser();
        String originalSwaggerContentAsString = Files.readString(pathToSwaggerFile);
        SwaggerParseResult swaggerParseResult = openAPIParser.readContents(originalSwaggerContentAsString, null, null);
        if (swaggerParseResult == null || swaggerParseResult.getOpenAPI() == null) {
            String message = "File " + pathToSwaggerFile.toString() + " is invalid and was not parsed.";
            System.err.println(message);
            throw new RuntimeException(message);
        }

        OpenAPI openApi = swaggerParseResult.getOpenAPI();
        YamlSerializerImpl yamlSerializer = new YamlSerializerImpl(new DqoConfigurationProperties());
        String upgradedSwaggerContent = yamlSerializer.serialize(openApi);

        Files.writeString(pathToSwaggerFile, upgradedSwaggerContent);
    }
}
