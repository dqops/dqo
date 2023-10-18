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
package com.dqops.metadata.definitions.sensors;

import com.dqops.connectors.ProviderType;
import com.dqops.metadata.basespecs.AbstractElementWrapper;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.Objects;

/**
 * Data quality provider specific sensor definition spec wrapper.
 */
public class ProviderSensorDefinitionWrapperImpl extends AbstractElementWrapper<ProviderType, ProviderSensorDefinitionSpec> implements ProviderSensorDefinitionWrapper {
    private static final ChildHierarchyNodeFieldMapImpl<ProviderSensorDefinitionWrapperImpl> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractElementWrapper.FIELDS) {
        {
        }
    };

    @JsonIgnore
    private ProviderType provider;
    @JsonIgnore
    private String sqlTemplate;
    @JsonIgnore
    private Instant sqlTemplateLastModified;

    /**
     * Creates a new provider specific sensor definition wrapper.
     */
    public ProviderSensorDefinitionWrapperImpl() {
    }

    /**
     * Creates a provider specific sensor definition given a provider type.
     * @param provider Provider type.
     */
    public ProviderSensorDefinitionWrapperImpl(ProviderType provider) {
        this();
        this.provider = provider;
    }

    /**
     * Gets the data provider type.
     *
     * @return Data provider type that supports this variant of the sensor.
     */
    @Override
    public ProviderType getProvider() {
        return this.provider;
    }

    /**
     * Returns the SQL template for the template. A sensor may not have an SQL template and could be implemented
     * as a python module instead.
     *
     * @return Sql Template.
     */
    @Override
    public String getSqlTemplate() {
        return this.sqlTemplate;
    }

    /**
     * Sets a sql template used by this provider.
     *
     * @param sqlTemplate Sql template string.
     */
    @Override
    public void setSqlTemplate(String sqlTemplate) {
		this.setModifiedIf(!Objects.equals(this.sqlTemplate, sqlTemplate));
        this.sqlTemplate = sqlTemplate;
    }

    /**
     * Returns the file modification timestamp when the SQL template was modified for the last time.
     *
     * @return Last file modification timestamp.
     */
    @Override
    public Instant getSqlTemplateLastModified() {
        return this.sqlTemplateLastModified;
    }

    /**
     * Sets the timestamp when the SQL template was modified for the last time.
     *
     * @param sqlTemplateLastModified SQL Template last modified timestamp.
     */
    @Override
    public void setSqlTemplateLastModified(Instant sqlTemplateLastModified) {
        this.setModifiedIf(!Objects.equals(this.sqlTemplateLastModified, sqlTemplateLastModified));
        this.sqlTemplateLastModified = sqlTemplateLastModified;
    }

    /**
     * Sets a data provider type that supports this sensor.
     *
     * @param providerType Data provider type.
     */
    @Override
    public void setProvider(ProviderType providerType) {
        assert this.provider == null || Objects.equals(this.provider, providerType); // cannot change the provider
		this.provider = providerType;
    }

    /**
     * Returns an object name that is used for indexing. The object name must correctly implement equals and hashCode.
     *
     * @return Object name;
     */
    @Override
    @JsonIgnore
    public ProviderType getObjectName() {
        return this.getProvider();
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
}
