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
package com.dqops.data.errorsamples.factory;

/**
 * Enumeration of possible error samples collection scopes. "table" - a whole table is analyzed for error samples, "data_groupings" - error samples are collected for each data grouping.
 */
public enum ErrorSamplesDataScope {
    /**
     * The error samples are collected for the whole table.
     */
    table,

    /**
     * The error samples are collected for each data grouping.
     */
    data_group
}
