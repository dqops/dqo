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
package ai.dqo.checks;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildFieldEntry;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.rules.custom.CustomRuleThresholdsMap;
import ai.dqo.rules.custom.CustomRuleThresholdsSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for collections of rules for each check. Each check has one matching rule set class with a selection of
 * rules that make sense for that check.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractRuleSetSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractRuleSetSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Returns a list of child rules that are configured and enabled.
     * @return List of rules with at least one enabled threshold level.
     */
    @JsonIgnore
    public List<AbstractRuleThresholdsSpec<?>> getEnabledRules() {
        ArrayList<AbstractRuleThresholdsSpec<?>> thresholdsSpecs = new ArrayList<>();

        for (ChildFieldEntry childFieldEntry : this.getChildMap().getChildEntries()) {
            HierarchyNode childNode = childFieldEntry.getGetChildFunc().apply(this);
            if (childNode instanceof CustomRuleThresholdsMap) {
                CustomRuleThresholdsMap customRulesMap = (CustomRuleThresholdsMap) childNode;
                List<CustomRuleThresholdsSpec> enabledCustomRules = customRulesMap.values()
                        .stream()
                        .filter(rule -> rule.isEnabled())
                        .collect(Collectors.toList());
                thresholdsSpecs.addAll(enabledCustomRules);
                continue;
            }

            if (!(childNode instanceof AbstractRuleThresholdsSpec<?>)) {
                continue;
            }

            AbstractRuleThresholdsSpec<?> ruleThresholdsSpec = (AbstractRuleThresholdsSpec<?>)childNode;
            if (ruleThresholdsSpec.isEnabled()) {
                thresholdsSpecs.add(ruleThresholdsSpec);
            }
        }

        return thresholdsSpecs;
    }
}
