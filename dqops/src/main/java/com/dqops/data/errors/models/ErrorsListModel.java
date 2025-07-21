/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errors.models;

import com.dqops.checks.CheckType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Error detailed statuses. Returned in the context of a single data group, with a supplied list of other data groups.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class ErrorsListModel {
    @JsonPropertyDescription("Check name")
    private String checkName;
    @JsonPropertyDescription("Check display name")
    private String checkDisplayName;
    @JsonPropertyDescription("Check type")
    private CheckType checkType;
    @JsonPropertyDescription("Check hash")
    private Long checkHash;
    @JsonPropertyDescription("Check category name")
    private String checkCategory;

    @JsonPropertyDescription("Data groups list")
    private List<String> dataGroupsNames;
    @JsonPropertyDescription("Selected data group")
    private String dataGroup;

    @JsonPropertyDescription("Error entries")
    private List<ErrorEntryModel> errorEntries = new ArrayList<>();
}
