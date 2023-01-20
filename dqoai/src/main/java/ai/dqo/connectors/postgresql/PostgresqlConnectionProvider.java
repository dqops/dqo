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
package ai.dqo.connectors.postgresql;

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

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Postgresql source connection provider.
 */
@Component("postgresql-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PostgresqlConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final ProviderDialectSettings dialectSettings = new ProviderDialectSettings("\"", "\"", "\"\"", false);

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     */
    @Autowired
    public PostgresqlConnectionProvider(BeanFactory beanFactory) {
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
    public PostgresqlSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection) {
        assert connectionSpec != null;
        PostgresqlSourceConnection connection = this.beanFactory.getBean(PostgresqlSourceConnection.class);
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
        HashMap<String, String> connectionProperties = connectionSpec.getProperties();
        PostgresqlParametersSpec postgresqlSpec = connectionSpec.getPostgresql();
        if (postgresqlSpec == null) {
            postgresqlSpec = new PostgresqlParametersSpec();
            connectionSpec.setPostgresql(postgresqlSpec);
        }

        if (connectionProperties.containsKey("postgresql-host")) {
            postgresqlSpec.setHost(connectionProperties.get("postgresql-host"));
            connectionProperties.remove("postgresql-host");
        }
        if (Strings.isNullOrEmpty(postgresqlSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("-P=postgresql-host");
            }

            postgresqlSpec.setHost(terminalReader.prompt("PostgreSQL host name (-P=postgresql-host)", "${POSTGRESQL_HOST}", false));
        }

        if (connectionProperties.containsKey("postgresql-port")) {
            postgresqlSpec.setHost(connectionProperties.get("postgresql-port"));
            connectionProperties.remove("postgresql-port");
        }


        if (connectionProperties.containsKey("postgresql-ssl")) {
            postgresqlSpec.setSsl("true".equalsIgnoreCase(connectionProperties.get("postgresql-ssl")) ? true : null);
            connectionProperties.remove("postgresql-ssl");
        }
        if (postgresqlSpec.getSsl() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("-P=postgresql-ssl");
            }

            postgresqlSpec.setSsl(terminalReader.promptBoolean("Require SSL connection (-P=postgresql-ssl)", true));
        }

        if (connectionProperties.containsKey("postgresql-options")) {
            postgresqlSpec.setOptions(connectionProperties.get("postgresql-options"));
            connectionProperties.remove("postgresql-options");
        }

        if (Strings.isNullOrEmpty(connectionSpec.getDatabaseName())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--database");
            }

            connectionSpec.setDatabaseName(terminalReader.prompt("PostgreSQL database name (--database)", "${POSTGRESQL_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(connectionSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--user");
            }

            connectionSpec.setUser(terminalReader.prompt("PostgreSQL user name (--user)", "${POSTGRESQL_USER}", false));
        }

        if (Strings.isNullOrEmpty(connectionSpec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--password");
            }

            connectionSpec.setPassword(terminalReader.prompt("PostgreSQL user password (--password)", "${POSTGRESQL_PASSWORD}", false));
        }
    }

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
            return new ColumnTypeSnapshotSpec("time without time zone");
        }
        else if (columnType == ColumnType.LOCAL_DATE_TIME) {
            return new ColumnTypeSnapshotSpec("timestamp without time zone");
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
