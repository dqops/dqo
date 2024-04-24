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

package com.dqops.metadata.labels.labelloader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Intermediary service used to retrieve the instance of the labels indexer, to avoid circular dependencies
 * during the IoC initialization.
 */
@Component
public class LabelsIndexerProviderImpl implements LabelsIndexerProvider {
    private final BeanFactory beanFactory;
    private LabelsIndexer labelsIndexer;

    /**
     * Default injection constructor that gets a reference to the bean factory.
     * @param beanFactory Bean factory instance.
     */
    @Autowired
    public LabelsIndexerProviderImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Additional constructor for testing.
     * @param beanFactory Bean factory.
     * @param labelsIndexer Labels indexer instance to use.
     */
    public LabelsIndexerProviderImpl(BeanFactory beanFactory, LabelsIndexer labelsIndexer) {
        this.beanFactory = beanFactory;
        this.labelsIndexer = labelsIndexer;
    }

    /**
     * Returns the current instance of the labels indexer.
     * @return Labels indexer instance.
     */
    @Override
    public LabelsIndexer getLabelsIndexer() {
        if (this.labelsIndexer == null) {
            this.labelsIndexer = this.beanFactory.getBean(LabelsIndexer.class);
        }

        return this.labelsIndexer;
    }
}
