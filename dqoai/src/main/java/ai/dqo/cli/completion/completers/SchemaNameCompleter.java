/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.completion.completers;

import ai.dqo.cli.completion.AbstractCommandAwareCompleter;
import ai.dqo.cli.completion.completedcommands.IConnectionNameCommand;
import ai.dqo.cli.completion.completers.cache.CliCompleterCacheKey;
import ai.dqo.cli.completion.completers.cache.CliCompletionCache;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextCache;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.utils.StaticBeanFactory;
import com.google.common.base.Strings;
import org.springframework.beans.factory.BeanFactory;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Command line completer that will complete the list of schemas when the connection name was already provided in the command line.
 */
public class SchemaNameCompleter extends AbstractCommandAwareCompleter<IConnectionNameCommand> {
    private UserHomeContextCache userHomeContextCache;

    /**
     * Default constructor called by the auto completion system.
     */
    public SchemaNameCompleter() {
        BeanFactory beanFactory = StaticBeanFactory.getBeanFactory();
        if (beanFactory != null) {
            this.userHomeContextCache = beanFactory.getBean(UserHomeContextCache.class);
        }
    }

    /**
     * Testable constructor that supports a custom user home context factory.
     * @param userHomeContextCache User home context cache.
     */
    public SchemaNameCompleter(UserHomeContextCache userHomeContextCache) {
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

                    HashSet<String> uniqueSchemaNames = new HashSet<>();

                    for (TableWrapper tableWrapper : connectionWrapper.getTables()) {
                        String schemaName = tableWrapper.getPhysicalTableName().getSchemaName();
                        uniqueSchemaNames.add(schemaName);
                    }

                    return uniqueSchemaNames.iterator();
                });

        return cachedCompletionCandidates;
    }
}
