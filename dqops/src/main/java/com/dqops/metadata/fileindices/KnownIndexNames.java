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
