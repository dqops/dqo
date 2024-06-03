/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration parameters for the configuration of importing remote metadata from data sources.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.metadata.import")
@EqualsAndHashCode(callSuper = false)
public class DqoMetadataImportConfigurationProperties implements Cloneable {
    /**
     * The default limit for the maximum number of tables that are imported from a data source without applying filters.
     */
    public static final int DEFAULT_TABLE_LIMIT = 300;

    private int tablesImportLimit = DEFAULT_TABLE_LIMIT;

    /**
     * Returns the default limit of the number of tables that are imported from a data source.
     * @return Limit of tables that are imported.
     */
    public int getTablesImportLimit() {
        return tablesImportLimit;
    }

    /**
     * Sets the limit of the number of tables that are imported from a data source.
     * @param tablesImportLimit Limit of tables that are imported.
     */
    public void setTablesImportLimit(int tablesImportLimit) {
        this.tablesImportLimit = tablesImportLimit;
    }

    /**
     * Clones the current object.
     * @return Cloned instance.
     */
    @Override
    public DqoMetadataImportConfigurationProperties clone() {
        try {
            return (DqoMetadataImportConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
