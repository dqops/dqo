/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.mysql;

import com.dqops.connectors.DataTypeCategory;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.utils.string.StringCheckUtility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Provider dialect settings that are specific to MySQL.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Component
public class MysqlProviderDialectSettings extends ProviderDialectSettings {
    public MysqlProviderDialectSettings() {
        super("`", "`", "\"\"", false);
    }

    /**
     * Returns the best matching column type for the type snapshot (real column type returned by the database).
     *
     * @param columnTypeSnapshot Column type snapshot.
     * @return Data type category.
     */
    @Override
    public DataTypeCategory detectColumnType(ColumnTypeSnapshotSpec columnTypeSnapshot) {
        if (columnTypeSnapshot == null || columnTypeSnapshot.getColumnType() == null) {
            return null;
        }

        String columnType = columnTypeSnapshot.getColumnType().toLowerCase(Locale.ROOT);
        if (StringCheckUtility.containsAny(columnType, "text")) {
            return DataTypeCategory.text;
        }

        return super.detectColumnType(columnTypeSnapshot);
    }
}
