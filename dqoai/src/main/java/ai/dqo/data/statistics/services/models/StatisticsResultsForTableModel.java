package ai.dqo.data.statistics.services.models;

import ai.dqo.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Model object with the statistics results for a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class StatisticsResultsForTableModel {
    @JsonPropertyDescription("Connection name")
    private String connectionName;

    @JsonPropertyDescription("Physical table name including the schema and table names")
    private PhysicalTableName table;

    @JsonPropertyDescription("List of statistics metrics")
    private Collection<StatisticsMetricModel> metrics = new ArrayList<>();

    @JsonPropertyDescription("Statistics metrics of columns")
    private Map<String, StatisticsResultsForColumnModel> columns = new LinkedHashMap<>();
}
