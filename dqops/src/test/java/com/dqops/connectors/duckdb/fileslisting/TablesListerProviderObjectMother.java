/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.duckdb.fileslisting.TablesListerProvider;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * TablesListerProvider object mother.
 */
public class TablesListerProviderObjectMother {
    /**
     * Returns the TablesListerProvider.
     * @return TablesListerProvider.
     */
    public static TablesListerProvider getProvider() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(TablesListerProvider.class);
    }

}
