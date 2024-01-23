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
package com.dqops.metadata.dictionaries;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.ObjectName;
import com.dqops.metadata.basespecs.PojoElementWrapper;

import java.nio.file.Path;

/**
 * Data dictionary file wrapper.
 */
public interface DictionaryWrapper extends PojoElementWrapper<FileContent>, ObjectName<String> {
    /**
     * Gets the file name of the data dictionary file.
     * @return Data dictionary file name.
     */
    String getDictionaryName();

    /**
     * Sets a data dictionary file name.
     * @param dictionaryName Data dictionary name.
     */
    void setDictionaryName(String dictionaryName);

    /**
     * Creates a deep clone of the object.
     * @return Deeply cloned object.
     */
    DictionaryWrapper clone();

    /**
     * Extracts an absolute file path to the data dictionary file. This method returns null if the data dictionary are not stored on the disk, but using an in-memory user home instance.
     * @return Absolute path to the file or null when it is not possible to find the file.
     */
    Path toAbsoluteFilePath();
}
