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

package com.dqops.services.check.mining;

import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Model that has a proposed configuration of checks on a table and its columns generated by a data quality check mining service.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckMiningProposalModel", description = "Model that has a proposed configuration of checks on a table and its columns generated by a data quality check mining service.")
public class CheckMiningProposalModel {
    /**
     * Proposed configuration of table-level data quality checks, such as volume, timeliness or schema.
     */
    @JsonPropertyDescription("Proposed configuration of table-level data quality checks, such as volume, timeliness or schema.")
    private CheckContainerModel tableChecks;

    /**
     * Dictionary of proposed data quality checks for each column.
     */
    @JsonPropertyDescription("Dictionary of proposed data quality checks for each column.")
    private Map<String, CheckContainerModel> columnChecks = new LinkedHashMap<>();
}