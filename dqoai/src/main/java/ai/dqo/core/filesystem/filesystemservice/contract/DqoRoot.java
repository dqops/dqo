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
package ai.dqo.core.filesystem.filesystemservice.contract;

/**
 * DQO root folders in the dqo use home that may be replicated to a remote file system (uploaded to DQO Cloud or any other cloud).
 */
public enum DqoRoot {
    /**
     * Parquet files with the sensor readings.
     */
    DATA_READINGS,

    /**
     * Parquet files with the alerts.
     */
    DATA_ALERTS,

    /**
     * Source metadata files (connections, tables).
     */
    SOURCES,

    /**
     * User custom sensor definitions (yaml SQL templates).
     */
    SENSORS,

    /**
     * User custom rules files (python).
     */
    RULES
}
