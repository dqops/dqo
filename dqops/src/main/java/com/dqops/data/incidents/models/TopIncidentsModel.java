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

package com.dqops.data.incidents.models;

import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Summary model with the most recent incidents grouped by one attribute (data quality dimension, data quality check category, etc).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class TopIncidentsModel {
    /**
     * Incident grouping used to group the top incidents in the dictionary of top incidents.
     */
    @JsonPropertyDescription("Incident grouping used to group the top incidents in the dictionary of top incidents.")
    private TopIncidentGrouping grouping;

    /**
     * Incident status of the incidents that are returned.
     */
    @JsonPropertyDescription("Incident status of the incidents that are returned.")
    private IncidentStatus status;

    /**
     * Dictionary of the top incidents, grouped by the grouping such as the data quality dimension or a data quality check category.
     * The incidents are sorted by the first seen descending (the most recent first).
     */
    @JsonPropertyDescription("Dictionary of the top incidents, grouped by the grouping such as the data quality dimension or a data quality check category. The incidents are sorted by the first seen descending (the most recent first).")
    private Map<String, List<IncidentModel>> topIncidents = new LinkedHashMap<>();

    // todo
    private IncidentSeverityLevelCountsModel openIncidentSeverityLevelCounts;

    // todo
    private IncidentSeverityLevelCountsModel acknowledgedIncidentSeverityLevelCounts;

    /**
     * Sample factory for an incident model.
     */
    public static class TopIncidentsModelSampleFactory implements SampleValueFactory<TopIncidentsModel> {
        @Override
        public TopIncidentsModel createSample() {
            return new TopIncidentsModel() {{
                setGrouping(TopIncidentGrouping.dimension);
                setStatus(IncidentStatus.open);
                getTopIncidents().put("Completeness", List.of(new IncidentModel.IncidentModelSampleFactory().createSample()));
            }};
        }
    }
}
