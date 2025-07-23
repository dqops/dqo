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

import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Simple model that returns a list of connections and a number of open (new) data quality incidents per connection.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentsPerConnectionModel {
    /**
     * Connection (data source) name.
     */
    @JsonPropertyDescription("Connection (data source) name.")
    private String connection;

    /**
     * Count of open (new) data quality incidents.
     */
    @JsonPropertyDescription("Count of open (new) data quality incidents.")
    private int openIncidents;

    /**
     * The UTC timestamp when the most recent data quality incident was first seen.
     */
    @JsonPropertyDescription("The UTC timestamp when the most recent data quality incident was first seen.")
    private Instant mostRecentFirstSeen;

    /**
     * Sample factory for an incident model.
     */
    public static class IncidentsPerConnectionModelSampleFactory implements SampleValueFactory<IncidentsPerConnectionModel> {
        @Override
        public IncidentsPerConnectionModel createSample() {
            return new IncidentsPerConnectionModel() {{
                setConnection("datalake");
                setOpenIncidents(40);
                setMostRecentFirstSeen(LocalDateTime.of(2024, 06, 01, 11, 45, 22, 0).toInstant(ZoneOffset.UTC));
            }};
        }
    }
}
