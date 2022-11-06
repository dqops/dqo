package ai.dqo.checks.table.timeliness;

import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.sensors.table.timeliness.TableTimelinessAverageDelaySensorParametersSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Table average delay check that shows the average difference between two columns.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class TableTimelinessAverageDelayCheckSpec extends AbstractCheckDeprecatedSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessAverageDelayCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckDeprecatedSpec.FIELDS) {
        {
            put("parameters", o -> o.parameters);
            put("rules", o -> o.rules);
        }
    };

    @JsonPropertyDescription("Average delay sensor parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessAverageDelaySensorParametersSpec parameters = new TableTimelinessAverageDelaySensorParametersSpec();

    @JsonPropertyDescription("Average delay validation rules at various alert severity levels (thresholds)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessAverageDelayRulesSpec rules = new TableTimelinessAverageDelayRulesSpec();

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    public TableTimelinessAverageDelaySensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(TableTimelinessAverageDelaySensorParametersSpec parameters) {
        this.setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters;
        this.propagateHierarchyIdToField(parameters, "parameters");
    }

    /**
     * Returns rules for the check.
     * @return Rules set for the check.
     */
    public TableTimelinessAverageDelayRulesSpec getRules() {
        return rules;
    }

    /**
     * Sets a rules set for the check.
     * @param rules Rules set.
     */
    public void setRules(TableTimelinessAverageDelayRulesSpec rules) {
        this.setDirtyIf(!Objects.equals(this.rules, rules));
        this.rules = rules;
        this.propagateHierarchyIdToField(rules, "rules");
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Returns the sensor parameters spec object that identifies the sensor definition to use and contains parameters.
     *
     * @return Sensor parameters.
     */
    @Override
    @JsonIgnore
    public AbstractSensorParametersSpec getSensorParameters() {
        return this.parameters;
    }

    /**
     * Returns a rule set for this check.
     *
     * @return Rule set.
     */
    @JsonIgnore
    @Override
    public AbstractRuleSetSpec getRuleSet() {
        return this.rules;
    }
}
