/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem;

/**
 * Built-in folder names used in the home folder.
 */
public final class BuiltInFolderNames {
    /**
     * Sources folder.
     */
    public static final String SOURCES = "sources";

    /**
     * Sensors folder.
     */
    public static final String SENSORS = "sensors";

    /**
     * Rules folder.
     */
    public static final String RULES = "rules";

    /**
     * Checks folder.
     */
    public static final String CHECKS = "checks";

    /**
     * Folder where configuration files that could be synchronized to the cloud are stored.
     */
    public static final String SETTINGS = "settings";

    /**
     * Local data folder.
     */
    public static final String DATA = ".data";

    /**
     * Subfolder name inside the {@link BuiltInFolderNames#DATA} folder where the sensor readouts are stored.
     */
    public static final String SENSOR_READOUTS = "sensor_readouts";

    /**
     * Subfolder name inside the {@link BuiltInFolderNames#DATA} folder where the rule results are stored.
     */
    public static final String CHECK_RESULTS = "check_results";

    /**
     * Subfolder name inside the {@link BuiltInFolderNames#DATA} folder where the statistics results (basic profile) are stored.
     */
    public static final String STATISTICS = "statistics";

    /**
     * Subfolder name inside the {@link BuiltInFolderNames#DATA} folder where the execution errors are stored.
     */
    public static final String ERRORS = "errors";

    /**
     * Subfolder name inside the {@link BuiltInFolderNames#DATA} folder where the error samples are stored.
     */
    public static final String ERROR_SAMPLES = "error_samples";

    /**
     * Subfolder name inside the {@link BuiltInFolderNames#DATA} folder where the incidents are stored.
     */
    public static final String INCIDENTS = "incidents";

    /**
     * Local index folder.
     */
    public static final String INDEX = ".index";

    /**
     * Local connection similarity folder inside the .index folder.
     */
    public static final String CONNECTION_SIMILARITY_INDEX = SOURCES;

    /**
     * A folder inside the .index folder where time series models can store persisted ML models.
     */
    public static final String TIME_SERIES_PREDICTION_MODELS = "models";

    /**
     * Credentials folder to store local credentials that are not stored in the repository, but are synchronized to DQOps cloud.
     */
    public static final String CREDENTIALS = ".credentials";

    /**
     * Data dictionaries folder to store dictionary CSV files used in some checks.
     */
    public static final String DICTIONARIES = "dictionaries";

    /**
     * Folder with the configuration of default check patterns.
     */
    public static final String PATTERNS = "patterns";

    /**
     * Local log folder inside the user home that stores logs.
     */
    public static final String LOGS = ".logs";

    /**
     * Local user folder to store additional binary files.
     */
    public static final String BIN = "bin";

    /**
     * Local user folder to store additional .jar libraries.
     */
    public static final String JARS = "jars";

    /**
     * The folder with additional data domains. Each data domain is a subfolder under the "domains" folder,
     * which is a new DQOps user home folder, but without nested data domains.
     */
    public static final String DATA_DOMAINS = "domains";
}
