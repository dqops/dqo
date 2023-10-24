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
