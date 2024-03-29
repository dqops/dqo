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
    private final Set<ControllerModel> controllers = new LinkedHashSet<>();
    private final Set<ComponentModel> models = new LinkedHashSet<>();

    public static OpenAPIModel fromOpenAPI(OpenAPI openAPI,
                                           LinkageStore<String> targetLinkage,
                                           LinkageStore<String> linkageStore,
                                           DocsModelLinkageService docsModelLinkageService,
                                           ComponentReflectionService componentReflectionService) {
        String serverPath = openAPI.getServers().get(0).getUrl();
        OpenAPIModel model = new OpenAPIModel();
        model.sourceModel = openAPI;

        Map<String, ControllerModel> controllerModelMap = new LinkedHashMap<>();

        for (Tag tag : openAPI.getTags()) {
            ControllerModel controllerModel = ControllerModel.fromTag(tag, componentReflectionService);
            model.controllers.add(controllerModel);
            controllerModelMap.put(tag.getName(), controllerModel);
        }

        for (Map.Entry<String, PathItem> pathItemEntry: openAPI.getPaths().entrySet()) {
            String path = serverPath + pathItemEntry.getKey();
            PathItem pathItem = pathItemEntry.getValue();

            for (Map.Entry<PathItem.HttpMethod, Operation> methodOperationEntry : pathItem.readOperationsMap().entrySet()) {
                PathItem.HttpMethod httpMethod = methodOperationEntry.getKey();
                Operation operation = methodOperationEntry.getValue();

                OperationModel operationModel = new OperationModel(operation, httpMethod, path);

                for (String tagName : operation.getTags()) {
                    controllerModelMap.get(tagName).getOperations().add(operationModel);
                }
            }
        }

        model.controllers.forEach(
                controllerModel -> controllerModel.getOperations().sort(Comparator.comparing(o -> o.getOperation().getOperationId()))
        );

        Collection<ComponentModel> componentModels = openAPI.getComponents().getSchemas().entrySet().stream()
                .map(entry -> new ComponentModel(entry.getKey(), entry.getValue(), componentReflectionService.getClassFromClassSimpleName(entry.getKey())))
                .collect(Collectors.toList());

        for (ComponentModel component : componentModels) {
            Path docsLink = null;

            String componentClassName = component.getClassName();
            Path linkage = docsModelLinkageService.findDocsLinkage(componentClassName);
            if (linkage != null) {
                linkageStore.put(componentClassName, linkage);
                docsLink = linkage;
            }
            else if (targetLinkage.containsKey(componentClassName)) {
                docsLink = targetLinkage.get(componentClassName);
            }

            component.setDocsLink(docsLink);
        }

        model.models.addAll(componentModels);
        return model;
    }
}
