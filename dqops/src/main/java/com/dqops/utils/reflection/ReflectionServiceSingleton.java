/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
