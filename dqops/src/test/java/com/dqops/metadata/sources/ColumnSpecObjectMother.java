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
