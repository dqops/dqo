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
package ai.dqo.metadata.sources;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Table owner information.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableOwnerSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<TableOwnerSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Data steward name")
    private String dataSteward;

    @JsonPropertyDescription("Business application name")
    private String application;

    /**
     * Returns the data steward name.
     * @return Data steward name.
     */
    public String getDataSteward() {
        return dataSteward;
    }

    /**
     * Sets the data steward name.
     * @param dataSteward Data steward name.
     */
    public void setDataSteward(String dataSteward) {
		this.setDirtyIf(!Objects.equals(this.dataSteward, dataSteward));
        this.dataSteward = dataSteward;
    }

    /**
     * Returns the business application name.
     * @return Business application name.
     */
    public String getApplication() {
        return application;
    }

    /**
     * Sets the business application name.
     * @param application Business application name.
     */
    public void setApplication(String application) {
		this.setDirtyIf(!Objects.equals(this.application, application));
        this.application = application;
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
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public TableOwnerSpec deepClone() {
        TableOwnerSpec cloned = (TableOwnerSpec) super.deepClone();
        return cloned;
    }
}
