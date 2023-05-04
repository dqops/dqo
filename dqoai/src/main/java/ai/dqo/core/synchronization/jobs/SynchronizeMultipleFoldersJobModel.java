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
package ai.dqo.core.synchronization.jobs;

import ai.dqo.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Simple object for starting multiple folder synchronization jobs with the same configuration.
 */
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class SynchronizeMultipleFoldersJobModel {
    /**
     * File synchronization direction.
     */
    @JsonPropertyDescription("File synchronization direction, the default is full synchronization (push local changes and pull other changes from DQO Cloud).")
    private FileSynchronizationDirection direction = FileSynchronizationDirection.full;

    @JsonPropertyDescription("Force full refresh of native tables in the data quality data warehouse. The default synchronization mode is to refresh only modified data.")
    private boolean forceRefreshNativeTables;

    /**
     * The synchronization status of the "sources" folder.
     */
    @JsonPropertyDescription("Synchronize the \"sources\" folder.")
    private boolean sources;

    /**
     * Synchronize the "sensors" folder.
     */
    @JsonPropertyDescription("Synchronize the \"sensors\" folder.")
    private boolean sensors;

    /**
     * Synchronize the "rules" folder.
     */
    @JsonPropertyDescription("Synchronize the \"rules\" folder.")
    private boolean rules;

    /**
     * Synchronize the "checks" folder.
     */
    @JsonPropertyDescription("Synchronize the \"checks\" folder.")
    private boolean checks;

    /**
     * Synchronize the ".data/sensor_readouts" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/sensor_readouts\" folder.")
    private boolean dataSensorReadouts;

    /**
     * Synchronize the ".data/check_results" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/check_results\" folder.")
    private boolean dataCheckResults;

    /**
     * Synchronize the ".data/statistics" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/statistics\" folder.")
    private boolean dataStatistics;

    /**
     * Synchronize the ".data/errors" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/errors\" folder.")
    private boolean dataErrors;


    /**
     * Synchronize the ".data/incidents" folder.
     */
    @JsonPropertyDescription("Synchronize the \".data/incidents\" folder.")
    private boolean dataIncidents;
}
