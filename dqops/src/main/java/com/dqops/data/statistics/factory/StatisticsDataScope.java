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
package com.dqops.data.statistics.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of possible statistics scopes. "table" - a whole table was profiled, "data_groupings" - groups of rows were profiled.
 */
public enum StatisticsDataScope {
    /**
     * The statistics (profile) is analyzed for the whole table.
     */
    table,

    /**
     * The statistics (profile) is analyzed for each group of data.
     */
    data_group
}
