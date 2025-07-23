/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.duckdb.fileslisting.azure;

/**
 * Azure specific contestants
 */
public class AzureConstants {

    /**
     * Azure Blob Storage Uri prefix with scheme component.
     */
    public static final String BLOB_STORAGE_URI_PREFIX = "az://";

    /**
     * Azure Data Lake Storage (ADLS) Uri prefix with scheme component.
     */
    public static final String DATA_LAKE_STORAGE_URI_PREFIX = "abfss://";

}
