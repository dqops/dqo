/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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

    /**
     * The default limit for the maximum number of tables that are imported from a data source by the auto import feature.
     */
    public static final int DEFAULT_TABLE_LIMIT_AUTO_IMPORT = 300;

    private int tablesImportLimit = DEFAULT_TABLE_LIMIT;
    private int autoImportTablesLimit = DEFAULT_TABLE_LIMIT_AUTO_IMPORT;

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
     * Returns the limit of tables imported by the auto import performed on a scheduler.
     * @return Auto import limit.
     */
    public int getAutoImportTablesLimit() {
        return autoImportTablesLimit;
    }

    /**
     * Sets the limit of tables imported by the auto import.
     * @param autoImportTablesLimit Auto import limit.
     */
    public void setAutoImportTablesLimit(int autoImportTablesLimit) {
        this.autoImportTablesLimit = autoImportTablesLimit;
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
