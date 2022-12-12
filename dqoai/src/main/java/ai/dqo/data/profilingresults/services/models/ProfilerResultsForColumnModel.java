package ai.dqo.data.profilingresults.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.*;

/**
 * Model object with the profiler results for a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class ProfilerResultsForColumnModel {
    public ProfilerResultsForColumnModel() {
    }

    public ProfilerResultsForColumnModel(String columnName) {
        this.columnName = columnName;
    }

    @JsonPropertyDescription("Column name")
    private String columnName;

    @JsonPropertyDescription("List of profiler metrics")
    private Collection<ProfilerMetricModel> metrics = new ArrayList<>();
}
