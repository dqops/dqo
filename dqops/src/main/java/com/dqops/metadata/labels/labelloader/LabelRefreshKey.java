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

import com.dqops.metadata.sources.PhysicalTableName;
import lombok.Data;

/**
 * A key object that a single connection or table that was loaded into the file system cache and a refresh of the labels is pending.
 */
@Data
@lombok.EqualsAndHashCode
@lombok.ToString
public class LabelRefreshKey {
    /**
     * The target that is refreshed.
     */
    private final LabelRefreshTarget target;

    /**
     * Data domain name.
     */
    private final String dataDomain;

    /**
     * Connection name.
     */
    private final String connectionName;

    /**
     * Optional physical table name, when the target is a table.
     */
    private final PhysicalTableName physicalTableName;

    /**
     * Creates a label refresh key.
     * @param target Target to refresh.
     * @param dataDomain Data domain name.
     * @param connectionName Connection name.
     * @param physicalTableName Optional table name when the target is a table.
     */
    public LabelRefreshKey(LabelRefreshTarget target, String dataDomain, String connectionName, PhysicalTableName physicalTableName) {
        this.target = target;
        this.dataDomain = dataDomain;
        this.connectionName = connectionName;
        this.physicalTableName = physicalTableName;
    }
}
