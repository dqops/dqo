/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors;

import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Object mother for ProviderDialectSettings.
 */
public class ProviderDialectSettingsObjectMother {
    /**
     * Retrieves the default provider dialect settings for a given provider type.
     * @param providerType Provider type.
     * @return Dialect settings.
     */
    public static ProviderDialectSettings getDialectForProvider(ProviderType providerType) {
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(providerType);
        ConnectionSpec connectionSpec = new ConnectionSpec(providerType);
        ProviderDialectSettings dialectSettings = connectionProvider.getDialectSettings(connectionSpec);
        return dialectSettings;
    }
}
