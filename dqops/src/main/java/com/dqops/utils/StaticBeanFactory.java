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
package com.dqops.utils;

import org.springframework.beans.factory.BeanFactory;

/**
 * Static bean factory access.
 */
public final class StaticBeanFactory {
    private static BeanFactory beanFactory;

    /**
     * Returns the current instance of the bean factory.
     * @return Bean factory.
     */
    public static BeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * Sets a reference to the bean factory.
     * @param newBeanFactory Bean factory.
     */
    protected static void setBeanFactory(BeanFactory newBeanFactory) {
		beanFactory = newBeanFactory;
    }
}
