/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
