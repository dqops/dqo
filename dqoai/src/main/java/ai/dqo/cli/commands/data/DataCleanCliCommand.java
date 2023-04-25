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
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.completion.completers.ColumnNameCompleter;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.cli.converters.StringToLocalDateCliConverterMonthEnd;
import ai.dqo.cli.converters.StringToLocalDateCliConverterMonthStart;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.data.statistics.factory.StatisticsCollectorTarget;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * "data clean" 2nd level CLI command that deletes data selectively from the data.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "clean", header = "Delete stored data that matches a given condition", description = "Delete stored data that matches certain conditions.It is important to use caution when using this command, as it will permanently delete the selected data and cannot be undone.")
public class DataCleanCliCommand extends BaseCommand implements ICommand {
    private DqoJobQueue dqoJobQueue;
    private DqoQueueJobFactory dqoQueueJobFactory;

    public DataCleanCliCommand() {
    }

    /**
     * Dependency injection constructor.
     * @param dqoJobQueue Job queue.
     * @param dqoQueueJobFactory Job queue factory.
     */
    @Autowired
    public DataCleanCliCommand(DqoJobQueue dqoJobQueue,
                               DqoQueueJobFactory dqoQueueJobFactory) {
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
    }

    @CommandLine.Option(names = {"-er", "--errors"}, description = "Delete the errors")
    private boolean deleteErrors = false;

    @CommandLine.Option(names = {"-st", "--statistics"}, description = "Delete the statistics")
    private boolean deleteStatistics = false;

    @CommandLine.Option(names = {"-cr", "--check-results"}, description = "Delete the check results")
    private boolean deleteCheckResults = false;

    @CommandLine.Option(names = {"-sr", "--sensor-readouts"}, description = "Delete the sensor readouts")
    private boolean deleteSensorReadouts = false;

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            completionCandidates = ConnectionNameCompleter.class,
            required = true)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table"}, description = "Full table name (schema.table), supports wildcard patterns 'sch*.tab*'",
            completionCandidates = FullTableNameCompleter.class,
            required = true)
    private String table;

    @CommandLine.Option(names = {"-b", "--begin"}, description = "Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD",
            required = true, converter = StringToLocalDateCliConverterMonthStart.class)
    private LocalDate begin;

    @CommandLine.Option(names = {"-e", "--end"}, description = "End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD",
            required = true, converter = StringToLocalDateCliConverterMonthEnd.class)
    private LocalDate end;

    @CommandLine.Option(names = {"-col", "--column"}, description = "Column name",
            completionCandidates = ColumnNameCompleter.class)
    private String column;

    @CommandLine.Option(names = {"-ch", "--check"}, description = "Data quality check name")
    private String check;

    @CommandLine.Option(names = {"-ct", "--check-type"}, description = "Data quality check type (adhoc, checkpoint, partitioned)")
    private CheckType checkType;

    @CommandLine.Option(names = {"-cat", "--category"}, description = "Check category name (standard, nulls, numeric, etc.)")
    private String checkCategory;

    @CommandLine.Option(names = {"-sc", "--statistics-collector"}, description = "Data quality statistics collector name")
    private String statisticsCollector;

    @CommandLine.Option(names = {"-stt", "--statistics-target"}, description = "Data quality statistics target (table, column)")
    private StatisticsCollectorTarget statisticsTarget;

    @CommandLine.Option(names = {"-stc", "--statistics-category"}, description = "Statistics category name (standard, nulls, numeric, etc.)")
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

        deleteStoredDataJobParameters.setDeleteErrors(this.deleteErrors);
        deleteStoredDataJobParameters.setDeleteStatistics(this.deleteStatistics);
        deleteStoredDataJobParameters.setDeleteCheckResults(this.deleteCheckResults);
        deleteStoredDataJobParameters.setDeleteSensorReadouts(this.deleteSensorReadouts);

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
        DeleteStoredDataQueueJobParameters deletionParameters = this.createDeletionParameters();

        DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
        deleteStoredDataJob.setDeletionParameters(deletionParameters);
        PushJobResult<DeleteStoredDataQueueJobResult> pushJobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob);
        DeleteStoredDataQueueJobResult jobResult = pushJobResult.getFuture().get();
        return 0;
    }
}
