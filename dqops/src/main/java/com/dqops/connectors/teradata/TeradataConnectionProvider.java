/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.teradata;

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
 * Teradata source connection provider.
 */
@Component("teradata-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TeradataConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final TeradataProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings Teradata dialect settings.
     */
    @Autowired
    public TeradataConnectionProvider(BeanFactory beanFactory,
                                      TeradataProviderDialectSettings dialectSettings) {
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
    public TeradataSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection, SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        TeradataSourceConnection connection = this.beanFactory.getBean(TeradataSourceConnection.class);
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
        TeradataParametersSpec teradataSpec = connectionSpec.getTeradata();
        if (teradataSpec == null) {
            teradataSpec = new TeradataParametersSpec();
            connectionSpec.setTeradata(teradataSpec);
        }

        if (Strings.isNullOrEmpty(teradataSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--teradata-host");
            }

            teradataSpec.setHost(terminalReader.prompt("Teradata host name (--teradata-host)", "${TERADATA_HOST}", false));
        }

        if (Strings.isNullOrEmpty(teradataSpec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--teradata-port");
            }

            teradataSpec.setPort(terminalReader.prompt("Teradata port number (--teradata-port)", "${TERADATA_PORT}", false));
        }

        if (Strings.isNullOrEmpty(teradataSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--teradata-user");
            }

            teradataSpec.setUser(terminalReader.prompt("Teradata user name (--teradata-user)", "${TERADATA_USER}", false));
        }

        if (Strings.isNullOrEmpty(teradataSpec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--teradata-password");
            }

            teradataSpec.setPassword(terminalReader.prompt("Teradata user password (--teradata-password)", "${TERADATA_PASSWORD}", false));
        }
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
            return new ColumnTypeSnapshotSpec("INTEGER");
        }
        else if (columnType == ColumnType.LONG) {
            return new ColumnTypeSnapshotSpec("BIGINT");
        }
        else if (columnType == ColumnType.FLOAT) {
            return new ColumnTypeSnapshotSpec("REAL");
        }
        else if (columnType == ColumnType.BOOLEAN) {
            return new ColumnTypeSnapshotSpec("BYTEINT");
        }
        else if (columnType == ColumnType.STRING) {
            return new ColumnTypeSnapshotSpec("VARCHAR(4096)");
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("DOUBLE PRECISION");
        }
        else if (columnType == ColumnType.LOCAL_DATE) {
            return new ColumnTypeSnapshotSpec("DATE");
        }
        else if (columnType == ColumnType.LOCAL_TIME) {
            return new ColumnTypeSnapshotSpec("TIME");
        }
        else if (columnType == ColumnType.LOCAL_DATE_TIME) {
            return new ColumnTypeSnapshotSpec("TIMESTAMP");
        }
        else if (columnType == ColumnType.INSTANT) {
            return new ColumnTypeSnapshotSpec("TIMESTAMP");
        }
        else if (columnType == ColumnType.TEXT) {
            return new ColumnTypeSnapshotSpec("VARCHAR(MAX)");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }

}
