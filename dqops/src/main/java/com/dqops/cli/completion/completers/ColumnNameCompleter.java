/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.completion.completers;

import com.dqops.cli.completion.AbstractCommandAwareCompleter;
import com.dqops.cli.completion.completedcommands.ITableNameCommand;
import com.dqops.cli.completion.completers.cache.CliCompleterCacheKey;
import com.dqops.cli.completion.completers.cache.CliCompletionCache;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextCache;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.StaticBeanFactory;
import com.google.common.base.Strings;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Command line completer that will complete the list of column names when the connection name and table name was already provided in the command line.
 */
public class ColumnNameCompleter extends AbstractCommandAwareCompleter<ITableNameCommand> {
    private UserHomeContextCache userHomeContextCache;

    /**
     * Default constructor called by the auto-completion system.
     */
    public ColumnNameCompleter() {
        BeanFactory beanFactory = StaticBeanFactory.getBeanFactory();
        if (beanFactory != null) {
            this.userHomeContextCache = beanFactory.getBean(UserHomeContextCache.class);
        }
    }

    /**
     * Testable constructor that supports a custom user home context factory.
     * @param userHomeContextCache User home context cache.
     */
    public ColumnNameCompleter(UserHomeContextCache userHomeContextCache) {
        this.userHomeContextCache = userHomeContextCache;
    }

    /**
     * The overriding method should create an iterator using the data from the command.
     *
     * @param command User command.
     * @return List of completion candidates.
     */
    @Override
    public Iterator<String> createIterator(ITableNameCommand command) {
        if (command == null || this.userHomeContextCache == null) {
            return createEmptyCompletionIterator();
        }

        String connectionName = command.getConnection();
        if (Strings.isNullOrEmpty(connectionName)) {
            return createEmptyCompletionIterator();
        }

        String schemaTableName = command.getTable();
        if (Strings.isNullOrEmpty(schemaTableName)) {
            return createEmptyCompletionIterator();
        }

        Iterator<String> cachedCompletionCandidates = CliCompletionCache.getCachedCompletionCandidates(
                new CliCompleterCacheKey(this.getClass(), connectionName, schemaTableName),
                () -> {
                    UserHomeContext userHomeContext = this.userHomeContextCache.getCachedLocalUserHome();
                    UserHome userHome = userHomeContext.getUserHome();
                    ConnectionList connections = userHome.getConnections();

                    ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                    if (connectionWrapper == null) {
                        return createEmptyCompletionIterator();
                    }

                    PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(schemaTableName);
                    TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(physicalTableName, true);
                    if (tableWrapper == null) {
                        return createEmptyCompletionIterator();
                    }

                    ArrayList<String> columnNames = new ArrayList<>();
                    columnNames.addAll(tableWrapper.getSpec().getColumns().keySet());

                    return columnNames.iterator();
                });

        return cachedCompletionCandidates;
    }
}
