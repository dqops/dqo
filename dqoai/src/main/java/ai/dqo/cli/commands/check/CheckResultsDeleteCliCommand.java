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
package ai.dqo.cli.commands.check;

import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.check.impl.CheckService;
import ai.dqo.cli.commands.converters.StringToLocalDateCliConverter;
import ai.dqo.cli.completion.completedcommands.ITableNameCommand;
import ai.dqo.cli.completion.completers.ColumnNameCompleter;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.cli.output.OutputFormatService;
import ai.dqo.cli.terminal.FileWritter;
import ai.dqo.cli.terminal.TablesawDatasetTableModel;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJob;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJobResult;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.utils.serialization.JsonSerializer;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * "check results delete" 3rd level CLI command that deletes results of a specified data quality check.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "delete", description = "Delete data quality check results matching specified filters")
public class CheckResultsDeleteCliCommand extends BaseCommand implements ICommand, ITableNameCommand {
    private final TerminalWriter terminalWriter;
    private final TerminalTableWritter terminalTableWritter;
    private final CheckService checkService;
    private JsonSerializer jsonSerializer;
    private final OutputFormatService outputFormatService;
    private final FileWritter fileWritter;
    private final DqoJobQueue dqoJobQueue;
    private final DqoQueueJobFactory dqoQueueJobFactory;

    /**
     * Dependency injection constructor.
     * @param terminalWriter Terminal writer.
     * @param checkService Check implementation service.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public CheckResultsDeleteCliCommand(TerminalWriter terminalWriter,
                                        TerminalTableWritter terminalTableWritter,
                                        CheckService checkService,
                                        JsonSerializer jsonSerializer,
                                        OutputFormatService outputFormatService,
                                        FileWritter fileWritter,
                                        DqoJobQueue dqoJobQueue,
                                        DqoQueueJobFactory dqoQueueJobFactory) {
        this.terminalWriter = terminalWriter;
        this.terminalTableWritter = terminalTableWritter;
        this.checkService = checkService;
        this.jsonSerializer = jsonSerializer;
        this.outputFormatService = outputFormatService;
        this.fileWritter = fileWritter;
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            completionCandidates = ConnectionNameCompleter.class,
            required = true)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table"}, description = "Full table name (schema.table)",
            completionCandidates = FullTableNameCompleter.class,
            required = true)
    private String table;

    @CommandLine.Option(names = "--begin", description = "Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD",
            required = true, converter = StringToLocalDateCliConverter.class, paramLabel = "yyyy.MM or yyyy.MM.dd")
    private LocalDate begin;

    @CommandLine.Option(names = "--end", description = "End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD",
            required = true, converter = StringToLocalDateCliConverter.class, paramLabel = "yyyy.MM or yyyy.MM.dd")
    private LocalDate end;

    @CommandLine.Option(names = {"-day", "--daily-detailed"},
            description = "Should the period consider days of the time period [begin, end]")
    private boolean dailyDetailedSpan = false;

    @CommandLine.Option(names = {"-col", "--column"}, description = "Column name",
            completionCandidates = ColumnNameCompleter.class)
    private String column;

    @CommandLine.Option(names = {"-ch", "--check"}, description = "Data quality check name")
    private String check;

    @CommandLine.Option(names = {"-s", "--sensor"}, description = "Data quality sensor name (sensor definition or sensor name)")
    private String sensor;

    @CommandLine.Option(names = {"-ct", "--check-type"}, description = "Data quality check type (adhoc, checkpoint, partitioned)")
    private CheckType checkType;

    // TODO: Change to time gradient for parquet compliance.
    @CommandLine.Option(names = {"-ts", "--time-scale"}, description = "Time scale for checkpoint and partitioned checks (daily, monthly, etc.)")
    private CheckTimeScale timeScale;

    @CommandLine.Option(names = {"-cat", "--category"}, description = "Check category name (standard, nulls, numeric, etc.)")
    private String checkCategory;

    @CommandLine.Option(names = {"-ds", "--data-stream"}, description = "Data stream hierarchy level filter (tag)")
    private String dataStream;

    @CommandLine.Option(names = {"-qd", "--quality-dimension"}, description = "Data quality dimension")
    private String qualityDimension;


    /**
     * Gets the connection name.
     * @return Connection name.
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Sets the connection name.
     * @param connection Connection name.
     */
    public void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Get the schema.table filter.
     * @return schema.table filter.
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the schema.table filter.
     * @param table Full table name filter.
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Get the beginning of the time period.
     * @return Beginning of the period for deletion.
     */
    public LocalDate getBegin() {
        return begin;
    }

    /**
     * Set the beginning of the time period.
     * @param begin New value to set for the start of the deletion period.
     */
    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public boolean isDailyDetailedSpan() {
        return dailyDetailedSpan;
    }

    public void setDailyDetailedSpan(boolean dailyDetailedSpan) {
        this.dailyDetailedSpan = dailyDetailedSpan;
    }

    /**
     * Get the column name filter.
     * @return Column name filter.
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets the column name filter.
     * @param column Column name filter.
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * Get the check filter.
     * @return Check filter.
     */
    public String getCheck() {
        return check;
    }

    /**
     * Sets the check name filter.
     * @param check Check name filter.
     */
    public void setCheck(String check) {
        this.check = check;
    }

    /**
     * Gets the sensor name or sensor definition filter.
     * @return Sensor name filter.
     */
    public String getSensor() {
        return sensor;
    }

    /**
     * Sets the sensor name filter.
     * @param sensor Sensor name.
     */
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    /**
     * Returns the check type (category).
     * @return Check type (category).
     */
    public CheckType getCheckType() {
        return checkType;
    }

    /**
     * Sets the check type to filter.
     * @param checkType Check type filter.
     */
    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    /**
     * Gets the time scale filter for checkpoint and partitioned checks.
     * @return Time scale filter.
     */
    public CheckTimeScale getTimeScale() {
        return timeScale;
    }

    /**
     * Sets the time scale filter for checkpoint and partitioned checks.
     * @param timeScale Time scale filter.
     */
    public void setTimeScale(CheckTimeScale timeScale) {
        this.timeScale = timeScale;
    }

    /**
     * Returns a check category filter.
     * @return Check category filter.
     */
    public String getCheckCategory() {
        return checkCategory;
    }

    /**
     * Sets the checks category filter.
     * @param checkCategory Check category filter.
     */
    public void setCheckCategory(String checkCategory) {
        this.checkCategory = checkCategory;
    }

    /**
     * Returns a data stream hierarchy tag for which data will be deleted.
     * @return Data stream hierarchy tag.
     */
    public String getDataStream() {
        return dataStream;
    }

    /**
     * Sets a data stream tag for deletion.
     * @param dataStream Data stream name tag.
     */
    public void setDataStream(String dataStream) {
        this.dataStream = dataStream;
    }

    public String getQualityDimension() {
        return qualityDimension;
    }

    public void setQualityDimension(String qualityDimension) {
        this.qualityDimension = qualityDimension;
    }

    protected DeleteStoredDataQueueJobParameters createDeletionParameters() {
        DeleteStoredDataQueueJobParameters result = new DeleteStoredDataQueueJobParameters(
                this.connection,
                this.table,
                this.begin,
                this.end
        );
        // TODO: Implement choosing a subset to delete. $A \subseteq \{ ruleresults, sensorreadouts, profilingresults, errors \}$
        result.setDeleteRuleResults(true);
        result.setIgnoreDateDay(!this.dailyDetailedSpan);

        if (!Strings.isNullOrEmpty(this.checkCategory)) {
            result.setCheckCategory(this.checkCategory);
        }

        if (!Strings.isNullOrEmpty(this.check)) {
            result.setCheckName(this.check);
        }

        if (!Strings.isNullOrEmpty(this.column)) {
            result.setColumnName(this.column);
        }

        if (!Strings.isNullOrEmpty(this.dataStream)) {
            result.setDataStreamName(this.dataStream);
        }

        if (!Strings.isNullOrEmpty(this.qualityDimension)) {
            result.setQualityDimension(this.qualityDimension);
        }

        if (this.checkType != null) {
            result.setCheckType(this.checkType.getDisplayName());
        }

        if (this.timeScale != null) {
            result.setTimeGradient(this.timeScale.name());
        }

        return result;
    }


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        DeleteStoredDataQueueJobParameters deletionParameters = this.createDeletionParameters();

        DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
        deleteStoredDataJob.setDeletionParameters(deletionParameters);
        PushJobResult<DeleteStoredDataQueueJobResult> pushJobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob);
        pushJobResult.wait(3000);
        return 0;
    }
}
