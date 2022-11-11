/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.groupings;

/**
 * Object mother for DimensionMappingSpec.
 */
public class DimensionMappingSpecObjectMother {
    /**
     * Creates a dimension mapping that uses a static, hardcoded value.
     * @param value Static value.
     * @return Dimension mapping.
     */
    public static DimensionMappingSpec createStaticValue(String value) {
        return new DimensionMappingSpec() {{
			setSource(DimensionMappingSource.STATIC_VALUE);
			setStaticValue(value);
        }};
    }

    /**
     * Creates a dimension mapping that uses a column name, so the dimension is dynamic.
     * @param columnName Column name.
     * @return Dimension mapping.
     */
    public static DimensionMappingSpec createColumnMapping(String columnName) {
        return new DimensionMappingSpec() {{
			setSource(DimensionMappingSource.DYNAMIC_FROM_GROUP_BY_COLUMN);
			setColumn(columnName);
        }};
    }
}
