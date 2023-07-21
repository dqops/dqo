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
package com.dqops.services.check.mapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of possible levels at which a schedule could be configured.
 */
public enum EffectiveScheduleLevelModel {
    /**
     * An enumeration for connection level (base level).
     */
    @JsonProperty("connection")
    connection,

    /**
     * An enumeration for table level override.
     */
    @JsonProperty("table_override")
    table_override,

    /**
     * An enumeration for check level override.
     */
    @JsonProperty("check_override")
    check_override,
}