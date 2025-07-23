/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client.operations.examples.serialization;

import org.springframework.stereotype.Component;

/**
 * Python serializer.
 */
@Component
public interface PythonSerializer {
    /**
     * Serializes an object as a Python snippet creating a new instance of this object.
     * @param source Source object to be serialized.
     * @return Object serialized as a Python snippet.
     */
    String serialize(Object source);

    /**
     * Serializes an object as a Python snippet creating a new instance of this object, using a pretty print writer.
     * @param source Source object to be serialized.
     * @return Object serialized as a Python snippet, formatted.
     */
     String serializePrettyPrint(Object source);
}
