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

package com.dqops.utils.docs.client.apimodel;

import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.client.ComponentReflectionService;
import com.dqops.utils.docs.client.DocsModelLinkageService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Data;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class OpenAPIModel {
    private OpenAPI sourceModel;
    private final Map<String, List<OperationModel>> controllersMethods = new HashMap<>();
    private final Set<ComponentModel> models = new HashSet<>();

    public static OpenAPIModel fromOpenAPI(OpenAPI openAPI,
                                           LinkageStore<String> linkageStore,
                                           DocsModelLinkageService docsModelLinkageService,
                                           ComponentReflectionService componentReflectionService) {
        OpenAPIModel model = new OpenAPIModel();
        model.sourceModel = openAPI;

        for (Tag tag : openAPI.getTags()) {
            model.controllersMethods.put(tag.getName(), new ArrayList<>());
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

        model.controllersMethods.values().forEach(
                operationModels -> operationModels.sort(Comparator.comparing(o -> o.getOperation().getOperationId()))
        );

        Collection<ComponentModel> componentModels = openAPI.getComponents().getSchemas().entrySet().stream()
                .map(entry -> new ComponentModel(entry.getKey(), entry.getValue(), componentReflectionService.getClassFromClassSimpleName(entry.getKey())))
                .collect(Collectors.toList());

        for (ComponentModel component : componentModels) {
            String componentClassName = component.getClassName();
            if (!linkageStore.containsKey(componentClassName)) {
                Path linkage = docsModelLinkageService.findDocsLinkage(componentClassName);
                if (linkage != null) {
                    linkageStore.put(componentClassName, linkage);
                }
            }

            component.setDocsLink(linkageStore.get(componentClassName));
        }

        model.models.addAll(componentModels);
        return model;
    }
}
