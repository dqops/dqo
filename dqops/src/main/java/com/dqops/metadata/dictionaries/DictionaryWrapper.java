/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.dictionaries;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.ObjectName;
import com.dqops.metadata.basespecs.PojoElementWrapper;

import java.nio.file.Path;
import java.util.Set;

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

    /**
     * Parses the data dictionary and returns a flat list of entries.
     * @return A list of dictionary entries.
     */
    Set<String> getDictionaryEntries();
}
