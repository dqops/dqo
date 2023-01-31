package ai.dqo.data.statistics.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * Returns a single metric captured by the data statistics collectors (basic profilers).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class StatisticsMetricModel {
    @JsonPropertyDescription("Statistics category")
    private String category;

    @JsonPropertyDescription("Statistics (metric) name")
    private String collector;

    @JsonPropertyDescription("Statistics result for the metric")
    private Object result;
}
