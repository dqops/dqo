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

package com.dqops.metadata.labels.labelloader;

/**
 * Identifies the target object that was modified, and whose labels should be loaded. The choices is a connection and a table.
 */
public enum LabelRefreshTarget {
    /**
     * Labels from a connection YAML object.
     */
    CONNECTION,

    /**
     * Labels from a TableSpec object.
     */
    TABLE
}