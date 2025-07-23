/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
