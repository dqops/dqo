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
package ai.dqo.services.check.mapping.models;

import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * UI model that returns the form definition and the form data to edit all data quality checks divided by categories.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UICheckContainerModel", description = "UI model that returns the form definition and the form data to edit all data quality checks divided by categories.")
public class UICheckContainerModel {
    @JsonPropertyDescription("List of all data quality categories that contain data quality checks inside.")
    private List<UIQualityCategoryModel> categories = new ArrayList<>();

    @JsonPropertyDescription("Model of configured schedule enabled on the check container.")
    private UIEffectiveScheduleModel effectiveSchedule;

    @JsonPropertyDescription("State of the effective scheduling on the check container.")
    private UIScheduleEnabledStatus effectiveScheduleEnabledStatus;

    @JsonPropertyDescription("The name of the column that partitioned checks will use for the time period partitioning. Important only for partitioned checks.")
    private String partitionByColumn;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check container")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;
}
