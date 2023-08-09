/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.utils.docs.python.apimodel;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class OpenAPIModel {
    private OpenAPI sourceModel;
    private final Map<String, Set<OperationModel>> controllersMethods = new HashMap<>();
    private final Set<ComponentModel> models = new HashSet<>();

    public static OpenAPIModel fromOpenAPI(OpenAPI openAPI) {
        OpenAPIModel model = new OpenAPIModel();
        model.sourceModel = openAPI;

        for (Tag tag : openAPI.getTags()) {
            model.controllersMethods.put(tag.getName(), new HashSet<>());
        }

        for (Map.Entry<String, PathItem> pathItemEntry: openAPI.getPaths().entrySet()) {
            String path = pathItemEntry.getKey();
            PathItem pathItem = pathItemEntry.getValue();

            for (Map.Entry<PathItem.HttpMethod, Operation> methodOperationEntry : pathItem.readOperationsMap().entrySet()) {
                PathItem.HttpMethod httpMethod = methodOperationEntry.getKey();
                Operation operation = methodOperationEntry.getValue();

                OperationModel operationModel = new OperationModel(operation, httpMethod, path);

                for (String tagName : operation.getTags()) {
                    model.controllersMethods.get(tagName).add(operationModel);
                }
            }
        }

        Collection<ComponentModel> componentModels = openAPI.getComponents().getSchemas().entrySet().stream()
                .map(entry -> new ComponentModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        model.models.addAll(componentModels);
        return model;
    }
}
