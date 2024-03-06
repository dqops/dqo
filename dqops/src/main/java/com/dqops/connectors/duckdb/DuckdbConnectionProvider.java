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
package com.dqops.connectors.duckdb;

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

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * DuckDB source connection provider.
 */
@Component("duckdb-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DuckdbConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final DuckdbProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings DuckDB dialect settings.
     */
    @Autowired
    public DuckdbConnectionProvider(BeanFactory beanFactory,
                                    DuckdbProviderDialectSettings dialectSettings) {
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
    public DuckdbSourceConnection createConnection(ConnectionSpec connectionSpec,
                                                   boolean openConnection,
                                                   SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        DuckdbSourceConnection connection = this.beanFactory.getBean(DuckdbSourceConnection.class);
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
        DuckdbParametersSpec duckdbSpec = connectionSpec.getDuckdb();
        if (duckdbSpec == null) {
            duckdbSpec = new DuckdbParametersSpec();
            connectionSpec.setDuckdb(duckdbSpec);
        }

        if (duckdbSpec.getReadMode() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--duckdb-read-mode");
            }
            duckdbSpec.setReadMode(terminalReader.promptEnum("DuckDB read mode.", DuckdbReadMode.class, DuckdbReadMode.files,false));
        }

        if (duckdbSpec.getReadMode().equals(DuckdbReadMode.in_memory) && Strings.isNullOrEmpty(duckdbSpec.getDatabase())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--duckdb-database");
            }
            duckdbSpec.setDatabase(terminalReader.prompt("DuckDB in memory database name (--duckdb-database)", "${DUCKDB_DATABASE}", false));
        }

        if(duckdbSpec.getReadMode().equals(DuckdbReadMode.in_memory)){
            return;
        }

        if(duckdbSpec.getStorageType() == null){
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--duckdb-storage-type");
            }
            duckdbSpec.setStorageType(terminalReader.promptEnum("Type of storage", DuckdbStorageType.class, DuckdbStorageType.local,true));
        }

        if(duckdbSpec.getFilesFormatType() == null){
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--duckdb-files-format-type");
            }
            duckdbSpec.setFilesFormatType(terminalReader.promptEnum("Type of source files for DuckDB", DuckdbFilesFormatType.class, null, false));
        }

        if (duckdbSpec.getDirectories().isEmpty()) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--duckdb-directories");
            }

            String directoriesRaw = terminalReader.prompt("Virtual schema names and paths (in a pattern schema=path)", null, false);
            if(!directoriesRaw.contains("/") && !directoriesRaw.contains("\\")){
                throw new RuntimeException("The provided path is invalid. The path should be an absolute path to the directory with folders or files. On Windows use double backslash (\\\\).");
            }
            List<String> directories = Arrays.stream(directoriesRaw.split(",")).collect(Collectors.toList());

            for (String directory : directories) {
                List<String> schemaDirectory = Arrays.stream(directory.split("="))
                        .map(String::trim)
                        .collect(Collectors.toList());
                if(schemaDirectory.size() != 2){
                    throw new RuntimeException("Unbalanced values for " + directory + "." +
                            "Ensure you provide directories in a schema=path pattern.");
                }
                duckdbSpec.getDirectories().put(schemaDirectory.get(0), schemaDirectory.get(1));
            }

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
