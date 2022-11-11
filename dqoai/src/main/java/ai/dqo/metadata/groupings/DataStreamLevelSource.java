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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Dimension mapping source. Specifies how a dimension value is populated.
 */
public enum DimensionMappingSource {
    /**
     * The dimension value is hardcoded as a static value. All data quality results are assigned the same, hardcoded dimension value.
     */
    @JsonProperty("static_value")
    STATIC_VALUE,

    /**
     * The dimension value is obtained from the data. An extra GROUP BY [column name] is added to the sensor query.
     */
    @JsonProperty("dynamic_from_group_by_column")
    DYNAMIC_FROM_GROUP_BY_COLUMN
}
