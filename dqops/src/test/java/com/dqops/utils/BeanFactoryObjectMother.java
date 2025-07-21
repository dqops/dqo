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
 * Provides static access to the bean factory. Mostly used in the unit test classes.
 */
public final class BeanFactoryObjectMother {
    private static BeanFactory beanFactory;

    /**
     * Returns a shared bean factory.
     * @return Bean factory.
     */
    public static BeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * Sets a static bean factory. Called from the {@link BaseTest}
     * @param beanFactory New bean factory.
     */
    public static void setBeanFactory(BeanFactory beanFactory) {
        BeanFactoryObjectMother.beanFactory = beanFactory;
    }
}
