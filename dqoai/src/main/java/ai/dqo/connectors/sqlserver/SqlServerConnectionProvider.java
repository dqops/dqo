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
package ai.dqo.connectors.sqlserver;

import ai.dqo.cli.exceptions.CliRequiredParameterMissingException;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.connectors.AbstractSqlConnectionProvider;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.metadata.sources.ColumnTypeSnapshotSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
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
    public final static ProviderDialectSettings DIALECT_SETTINGS = new ProviderDialectSettings("\"", "\"", "\"\"", false);

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     */
    @Autowired
    public SqlServerConnectionProvider(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Creates a connection to a target data source.
     *
     * @param connectionSpec Connection specification.
     * @param openConnection Open the connection after creating.
     * @return Connection object.
     */
    @Override
    public SqlServerSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection) {
        assert connectionSpec != null;
        SqlServerSourceConnection connection = this.beanFactory.getBean(SqlServerSourceConnection.class);
        connection.setConnectionSpec(connectionSpec);
        if (openConnection) {
            connection.open();
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
        return this.DIALECT_SETTINGS;
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

        if (sqlserverSpec.getSsl() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--sqlserver-ssl");
            }

            sqlserverSpec.setSsl(terminalReader.promptBoolean("Require SSL connection (--sqlserver-ssl)", true));
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
    }

    /**
     * Formats a constant for the target database.
     *
     * @param constant   Constant to be formatted.
     * @param columnType Column type snapshot.
     * @return Formatted constant.
     */

    /**
     * Proposes a physical (provider specific) column type that is able to store the data of the given Tablesaw column.
     *
     * @param dataColumn Tablesaw column with data that should be stored.
     * @return Column type snapshot.
     */
    @Override
    public ColumnTypeSnapshotSpec proposePhysicalColumnType(Column<?> dataColumn) {
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
            return new ColumnTypeSnapshotSpec("varchar", 255);
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("real");
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
            return new ColumnTypeSnapshotSpec("text");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }
}
