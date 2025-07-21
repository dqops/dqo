/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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

    /**
     * Incident severity level count container for the incident with open status.
     */
    @JsonPropertyDescription("Incident severity level count container for the incident with open status.")
    private IncidentSeverityLevelCountsModel openIncidentSeverityLevelCounts;

    /**
     * Incident severity level count container for the incident with acknowledged status.
     */
    @JsonPropertyDescription("Incident severity level count container for the incident with acknowledged status.")
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
                setOpenIncidentSeverityLevelCounts(new IncidentSeverityLevelCountsModel.IncidentSeverityLevelCountsModelSampleFactory().createSample());
            }};
        }
    }
}
