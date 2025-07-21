/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks;

import com.dqops.checks.custom.CustomCategoryCheckSpecMap;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildFieldEntry;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Base abstract class for containers of checks for a single category.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractCheckCategorySpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractCheckCategorySpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("custom_checks", o -> o.customChecks);
        }
    };

    @JsonPropertyDescription("Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CustomCategoryCheckSpecMap customChecks;

    /**
     * Returns a dictionary of custom checks.
     * @return Dictionary of custom checks.
     */
    public CustomCategoryCheckSpecMap getCustomChecks() {
        return customChecks;
    }

    /**
     * Set a dictionary of custom checks.
     * @param customChecks Dictionary of custom checks.
     */
    public void setCustomChecks(CustomCategoryCheckSpecMap customChecks) {
        this.setDirtyIf(!Objects.equals(this.customChecks, customChecks));
        this.customChecks = customChecks;
        propagateHierarchyIdToField(customChecks, "custom_checks");
    }

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
     * Checks if there are any configured checks (not null).
     * @return True when there are some checks configured, false when all checks are nulls.
     */
    public boolean hasAnyConfiguredChecks() {
        for (ChildFieldEntry childFieldEntry : this.getChildMap().getChildEntries()) {
            HierarchyNode childNode = childFieldEntry.getGetChildFunc().apply(this);

            if (childNode instanceof AbstractCheckSpec) {
                return true; // check is not null, so there are some configured checks
            }
        }

        return false;
    }

    /**
     * Gets the check target appropriate for all checks in this category.
     * @return Corresponding check target.
     */
    @JsonIgnore
    public abstract CheckTarget getCheckTarget();

    /**
     * Gets the check type appropriate for all checks in this category.
     * @return Corresponding check type.
     */
    @JsonIgnore
    public abstract CheckType getCheckType();

    /**
     * Gets the check timescale appropriate for all checks in this category.
     * @return Corresponding check timescale.
     */
    @JsonIgnore
    public abstract CheckTimeScale getCheckTimeScale();

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     * @return Array of supported data type categories.
     */
    @JsonIgnore
    public abstract DataTypeCategory[] getSupportedDataTypeCategories();
}
