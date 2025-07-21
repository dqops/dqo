/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.fileindices;

import com.dqops.core.filesystem.BuiltInFolderNames;

/**
 * Constants with the known index names. Indexes are related to the folders that are indexed by them.
 */
public class KnownIndexNames {
    /**
     * Sources index.
     */
    public static final String SOURCES = BuiltInFolderNames.SOURCES;

    /**
     * Custom rules index.
     */
    public static final String RULES = BuiltInFolderNames.RULES;

    /**
     * Custom checks index.
     */
    public static final String CHECKS = BuiltInFolderNames.CHECKS;

    /**
     * Custom sensors index.
     */
    public static final String SENSORS = BuiltInFolderNames.SENSORS;

    /**
     * Settings index.
     */
    public static final String SETTINGS = BuiltInFolderNames.SETTINGS;

    /**
     * Shared credentials index.
     */
    public static final String CREDENTIALS = BuiltInFolderNames.CREDENTIALS;

    /**
     * Data dictionaries index.
     */
    private static final String DICTIONARIES = BuiltInFolderNames.DICTIONARIES;

    /**
     * Sensor readouts data index.
     */
    public static final String SENSOR_READOUTS = "sensor_readouts";

    /**
     * Data quality rule results data index.
     */
    public static final String RULE_RESULTS = "rule_results";
}
