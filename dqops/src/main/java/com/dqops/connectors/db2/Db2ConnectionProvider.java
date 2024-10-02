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
package com.dqops.connectors.db2;

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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * IBM DB2 source connection provider.
 */
@Component("db2-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Db2ConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final Db2ProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings DB2 dialect settings.
     */
    @Autowired
    public Db2ConnectionProvider(BeanFactory beanFactory,
                                 Db2ProviderDialectSettings dialectSettings) {
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
    public Db2SourceConnection createConnection(ConnectionSpec connectionSpec,
                                                boolean openConnection,
                                                SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        Db2SourceConnection connection = this.beanFactory.getBean(Db2SourceConnection.class);
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
    public void promptForConnectionParameters(
            ConnectionSpec connectionSpec,
            boolean isHeadless,
            TerminalReader terminalReader,
            TerminalWriter terminalWriter) {

        Db2ParametersSpec db2Spec = connectionSpec.getDb2();
        if (db2Spec == null) {
            db2Spec = new Db2ParametersSpec();
            connectionSpec.setDb2(db2Spec);
        }

        if (Strings.isNullOrEmpty(db2Spec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--db2-host");
            }

            db2Spec.setHost(terminalReader.prompt("DB2 host name (--db2-host)", "${DB2_HOST}", false));
        }

        if (Strings.isNullOrEmpty(db2Spec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--db2-port");
            }

            db2Spec.setPort(terminalReader.prompt("DB2 port number (--db2-port)", "${DB2_PORT}", false));
        }

        if (Strings.isNullOrEmpty(db2Spec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--db2-database");
            }

            db2Spec.setDatabase(terminalReader.prompt("DB2 database name (--db2-database)", "${DB2_DATABASE}", false));
        }

        if (Strings.isNullOrEmpty(db2Spec.getUser())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--db2-user");
            }

            db2Spec.setUser(terminalReader.prompt("DB2 user name (--db2-user)", "${DB2_USER}", false));
        }

        if (Strings.isNullOrEmpty(db2Spec.getPassword())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--db2-password");
            }

            db2Spec.setPassword(terminalReader.prompt("DB2 user password (--db2-password)", "${DB2_PASSWORD}", false));
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

        if (constant instanceof Boolean) {
            Boolean asBoolean = (Boolean)constant;
            return asBoolean ? "true" : "false";
        }

        if (constant instanceof LocalDate) {
            LocalDate asLocalDate = (LocalDate)constant;
            return "date('" + asLocalDate.format(DateTimeFormatter.ISO_DATE) + "')";
        }

        if (constant instanceof LocalDateTime) {
            LocalDateTime asLocalTimeTime = (LocalDateTime)constant;
            return "cast('" + asLocalTimeTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "' as timestamp)";
        }

        if (constant instanceof Instant) {
            Instant asInstant = (Instant)constant;
            return "cast('" + asInstant + "' as timestamp)";
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
            return new ColumnTypeSnapshotSpec("VARCHAR(256)");
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("DOUBLE");
        }
        else if (columnType == ColumnType.LOCAL_DATE) {
            return new ColumnTypeSnapshotSpec("DATE");
        }
        else if (columnType == ColumnType.LOCAL_TIME) {
            return new ColumnTypeSnapshotSpec("TIMESTAMP");
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
