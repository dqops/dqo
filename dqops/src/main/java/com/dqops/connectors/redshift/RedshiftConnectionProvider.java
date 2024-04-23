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
package com.dqops.connectors.redshift;

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
 * Redshift source connection provider.
 */
@Component("redshift-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RedshiftConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final RedshiftProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings Redshift dialect settings.
     */
    @Autowired
    public RedshiftConnectionProvider(BeanFactory beanFactory,
                                      RedshiftProviderDialectSettings dialectSettings) {
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
    public RedshiftSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection, SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        RedshiftSourceConnection connection = this.beanFactory.getBean(RedshiftSourceConnection.class);
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
        RedshiftParametersSpec redshiftSpec = connectionSpec.getRedshift();
        if (redshiftSpec == null) {
            redshiftSpec = new RedshiftParametersSpec();
            connectionSpec.setRedshift(redshiftSpec);
        }

        if (Strings.isNullOrEmpty(redshiftSpec.getHost())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--redshift-host");
            }
            redshiftSpec.setHost(terminalReader.prompt("Redshift host name (--redshift-host)", "${REDSHIFT_HOST}", false));
        }

        if (Strings.isNullOrEmpty(redshiftSpec.getPort())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--redshift-port");
            }
            redshiftSpec.setPort(terminalReader.prompt("Redshift port number (--redshift-port)", "${REDSHIFT_PORT}", false));
        }

        if (Strings.isNullOrEmpty(redshiftSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--redshift-database");
            }
            redshiftSpec.setDatabase(terminalReader.prompt("Redshift database name (--redshift-database)", "${REDSHIFT_DATABASE}", false));
        }

        RedshiftAuthenticationMode redshiftAuthenticationMode = terminalReader.promptEnum("Redshift AWS authentication mode (--redshift-authentication-mode)", RedshiftAuthenticationMode.class, null, false);
        redshiftSpec.setRedshiftAuthenticationMode(redshiftAuthenticationMode);

        switch (redshiftAuthenticationMode){
            case user_password:
                if (Strings.isNullOrEmpty(redshiftSpec.getUser())) {
                    if (isHeadless) {
                        throw new CliRequiredParameterMissingException("--redshift-user");
                    }
                    redshiftSpec.setUser(terminalReader.prompt("Redshift user name (--redshift-user)", "${REDSHIFT_USER}", false));
                }

                if (Strings.isNullOrEmpty(redshiftSpec.getPassword())) {
                    if (isHeadless) {
                        throw new CliRequiredParameterMissingException("--redshift-password");
                    }
                    redshiftSpec.setPassword(terminalReader.prompt("Redshift user password (--redshift-password)", "${REDSHIFT_PASSWORD}", false));
                }
            case iam:
                if (Strings.isNullOrEmpty(redshiftSpec.getUser())) {
                    if (isHeadless) {
                        throw new CliRequiredParameterMissingException("--redshift-user");
                    }
                    redshiftSpec.setUser(terminalReader.prompt(" AWS AccessKeyId (--redshift-user)", "${REDSHIFT_USER}", false));
                }
                if (Strings.isNullOrEmpty(redshiftSpec.getPassword())) {
                    if (isHeadless) {
                        throw new CliRequiredParameterMissingException("--redshift-password");
                    }
                    redshiftSpec.setPassword(terminalReader.prompt(" AWS SecretAccessKey (--redshift-password)", "${REDSHIFT_PASSWORD}", false));
                }
                break;
            case default_credentials:
                // Default credentials are set automatically from the file when the jdbc connection string creation.
                break;
            default:
                throw new RuntimeException("Given enum is not supported : " + redshiftSpec.getRedshiftAuthenticationMode());
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
        if(constant instanceof Boolean){
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
            return new ColumnTypeSnapshotSpec("TIMESTAMPTZ");
        }
        else if (columnType == ColumnType.TEXT) {
            return new ColumnTypeSnapshotSpec("TEXT");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }
}
