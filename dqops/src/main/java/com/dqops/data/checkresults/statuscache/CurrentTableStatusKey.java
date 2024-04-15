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

package com.dqops.data.checkresults.statuscache;

import com.dqops.metadata.sources.PhysicalTableName;
import lombok.Data;

/**
 * A key object that identifies every table. These keys are used in a cache to store the most recent
 * table quality status for each table.
 */
@Data
@lombok.EqualsAndHashCode
@lombok.ToString
public class CurrentTableStatusKey {
    private String dataDomain;
    private String connectionName;
    private PhysicalTableName physicalTableName;

    /**
     * Creates a table status key.
     * @param dataDomain Data domain name.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     */
    public CurrentTableStatusKey(String dataDomain, String connectionName, PhysicalTableName physicalTableName) {
        this.dataDomain = dataDomain;
        this.connectionName = connectionName;
        this.physicalTableName = physicalTableName;
    }
}
