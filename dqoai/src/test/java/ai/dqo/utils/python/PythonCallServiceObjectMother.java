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
package ai.dqo.utils.python;

import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.configuration.DqoPythonConfigurationProperties;
import ai.dqo.core.configuration.DqoPythonConfigurationPropertiesObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;
import ai.dqo.utils.serialization.JsonSerializerImpl;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for PythonCallService.
 */
public class PythonCallServiceObjectMother {
    /**
     * Creates a new instance of a python call service.
     * @return Python call service.
     */
    public static PythonCallerServiceImpl createNewDefault() {
        DqoConfigurationProperties configurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        PythonVirtualEnvService pythonVirtualEnvService = PythonVirtualEnvServiceObjectMother.getDefault();
        DqoPythonConfigurationProperties pythonConfigurationProperties = DqoPythonConfigurationPropertiesObjectMother.getDefaultCloned();
        return new PythonCallerServiceImpl(configurationProperties, pythonConfigurationProperties, new JsonSerializerImpl(), pythonVirtualEnvService);
    }

    /**
     * Retrieves the default python call service.
     * @return Default python call service.
     */
    public static PythonCallerService getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(PythonCallerService.class);
    }
}
