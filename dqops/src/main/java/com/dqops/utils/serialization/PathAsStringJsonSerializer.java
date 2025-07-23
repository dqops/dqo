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

import java.io.IOException;
import java.nio.file.Path;

/**
 * Custom JSON serializer that serializes {@link Path} as a string.
 */
public class PathAsStringJsonSerializer extends JsonSerializer<Path> {
    @Override
    public void serialize(Path o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (o == null) {
            return;
        }

        String pathAsLinuxString = o.toString().replace('\\', '/');
        jsonGenerator.writeString(pathAsLinuxString);
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, Path value) {
        return value == null;
    }
}
