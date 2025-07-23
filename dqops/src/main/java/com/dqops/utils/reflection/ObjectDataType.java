/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.reflection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of types of objects used in reflections. Indicates how the object should be rendered.
 * Is an extension of {@link com.dqops.metadata.fields.ParameterDataType} when type is "object".
 */
public enum ObjectDataType {
    @JsonProperty("object_type")
    object_type,

    @JsonProperty("list_type")
    list_type,

    @JsonProperty("map_type")
    map_type,
}
