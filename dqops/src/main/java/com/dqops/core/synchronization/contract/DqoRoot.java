/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.contract;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.metadata.storage.localfiles.SpecFileNames;

import java.util.Objects;

/**
 * DQOps root folders in the dqo use home that may be replicated to a remote file system (uploaded to DQOps Cloud or any other cloud).
 * It is also used as a lock scope.
 */
public enum DqoRoot {
    /**
     * Parquet files with the sensor readouts.
     */
    data_sensor_readouts,

    /**
     * Parquet files with the check evaluation results (alerts and passed data quality checks).
     */
    data_check_results,

    /**
     * Parquet files with the statistics results (basic profiler results).
     */
    data_statistics,

    /**
     * Parquet files with the execution errors.
     */
    data_errors,

    /**
     * Parquet files with the error samples.
     */
    data_error_samples,

    /**
     * Parquet files with the incidents.
     */
    data_incidents,

    /**
     * Source metadata files (connections, tables).
     */
    sources,

    /**
     * User custom sensor definitions (yaml SQL templates).
     */
    sensors,

    /**
     * User custom rules files (python).
     */
    rules,

    /**
     * User custom check definition files.
     */
    checks,

    /**
     * Settings in the settings/ folder that are synchronized to DQOps Cloud.
     */
    settings,

    /**
     * Shared credentials in the .credentials/ folder that are synchronized to DQOps Cloud.
     */
    credentials,

    /**
     * Data dictionaries (CSV files) in the dictionaries/ folder that are synchronized to DQOps Cloud.
     */
    dictionaries,

    /**
     * Default checks configurations for tables or columns matching a pattern.
     */
    patterns,

    /**
     * Local file indexes.
     */
    _indexes,

    /**
     * Table similarity indexes.
     */
    _indexes_sources,

    /**
     * Local settings file that is not synchronized.
     */
    _local_settings;

    /**
     * Creates a dqo user home root from the folder path.
     * @param folderPath Folder path.
     * @return DQOps root folder or null when locking on the target folder is not supported.
     */
    public static DqoRoot fromHomeFolderPath(HomeFolderPath folderPath) {
        if (folderPath.isEmpty()) {
            return null;
        }

        String folder1 = folderPath.get(0).getObjectName();
        if (Objects.equals(folder1, BuiltInFolderNames.SOURCES)) {
            return sources;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.SENSORS)) {
            return sensors;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.RULES)) {
            return rules;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.CHECKS)) {
            return checks;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.SETTINGS)) {
            return settings;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.CREDENTIALS)) {
            return credentials;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.DICTIONARIES)) {
            return dictionaries;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.PATTERNS)) {
            return patterns;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.INDEX)) {
            String folder2 = folderPath.size() > 1 ? folderPath.get(1).getObjectName() : null;
            if (folder2 == null) {
                return _indexes;
            } else if (Objects.equals(folder2, BuiltInFolderNames.CONNECTION_SIMILARITY_INDEX)) {
                return _indexes_sources;
            }
        }

        if (Objects.equals(folder1, SpecFileNames.LOCAL_SETTINGS_SPEC_FILE_NAME_YAML)) {
            return _local_settings;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.DATA) && folderPath.size() > 1) {
            String folder2 = folderPath.get(1).getObjectName();
            if (Objects.equals(folder2, BuiltInFolderNames.SENSOR_READOUTS)) {
                return data_sensor_readouts;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.CHECK_RESULTS)) {
                return data_check_results;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.STATISTICS)) {
                return data_statistics;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.ERRORS)) {
                return data_errors;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.ERROR_SAMPLES)) {
                return data_error_samples;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.INCIDENTS)) {
                return data_incidents;
            }
        }

        return null; // unknown folder, not
    }

    /**
     * Checks if the given root is a data folder, with a parquet organized table.
     * @return True when it is a table folder, otherwise false.
     */
    public boolean isDataFolder() {
        if (this == data_sensor_readouts ||
            this == data_check_results ||
            this == data_errors ||
            this == data_error_samples ||
            this == data_statistics ||
            this == data_incidents) {
            return true;
        }

        return false;
    }
}
