/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
     * Subfolder name inside the {@link BuiltInFolderNames#DATA} folder where the errors are stored.
     */
    public static final String ERRORS = "errors";

    /**
     * Subfolder name inside the {@link BuiltInFolderNames#DATA} folder where the incidents are stored.
     */
    public static final String INCIDENTS = "incidents";

    /**
     * Local index folder.
     */
    public static final String INDEX = ".index";

    /**
     * Credentials folder to store local credentials that are not stored in the repository, but are synchronized to DQOps cloud.
     */
    public static final String CREDENTIALS = ".credentials";

    /**
     * Local log folder inside the user home that stores logs.
     */
    public static final String LOGS = ".logs";

    /**
     * The folder with additional data domains. Each data domain is a subfolder under the "domains" folder,
     * which is a new DQOps user home folder, but without nested data domains.
     */
    public static final String DATA_DOMAINS = "domains";
}
