/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * Base class for a quality rule.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractRuleParametersSpec extends AbstractSpec implements Cloneable {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
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
     * Retrieves the severity level for this class.
     * in fields named: low, medium, high  - and those are the severity levels returned.
     * @return Severity level.
     */
    @JsonIgnore
    public RuleSeverityLevel getSeverityLevel() {
        String severityNameLowerCase = this.getHierarchyId().getLast().toString();
        RuleSeverityLevel ruleSeverityLevel = Enum.valueOf(RuleSeverityLevel.class, severityNameLowerCase);
        return ruleSeverityLevel;
    }

    /**
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     * @return Rule definition name (python module name without .py extension).
     */
    @JsonIgnore
    public abstract String getRuleDefinitionName();

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        return false; // always render
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    public abstract void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck);

    /**
     * Returns the default configuration of rule parameters (additional parameters passed to the rule) that should be published in its .dqorule.yaml configuration file.
     * @return Additional configuration to save.
     */
    @JsonIgnore
    public Map<String, String> getRuleParametersTemplate() {
        return null;
    }
}
