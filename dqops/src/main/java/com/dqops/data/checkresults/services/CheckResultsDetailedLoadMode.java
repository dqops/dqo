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

package com.dqops.data.checkresults.services;

/**
 * The mode of loading a list of detailed check results: the most recent values for each data group, or all results of the first group.
 */
public enum CheckResultsDetailedLoadMode {
    /**
     * Find the first result from any data group and load results only for that group that was picked.
     */
    first_data_group,

    /**
     * Return the most recent result for each data group, not returning older values.
     */
    most_recent_per_group
}
