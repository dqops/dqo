/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.dqocloud.apikey;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * API key usage (disposition). Identifies API keys that are general purpose or a single purpose (read only).
 */
public enum CloudDqoApiKeyDisposition {
    @JsonProperty("all")
    ALL_PURPOSES,

    @JsonProperty("ui")
    UI,

    @JsonProperty("fl")
    FILE_EXCHANGE,

    @JsonProperty("qu")
    QUERY
}
