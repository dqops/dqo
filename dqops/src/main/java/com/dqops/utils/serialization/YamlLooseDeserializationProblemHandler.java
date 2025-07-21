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

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import java.io.IOException;

/**
 * Deserialization problem handler that ignores invalid numbers.
 */
public class YamlLooseDeserializationProblemHandler extends DeserializationProblemHandler {
    @Override
    public Object handleWeirdNumberValue(DeserializationContext ctxt, Class<?> targetType, Number valueToConvert, String failureMsg) throws IOException {
        if (targetType == int.class) {
            return 0;
        }

        if (targetType == long.class) {
            return 0L;
        }

        if (targetType == double.class) {
            return 0.0;
        }

        return null;
    }
}
