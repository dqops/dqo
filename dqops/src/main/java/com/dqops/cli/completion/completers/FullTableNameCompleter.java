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
package com.dqops.cli.completion.completers;

import com.dqops.cli.completion.AbstractCommandAwareCompleter;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completers.cache.CliCompleterCacheKey;
import com.dqops.cli.completion.completers.cache.CliCompletionCache;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextCache;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.StaticBeanFactory;
import com.google.common.base.Strings;
import org.springframework.beans.factory.BeanFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Command line completer that will complete the list of tables as a "schema.table" entry when the connection name was already provided in the command line.
 */
public class FullTableNameCompleter extends AbstractCommandAwareCompleter<IConnectionNameCommand> {
    private UserHomeContextCache userHomeContextCache;

    /**
     * Default constructor called by the auto completion system.
     */
    public FullTableNameCompleter() {
        BeanFactory beanFactory = StaticBeanFactory.getBeanFactory();
        if (beanFactory != null) {
            this.userHomeContextCache = beanFactory.getBean(UserHomeContextCache.class);
        }
    }

    /**
     * Testable constructor that supports a custom user home context factory.
     * @param userHomeContextCache User home context factory.
     */
    public FullTableNameCompleter(UserHomeContextCache userHomeContextCache) {
        this.userHomeContextCache = userHomeContextCache;
    }

    /**
     * The overriding method should create an iterator using the data from the command.
     *
     * @param command User command.
     * @return List of completion candidates.
     */
    @Override
    public Iterator<String> createIterator(IConnectionNameCommand command) {
        if (command == null || this.userHomeContextCache == null) {
            return createEmptyCompletionIterator();
        }

        String connectionName = command.getConnection();
        if (Strings.isNullOrEmpty(connectionName)) {
            return createEmptyCompletionIterator();
        }

        Iterator<String> cachedCompletionCandidates = CliCompletionCache.getCachedCompletionCandidates(
                new CliCompleterCacheKey(this.getClass(), connectionName),
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextCache.getCachedLocalUserHome();
                    UserHome userHome = userHomeContext.getUserHome();
                    ConnectionList connections = userHome.getConnections();

                    ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                    if (connectionWrapper == null) {
                        return createEmptyCompletionIterator();
                    }

                    Set<String> uniqueSchemaTableNames = new LinkedHashSet<>();

                    for (TableWrapper tableWrapper : connectionWrapper.getTables()) {
                        String schemaDotTable = tableWrapper.getPhysicalTableName().toString();
                        uniqueSchemaTableNames.add(schemaDotTable);
                    }

                    return uniqueSchemaTableNames.iterator();
                });

        return cachedCompletionCandidates;
    }
}
