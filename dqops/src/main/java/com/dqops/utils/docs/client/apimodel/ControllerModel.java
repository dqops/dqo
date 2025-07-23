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

import com.dqops.utils.docs.client.ComponentReflectionService;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ControllerModel {
    private final String controllerName;
    private final String description;
    private final Class<?> controllerClass;

    private final List<OperationModel> operations = new ArrayList<>();

    private ControllerModel(String controllerName, String description, Class<?> controllerClass) {
        this.controllerName = controllerName;
        this.description = description;
        this.controllerClass = controllerClass;
    }

    public static ControllerModel fromTag(Tag tag, ComponentReflectionService componentReflectionService) {
        return new ControllerModel(tag.getName(),
                tag.getDescription(),
                componentReflectionService.getClassFromClassSimpleName(tag.getName()));
    }
}
