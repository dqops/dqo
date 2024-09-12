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

package com.dqops.metadata.settings.domains;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Specification of the local data domain.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class LocalDataDomainSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<LocalDataDomainSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Enables the job scheduler for this domain.")
    private boolean enableScheduler = true;

    /**
     * Returns true when the job scheduler is enabled for this domain.
     * @return Job scheduler is enabled.
     */
    public boolean isEnableScheduler() {
        return enableScheduler;
    }

    /**
     * Sets the parameter to tell if the domain should have a scheduler enabled on startup.
     * @param enableScheduler Enable job scheduler.
     */
    public void setEnableScheduler(boolean enableScheduler) {
        this.setDirtyIf(this.enableScheduler != enableScheduler);
        this.enableScheduler = enableScheduler;
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    @JsonIgnore
    public String getDataDomainName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }

        return hierarchyId.getLast().toString();
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public LocalDataDomainSpec deepClone() {
        LocalDataDomainSpec cloned = (LocalDataDomainSpec) super.deepClone();
        return cloned;
    }
}
