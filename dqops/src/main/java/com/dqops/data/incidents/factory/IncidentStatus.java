/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.incidents.factory;


import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of the statuses used in the "status" field of the "incidents" table.
 */
public enum IncidentStatus {
    /**
     * It is a new incident that was not reviewed.
     */
    @JsonProperty("open")
    open,

    /**
     * This is an incident that was reviewed and there is a work in progress to resolve the incident.
     * Additional incidents should not be raised until it is resolved.
     */
    @JsonProperty("acknowledged")
    acknowledged,

    /**
     * The incident was resolved. If a new data quality issue is detected, a new incident will be raised.
     */
    @JsonProperty("resolved")
    resolved,

    /**
     * The incident is muted and will not be reported.
     */
    @JsonProperty("muted")
    muted;

    public static class IncidentStatusSampleFactory implements SampleValueFactory<IncidentStatus> {
        @Override
        public IncidentStatus createSample() {
            return open;
        }
    }
}
