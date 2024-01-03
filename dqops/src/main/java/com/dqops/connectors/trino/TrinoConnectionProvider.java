/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
package com.dqops.connectors.trino;

import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.connectors.AbstractSqlConnectionProvider;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.string.StringCheckUtility;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.columns.Column;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * Trino source connection provider.
 */
@Component("trino-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TrinoConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final TrinoProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings Trino dialect settings.
     */
    @Autowired
    public TrinoConnectionProvider(BeanFactory beanFactory,
                                    TrinoProviderDialectSettings dialectSettings) {
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
    public TrinoSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection, SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        TrinoSourceConnection connection = this.beanFactory.getBean(TrinoSourceConnection.class);
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
        TrinoParametersSpec trinoSpec = connectionSpec.getTrino();
        if (trinoSpec == null) {
            trinoSpec = new TrinoParametersSpec();
            connectionSpec.setTrino(trinoSpec);
        }

        if (Strings.isNullOrEmpty(trinoSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--trino-host");
            }

            trinoSpec.setHost(terminalReader.prompt("Trino host name (--trino-host)", "${TRINO_HOST}", false));
        }

        if (Strings.isNullOrEmpty(trinoSpec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--trino-port");
            }

            trinoSpec.setPort(terminalReader.prompt("Trino port number (--trino-port)", "${TRINO_PORT}", false));
        }

        if (Strings.isNullOrEmpty(trinoSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--trino-database");
            }

            trinoSpec.setDatabase(terminalReader.prompt("Trino database name (--trino-database)", "${TRINO_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(trinoSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--trino-user");
            }

            trinoSpec.setUser(terminalReader.prompt("Trino user name (--trino-user)", "${TRINO_USER}", false));
        }

//        if (Strings.isNullOrEmpty(trinoSpec.getPassword())) {
//            if (isHeadless) {
//                throw new CliRequiredParameterMissingException("--trino-password");
//            }
//
//            trinoSpec.setPassword(terminalReader.prompt("Trino user password (--trino-password)", "${TRINO_PASSWORD}", false));
//        }
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
            return new ColumnTypeSnapshotSpec("REAL");
        }
        else if (columnType == ColumnType.BOOLEAN) {
            return new ColumnTypeSnapshotSpec("BOOLEAN");
        }
        else if (columnType == ColumnType.STRING) {
            return new ColumnTypeSnapshotSpec("VARCHAR");
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
            return new ColumnTypeSnapshotSpec("TIMESTAMP");
        }
        else if (columnType == ColumnType.INSTANT) {
            return new ColumnTypeSnapshotSpec("TIMESTAMP");
        }
        else if (columnType == ColumnType.TEXT) {
            return new ColumnTypeSnapshotSpec("VARCHAR");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
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
        StringBuilder formattedConstant = new StringBuilder();

        if (constant instanceof Boolean) {
            Boolean asBoolean = (Boolean)constant;
            return asBoolean ? "true" : "false";
        }

        if (StringCheckUtility.equalsAny(columnType.getColumnType(), "DATE", "TIME")) {
            formattedConstant.append( columnType.getColumnType().toLowerCase());
            formattedConstant.append(" ");
        }

        // due to bug in presto/trino the datetime yyyy-MM-dd HH:mm:ss format is not supported
        if (constant instanceof LocalDateTime) {
            LocalDateTime asLocalTimeTime = (LocalDateTime)constant;
            return "cast('" + asLocalTimeTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "' as timestamp)";
        }

        formattedConstant.append(super.formatConstant(constant, columnType));
        return formattedConstant.toString();
    }
}
