package ai.dqo.data.ruleresults.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Lists;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Check recent results overview. Returns the highest severity for the last several runs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultsOverviewDataModel {
    @JsonPropertyDescription("Check hash.")
    private long checkHash;

    @JsonPropertyDescription("Check category name.")
    private String checkCategory;

    @JsonPropertyDescription("Check name.")
    private String checkName;

    @JsonPropertyDescription("List of time periods for the results, sorted from the oldest to the newest.")
    private List<LocalDateTime> timePeriods = new ArrayList<>();

    @JsonPropertyDescription("List of check severity levels or an error status, indexes with the severity levels match the time periods.")
    private List<CheckResultStatus> statuses = new ArrayList<>();

    @JsonPropertyDescription("List of data stream names. Identifies the data stream with the highest severity or error result.")
    private List<String> dataStreams = new ArrayList<>();

    /**
     * Appends a new result. If the time period is not known, a new set of values are added. If the time period matches the most recent
     * time period then the current severity and data stream is replaced if the severity is higher.
     * NOTE: This method must be called in order, adding the most recent time periods first. Following time periods must be descending.
     * @param timePeriod Time period to add.
     * @param severity Check severity.
     * @param dataStreamName Data stream name.
     * @param resultsCount Maximum results count to store. The result is not added if the result count is exceeded.
     */
    public void appendResult(LocalDateTime timePeriod, Integer severity, String dataStreamName, int resultsCount) {
        assert this.timePeriods.size() == 0 || !this.timePeriods.get(this.timePeriods.size() - 1).isAfter(timePeriod);

        if (this.timePeriods.size() == 0) {
            this.timePeriods.add(timePeriod);
            this.statuses.add(CheckResultStatus.fromSeverity(severity));
            this.dataStreams.add(dataStreamName);
            return;
        }

        if (this.timePeriods.get(this.timePeriods.size() - 1).equals(timePeriod)) {
            if (severity > this.statuses.get(this.statuses.size() - 1).getSeverity()) {
                // another result with a higher severity, replacing the current one, we found a bigger issue
                this.statuses.set(this.statuses.size() - 1, CheckResultStatus.fromSeverity(severity));
                this.dataStreams.set(this.dataStreams.size() - 1, dataStreamName);
            }
        }
        else {
            if (this.timePeriods.size() >= resultsCount) {
                return;
            }

            this.timePeriods.add(timePeriod);
            this.statuses.add(CheckResultStatus.fromSeverity(severity));
            this.dataStreams.add(dataStreamName);
        }
    }

    /**
     * Reverses the lists because we are appending the most recent (newest) statuses first, but we want to return the results ordered in chronological order (the most recent is the last in the list).
     */
    public void reverseLists() {
        this.timePeriods = Lists.reverse(this.timePeriods);
        this.statuses = Lists.reverse(this.statuses);
        this.dataStreams = Lists.reverse(this.dataStreams);
    }
}
