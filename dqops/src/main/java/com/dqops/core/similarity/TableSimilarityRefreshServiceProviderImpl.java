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
