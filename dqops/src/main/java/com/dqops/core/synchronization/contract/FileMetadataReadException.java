/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.contract;

import com.dqops.utils.exceptions.DqoRuntimeException;

import java.nio.file.Path;

/**
 * Exception thrown when it was not possible to read the metadata a file.
 */
public class FileMetadataReadException extends DqoRuntimeException {
    private Path filePath;

    public FileMetadataReadException(Path filePath, String message, Throwable cause) {
        super(message, cause);
        this.filePath = filePath;
    }

    /**
     * Path to a file that was not read.
     * @return File path.
     */
    public Path getFilePath() {
        return filePath;
    }
}
