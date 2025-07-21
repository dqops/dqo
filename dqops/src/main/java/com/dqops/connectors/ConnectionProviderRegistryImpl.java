/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors;

import com.google.common.base.Strings;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Connection registry that returns connection providers for dialects.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ConnectionProviderRegistryImpl implements ConnectionProviderRegistry {
    private final BeanFactory beanFactory;

    @Autowired
    public ConnectionProviderRegistryImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Returns a connection provider for the given dialect.
     * @param dialect Dialect name.
     * @return Connection provider for the given dialect.
     */
    public ConnectionProvider getConnectionProvider(String dialect) {
        assert !Strings.isNullOrEmpty(dialect);
        String lowerCaseDialect = dialect.toLowerCase(Locale.ROOT);
        String beanName = lowerCaseDialect + "-provider";
        ConnectionProvider connectionProvider = this.beanFactory.getBean(beanName, ConnectionProvider.class);
        return connectionProvider;
    }

    /**
     * Returns a connection provider for the given dialect.
     * @param dialect Dialect name.
     * @return Connection provider for the given dialect.
     */
    public ConnectionProvider getConnectionProvider(ProviderType dialect) {
        String lowerCaseDialect = dialect.toString().toLowerCase(Locale.ROOT);
        return getConnectionProvider(lowerCaseDialect);
    }
}
