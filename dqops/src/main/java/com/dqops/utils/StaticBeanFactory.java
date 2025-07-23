/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
