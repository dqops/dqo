package com.dqops.connectors.trino;

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

public class TrinoSourceConnectionObjectMother {

    /**
     * The static factory method for the TrinoSourceConnection creation.
     * @return TrinoSourceConnection
     */
    public static TrinoSourceConnection getTrinoSourceConnection() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(TrinoSourceConnection.class);
    }

}
