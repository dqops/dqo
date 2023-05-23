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
package ai.dqo.cli.commands.data;

import ai.dqo.checks.CheckType;
import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.data.impl.DataCliService;
import ai.dqo.cli.completion.completers.ColumnNameCompleter;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.cli.converters.StringToLocalDateCliConverterMonthEnd;
import ai.dqo.cli.converters.StringToLocalDateCliConverterMonthStart;
import ai.dqo.cli.terminal.TablesawDatasetTableModel;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.data.statistics.factory.StatisticsCollectorTarget;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * "data delete" 2nd level CLI command that selectively deletes stored data.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "delete", header = "Deletes stored data that matches the specified conditions", description = "Deletes stored data that matches specified conditions. Be careful when using this command, as it permanently deletes the selected data and cannot be undone.")
public class DataDeleteCliCommand extends BaseCommand implements ICommand {
    private DataCliService dataCliService;
    private TerminalReader terminalReader;
    private TerminalWriter terminalWriter;
    private TerminalTableWritter terminalTableWritter;


    public DataDeleteCliCommand() {
    }

    /**
     * Dependency injection constructor.
     *
     * @param dataCliService       Data CLI service.
     * @param terminalReader       Terminal reader.
     * @param terminalWriter       Terminal writer.
     * @param terminalTableWritter Terminal table writer.
     */
    @Autowired
    public DataDeleteCliCommand(DataCliService dataCliService,
                                TerminalReader terminalReader, TerminalWriter terminalWriter, TerminalTableWritter terminalTableWritter) {
        this.dataCliService = dataCliService;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.terminalTableWritter = terminalTableWritter;
    }

    @CommandLine.Option(names = {"-er", "--errors"}, description = "Delete the execution errors")
    private boolean deleteErrors = false;

    @CommandLine.Option(names = {"-st", "--statistics"}, description = "Delete the statistics")
    private boolean deleteStatistics = false;

    @CommandLine.Option(names = {"-cr", "--check-results"}, description = "Delete the check results")
    private boolean deleteCheckResults = false;

    @CommandLine.Option(names = {"-sr", "--sensor-readouts"}, description = "Delete the sensor readouts")
    private boolean deleteSensorReadouts = false;

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            completionCandidates = ConnectionNameCompleter.class)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table"}, description = "Full table name (schema.table), supports wildcard patterns 'sch*.tab*'",
            completionCandidates = FullTableNameCompleter.class)
    private String table;

    @CommandLine.Option(names = {"-b", "--begin"}, description = "Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD",
            converter = StringToLocalDateCliConverterMonthStart.class)
    private LocalDate begin;

    @CommandLine.Option(names = {"-e", "--end"}, description = "End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD",
            converter = StringToLocalDateCliConverterMonthEnd.class)
    private LocalDate end;

    @CommandLine.Option(names = {"-col", "--column"}, description = "Column name",
            completionCandidates = ColumnNameCompleter.class)
    private String column;

    @CommandLine.Option(names = {"-ch", "--check"}, description = "Data quality check name")
    private String check;

    @CommandLine.Option(names = {"-ct", "--check-type"}, description = "Data quality check type (profiling, recurring, partitioned)")
    private CheckType checkType;

    @CommandLine.Option(names = {"-cat", "--category"}, description = "Check category name (volume, nulls, numeric, etc.)")
    private String checkCategory;

    @CommandLine.Option(names = {"-sc", "--statistics-collector"}, description = "Data quality statistics collector name")
    private String statisticsCollector;

    @CommandLine.Option(names = {"-stt", "--statistics-target"}, description = "Data quality statistics target (table, column)")
    private StatisticsCollectorTarget statisticsTarget;

    @CommandLine.Option(names = {"-stc", "--statistics-category"}, description = "Statistics category name (volume, nulls, numeric, etc.)")
    private String statisticsCategory;

    @CommandLine.Option(names = {"-s", "--sensor"}, description = "Data quality sensor name (sensor definition or sensor name)")
    private String sensor;

    @CommandLine.Option(names = {"-tg", "--time-gradient"}, description = "Time gradient of the sensor")
    private String timeGradient;

    @CommandLine.Option(names = {"-ds", "--data-stream"}, description = "Data stream hierarchy level filter (tag)")
    private String dataStream;

    @CommandLine.Option(names = {"-qd", "--quality-dimension"}, description = "Data quality dimension")
    private String qualityDimension;


    protected DeleteStoredDataQueueJobParameters createDeletionParameters() {
        DeleteStoredDataQueueJobParameters deleteStoredDataJobParameters = new DeleteStoredDataQueueJobParameters(
                this.connection,
                this.table,
                this.begin,
                this.end
        );

        if (!(this.deleteErrors || this.deleteStatistics || this.deleteSensorReadouts || this.deleteCheckResults)) {
            deleteStoredDataJobParameters.setDeleteErrors(true);
            deleteStoredDataJobParameters.setDeleteStatistics(true);
            deleteStoredDataJobParameters.setDeleteCheckResults(true);
            deleteStoredDataJobParameters.setDeleteSensorReadouts(true);
        }
        else {
            deleteStoredDataJobParameters.setDeleteErrors(this.deleteErrors);
            deleteStoredDataJobParameters.setDeleteStatistics(this.deleteStatistics);
            deleteStoredDataJobParameters.setDeleteCheckResults(this.deleteCheckResults);
            deleteStoredDataJobParameters.setDeleteSensorReadouts(this.deleteSensorReadouts);
        }

        if (!Strings.isNullOrEmpty(this.checkCategory)) {
            deleteStoredDataJobParameters.setCheckCategory(this.checkCategory);
        }

        if (!Strings.isNullOrEmpty(this.check)) {
            deleteStoredDataJobParameters.setCheckName(this.check);
        }

        if (!Strings.isNullOrEmpty(this.statisticsCategory)) {
            deleteStoredDataJobParameters.setCollectorCategory(this.statisticsCategory);
        }

        if (!Strings.isNullOrEmpty(this.statisticsCollector)) {
            deleteStoredDataJobParameters.setCollectorName(this.statisticsCollector);
        }

        if (!Strings.isNullOrEmpty(this.column)) {
            List<String> columnNames = new LinkedList<>(){{add(column);}};
            deleteStoredDataJobParameters.setColumnNames(columnNames);
        }

        if (!Strings.isNullOrEmpty(this.sensor)) {
            deleteStoredDataJobParameters.setSensorName(this.sensor);
        }

        if (!Strings.isNullOrEmpty(this.dataStream)) {
            deleteStoredDataJobParameters.setDataStreamName(this.dataStream);
        }

        if (!Strings.isNullOrEmpty(this.qualityDimension)) {
            deleteStoredDataJobParameters.setQualityDimension(this.qualityDimension);
        }

        if (!Strings.isNullOrEmpty(this.timeGradient)) {
            deleteStoredDataJobParameters.setTimeGradient(this.timeGradient);
        }

        if (this.checkType != null) {
            deleteStoredDataJobParameters.setCheckType(this.checkType.getDisplayName());
        }

        if (this.statisticsTarget != null) {
            deleteStoredDataJobParameters.setCollectorTarget(this.statisticsTarget.name());
        }

        return deleteStoredDataJobParameters;
    }


    /**
     * Cleans the data based on the supplied filters, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        if (Strings.isNullOrEmpty(this.connection)) {
            throwRequiredParameterMissingIfHeadless("--connection");
            this.connection = this.terminalReader.prompt("Connection name (--connection)", null, false);
        }

        DeleteStoredDataQueueJobParameters deletionParameters = this.createDeletionParameters();
        CliOperationStatus cliOperationStatus = this.dataCliService.deleteStoredData(deletionParameters);
        this.terminalWriter.writeLine(cliOperationStatus.getMessage());

        Table cliOperationStatusTable = cliOperationStatus.getTable();
        if (cliOperationStatusTable != null) {
            TablesawDatasetTableModel tablesawDatasetTableModel = new TablesawDatasetTableModel(cliOperationStatus.getTable());
            this.terminalTableWritter.writeTable(tablesawDatasetTableModel, true);
        }

        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
