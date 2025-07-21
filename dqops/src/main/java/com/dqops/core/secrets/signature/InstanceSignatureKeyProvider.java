/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.secrets.signature;

/**
 * Service that returns (and generates) a local signature key that is used to sign the API keys.
 */
public interface InstanceSignatureKeyProvider {
    /**
     * Returns an instance signature key. This method will generate and save an instance key if it is missing (it has a side condition).
     *
     * @return Instance signature key.
     */
    byte[] getInstanceSignatureKey();

    /**
     * Invalidates the instance signature key. The key will be retrieved from the configuration again.
     */
    void invalidate();
}
