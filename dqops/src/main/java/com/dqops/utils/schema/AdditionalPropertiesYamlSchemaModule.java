/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
