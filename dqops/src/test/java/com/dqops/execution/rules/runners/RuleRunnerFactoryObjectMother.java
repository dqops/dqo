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
package com.dqops.execution.rules.runners;

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for RuleRunnerFactory.
 */
public class RuleRunnerFactoryObjectMother {
    /**
     * Creates (retrives) the default rule runner factory from Spring.
     * @return Rule runner factory.
     */
    public static RuleRunnerFactoryImpl createDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return (RuleRunnerFactoryImpl) beanFactory.getBean(RuleRunnerFactory.class);
    }
}
