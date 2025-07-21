/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
