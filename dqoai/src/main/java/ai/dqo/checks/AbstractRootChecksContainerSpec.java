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
import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Base abstract class for check container node.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractRootChecksContainerSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractRootChecksContainerSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
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
     * Returns the type of checks (profiling, recurring, partitioned).
     * @return Check type.
     */
    @JsonIgnore
    public abstract CheckType getCheckType();

    /**
     * Returns the time scale for recurring and partitioned checks (daily, monthly, etc.).
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
     * @return Recurring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @JsonIgnore
    public abstract CheckRunRecurringScheduleGroup getSchedulingGroup();

    /**
     * Checks if there are any configured checks (not null) in any check category.
     * @return True when there are some checks configured, false when all checks are nulls.
     */
    public boolean hasAnyConfiguredChecks() {
        for (ChildFieldEntry childFieldEntry : this.getChildMap().getChildEntries()) {
            HierarchyNode childNode = childFieldEntry.getGetChildFunc().apply(this);

            if (childNode instanceof AbstractCheckCategorySpec) {
                AbstractCheckCategorySpec checkCategorySpec = (AbstractCheckCategorySpec)childNode;
                if (checkCategorySpec.hasAnyConfiguredChecks()) {
                    return true;
                }
            }
        }

        return false;
    }
}
