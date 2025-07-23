/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.dqohome;

/**
 * Creates a dqo come context and loads the dqo home from the file system.
 */
public interface DqoHomeContextFactory {
    /**
     * Opens and returns a shared DQOps user home.
     * @return Dqo home context with an active dqo home model that is backed by the local home file system.
     */
    DqoHomeContext openLocalDqoHome();
}
