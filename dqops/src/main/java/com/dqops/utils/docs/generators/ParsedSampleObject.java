/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.generators;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.reflection.ClassInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Parsed tree of objects with a nested structure. Helpful when handling serialization.
 */
@Data
public class ParsedSampleObject {
    /**
     * Object that has been parsed.
     */
    private Object source;

    /**
     * Parsed object's data type.
     */
    private ParameterDataType dataType;

    /**
     * Parsed object's class info, if source is a complex object.
     */
    private ClassInfo classInfo;

    /**
     * Elements in a list, if source is a list.
     */
    private List<ParsedSampleObject> listElements;

    /**
     * Elements in a map, if source is a map.
     */
    private Map<String, ParsedSampleObject> mapElements;
}
