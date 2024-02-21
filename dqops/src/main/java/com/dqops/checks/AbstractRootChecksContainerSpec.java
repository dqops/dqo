/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.checks;

import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.custom.CustomCategoryCheckSpecMap;
import com.dqops.checks.custom.CustomCheckSpec;
import com.dqops.checks.custom.CustomCheckSpecMap;
import com.dqops.checks.table.checkspecs.comparison.TableComparisonColumnCountMatchCheckSpec;
import com.dqops.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.*;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.Objects;

/**
 * Base abstract class for check container node.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractRootChecksContainerSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractRootChecksContainerSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("custom", o -> o.custom);
        }
    };

    @JsonPropertyDescription("Dictionary of custom checks. The keys are check names within this category.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CustomCheckSpecMap custom;

    /**
     * Returns a dictionary of custom checks.
     * @return Dictionary of custom checks.
     */
    public CustomCheckSpecMap getCustom() {
        return custom;
    }

    /**
     * Set a dictionary of custom checks.
     * @param custom Dictionary of custom checks.
     */
    public void setCustom(CustomCheckSpecMap custom) {
        this.setDirtyIf(!Objects.equals(this.custom, custom));
        this.custom = custom;
        propagateHierarchyIdToField(custom, "custom");
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
     * Returns the type of checks (profiling, monitoring, partitioned).
     * @return Check type.
     */
    @JsonIgnore
    public abstract CheckType getCheckType();

    /**
     * Returns the time scale for monitoring and partitioned checks (daily, monthly, etc.).
     * Profiling checks do not have a time scale and return null.
     * @return Time scale (daily, monthly, ...).
     */
    @JsonIgnore
    public abstract CheckTimeScale getCheckTimeScale();

    /**
     * Returns the check target, where the check could be applied.
     * @return Check target, "table" or "column".
     */
    @JsonIgnore
    public abstract CheckTarget getCheckTarget();

    /**
     * Returns the name of the cron expression that is used to schedule checks in this check root object.
     * @return Monitoring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @JsonIgnore
    public abstract CheckRunScheduleGroup getSchedulingGroup();

    /**
     * Returns the comparisons container for table comparison checks, indexed by the reference table configuration name.
     * @return Table comparison container.
     */
    public abstract AbstractComparisonCheckCategorySpecMap<?> getComparisons();

    /**
     * Returns time series configuration for the given group of checks.
     * @param tableSpec Parent table specification - used to get the details about the time partitioning column.
     * @return Time series configuration.
     */
    public abstract TimeSeriesConfigurationSpec getTimeSeriesConfiguration(TableSpec tableSpec);

    /**
     * Checks if there are any configured checks (not null) in any check category.
     * @return True when there are some checks configured, false when all checks are nulls.
     */
    public boolean hasAnyConfiguredChecks() {
        for (ChildFieldEntry childFieldEntry : this.getChildMap().getChildEntries()) {
            HierarchyNode childNode = childFieldEntry.getGetChildFunc().apply(this);

            if (this.getComparisons() != null) {
                for (Object value : this.getComparisons().values()) {
                    if (value instanceof AbstractCheckCategorySpec) {
                        AbstractCheckCategorySpec checkCategorySpec = (AbstractCheckCategorySpec) value;
                        if (checkCategorySpec.hasAnyConfiguredChecks()) {
                            return true;
                        }
                    }
                }
            }
            if (childNode instanceof AbstractCheckCategorySpec) {
                AbstractCheckCategorySpec checkCategorySpec = (AbstractCheckCategorySpec)childNode;
                if (checkCategorySpec.hasAnyConfiguredChecks()) {
                    return true;
                }
            }
        }

        if (this.custom != null && this.custom.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * Copies all configured data quality checks from this root checks container (a container of default checks) to the target container.
     * @param targetChecksContainer Target root checks container to copy the definitions.
     * @param columnDataTypeCategory Detected data type of a column, if we are applying it on a column.
     * @param dialectSettings Dialect settings.
     */
    public void copyChecksToContainer(AbstractRootChecksContainerSpec targetChecksContainer,
                                      DataTypeCategory columnDataTypeCategory,
                                      ProviderDialectSettings dialectSettings) {
        if (this.isDefault()) {
            return;
        }

        ChildHierarchyNodeFieldMap targetContainerChildMap = targetChecksContainer.childMap();

        for (ChildFieldEntry defaultChecksCategoryEntry : this.childMap().getChildEntries()) {
            HierarchyNode defaultCategoryNode = defaultChecksCategoryEntry.getGetChildFunc().apply(this);
            if (defaultCategoryNode instanceof AbstractCheckCategorySpec) {
                AbstractCheckCategorySpec defaultCheckCategory = (AbstractCheckCategorySpec)defaultCategoryNode;

                if (defaultCheckCategory.isDefault()) {
                    continue;
                }

                if (!DataTypeCategory.isDataTypeInList(columnDataTypeCategory, defaultCheckCategory.getSupportedDataTypeCategories())) {
                    continue;
                }

                AbstractCheckCategorySpec targetCategoryContainer =(AbstractCheckCategorySpec)defaultChecksCategoryEntry.getGetChildFunc().apply(targetChecksContainer);;
                if (targetCategoryContainer == null) {
                    FieldInfo targetContainerCategoryFieldInfo = targetContainerChildMap.getReflectionClassInfo().getFieldByYamlName(defaultChecksCategoryEntry.getChildName());
                    targetCategoryContainer = (AbstractCheckCategorySpec)targetContainerCategoryFieldInfo.getFieldValueOrNewObject(targetChecksContainer);
                    targetContainerCategoryFieldInfo.setFieldValue(targetCategoryContainer, targetChecksContainer);
                }

                ChildHierarchyNodeFieldMap targetCategoryChildMap = targetCategoryContainer.childMap();

                for (ChildFieldEntry defaultChecksEntry : defaultCheckCategory.childMap().getChildEntries()) {
                    HierarchyNode defaultCheckNode = defaultChecksEntry.getGetChildFunc().apply(defaultCheckCategory);
                    if (defaultCheckNode instanceof AbstractCheckSpec<?,?,?,?>) {
                        AbstractCheckSpec<?,?,?,?> defaultCheck = (AbstractCheckSpec<?,?,?,?>)defaultCheckNode;

                        Object alreadyConfiguredCheckSpec = defaultChecksEntry.getGetChildFunc().apply(defaultCheckCategory);
                        if (alreadyConfiguredCheckSpec != null) {
                            continue;
                        }

                        AbstractCheckSpec<?,?,?,?> targetCheckCloned = defaultCheck.deepClone();
                        targetCheckCloned.setDefaultCheck(true);
                        FieldInfo targetCategoryCheckFieldInfo = targetCategoryChildMap.getReflectionClassInfo().getFieldByYamlName(defaultChecksEntry.getChildName());
                        targetCategoryCheckFieldInfo.setFieldValue(targetCheckCloned, targetCategoryContainer);
                    }
                }

                CustomCheckSpecMap defaultCategoryCustomChecks = defaultCheckCategory.getCustomChecks();
                if (defaultCategoryCustomChecks != null && !defaultCategoryCustomChecks.isEmpty()) {
                    CustomCategoryCheckSpecMap targetCategoryCustomChecks = targetCategoryContainer.getCustomChecks();
                    if (targetCategoryCustomChecks == null) {
                        targetCategoryCustomChecks = new CustomCategoryCheckSpecMap();
                        targetCategoryContainer.setCustomChecks(targetCategoryCustomChecks);
                    }

                    for (Map.Entry<String, CustomCheckSpec> defaultCategoryCustomCheckKeyValue : defaultCategoryCustomChecks.entrySet()) {
                        String customCheckName = defaultCategoryCustomCheckKeyValue.getKey();
                        if (targetCategoryCustomChecks.containsKey(customCheckName)) {
                            continue;
                        }

                        CustomCheckSpec clonedTargetCustomCheck = (CustomCheckSpec)defaultCategoryCustomCheckKeyValue.getValue().deepClone();
                        clonedTargetCustomCheck.setDefaultCheck(true);
                        targetCategoryCustomChecks.put(customCheckName, clonedTargetCustomCheck);
                    }
                }
            }
        }

        CustomCheckSpecMap defaultCustomChecks = this.getCustom();
        if (defaultCustomChecks != null && !defaultCustomChecks.isEmpty()) {
            CustomCheckSpecMap targetCustomChecks = targetChecksContainer.getCustom();
            if (targetCustomChecks == null) {
                targetCustomChecks = new CustomCheckSpecMap();
                targetChecksContainer.setCustom(targetCustomChecks);
            }

            for (Map.Entry<String, CustomCheckSpec> defaultCustomCheckKeyValue : defaultCustomChecks.entrySet()) {
                String customCheckName = defaultCustomCheckKeyValue.getKey();
                if (targetCustomChecks.containsKey(customCheckName)) {
                    continue;
                }

                CustomCheckSpec clonedTargetCustomCheck = (CustomCheckSpec)defaultCustomCheckKeyValue.getValue().deepClone();
                clonedTargetCustomCheck.setDefaultCheck(true);
                targetCustomChecks.put(customCheckName, clonedTargetCustomCheck);
            }
        }
    }
}
