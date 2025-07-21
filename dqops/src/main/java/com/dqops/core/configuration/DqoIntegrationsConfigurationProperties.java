/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.integrations. - for configuring REST API calls where the table health status should be sent.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.integrations")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoIntegrationsConfigurationProperties implements Cloneable {
    /**
     * The URLs of webhooks that should receive information about changes of a table healths status.
     */
    private String tableHealthWebhookUrls;

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoIntegrationsConfigurationProperties clone() {
        try {
            DqoIntegrationsConfigurationProperties cloned = (DqoIntegrationsConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
