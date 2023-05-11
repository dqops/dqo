/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.rules.comparison;

import ai.dqo.metadata.fields.SampleValues;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.AbstractRuleParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies that a data quality check readout of a string_datatype_detect (the data type detection) matches an expected data type.
 * The supported values are in the range 1..7, which are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DatatypeEqualsRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<DatatypeEqualsRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Expected data type code, the data type codes are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.")
    @SampleValues(values = "1")
    private Integer expectedDatatype;

    /**
     * Creates the default object that expects a correct data type.
     */
    public DatatypeEqualsRuleParametersSpec() {
    }

    /**
     * Creates the rule, given an expected data type code.
     * @param expectedDatatype Expected data type code (1..7).
     */
    public DatatypeEqualsRuleParametersSpec(Integer expectedDatatype) {
        this.expectedDatatype = expectedDatatype;
    }

    /**
     * Returns the expected data type code.
     * @return Expected data type code.
     */
    public Integer getExpectedDatatype() {
        return expectedDatatype;
    }

    /**
     * Sets the expected data type code.
     * @param expectedDatatype Expected data type code.
     */
    public void setExpectedDatatype(Integer expectedDatatype) {
        this.setDirtyIf(!Objects.equals(this.expectedDatatype, expectedDatatype));
        this.expectedDatatype = expectedDatatype;
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
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     *
     * @return Rule definition name (python module name without .py extension).
     */
    @Override
    public String getRuleDefinitionName() {
        return "comparison/datatype_equals";
    }
}
