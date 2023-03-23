package ai.dqo.data.checkresults.services.models;

import ai.dqo.checks.CheckTimeScale;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Lists;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Check recent results overview. Returns the highest severity for the last several runs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultsOverviewDataModel {
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM X", Locale.ROOT);

    @JsonPropertyDescription("Check hash.")
    private long checkHash;

    @JsonPropertyDescription("Check category name.")
    private String checkCategory;

    @JsonPropertyDescription("Check name.")
    private String checkName;

    @JsonPropertyDescription("List of time periods for the results, returned as a local time, sorted from the newest to the oldest.")
    private List<LocalDateTime> timePeriods = new ArrayList<>();

    @JsonPropertyDescription("List of time periods for the results, returned as absolute UTC time.")
    private List<Instant> timePeriodsUtc = new ArrayList<>();

    @JsonPropertyDescription("List of absolute timestamp (UTC) when the check was executed or an error was raised.")
    private List<Instant> executedAtTimestamps = new ArrayList<>();

    @JsonPropertyDescription("List of time periods, sorted descending, returned as a text with a possible time zone.")
    private List<String> timePeriodDisplayTexts = new ArrayList<>();

    @JsonPropertyDescription("List of check severity levels or an error status, indexes with the severity levels match the time periods.")
    private List<CheckResultStatus> statuses = new ArrayList<>();

    @JsonPropertyDescription("List of data stream names. Identifies the data stream with the highest severity or error result.")
    private List<String> dataStreams = new ArrayList<>();

    @JsonPropertyDescription("List of sensor results. Returns the data quality result readout for the data stream with the alert of the higest severity level.")
    private List<Double> results = new ArrayList<>();

    /**
     * Appends a new result. If the time period is not known, a new set of values are added. If the time period matches the most recent
     * time period then the current severity and data stream is replaced if the severity is higher.
     * NOTE: This method must be called in order, adding the most recent time periods first. Following time periods must be descending.
     * @param timePeriod Time period to add.
     * @param timePeriodUtc Time period converted to UTC time (real).
     * @param executedAt Executed at timestamp.
     * @param checkTimeScale Time scale used by the check. For daily checks, the results are truncated.
     * @param severity Check severity.
     * @param actualValue Actual value returned by the sensor.
     * @param dataStreamName Data stream name.
     * @param resultsCount Maximum results count to store. The result is not added if the result count is exceeded.
     */
    public void appendResult(LocalDateTime timePeriod,
                             Instant timePeriodUtc,
                             Instant executedAt,
                             CheckTimeScale checkTimeScale,
                             Integer severity,
                             Double actualValue,
                             String dataStreamName,
                             int resultsCount) {
        assert this.timePeriods.size() == 0 || !this.timePeriods.get(this.timePeriods.size() - 1).isAfter(timePeriod);

        if (this.timePeriods.size() == 0) {
            this.timePeriods.add(timePeriod);
            this.timePeriodsUtc.add(timePeriodUtc);
            this.executedAtTimestamps.add(executedAt);
            this.timePeriodDisplayTexts.add(makeTimePeriodDisplayText(timePeriod, timePeriodUtc, checkTimeScale));
            this.statuses.add(CheckResultStatus.fromSeverity(severity));
            this.dataStreams.add(dataStreamName);
            this.results.add(actualValue);
            return;
        }

        if (this.timePeriodsUtc.get(this.timePeriodsUtc.size() - 1).equals(timePeriodUtc)) {
            if (severity > this.statuses.get(this.statuses.size() - 1).getSeverity()) {
                // another result with a higher severity, replacing the current one, we found a bigger issue
                this.executedAtTimestamps.set(this.statuses.size() - 1, executedAt);
                this.timePeriodDisplayTexts.set(this.statuses.size() - 1, makeTimePeriodDisplayText(timePeriod, timePeriodUtc, checkTimeScale));
                this.statuses.set(this.statuses.size() - 1, CheckResultStatus.fromSeverity(severity));
                this.dataStreams.set(this.dataStreams.size() - 1, dataStreamName);
                this.results.set(this.dataStreams.size() - 1, actualValue);
            }
        }
        else {
            if (this.timePeriods.size() >= resultsCount) {
                return;
            }

            this.timePeriods.add(timePeriod);
            this.timePeriodsUtc.add(timePeriodUtc);
            this.executedAtTimestamps.add(executedAt);
            this.timePeriodDisplayTexts.add(makeTimePeriodDisplayText(timePeriod, timePeriodUtc, checkTimeScale));
            this.statuses.add(CheckResultStatus.fromSeverity(severity));
            this.dataStreams.add(dataStreamName);
            this.results.add(actualValue);
        }
    }

    /**
     * Creates a time period text that is valid text to be shown.
     * @param timePeriod Time period in local time.
     * @param timePeriodUtc Time period as UTC absolute time.
     * @param checkTimeScale Check time scale (daily, etc) how to format the date or datetime.
     * @return Display text that is usable, optionally has a time zone.
     */
    protected String makeTimePeriodDisplayText(LocalDateTime timePeriod, Instant timePeriodUtc, CheckTimeScale checkTimeScale) {
        Instant timePeriodForcedUtc = timePeriod.toInstant(ZoneOffset.UTC);
        long timeOffsetSeconds = timePeriodForcedUtc.getEpochSecond() - timePeriodUtc.getEpochSecond();
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds((int) timeOffsetSeconds);

        if (checkTimeScale != null) {
            switch (checkTimeScale) {
                case daily:
                    return timePeriod.atOffset(zoneOffset).format(DateTimeFormatter.ISO_OFFSET_DATE)
                            .replace("Z", " UTC");

                case monthly:
                    return timePeriod.atOffset(zoneOffset).format(MONTH_FORMATTER)
                            .replace("Z", "UTC");
            }
        }

        return timePeriod.atOffset(zoneOffset).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .replace('T', ' ')
                .replace("Z", " UTC");
    }

    /**
     * Reverses the lists because we are appending the most recent (newest) statuses first, but we want to return the results ordered in chronological order (the most recent is the last in the list).
     */
    public void reverseLists() {
        this.timePeriods = Lists.reverse(this.timePeriods);
        this.timePeriodsUtc = Lists.reverse(this.timePeriodsUtc);
        this.executedAtTimestamps = Lists.reverse(this.executedAtTimestamps);
        this.timePeriodDisplayTexts = Lists.reverse(this.timePeriodDisplayTexts);
        this.statuses = Lists.reverse(this.statuses);
        this.dataStreams = Lists.reverse(this.dataStreams);
        this.results = Lists.reverse(this.results);
    }
}
