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

import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Incidents configuration properties object mother.
 */
public class DqoIncidentsConfigurationPropertiesObjectMother {
    /**
     * Retrieves the default incident configuration parameters and clones it.
     * @return Incident configuration.
     */
    public static DqoIncidentsConfigurationProperties getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(DqoIncidentsConfigurationProperties.class).clone();
    }
}
