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
