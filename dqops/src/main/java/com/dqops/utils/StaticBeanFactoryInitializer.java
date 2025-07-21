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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Initializing bean that will capture and store a reference to the {@link org.springframework.beans.factory.BeanFactory} in
 * {@link StaticBeanFactory}.
 */
@Component
@Lazy(false)
public class StaticBeanFactoryInitializer implements InitializingBean {
    private final BeanFactory beanFactory;

    @Autowired
    public StaticBeanFactoryInitializer(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Called by Spring after all beans were created. This method will capture the reference to the BeanFactory for static access.
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        StaticBeanFactory.setBeanFactory(this.beanFactory);
    }
}
