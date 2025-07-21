/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sampledata;

import com.dqops.connectors.ProviderType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Object mother that creates sample tables for integration tests.
 */
public class IntegrationTestSampleDataObjectMother {
    private static final Map<ProviderType, ProviderTestDataProxy> providerTestData = new HashMap<>();

    /**
     * Checks if a table exist on the target connection. Creates and loads the data into the table on the first call.
     * @param sampleTableMetadata Metadata and data of the sample table to make.
     * @return Provided sample table metadata or a cached copy if a table with the same name is already present in the target database.
     */
    public static SampleTableMetadata ensureTableExists(@NotNull SampleTableMetadata sampleTableMetadata) {
        ProviderType providerType = sampleTableMetadata.getConnectionSpec().getProviderType();
        ProviderTestDataProxy providerTestDataProxy = providerTestData.get(providerType);
        if (providerTestDataProxy == null) {
            providerTestDataProxy = new ProviderTestDataProxy(providerType);
			providerTestData.put(providerType, providerTestDataProxy);
        }

        providerTestDataProxy.ensureTableExists(sampleTableMetadata);
        return sampleTableMetadata;
    }
}
