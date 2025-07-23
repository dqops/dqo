/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.questdb;

import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.connectors.AbstractSqlConnectionProvider;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.columns.Column;

import java.util.NoSuchElementException;

/**
 * QuestDB source connection provider.
 */
@Component("questdb-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class QuestDbConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final QuestDbProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings QuestDb dialect settings.
     */
    @Autowired
    public QuestDbConnectionProvider(BeanFactory beanFactory,
                                        QuestDbProviderDialectSettings dialectSettings) {
        this.beanFactory = beanFactory;
        this.dialectSettings = dialectSettings;
    }

    /**
     * Creates a connection to a target data source.
     *
     * @param connectionSpec Connection specification.
     * @param openConnection Open the connection after creating.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Connection object.
     */
    @Override
    public QuestDbSourceConnection createConnection(ConnectionSpec connectionSpec,
                                                       boolean openConnection,
                                                       SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        QuestDbSourceConnection connection = this.beanFactory.getBean(QuestDbSourceConnection.class);
        connection.setConnectionSpec(connectionSpec);
        if (openConnection) {
            connection.open(secretValueLookupContext);
        }
        return connection;
    }

    /**
     * Returns the dialect settings for the provider. The dialect settings has information about supported SQL features, identifier quoting, etc.
     *
     * @param connectionSpec Connection specification if the settings are database version specific.
     * @return Provider dialect settings.
     */
    @Override
    public ProviderDialectSettings getDialectSettings(ConnectionSpec connectionSpec) {
        return this.dialectSettings;
    }

    /**
     * Delegates the connection configuration to the provider.
     *
     * @param connectionSpec Connection specification to fill.
     * @param isHeadless     When true and some required parameters are missing then throws an exception {@link CliRequiredParameterMissingException},
     *                       otherwise prompts the user to fill the answer.
     * @param terminalReader Terminal reader that may be used to prompt the user.
     * @param terminalWriter Terminal writer that should be used to write any messages.
     */
    @Override
    public void promptForConnectionParameters(ConnectionSpec connectionSpec, boolean isHeadless, TerminalReader terminalReader, TerminalWriter terminalWriter) {
        QuestDbParametersSpec questDbParametersSpec = connectionSpec.getQuestdb();
        if (questDbParametersSpec == null) {
            questDbParametersSpec = new QuestDbParametersSpec();
            connectionSpec.setQuestdb(questDbParametersSpec);
        }

        if (Strings.isNullOrEmpty(questDbParametersSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--questdb-host");
            }

            questDbParametersSpec.setHost(terminalReader.prompt("QuestDB host name (--questdb-host)", "${QUESTDB_HOST}", false));
        }

        if (Strings.isNullOrEmpty(questDbParametersSpec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--questdb-port");
            }

            questDbParametersSpec.setPort(terminalReader.prompt("QuestDB port number (--questdb-port)", "${QUESTDB_PORT}", false));
        }

        if (Strings.isNullOrEmpty(questDbParametersSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--questdb-database");
            }

            questDbParametersSpec.setDatabase(terminalReader.prompt("QuestDB database name (--questdb-database)", "${QUESTDB_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(questDbParametersSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--questdb-user");
            }

            questDbParametersSpec.setUser(terminalReader.prompt("QuestDB user name (--questdb-user)", "${QUESTDB_USER}", false));
        }

        if (Strings.isNullOrEmpty(questDbParametersSpec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--questdb-password");
            }

            questDbParametersSpec.setPassword(terminalReader.prompt("QuestDB user password (--questdb-password)", "${QUESTDB_PASSWORD}", false));
        }
    }

    /**
     * Formats a constant for the target database.
     *
     * @param constant   Constant to be formatted.
     * @param columnType Column type snapshot.
     * @return Formatted constant.
     */
    @Override
    public String formatConstant(Object constant, ColumnTypeSnapshotSpec columnType) {
        if (constant instanceof Boolean){
            Boolean asBoolean = (Boolean)constant;
            return asBoolean ? "true" : "false";
        }
        return super.formatConstant(constant, columnType);
    }

    /**
     * Proposes a physical (provider specific) column type that is able to store the data of the given Tablesaw column.
     *
     * @param connectionSpec Connection specification if the settings are database version specific.
     * @param dataColumn Tablesaw column with data that should be stored.
     * @return Column type snapshot.
     */
    @Override
    public ColumnTypeSnapshotSpec proposePhysicalColumnType(ConnectionSpec connectionSpec, Column<?> dataColumn) {
        ColumnType columnType = dataColumn.type();

        if (columnType == ColumnType.SHORT) {
            return new ColumnTypeSnapshotSpec("smallint");
        }
        else if (columnType == ColumnType.INTEGER) {
            return new ColumnTypeSnapshotSpec("integer");
        }
        else if (columnType == ColumnType.LONG) {
            return new ColumnTypeSnapshotSpec("bigint");
        }
        else if (columnType == ColumnType.FLOAT) {
            return new ColumnTypeSnapshotSpec("real");
        }
        else if (columnType == ColumnType.BOOLEAN) {
            return new ColumnTypeSnapshotSpec("boolean");
        }
        else if (columnType == ColumnType.STRING) {
            return new ColumnTypeSnapshotSpec("varchar");
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("double precision");
        }
        else if (columnType == ColumnType.LOCAL_DATE) {
            return new ColumnTypeSnapshotSpec("date");
        }
        else if (columnType == ColumnType.LOCAL_TIME) {
            return new ColumnTypeSnapshotSpec("time");
        }
        else if (columnType == ColumnType.LOCAL_DATE_TIME) {
            return new ColumnTypeSnapshotSpec("timestamp");
        }
        else if (columnType == ColumnType.INSTANT) {
            return new ColumnTypeSnapshotSpec("timestamp with time zone");
        }
        else if (columnType == ColumnType.TEXT) {
            return new ColumnTypeSnapshotSpec("text");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }
}
