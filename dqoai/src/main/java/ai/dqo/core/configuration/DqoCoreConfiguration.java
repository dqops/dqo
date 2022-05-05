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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Core configuration.
 */
@Configuration
@EnableConfigurationProperties
@EqualsAndHashCode(callSuper = false)
public class DqoCoreConfiguration {
    @Autowired
    private DqoCoreConfigurationProperties core;

    @Autowired
    private DqoConfigurationProperties dqo;


    /**
     * Configuration properties for the core module.
     * @return Configuration properties.
     */
    public DqoCoreConfigurationProperties getCore() {
        return core;
    }

    /**
     * Sets the configuration properties for the core project.
     * @param core Core configuration properties.
     */
    public void setCore(DqoCoreConfigurationProperties core) {
        this.core = core;
    }

    /**
     * Configuration properties on the "dqo." prefix.
     * @return Root dqo configuration properties.
     */
    public DqoConfigurationProperties getDqo() {
        return dqo;
    }

    /**
     *  Stores (replaces) the root configuration properties.
     * @param dqo Configuration properties.
     */
    public void setDqo(DqoConfigurationProperties dqo) {
        this.dqo = dqo;
    }
}
