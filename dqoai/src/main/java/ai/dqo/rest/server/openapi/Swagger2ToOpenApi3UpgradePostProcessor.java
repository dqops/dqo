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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

        HttpResponse<String> convertResponse = httpClient.send(converterRequest, HttpResponse.BodyHandlers.ofString());
        String convertedSwaggerContent = convertResponse.body();

        Files.writeString(pathToTargetSwaggerFile, convertedSwaggerContent);
    }
}
