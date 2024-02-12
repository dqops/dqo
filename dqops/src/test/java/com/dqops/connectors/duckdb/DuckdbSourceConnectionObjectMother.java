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