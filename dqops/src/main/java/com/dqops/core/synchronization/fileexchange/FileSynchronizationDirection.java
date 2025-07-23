/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.fileexchange;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data synchronization direction between a local DQOps Home and DQOps Cloud data quality data warehouse.
 */
public enum FileSynchronizationDirection {
    /**
     * Full synchronization that both uploads local changes to the DQOps Cloud and downloads changes from DQOps Cloud.
     */
    @JsonProperty("full")
    full,

    /**
     * Only download new changes from DQOps Cloud.
     */
    @JsonProperty("download")
    download,

    /**
     * Only upload new local changes to DQOps Cloud.
     */
    @JsonProperty("upload")
    upload
}
