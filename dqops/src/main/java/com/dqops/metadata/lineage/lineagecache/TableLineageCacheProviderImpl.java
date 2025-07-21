/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.lineage.lineagecache;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Intermediary service used to retrieve the instance of the table lineage cache, to avoid circular dependencies
 * during the IoC initialization.
 */
@Component
public class TableLineageCacheProviderImpl implements TableLineageCacheProvider {
    private final BeanFactory beanFactory;
    private TableLineageCache tableLineageCache;

    /**
     * Default injection constructor that gets a reference to the bean factory.
     * @param beanFactory Bean factory instance.
     */
    @Autowired
    public TableLineageCacheProviderImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Additional constructor for testing.
     * @param beanFactory Bean factory.
     * @param tableLineageCache Table lineage instance to use.
     */
    public TableLineageCacheProviderImpl(BeanFactory beanFactory, TableLineageCache tableLineageCache) {
        this.beanFactory = beanFactory;
        this.tableLineageCache = tableLineageCache;
    }

    /**
     * Returns the current instance of the table lineage cache.
     * @return Table data lineage cache instance.
     */
    @Override
    public TableLineageCache getTableLineageCache() {
        if (this.tableLineageCache == null) {
            this.tableLineageCache = this.beanFactory.getBean(TableLineageCache.class);
        }

        return this.tableLineageCache;
    }
}
