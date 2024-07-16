package com.dqops.data.incidents.models;

import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Stores numbers of incidents over a specific time period.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentCountsModel {

    /**
     * Number of incidents from the last 24h from now.
     */
    @JsonPropertyDescription("Number of incidents from the last 24h from now.")
    int countFromLast24h;

    /**
     * Number of incidents from the last 7 days, the number of 7 * 24 hours from now.
     */
    @JsonPropertyDescription("Number of incidents from the last 7 days, the number of 7 * 24 hours from now.")
    int countFromLast7days;

    /**
     * Number of incidents from the complete current month.
     */
    @JsonPropertyDescription("Number of incidents from the complete current month.")
    int currentMonthCount;

    /**
     * The first day of the current month date.
     */
    @JsonPropertyDescription("The first day of the current month date.")
    LocalDate currentMonthDate;

    /**
     * Number of incidents from the complete previous month.
     */
    @JsonPropertyDescription("Number of incidents from the complete previous month.")
    int previousMonthCount;

    /**
     * The first day of the previous month date.
     */
    @JsonPropertyDescription("The first day of the previous month date.")
    LocalDate previousMonthDate;

    @JsonIgnore
    ZoneId defaultTimeZoneId;

    /**
     * Creates a new instance of the IncidentCountsModel, setting the defaultTimeZoneId.
     * @param defaultTimeZoneId The default time zone id of the system.
     * @return A new instance of the IncidentCountsModel.
     */
    public static IncidentCountsModel createInstance(ZoneId defaultTimeZoneId){
        IncidentCountsModel incidentCountsModel = new IncidentCountsModel();
        incidentCountsModel.defaultTimeZoneId = defaultTimeZoneId;
        LocalDate rawLocalDate = Instant.now().atZone(defaultTimeZoneId).toLocalDate();
        incidentCountsModel.currentMonthDate = rawLocalDate.withDayOfMonth(1);
        incidentCountsModel.previousMonthDate = rawLocalDate.withDayOfMonth(1).minusMonths(1);
        return incidentCountsModel;
    }

    /**
     * Verifies whether the incident of a given time of occurrence should be added to counts.
     * It increments all counts of that match the incident time.
     * @param occurrenceTime Time of incident occurrence.
     */
    public void processCountIncrementation(Instant occurrenceTime){
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


    /**
     * Sample factory for an IncidentCountsModel.
     */
    public static class IncidentCountsModelSampleFactory implements SampleValueFactory<IncidentCountsModel> {
        @Override
        public IncidentCountsModel createSample() {
            return new IncidentCountsModel() {{
                setCountFromLast24h(3);
                setCountFromLast7days(22);
                setCurrentMonthCount(129);
                setPreviousMonthCount(165);
                setCurrentMonthDate(LocalDate.of(2024, 2, 1));
                setPreviousMonthDate(LocalDate.of(2024, 1, 1));
            }};
        }
    }

}
