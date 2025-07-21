/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.duckdb;

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

public class DuckdbSourceConnectionObjectMother {

    /**
     * The static factory method for the DuckdbSourceConnection creation.
     * @return DuckdbSourceConnection
     */
    public static DuckdbSourceConnection getDuckdbSourceConnection() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(DuckdbSourceConnection.class);
    }

}