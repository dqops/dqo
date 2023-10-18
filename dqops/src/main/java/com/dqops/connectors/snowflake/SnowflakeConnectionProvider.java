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
package com.dqops.connectors.snowflake;

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
 * Snowflake source connection provider.
 */
@Component("snowflake-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SnowflakeConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final SnowflakeProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings Snowflake dialect settings.
     */
    @Autowired
    public SnowflakeConnectionProvider(BeanFactory beanFactory,
                                       SnowflakeProviderDialectSettings dialectSettings) {
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
    public SnowflakeSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection, SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        SnowflakeSourceConnection connection = this.beanFactory.getBean(SnowflakeSourceConnection.class);
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
        SnowflakeParametersSpec snowflakeSpec = connectionSpec.getSnowflake();
        if (snowflakeSpec == null) {
            snowflakeSpec = new SnowflakeParametersSpec();
            connectionSpec.setSnowflake(snowflakeSpec);
        }

        if (Strings.isNullOrEmpty(snowflakeSpec.getAccount())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--snowflake-account");
            }

            snowflakeSpec.setAccount(terminalReader.prompt("Snowflake account name (--snowflake-account)", "${SNOWFLAKE_ACCOUNT}", false));
        }

        if (Strings.isNullOrEmpty(snowflakeSpec.getWarehouse())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--snowflake-warehouse");
            }

            snowflakeSpec.setWarehouse(terminalReader.prompt("Snowflake warehouse name (--snowflake-warehouse)", "${SNOWFLAKE_WAREHOUSE}", false));
        }

        if (Strings.isNullOrEmpty(snowflakeSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--snowflake-database");
            }

            snowflakeSpec.setDatabase(terminalReader.prompt("Snowflake database name (--snowflake-database)", "${SNOWFLAKE_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(snowflakeSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--snowflake-user");
            }

            snowflakeSpec.setUser(terminalReader.prompt("Snowflake user name (--snowflake-user)", "${SNOWFLAKE_USER}", false));
        }

        // TODO: support password or private key authentication (but here we must ask for the authentication method, show a prompt with a value selection)
        if (Strings.isNullOrEmpty(snowflakeSpec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--snowflake-password");
            }

            snowflakeSpec.setPassword(terminalReader.prompt("Snowflake user password (--snowflake-password)", "${SNOWFLAKE_PASSWORD}", false));
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
            return new ColumnTypeSnapshotSpec("SMALLINT");
        }
        else if (columnType == ColumnType.INTEGER) {
            return new ColumnTypeSnapshotSpec("INTEGER");
        }
        else if (columnType == ColumnType.LONG) {
            return new ColumnTypeSnapshotSpec("BIGINT");
        }
        else if (columnType == ColumnType.FLOAT) {
            return new ColumnTypeSnapshotSpec("FLOAT4");
        }
        else if (columnType == ColumnType.BOOLEAN) {
            return new ColumnTypeSnapshotSpec("BOOLEAN");
        }
        else if (columnType == ColumnType.STRING) {
            return new ColumnTypeSnapshotSpec("STRING");
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("FLOAT8");
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
            return new ColumnTypeSnapshotSpec("STRING");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }
}
