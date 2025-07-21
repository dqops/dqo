/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.cli.commands.check.impl.models;

import com.dqops.metadata.search.CheckSearchFilters;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * Parameter object for creating pruned patch trees of all checks that fit the filters.
 */
@Data
public class AllChecksModelCliPatchParameters {
    @NotNull
    CheckSearchFilters checkSearchFilters;

    boolean overrideConflicts;

    boolean disableWarningLevel;
    boolean disableErrorLevel;
    boolean disableFatalLevel;

    Map<String, String> sensorOptions;

    Map<String, String> warningLevelOptions;
    Map<String, String> errorLevelOptions;
    Map<String, String> fatalLevelOptions;
}
