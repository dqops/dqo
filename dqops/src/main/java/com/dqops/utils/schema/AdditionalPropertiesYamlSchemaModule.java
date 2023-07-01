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

package com.dqops.utils.schema;

import com.github.victools.jsonschema.generator.Module;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;

/**
 * Custom module for com.github.gictools json schema generator. Enables "additionalProperties" for custom sensor parameters and custom rule parameters.
 */
public class AdditionalPropertiesYamlSchemaModule implements Module {
    @Override
    public void applyToConfigBuilder(SchemaGeneratorConfigBuilder schemaGeneratorConfigBuilder) {
        schemaGeneratorConfigBuilder.forTypesInGeneral()
                .withAdditionalPropertiesResolver((scope) -> {
                    JsonAdditionalProperties jsonAdditionalProperties = scope.getType().getErasedType().getAnnotation(JsonAdditionalProperties.class);
                    if (jsonAdditionalProperties != null && jsonAdditionalProperties.enable()) {
                        return Object.class;
                    }
                    return null;
                });
    }
}
