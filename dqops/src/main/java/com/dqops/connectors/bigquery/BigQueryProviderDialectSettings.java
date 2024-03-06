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

package com.dqops.connectors.bigquery;

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
 * Provider dialect settings that are specific to BigQuery.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Component
public class BigQueryProviderDialectSettings extends ProviderDialectSettings {
    public BigQueryProviderDialectSettings() {
        super("`", "`", "``", false);
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
        if (StringCheckUtility.containsAny(columnType, "array")) {
            return DataTypeCategory.array;
        }

        if (StringCheckUtility.containsAny(columnType, "text")) {
            return DataTypeCategory.text;
        }

        if (StringCheckUtility.containsAny(columnType, "json")) {
            return DataTypeCategory.json;
        }

        return super.detectColumnType(columnTypeSnapshot);
    }
}
