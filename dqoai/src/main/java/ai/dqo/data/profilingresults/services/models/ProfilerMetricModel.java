package ai.dqo.data.profilingresults.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * Returns a single metric captured by the data profiler.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class ProfilerMetricModel {
    @JsonPropertyDescription("Profiler category")
    private String category;

    @JsonPropertyDescription("Profiler (metric) name")
    private String profiler;

    @JsonPropertyDescription("Profiler result for the metric")
    private Object result;
}
