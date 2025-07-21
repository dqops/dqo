/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

/**
 * Object mother for ColumnSpec.
 */
public class ColumnSpecObjectMother {
    /**
     * Creates a column spec for a given data type. The column is nullable.
     * @param columnType Column data type.
     * @return Column spec.
     */
    public static ColumnSpec createForType(String columnType) {
        return new ColumnSpec() {{
			setTypeSnapshot(new ColumnTypeSnapshotSpec() {{
				setColumnType(columnType);
				setNullable(true);
            }});
        }};
    }

    /**
     * Creates a column spec for a column type and length. The column is nullable.
     * @param columnType Column type.
     * @param length Column length.
     * @return Column spec.
     */
    public static ColumnSpec createForTypeAndLength(String columnType, int length) {
        return new ColumnSpec() {{
			setTypeSnapshot(new ColumnTypeSnapshotSpec() {{
				setColumnType(columnType);
				setLength(length);
				setNullable(true);
            }});
        }};
    }
}
