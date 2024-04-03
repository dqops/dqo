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
package com.dqops.data.readouts.services.models;

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
