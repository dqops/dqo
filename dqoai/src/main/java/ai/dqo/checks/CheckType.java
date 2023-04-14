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
package ai.dqo.checks;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of data quality check types: profiling, recurring, partitioned.
 */
public enum CheckType {
    @JsonProperty("profiling")
    PROFILING("profiling"),

    @JsonProperty("recurring")
    RECURRING("recurring"),

    @JsonProperty("partitioned")
    PARTITIONED("partitioned");

    private final String displayName;

    CheckType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns a lower case display name used for the check. The value is stored in parquet files.
     * @return Lower case display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
