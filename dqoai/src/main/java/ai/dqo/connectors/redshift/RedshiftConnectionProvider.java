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
package ai.dqo.connectors.redshift;

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
 * Redshift source connection provider.
 */
@Component("redshift-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RedshiftConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    public final static ProviderDialectSettings DIALECT_SETTINGS = new ProviderDialectSettings("\"", "\"", "\"\"", false);

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     */
    @Autowired
    public RedshiftConnectionProvider(BeanFactory beanFactory) {
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
    public RedshiftSourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection) {
        assert connectionSpec != null;
        RedshiftSourceConnection connection = this.beanFactory.getBean(RedshiftSourceConnection.class);
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

        if (redshiftSpec.getSsl() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--redshift-ssl");
            }

            redshiftSpec.setSsl(terminalReader.promptBoolean("Require SSL connection (--redshift-ssl)", true));
        }

        if (Strings.isNullOrEmpty(redshiftSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--redshift-database");
            }

            redshiftSpec.setDatabase(terminalReader.prompt("Redshift database name (--redshift-database)", "${REDSHIFT_DATABASE}", false));
        }

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
