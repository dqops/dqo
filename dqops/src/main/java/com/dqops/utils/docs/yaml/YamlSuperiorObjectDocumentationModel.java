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
package com.dqops.utils.docs.yaml;

import com.dqops.utils.docs.TypeModel;
import lombok.Data;

import java.util.List;

/**
 * Yaml superior class description model. Contains info about superior class and the objects it comprises.
 */
@Data
public class YamlSuperiorObjectDocumentationModel {
    /**
     * Superior class full name.
     */
    private String superiorClassFullName;
    /**
     * Superior class simple name.
     */
    private String superiorClassSimpleName;
    /**
     * Superior class file path.
     */
    private String superiorClassFilePath;
    /**
     * Superior class.
     */
    private Class<?> reflectedSuperiorClass;
    /**
     * Superior class data-type.
     */
    private TypeModel reflectedSuperiorDataType;

    /**
     * List of all superior class fields.
     */
    private List<YamlObjectDocumentationModel> classObjects;

    public String getLocationFilePath() {
        if (this.superiorClassFilePath == null) {
            return null;
        }

        return this.superiorClassFilePath + ".md";
    }

}
