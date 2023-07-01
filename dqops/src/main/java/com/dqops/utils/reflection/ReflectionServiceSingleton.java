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
package com.dqops.utils.reflection;

import com.dqops.utils.StaticBeanFactory;

/**
 * Provides static access to the {@link ReflectionService} singleton instance from static code.
 */
public class ReflectionServiceSingleton {
    private static ReflectionService reflectionService;

    /**
     * Returns a static shared instance of the reflection service.
     * @return Reflection service.
     */
    public static ReflectionService getInstance() {
        if (reflectionService == null && StaticBeanFactory.getBeanFactory() == null) {
            reflectionService = new ReflectionServiceImpl();
        } else if (reflectionService == null) {
            reflectionService = StaticBeanFactory.getBeanFactory().getBean(ReflectionService.class);
        }
        return reflectionService;
    }
}
