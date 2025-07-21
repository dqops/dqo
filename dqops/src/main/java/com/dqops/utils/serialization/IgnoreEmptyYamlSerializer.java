/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Custom YAML serializer added to columns that return objects that should be not rendered in YAML because when they are empty (only default values),
 * then the rendered value would be like:  node_name: {}  which will make editing YAML much harder (it will become a JSON with JSON rules)
 */
@Slf4j
public class IgnoreEmptyYamlSerializer extends JsonSerializer<YamlNotRenderWhenDefault> {
    @Override
    public void serialize(YamlNotRenderWhenDefault o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (o == null || o.isDefault()) {
            return;
        }

        jsonGenerator.writeObject(o);
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, YamlNotRenderWhenDefault value) {
        try {
            return value == null || value.isDefault();
        }
        catch (Exception ex) {
            log.error("Failed to detect an empty field: " + ex.getMessage(), ex);
            throw ex;
        }
    }
}
