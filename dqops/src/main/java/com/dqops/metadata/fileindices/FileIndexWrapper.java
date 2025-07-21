/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.fileindices;

import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;

/**
 * File index spec wrapper.
 */
public interface FileIndexWrapper extends ElementWrapper<FileIndexSpec>, ObjectName<FileIndexName> {
    /**
     * Gets the file index name.
     * @return File index name.
     */
    FileIndexName getIndexName();

    /**
     * Sets a file index name.
     * @param indexName File index name.
     */
    void setIndexName(FileIndexName indexName);
}
