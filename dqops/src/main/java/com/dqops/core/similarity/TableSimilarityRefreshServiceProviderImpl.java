/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.similarity;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Intermediary service used to retrieve the instance of the table similarity refresh service, to avoid circular dependencies
 * during the IoC initialization.
 */
@Component
public class TableSimilarityRefreshServiceProviderImpl implements TableSimilarityRefreshServiceProvider {
    private final BeanFactory beanFactory;
    private TableSimilarityRefreshService tableSimilarityRefreshService;

    /**
     * Default injection constructor that gets a reference to the bean factory.
     * @param beanFactory Bean factory instance.
     */
    @Autowired
    public TableSimilarityRefreshServiceProviderImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Additional constructor for testing.
     * @param beanFactory Bean factory.
     * @param tableSimilarityRefreshService Table similarity refresh service instance to use.
     */
    public TableSimilarityRefreshServiceProviderImpl(BeanFactory beanFactory, TableSimilarityRefreshService tableSimilarityRefreshService) {
        this.beanFactory = beanFactory;
        this.tableSimilarityRefreshService = tableSimilarityRefreshService;
    }

    /**
     * Returns the current instance of the table similarity refresh service.
     * @return Table similarity refresh service instance.
     */
    @Override
    public TableSimilarityRefreshService getTableSimilarityRefreshService() {
        if (this.tableSimilarityRefreshService == null) {
            this.tableSimilarityRefreshService = this.beanFactory.getBean(TableSimilarityRefreshService.class);
        }

        return this.tableSimilarityRefreshService;
    }
}
