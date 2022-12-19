package ai.dqo.data.ruleresults.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Check recent results overview. Returns the highest severity for the last several runs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultsOverviewDataModel {
    @JsonPropertyDescription("Check category name.")
    private String checkCategory;

    @JsonPropertyDescription("Check name.")
    private String checkName;

    @JsonPropertyDescription("Array of time periods for the results, sorted from the oldest to the newest.")
    private LocalDateTime[] timePeriods;

    @JsonPropertyDescription("Array of check severity levels or an error status, indexes with the severity levels match the time periods.")
    private CheckResultStatus[] statuses;

    @JsonPropertyDescription("Array of data stream names. Identifies the data stream with the highest severity or error result.")
    private String[] dataStreams;
}
