/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.mariadb;

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
 * MariaDB source connection provider.
 */
@Component("mariadb-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MariaDbConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final MariaDbProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings MariaDb dialect settings.
     */
    @Autowired
    public MariaDbConnectionProvider(BeanFactory beanFactory,
                                   MariaDbProviderDialectSettings dialectSettings) {
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
    public MariaDbSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection, SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        MariaDbSourceConnection connection = this.beanFactory.getBean(MariaDbSourceConnection.class);
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
        MariaDbParametersSpec mariaDbParametersSpec = connectionSpec.getMariadb();
        if (mariaDbParametersSpec == null) {
            mariaDbParametersSpec = new MariaDbParametersSpec();
            connectionSpec.setMariadb(mariaDbParametersSpec);
        }

        if (Strings.isNullOrEmpty(mariaDbParametersSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--mariadb-host");
            }
            mariaDbParametersSpec.setHost(terminalReader.prompt("MariaDB host name (--mariadb-host)", "${MARIADB_HOST}", false));
        }

        if (Strings.isNullOrEmpty(mariaDbParametersSpec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--mariadb-port");
            }
            mariaDbParametersSpec.setPort(terminalReader.prompt("MariaDB port number (--mariadb-port)", "${MARIADB_PORT}", false));
        }

        if (Strings.isNullOrEmpty(mariaDbParametersSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--mariadb-database");
            }
            mariaDbParametersSpec.setDatabase(terminalReader.prompt("MariaDB database name (--mariadb-database)", "${MARIADB_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(mariaDbParametersSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--mariadb-user");
            }
            mariaDbParametersSpec.setUser(terminalReader.prompt("MariaDB user name (--mariadb-user)", "${MARIADB_USER}", false));
        }

        if (Strings.isNullOrEmpty(mariaDbParametersSpec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--mariadb-password");
            }
            mariaDbParametersSpec.setPassword(terminalReader.prompt("MariaDB user password (--mariadb-password)", "${MARIADB_PASSWORD}", false));
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
        if(constant instanceof Boolean){
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
            return new ColumnTypeSnapshotSpec("SMALLINT");
        }
        else if (columnType == ColumnType.INTEGER) {
            return new ColumnTypeSnapshotSpec("INT");
        }
        else if (columnType == ColumnType.LONG) {
            return new ColumnTypeSnapshotSpec("BIGINT");
        }
        else if (columnType == ColumnType.FLOAT) {
            return new ColumnTypeSnapshotSpec("FLOAT");
        }
        else if (columnType == ColumnType.BOOLEAN) {
            return new ColumnTypeSnapshotSpec("BOOLEAN");
        }
        else if (columnType == ColumnType.STRING) {
            return new ColumnTypeSnapshotSpec("VARCHAR", 255);
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("DOUBLE");
        }
        else if (columnType == ColumnType.LOCAL_DATE) {
            return new ColumnTypeSnapshotSpec("DATE");
        }
        else if (columnType == ColumnType.LOCAL_TIME) {
            return new ColumnTypeSnapshotSpec("TIME");
        }
        else if (columnType == ColumnType.LOCAL_DATE_TIME) {
            return new ColumnTypeSnapshotSpec("DATETIME");
        }
        else if (columnType == ColumnType.INSTANT) {
            return new ColumnTypeSnapshotSpec("TIMESTAMP");
        }
        else if (columnType == ColumnType.TEXT) {
            return new ColumnTypeSnapshotSpec("TEXT");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }
}
