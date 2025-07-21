/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.checkresults.statuscache;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Intermediary service used to retrieve the instance of the table status cache, to avoid circular dependencies
 * during the IoC initialization.
 */
@Component
public class TableStatusCacheProviderImpl implements TableStatusCacheProvider {
    private final BeanFactory beanFactory;
    private TableStatusCache tableStatusCache;

    /**
     * Default injection constructor that gets a reference to the bean factory.
     * @param beanFactory Bean factory instance.
     */
    @Autowired
    public TableStatusCacheProviderImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Additional constructor for testing.
     * @param beanFactory Bean factory.
     * @param tableStatusCache Table status cache instance to use.
     */
    public TableStatusCacheProviderImpl(BeanFactory beanFactory, TableStatusCache tableStatusCache) {
        this.beanFactory = beanFactory;
        this.tableStatusCache = tableStatusCache;
    }

    /**
     * Returns the current instance of the table status cache.
     * @return Table status cache instance.
     */
    @Override
    public TableStatusCache getTableStatusCache() {
        if (this.tableStatusCache == null) {
            this.tableStatusCache = this.beanFactory.getBean(TableStatusCache.class);
        }

        return this.tableStatusCache;
    }
}
