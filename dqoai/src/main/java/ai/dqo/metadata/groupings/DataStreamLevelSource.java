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
 * Data stream level property source. Specifies how a single level of a data stream value is populated.
 */
public enum DataStreamLevelSource {
    /**
     * The data stream level ID value is hardcoded as a static value (tag). All data quality results are assigned the same, hardcoded data stream level value.
     */
    @JsonProperty("tag")
    TAG,

    /**
     * The data stream level ID value is obtained from the data. An extra GROUP BY [column name] is added to the sensor query.
     */
    @JsonProperty("column_value")
    COLUMN_VALUE
}
