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
package ai.dqo.services.check.mapping.models.table;

import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.services.check.mapping.models.CheckContainerModel;
import ai.dqo.services.check.mapping.models.CheckContainerTypeModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Model containing information related to table-level checks on a table.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableChecksModel", description = "Model containing information related to table-level checks on a table.")
public class TableChecksModel {
    @JsonPropertyDescription("Table name.")
    private String tableName;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored table checks results connected with this table.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    @JsonPropertyDescription("Mapping of check type and timescale to check container on this table.")
    private Map<CheckContainerTypeModel, CheckContainerModel> checkContainers = new HashMap<>();
}
