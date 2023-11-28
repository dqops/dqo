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
package com.dqops.connectors.spark;

import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.connectors.AbstractSqlConnectionProvider;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.oracle.OracleParametersSpec;            // todo
import com.dqops.connectors.oracle.OracleProviderDialectSettings;   // todo
import com.dqops.connectors.oracle.OracleSourceConnection;          // todo
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
 * Spark source connection provider.
 */
@Component("spark-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SparkConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final SparkProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings Spark dialect settings.
     */
    @Autowired
    public SparkConnectionProvider(BeanFactory beanFactory,
                                   SparkProviderDialectSettings dialectSettings) {
        this.beanFactory = beanFactory;
        this.dialectSettings = dialectSettings;
    }

    /**
     * Creates a connection to a target data source.
     *
     * @param connectionSpec Connection specification.
     * @param openConnection Open the connection after creating.
     * @return Connection object.
     */
    @Override
    public SparkSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection) {
        assert connectionSpec != null;
        SparkSourceConnection connection = this.beanFactory.getBean(SparkSourceConnection.class);
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
    public void promptForConnectionParameters(
            ConnectionSpec connectionSpec,
            boolean isHeadless,
            TerminalReader terminalReader,
            TerminalWriter terminalWriter) { // todo

        SparkParametersSpec sparkSpec = connectionSpec.getSpark();
        if (sparkSpec == null) {
            sparkSpec = new SparkParametersSpec();
            connectionSpec.setSpark(sparkSpec);
        }

//        if (Strings.isNullOrEmpty(sparkSpec.getHost())) {
//            if (isHeadless) {
//                throw new CliRequiredParameterMissingException("--oracle-host");
//            }
//
//            sparkSpec.setHost(terminalReader.prompt("Oracle host name (--oracle-host)", "${ORACLE_HOST}", false));
//        }
//
//        if (Strings.isNullOrEmpty(sparkSpec.getPort())) {
//            if (isHeadless) {
//                throw new CliRequiredParameterMissingException("--oracle-port");
//            }
//
//            sparkSpec.setPort(terminalReader.prompt("Oracle port number (--oracle-port)", "${ORACLE_PORT}", false));
//        }
//
//        if (Strings.isNullOrEmpty(sparkSpec.getDatabase())) {
//            if (isHeadless) {
//                throw new CliRequiredParameterMissingException("--oracle-database");
//            }
//
//            sparkSpec.setDatabase(terminalReader.prompt("Oracle database name (--oracle-database)", "${ORACLE_DATABASE}", false));
//        }
//
//        if (Strings.isNullOrEmpty(sparkSpec.getUser())) {
//            if (isHeadless) {
//                throw new CliRequiredParameterMissingException("--oracle-user");
//            }
//
//            sparkSpec.setUser(terminalReader.prompt("Oracle user name (--oracle-user)", "${ORACLE_USER}", false));
//        }
//
//        if (Strings.isNullOrEmpty(sparkSpec.getPassword())) {
//            if (isHeadless) {
//                throw new CliRequiredParameterMissingException("--oracle-password");
//            }
//
//            sparkSpec.setPassword(terminalReader.prompt("Oracle user password (--oracle-password)", "${ORACLE_PASSWORD}", false));
//        }
    }

    /**
     * Formats a constant for the target database.
     *
     * @param constant   Constant to be formatted.
     * @param columnType Column type snapshot.
     * @return Formatted constant.
     */
    @Override
    public String formatConstant(Object constant, ColumnTypeSnapshotSpec columnType) { // todo
        if (constant instanceof Boolean) {
            Boolean asBoolean = (Boolean)constant;
            return asBoolean ? "1" : "0";
        }
        return super.formatConstant(constant, columnType);
    }

    /**
     * Proposes a physical (provider specific) column type that is able to store the data of the given Tablesaw column.
     *
     * @param dataColumn Tablesaw column with data that should be stored.
     * @return Column type snapshot.
     */
    @Override
    public ColumnTypeSnapshotSpec proposePhysicalColumnType(Column<?> dataColumn) { // todo
        ColumnType columnType = dataColumn.type();

        if (columnType == ColumnType.SHORT) {
            return new ColumnTypeSnapshotSpec("NUMBER", null, 10, 0);
        }
        else if (columnType == ColumnType.INTEGER) {
            return new ColumnTypeSnapshotSpec("NUMBER", null, 20, 0);
        }
        else if (columnType == ColumnType.LONG) {
            return new ColumnTypeSnapshotSpec("NUMBER", null, 38, 0);
        }
        else if (columnType == ColumnType.FLOAT) {
            return new ColumnTypeSnapshotSpec("BINARY_FLOAT");
        }
        else if (columnType == ColumnType.BOOLEAN) {
            return new ColumnTypeSnapshotSpec("NUMBER", null, 1, 0);
        }
        else if (columnType == ColumnType.STRING) {
            return new ColumnTypeSnapshotSpec("NVARCHAR2", 255);
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("BINARY_DOUBLE");
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
            return new ColumnTypeSnapshotSpec("TIMESTAMP WITH TIME ZONE");
        }
        else if (columnType == ColumnType.TEXT) {
            return new ColumnTypeSnapshotSpec("NCLOB");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }
}
