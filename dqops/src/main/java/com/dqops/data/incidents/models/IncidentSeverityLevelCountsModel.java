package com.dqops.data.incidents.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;

// todo: descriptions, json property descriptions in fields
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentSeverityLevelCountsModel {

    private IncidentCountsModel warningCounts;
    private IncidentCountsModel errorCounts;
    private IncidentCountsModel fatalCounts;

    public static IncidentSeverityLevelCountsModel createInstance(ZoneId defaultTimeZoneId){
        IncidentSeverityLevelCountsModel incidentSeverityLevelCountsModel = new IncidentSeverityLevelCountsModel();
        incidentSeverityLevelCountsModel.warningCounts = IncidentCountsModel.createInstance(defaultTimeZoneId);
        incidentSeverityLevelCountsModel.errorCounts = IncidentCountsModel.createInstance(defaultTimeZoneId);
        incidentSeverityLevelCountsModel.fatalCounts = IncidentCountsModel.createInstance(defaultTimeZoneId);
        return incidentSeverityLevelCountsModel;
    }

    public void processAddCount(int highestSeverity, Instant occurrenceTime){
        switch(highestSeverity){
            case 1:
                warningCounts.verifyAddition(occurrenceTime);
            case 2:
                errorCounts.verifyAddition(occurrenceTime);
            case 3:
                fatalCounts.verifyAddition(occurrenceTime);
        }
    }

}
