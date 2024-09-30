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
