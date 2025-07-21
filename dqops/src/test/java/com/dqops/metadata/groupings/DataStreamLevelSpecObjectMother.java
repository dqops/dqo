/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.groupings;

/**
 * Object mother for {@link DataGroupingDimensionSpec}.
 */
public class DataStreamLevelSpecObjectMother {
    /**
     * Creates a data stream level mapping that uses a static, hardcoded value (a tag).
     * @param value Tag value.
     * @return Data stream level mapping.
     */
    public static DataGroupingDimensionSpec createTag(String value) {
        return new DataGroupingDimensionSpec() {{
			setSource(DataGroupingDimensionSource.tag);
			setTag(value);
        }};
    }

    /**
     * Creates a data stream mapping that uses a column name, so the data stream level is dynamic.
     * @param columnName Column name.
     * @return Data stream level mapping.
     */
    public static DataGroupingDimensionSpec createColumnMapping(String columnName) {
        return new DataGroupingDimensionSpec() {{
			setSource(DataGroupingDimensionSource.column_value);
			setColumn(columnName);
        }};
    }
}
