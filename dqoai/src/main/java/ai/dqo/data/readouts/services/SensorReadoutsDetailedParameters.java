/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.readouts.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;


/**
 * The parameters for retrieving the sensor readouts model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class SensorReadoutsDetailedParameters {
    /**
     * The number of recent readouts to return.
     */
    @JsonPropertyDescription("Number of recent readouts to load.")
    private int readoutsCount = 100;

    /**
     * Name of the data group name for which to get the readouts.
     * If null, the default one is picked.
     */
    @JsonPropertyDescription("Data group name for which to load the readouts.")
    private String dataGroupName = null;

    /**
     * Start month to load. Loads the data for the whole month identified by the date.
     * If null, two most recent months for which the readouts are available are selected.
     */
    @JsonPropertyDescription("Start month to load. Loads the data for the whole month identified by the date.")
    private LocalDate startMonth = null;

    /**
     * End month to load. Loads the data for the whole month identified by the date.
     * If null, two most recent months for which the readouts are available are selected.
     */
    @JsonPropertyDescription("End month to load. Loads the data for the whole month identified by the date.")
    private LocalDate endMonth = null;
}
