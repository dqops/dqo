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
import com.dqops.metadata.basespecs.ElementWrapper;
import com.dqops.metadata.basespecs.ObjectName;

import java.time.Instant;

/**
 * Provider specific data quality sensor definition spec wrapper.
 */
public interface ProviderSensorDefinitionWrapper extends ElementWrapper<ProviderSensorDefinitionSpec>, ObjectName<ProviderType> {
    /**
     * Gets the data provider type.
     * @return Data provider type that supports this variant of the sensor.
     */
    ProviderType getProvider();

    /**
     * Sets a data provider type that supports this sensor.
     * @param providerType Data provider type.
     */
    void setProvider(ProviderType providerType);

    /**
     * Returns the SQL template for the template. A check may not have an SQL template and could be implemented
     * as a python module instead.
     * @return Sql Template.
     */
    String getSqlTemplate();

    /**
     * Sets a sql template used by this provider.
     * @param sqlTemplate Sql template string.
     */
    void setSqlTemplate(String sqlTemplate);

    /**
     * Returns the file modification timestamp when the SQL template was modified for the last time.
     * @return Last file modification timestamp.
     */
    Instant getSqlTemplateLastModified();

    /**
     * Sets the timestamp when the SQL template was modified for the last time.
     * @param sqlTemplateLastModified SQL Template last modified timestamp.
     */
    void setSqlTemplateLastModified(Instant sqlTemplateLastModified);

    /**
     * Returns the error sampling SQL template for the template. A check may not have an SQL template and could be implemented
     * as a python module instead.
     * @return Error sampling sql Template.
     */
    String getErrorSamplingTemplate();

    /**
     * Sets an error sampling sql template used by this provider.
     * @param errorSamplingTemplate Error sampling sql template string.
     */
    void setErrorSamplingTemplate(String errorSamplingTemplate);

    /**
     * Returns the file modification timestamp when the error sampling SQL template was modified for the last time.
     * @return Last file modification timestamp for the error sampling template.
     */
    Instant getErrorSamplingTemplateLastModified();

    /**
     * Sets the timestamp when the error sampling SQL template was modified for the last time.
     * @param errorSamplingTemplateLastModified Error sampling SQL template last modified timestamp.
     */
    void setErrorSamplingTemplateLastModified(Instant errorSamplingTemplateLastModified);
}
