/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
    @JsonIgnore
    private String errorSamplingTemplate;
    @JsonIgnore
    private Instant errorSamplingTemplateLastModified;

    /**
     * Creates a new provider specific sensor definition wrapper.
     */
    public ProviderSensorDefinitionWrapperImpl() {
    }

    /**
     * Creates a new instance of a provider specific sensor definition wrapper in possibly a read-only mode.
     * @param readOnly Turn on read-only mode.
     */
    public ProviderSensorDefinitionWrapperImpl(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Creates a provider specific sensor definition given a provider type.
     * @param provider Provider type.
     * @param readOnly Make the wrapper read-only.
     */
    public ProviderSensorDefinitionWrapperImpl(ProviderType provider, boolean readOnly) {
        this(readOnly);
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
     * Returns the error sampling SQL template for the template. A sensor may not have an SQL template and could be implemented
     * as a python module instead.
     *
     * @return Error sampling sql Template.
     */
    @Override
    public String getErrorSamplingTemplate() {
        return this.errorSamplingTemplate;
    }

    /**
     * Sets an error sampling sql template used by this provider.
     *
     * @param errorSamplingTemplate Error sampling SQL template string.
     */
    @Override
    public void setErrorSamplingTemplate(String errorSamplingTemplate) {
        this.setModifiedIf(!Objects.equals(this.errorSamplingTemplate, errorSamplingTemplate));
        this.errorSamplingTemplate = errorSamplingTemplate;
    }

    /**
     * Returns the file modification timestamp when the error sampling SQL template was modified for the last time.
     *
     * @return Last file modification timestamp of the error sampling template.
     */
    @Override
    public Instant getErrorSamplingTemplateLastModified() {
        return this.errorSamplingTemplateLastModified;
    }

    /**
     * Sets the timestamp when the error sampling SQL template was modified for the last time.
     *
     * @param errorSamplingTemplateLastModified Error sampling SQL Template last modified timestamp.
     */
    @Override
    public void setErrorSamplingTemplateLastModified(Instant errorSamplingTemplateLastModified) {
        this.setModifiedIf(!Objects.equals(this.errorSamplingTemplateLastModified, errorSamplingTemplateLastModified));
        this.errorSamplingTemplateLastModified = errorSamplingTemplateLastModified;
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
