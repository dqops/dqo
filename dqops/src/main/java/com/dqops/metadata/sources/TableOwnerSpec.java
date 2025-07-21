/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
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
