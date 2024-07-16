package com.dqops.data.incidents.models;

import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;

/**
 * Contains the counts for each of severity level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentSeverityLevelCountsModel {

    /**
     * Counts for the warning severity level.
     */
    @JsonPropertyDescription("Counts for the warning severity level.")
    private IncidentCountsModel warningCounts;

    /**
     * Counts for the error severity level.
     */
    @JsonPropertyDescription("Counts for the error severity level.")
    private IncidentCountsModel errorCounts;

    /**
     * Counts for the fatal severity level.
     */
    @JsonPropertyDescription("Counts for the fatal severity level.")
    private IncidentCountsModel fatalCounts;

    /**
     * Creates a new instance of the IncidentSeverityLevelCountsModel setting the defaultTimeZoneId.
     * @param defaultTimeZoneId The default time zone id of the system.
     * @return A new instance of the IncidentSeverityLevelCountsModel.
     */
    public static IncidentSeverityLevelCountsModel createInstance(ZoneId defaultTimeZoneId){
        IncidentSeverityLevelCountsModel incidentSeverityLevelCountsModel = new IncidentSeverityLevelCountsModel();
        incidentSeverityLevelCountsModel.warningCounts = IncidentCountsModel.createInstance(defaultTimeZoneId);
        incidentSeverityLevelCountsModel.errorCounts = IncidentCountsModel.createInstance(defaultTimeZoneId);
        incidentSeverityLevelCountsModel.fatalCounts = IncidentCountsModel.createInstance(defaultTimeZoneId);
        return incidentSeverityLevelCountsModel;
    }

    /**
     * Processes the incident of a given severity that occurred on the specific time to verify the incrementation of inner counts.
     * @param highestSeverity The highest severity value that.
     * @param occurrenceTime The time of occurrence of the incident.
     */
    public void processAddCount(int highestSeverity, Instant occurrenceTime){
        switch(highestSeverity){
            case 1:
                warningCounts.processCountIncrementation(occurrenceTime);
                break;
            case 2:
                errorCounts.processCountIncrementation(occurrenceTime);
                break;
            case 3:
                fatalCounts.processCountIncrementation(occurrenceTime);
                break;
        }
    }

    /**
     * Sample factory for an IncidentSeverityLevelCountsModel.
     */
    public static class IncidentSeverityLevelCountsModelSampleFactory implements SampleValueFactory<IncidentSeverityLevelCountsModel> {
        @Override
        public IncidentSeverityLevelCountsModel createSample() {
            return new IncidentSeverityLevelCountsModel() {{
                setWarningCounts(new IncidentCountsModel.IncidentCountsModelSampleFactory().createSample());
            }};
        }
    }

}
