package com.dqops.data.incidents.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

// todo: descriptions, json property descriptions in fields
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentCountsModel {

    // Based on first seen date
    int countFromLast24h;

    int countFromLast7days;

    int currentMonthCount;

    LocalDate currentMonthDate;

    int previousMonthCount;

    LocalDate previousMonthDate;

    @JsonIgnore
    ZoneId defaultTimeZoneId;

    public static IncidentCountsModel createInstance(ZoneId defaultTimeZoneId){
        IncidentCountsModel incidentCountsModel = new IncidentCountsModel();
        incidentCountsModel.defaultTimeZoneId = defaultTimeZoneId;
        LocalDate rawLocalDate = Instant.now().atZone(defaultTimeZoneId).toLocalDate();
        incidentCountsModel.currentMonthDate = rawLocalDate.withDayOfMonth(1);
        incidentCountsModel.previousMonthDate = rawLocalDate.withDayOfMonth(1).minusMonths(1);
        return incidentCountsModel;
    }

    public void verifyAddition(Instant occurrenceTime){
        Instant now = Instant.now();
        if(occurrenceTime.isAfter(now.minus(1, ChronoUnit.DAYS))){
            countFromLast24h++;
        }
        if(occurrenceTime.isAfter(now.minus(7, ChronoUnit.DAYS))){
            countFromLast7days++;
        }
        Instant currentMonth = Instant.from(currentMonthDate.atStartOfDay(defaultTimeZoneId));
        LocalDate nextMonthDate = LocalDate.ofInstant(occurrenceTime, defaultTimeZoneId).plusMonths(1);

        if(occurrenceTime.equals(currentMonth) ||
                occurrenceTime.isAfter(currentMonth) &&
                occurrenceTime.isBefore(nextMonthDate.atStartOfDay(defaultTimeZoneId).toInstant())
        ){
            currentMonthCount++;
        }
        Instant previousMonth = Instant.from(previousMonthDate.atStartOfDay(defaultTimeZoneId));
        if(occurrenceTime.equals(previousMonth) ||
                occurrenceTime.isAfter(previousMonth) &&
                occurrenceTime.isBefore(currentMonthDate.atStartOfDay(defaultTimeZoneId).toInstant())
        ){
            previousMonthCount++;
        }
    }

}
