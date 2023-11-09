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

package com.dqops.utils.docs;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.reflection.ObjectDataType;
import lombok.Data;

@Data
public class TypeModel {
    /**
     * Field class.
     */
    private Class<?> clazz;
    /**
     * Field class name.
     */
    private String classNameUsedOnTheField;
    /**
     * Field class link.
     */
    private String classUsedOnTheFieldPath;
    /**
     * Field data type.
     */
    private ParameterDataType dataType;
    /**
     * Object field data type.
     */
    private ObjectDataType objectDataType;
    /**
     * Key type
     */
    private TypeModel genericKeyType;
    /**
     * Value type
     */
    private TypeModel genericValueType;
}
