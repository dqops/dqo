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
package ai.dqo.core.synchronization.contract;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.metadata.storage.localfiles.SpecFileNames;

import java.util.Objects;

/**
 * DQO root folders in the dqo use home that may be replicated to a remote file system (uploaded to DQO Cloud or any other cloud).
 * It is also used as a lock scope.
 */
public enum DqoRoot {
    /**
     * Parquet files with the sensor readouts.
     */
    data_sensor_readouts,

    /**
     * Parquet files with the rule evaluation results (alerts and passed data quality checks).
     */
    data_rule_results,

    /**
     * Parquet files with the statistics results (basic profiler results).
     */
    data_statistics,

    /**
     * Parquet files with the errors.
     */
    data_errors,

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
     * Local file indexes.
     */
    _indexes,

    /**
     * Local settings file.
     */
    _settings;

    /**
     * Creates a dqo user home root from the folder path.
     * @param folderPath Folder path.
     * @return DQO root folder or null when locking on the target folder is not supported.
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

        if (Objects.equals(folder1, BuiltInFolderNames.INDEX)) {
            return _indexes;
        }

        if (Objects.equals(folder1, SpecFileNames.SETTINGS_SPEC_FILE_NAME_YAML)) {
            return _settings;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.DATA) && folderPath.size() > 1) {
            String folder2 = folderPath.get(1).getObjectName();
            if (Objects.equals(folder2, BuiltInFolderNames.SENSOR_READOUTS)) {
                return data_sensor_readouts;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.RULE_RESULTS)) {
                return data_rule_results;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.STATISTICS)) {
                return data_statistics;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.ERRORS)) {
                return data_errors;
            }
        }

        return null; // unknown folder, not
    }
}
