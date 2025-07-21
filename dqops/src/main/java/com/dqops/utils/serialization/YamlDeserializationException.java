/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.serialization;

import com.dqops.utils.exceptions.DqoRuntimeException;

import java.nio.file.Path;

/**
 * YAML deserialization exception thrown when the YAML file is invalid.
 */
public class YamlDeserializationException extends DqoRuntimeException {
    private Path yamlFilePath;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public YamlDeserializationException() {
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public YamlDeserializationException(String message, Path yamlFilePath) {
        super(message);
        this.yamlFilePath = yamlFilePath;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public YamlDeserializationException(String message, Path yamlFilePath, Throwable cause) {
        super(message, cause);
        this.yamlFilePath = yamlFilePath;
    }

    /**
     * Returns the path to the YAML file that failed to deserialize.
     * @return Path to the file that failed to deserialize.
     */
    public Path getYamlFilePath() {
        return yamlFilePath;
    }
}
