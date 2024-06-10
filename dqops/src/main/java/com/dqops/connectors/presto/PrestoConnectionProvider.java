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
package com.dqops.connectors.presto;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * Presto source connection provider.
 */
@Component("presto-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PrestoConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final PrestoProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings Presto dialect settings.
     */
    @Autowired
    public PrestoConnectionProvider(BeanFactory beanFactory,
                                    PrestoProviderDialectSettings dialectSettings) {
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
    public PrestoSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection, SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        PrestoSourceConnection connection = this.beanFactory.getBean(PrestoSourceConnection.class);
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
        PrestoParametersSpec prestoSpec = connectionSpec.getPresto();
        if (prestoSpec == null) {
            prestoSpec = new PrestoParametersSpec();
            connectionSpec.setPresto(prestoSpec);
        }

        if (Strings.isNullOrEmpty(prestoSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--presto-host");
            }

            prestoSpec.setHost(terminalReader.prompt("Presto host name (--presto-host)", "${PRESTO_HOST}", false));
        }

        if (Strings.isNullOrEmpty(prestoSpec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--presto-port");
            }

            prestoSpec.setPort(terminalReader.prompt("Presto port number (--presto-port)", "${PRESTO_PORT}", false));
        }

        if (Strings.isNullOrEmpty(prestoSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--presto-database");
            }

            prestoSpec.setDatabase(terminalReader.prompt("Presto database name (--presto-database)", "${PRESTO_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(prestoSpec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--presto-user");
            }

            prestoSpec.setUser(terminalReader.prompt("Presto user name (--presto-user)", "${PRESTO_USER}", false));
        }

        if (Strings.isNullOrEmpty(prestoSpec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--presto-password");
            }

            prestoSpec.setPassword(terminalReader.prompt("Presto user password (--presto-password)", "${PRESTO_PASSWORD}", false));
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

        // due to bug in presto the datetime yyyy-MM-dd HH:mm:ss format is not supported
        if (constant instanceof LocalDateTime) {
            LocalDateTime asLocalTimeTime = (LocalDateTime)constant;
            return "cast('" + asLocalTimeTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "' as timestamp)";
        }

        if (constant instanceof LocalDate) {
            LocalDate asLocalDate = (LocalDate)constant;
            return "cast('" + asLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "' as date)";
        }

        formattedConstant.append(super.formatConstant(constant, columnType));
        return formattedConstant.toString();
    }

}
