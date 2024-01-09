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
package com.dqops.connectors.sqlserver;

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
 * SQL Server source connection provider.
 */
@Component("sqlserver-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SqlServerConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final SqlServerProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings SQL Server dialect settings.
     */
    @Autowired
    public SqlServerConnectionProvider(BeanFactory beanFactory,
                                       SqlServerProviderDialectSettings dialectSettings) {
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
    public SqlServerSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection, SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        SqlServerSourceConnection connection = this.beanFactory.getBean(SqlServerSourceConnection.class);
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
        SqlServerParametersSpec sqlserverSpec = connectionSpec.getSqlserver();
        if (sqlserverSpec == null) {
            sqlserverSpec = new SqlServerParametersSpec();
            connectionSpec.setSqlserver(sqlserverSpec);
        }

        if (Strings.isNullOrEmpty(sqlserverSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--sqlserver-host");
            }

            sqlserverSpec.setHost(terminalReader.prompt("SQL Server host name (--sqlserver-host)", "${SQLSERVER_HOST}", false));
        }

        if (Strings.isNullOrEmpty(sqlserverSpec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--sqlserver-port");
            }

            sqlserverSpec.setPort(terminalReader.prompt("SQL Server port number (--sqlserver-port)", "${SQLSERVER_PORT}", false));
        }


        if (Strings.isNullOrEmpty(sqlserverSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--sqlserver-database");
            }

            sqlserverSpec.setDatabase(terminalReader.prompt("SQL Server database name (--sqlserver-database)", "${SQLSERVER_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(sqlserverSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--sqlserver-user");
            }

            sqlserverSpec.setUser(terminalReader.prompt("SQL Server user name (--sqlserver-user)", "${SQLSERVER_USER}", false));
        }

        if (Strings.isNullOrEmpty(sqlserverSpec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--sqlserver-password");
            }

            sqlserverSpec.setPassword(terminalReader.prompt("SQL Server user password (--sqlserver-password)", "${SQLSERVER_PASSWORD}", false));
        }

        if (sqlserverSpec.getDisableEncryption() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--sqlserver-disable-encryption");
            }

            sqlserverSpec.setDisableEncryption(terminalReader.promptBoolean("Disable SSL encryption (--sqlserver-disable-encryption)", false));
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
            return new ColumnTypeSnapshotSpec("smallint");
        }
        else if (columnType == ColumnType.INTEGER) {
            return new ColumnTypeSnapshotSpec("int");
        }
        else if (columnType == ColumnType.LONG) {
            return new ColumnTypeSnapshotSpec("bigint");
        }
        else if (columnType == ColumnType.FLOAT) {
            return new ColumnTypeSnapshotSpec("real");
        }
        else if (columnType == ColumnType.BOOLEAN) {
            return new ColumnTypeSnapshotSpec("bit");
        }
        else if (columnType == ColumnType.STRING) {
            return new ColumnTypeSnapshotSpec("nvarchar", 255);
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("float");
        }
        else if (columnType == ColumnType.LOCAL_DATE) {
            return new ColumnTypeSnapshotSpec("date");
        }
        else if (columnType == ColumnType.LOCAL_TIME) {
            return new ColumnTypeSnapshotSpec("time");
        }
        else if (columnType == ColumnType.LOCAL_DATE_TIME) {
            return new ColumnTypeSnapshotSpec("datetime");
        }
        else if (columnType == ColumnType.INSTANT) {
            return new ColumnTypeSnapshotSpec("datetimeoffset");
        }
        else if (columnType == ColumnType.TEXT) {
            return new ColumnTypeSnapshotSpec("ntext");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }
}
