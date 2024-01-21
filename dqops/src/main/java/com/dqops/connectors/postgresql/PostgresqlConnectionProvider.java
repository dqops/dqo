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
package com.dqops.connectors.postgresql;

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
 * Postgresql source connection provider.
 */
@Component("postgresql-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PostgresqlConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final PostgresqlProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings Postgresql dialect settings.
     */
    @Autowired
    public PostgresqlConnectionProvider(BeanFactory beanFactory,
                                        PostgresqlProviderDialectSettings dialectSettings) {
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
    public PostgresqlSourceConnection createConnection(ConnectionSpec connectionSpec,
                                                       boolean openConnection,
                                                       SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        PostgresqlSourceConnection connection = this.beanFactory.getBean(PostgresqlSourceConnection.class);
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
        PostgresqlParametersSpec postgresqlSpec = connectionSpec.getPostgresql();
        if (postgresqlSpec == null) {
            postgresqlSpec = new PostgresqlParametersSpec();
            connectionSpec.setPostgresql(postgresqlSpec);
        }

        if (Strings.isNullOrEmpty(postgresqlSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--postgresql-host");
            }

            postgresqlSpec.setHost(terminalReader.prompt("PostgreSQL host name (--postgresql-host)", "${POSTGRESQL_HOST}", false));
        }

        if (Strings.isNullOrEmpty(postgresqlSpec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--postgresql-port");
            }

            postgresqlSpec.setPort(terminalReader.prompt("PostgreSQL port number (--postgresql-port)", "${POSTGRESQL_PORT}", false));
        }

        if (Strings.isNullOrEmpty(postgresqlSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--postgresql-database");
            }

            postgresqlSpec.setDatabase(terminalReader.prompt("PostgreSQL database name (--postgresql-database)", "${POSTGRESQL_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(postgresqlSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--postgresql-user");
            }

            postgresqlSpec.setUser(terminalReader.prompt("PostgreSQL user name (--postgresql-user)", "${POSTGRESQL_USER}", false));
        }

        if (Strings.isNullOrEmpty(postgresqlSpec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--postgresql-password");
            }

            postgresqlSpec.setPassword(terminalReader.prompt("PostgreSQL user password (--postgresql-password)", "${POSTGRESQL_PASSWORD}", false));
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
