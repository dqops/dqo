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

import ai.dqo.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother that returns {@link DqoConfigurationProperties} configuration properties.
 */
public class DqoConfigurationPropertiesObjectMother {
    /**
     * Returns the default (but cloned) configuration properties.
     * @return Configuration properties, cloned to allow modifications.
     */
    public static DqoConfigurationProperties getDefaultCloned() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(DqoConfigurationProperties.class).clone();
    }
}
