/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.dqocloud.apikey;

import com.dqops.core.principal.UserDomainIdentity;
import org.apache.commons.codec.DecoderException;
import org.jetbrains.annotations.NotNull;

/**
 * Service that retrieves the active DQOps Cloud API key for the current user.
 * The api key could be enforced by setting an environment variable DQO_CLOUD_APIKEY or is stored in the settings
 * after the user executed the "login" CLI command.
 */
public interface DqoCloudApiKeyProvider {
    /**
     * Returns the api key for the DQOps Cloud.
     * @param userIdentity User identity, used to find the data domain name for which we need the DQOps Cloud synchronization key.
     * @return DQOps Cloud api key or null when the key was not yet configured.
     */
    DqoCloudApiKey getApiKey(UserDomainIdentity userIdentity);

    /**
     * Checks if the synchronization with DQOps Cloud is intentionally disabled (by running a `dqo cloud sync disable` command), so the returned api key was null,
     * but in fact an api key is present.
     * @param userIdentity User identity, used to find the data domain name for which we need the DQOps Cloud synchronization key.
     * @return True when the api key was intentionally disabled.
     */
    boolean isCloudSynchronizationDisabled(UserDomainIdentity userIdentity);

    /**
     * Invalidates the cached api key.
     */
    void invalidate();

    /**
     * Decodes a given API Key.
     * @param apiKey API key to decode.
     * @return Decoded api key.
     * @throws DecoderException When the api key is invalid.
     */
    @NotNull
    DqoCloudApiKey decodeApiKey(String apiKey);
}
