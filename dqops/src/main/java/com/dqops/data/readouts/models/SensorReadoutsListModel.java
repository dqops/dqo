/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.models;

import com.dqops.checks.CheckType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Sensor readout detailed results. Returned in the context of a single data group, with a supplied list of other data groups.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class SensorReadoutsListModel {
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

    @JsonPropertyDescription("Sensor name")
    private String sensorName;

    @JsonPropertyDescription("List of data groups that have values for this sensor readout (list of time series)")
    private List<String> dataGroupNames;
    @JsonPropertyDescription("Selected data group")
    private String dataGroup;

    @JsonPropertyDescription("Sensor readout entries")
    private List<SensorReadoutEntryModel> sensorReadoutEntries = new ArrayList<>();
}
