/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.secrets;

import java.io.Closeable;
import java.io.IOException;

/**
 * Thread local storage access to the current {@link SecretValueLookupContext}.
 */
public final class CurrentSecretValueLookupContext implements Closeable {
    private SecretValueLookupContext secretValueLookupContext;
    private static final ThreadLocal<CurrentSecretValueLookupContext> threadLocalLookupContext = new ThreadLocal<>();

    /**
     * Creates an instance of the lookup context in the current thread.
     * @param secretValueLookupContext Secret value lookup context to store.
     */
    private CurrentSecretValueLookupContext(SecretValueLookupContext secretValueLookupContext) {
        this.secretValueLookupContext = secretValueLookupContext;
    }

    /**
     * Returns the secret value lookup context stored by this thread.
     * @return Secret value context.
     */
    public SecretValueLookupContext getSecretValueLookupContext() {
        return secretValueLookupContext;
    }

    /**
     * Stores the secret value lookup context in the current thread (using the thread local storage).
     * @param secretValueLookupContext Secret value lookup context to store.
     * @return Secret value lookup context holder.
     */
    public static CurrentSecretValueLookupContext storeLookupContext(SecretValueLookupContext secretValueLookupContext) {
        CurrentSecretValueLookupContext currentLookupContextHolder = new CurrentSecretValueLookupContext(secretValueLookupContext);
        threadLocalLookupContext.set(currentLookupContextHolder);
        return currentLookupContextHolder;
    }

    /**
     * Releases the object and removes it from the thread local storage.
     */
    @Override
    public void close() throws IOException {
        threadLocalLookupContext.set(null);
    }

    /**
     * Retrieves the current secret value lookup context.
     * @return Current secret value lookup context.
     */
    public static SecretValueLookupContext getCurrentLookupContext() {
        CurrentSecretValueLookupContext currentSecretValueLookupContext = threadLocalLookupContext.get();
        if (currentSecretValueLookupContext != null) {
            return currentSecretValueLookupContext.getSecretValueLookupContext();
        }

        return null;
    }
}
