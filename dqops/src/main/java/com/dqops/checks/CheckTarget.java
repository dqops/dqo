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
package com.dqops.checks;

import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of targets where the check is applied. It is one of "table" or "column".
 */
public enum CheckTarget {
    @JsonProperty("table")
    table,  // lowercase name to make swagger work

    @JsonProperty("column")
    column; // lowercase name to make swagger work

    public static class CheckTargetSampleFactory implements SampleValueFactory<CheckTarget> {
        @Override
        public CheckTarget createSample() {
            return column;
        }
    }
}
