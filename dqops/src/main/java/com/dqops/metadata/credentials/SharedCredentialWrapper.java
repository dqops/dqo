/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.credentials;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.ObjectName;
import com.dqops.metadata.basespecs.PojoElementWrapper;

import java.nio.file.Path;

/**
 * Credential file wrapper.
 */
public interface SharedCredentialWrapper extends PojoElementWrapper<FileContent>, ObjectName<String> {
    /**
     * Gets the file name of the credential file.
     * @return Credential file name.
     */
    String getCredentialName();

    /**
     * Sets a credential file name.
     * @param credentialName Shared credential name.
     */
    void setCredentialName(String credentialName);

    /**
     * Creates a deep clone of the object.
     * @return Deeply cloned object.
     */
    SharedCredentialWrapper clone();

    /**
     * Extracts an absolute file path to the credential file. This method returns null if the credentials are not stored on the disk, but using an in-memory user home instance.
     * @return Absolut path to the file or null when it is not possible to find the file.
     */
    Path toAbsoluteFilePath();
}
