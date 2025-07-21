/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.userhome;

/**
 * User home context factory object mother that creates a testable user home context factory
 * that uses a predefined user home context (in-memory or file based).
 */
public final class UserHomeContextFactoryObjectMother {
    /**
     * Creates a user home context factory that always returns an in-memory user context.
     * @return Returns an in-memory user context.
     */
    public static UserHomeContextFactory createWithInMemoryContext() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        return new UserHomeContextFactoryStub(inMemoryFileHomeContext);
    }

    /**
     * Creates a user home context factory that creates an empty, file-based user context in a temporary folder.
     * @return Returns a stub factory that returns an empty file based user context.
     */
    public static UserHomeContextFactory createWithEmptyTemporaryContext() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
        return new UserHomeContextFactoryStub(inMemoryFileHomeContext);
    }
}
