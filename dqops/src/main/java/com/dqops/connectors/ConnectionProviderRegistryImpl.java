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
