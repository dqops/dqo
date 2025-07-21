/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
