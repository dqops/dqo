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
package com.dqops.metadata.fields;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Additional display hints for fields.
 */
public enum DisplayHint {
    /**
     * Show a text area to edit bigger text values.
     */
    @JsonProperty("textarea")
    textarea,

    /**
     * Show a popup with a list of columns to select with checkboxes.
     */
    @JsonProperty("column_names")
    column_names,

    /**
     * Activating this field requires a paid version of DQOps. It is used on the "use_ai" checkbox.
     */
    @JsonProperty("requires_paid_version")
    requires_paid_version
}
