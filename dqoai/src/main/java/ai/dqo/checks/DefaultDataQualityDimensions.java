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

/**
 * Enumeration of data quality dimension names that are used in checks.
 */
public enum DefaultDataQualityDimensions {
    COMPLETENESS("Completeness"),
    TIMELINESS("Timeliness"),
    CONSISTENCY("Consistency"),
    REASONABLENESS("Reasonableness"),
    ACCURACY("Accuracy"),
    VALIDITY("Validity");

    private final String displayName;

    DefaultDataQualityDimensions(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the data quality dimension that should be stored in parquet.
     * @return Data quality dimension display name.
     */
    public String getDisplayName() {
        return displayName;
    }
}
