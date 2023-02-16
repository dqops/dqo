/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.sampledata;

import ai.dqo.connectors.ProviderType;

import java.util.HashMap;
import java.util.Map;

/**
 * Object mother that creates sample tables for integration tests.
 */
public class IntegrationTestSampleDataForeignObjectMother {
    private static final Map<ProviderType, ProviderTestDataProxy> providerTestData = new HashMap<>();

    /**
     * Checks if a table exist on the target connection. Creates and loads the data into the table on the first call.
     * @param sampleTableMetadataForeign Metadata and data of the sample table to make.
     * @return Provided sample table metadata or a cached copy if a table with the same name is already present in the target database.
     */
    public static SampleTableMetadataForeign ensureForeignTableExists(SampleTableMetadataForeign sampleTableMetadataForeign) {
        ProviderType providerType = sampleTableMetadataForeign.getConnectionSpec().getProviderType();
        ProviderTestDataProxy providerTestDataProxy = providerTestData.get(providerType);
        if (providerTestDataProxy == null) {
            providerTestDataProxy = new ProviderTestDataProxy(providerType);
            providerTestData.put(providerType, providerTestDataProxy);
        }

        providerTestDataProxy.ensureForeignTableExists(sampleTableMetadataForeign);
        return sampleTableMetadataForeign;
    }
}
