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
