/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.cli.commands.registry;

import ai.dqo.checks.CheckType;
import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.completion.completers.ColumnNameCompleter;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.cli.converters.StringToLocalDateCliConverter;
import ai.dqo.cli.output.OutputFormatService;
import ai.dqo.cli.terminal.FileWritter;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.data.profilingresults.factory.ProfilerTargetType;
import ai.dqo.utils.serialization.JsonSerializer;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.time.LocalDate;

/**
 * "registry clean" 2nd level CLI command that deletes data selectively from the registry.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "clean", description = "Delete stored registry data matching specified filters")
public class RegistryCleanCliCommand extends BaseCommand implements ICommand {
    private TerminalWriter terminalWriter;
    private TerminalTableWritter terminalTableWritter;
    private JsonSerializer jsonSerializer;
    private OutputFormatService outputFormatService;
    private FileWritter fileWritter;
    private DqoJobQueue dqoJobQueue;
    private DqoQueueJobFactory dqoQueueJobFactory;

    public RegistryCleanCliCommand() {
    }

    /**
     * Dependency injection constructor.
     * @param terminalWriter Terminal writer.
     * @param terminalTableWritter Terminal table writer.
     * @param jsonSerializer Json serializer.
     * @param outputFormatService Output format service.
     * @param fileWritter File writer.
     * @param dqoJobQueue Job queue.
     * @param dqoQueueJobFactory Job queue factory.
     */
    @Autowired
    public RegistryCleanCliCommand(TerminalWriter terminalWriter,
                                   TerminalTableWritter terminalTableWritter,
                                   JsonSerializer jsonSerializer,
                                   OutputFormatService outputFormatService,
                                   FileWritter fileWritter,
                                   DqoJobQueue dqoJobQueue,
                                   DqoQueueJobFactory dqoQueueJobFactory) {
        this.terminalWriter = terminalWriter;
        this.terminalTableWritter = terminalTableWritter;
        this.jsonSerializer = jsonSerializer;
        this.outputFormatService = outputFormatService;
        this.fileWritter = fileWritter;
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
    }

    @CommandLine.Option(names = {"-er", "--errors"}, description = "Delete the errors")
    private boolean deleteErrors = false;

    @CommandLine.Option(names = {"-pr", "--profiling-results"}, description = "Delete the profiling results")
    private boolean deleteProfilingResults = false;

    @CommandLine.Option(names = {"-rr", "--rule-results"}, description = "Delete the rule results")
    private boolean deleteRuleResults = false;

    @CommandLine.Option(names = {"-sr", "--sensor-readouts"}, description = "Delete the sensor readouts")
    private boolean deleteSensorReadouts = false;

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            completionCandidates = ConnectionNameCompleter.class,
            required = true)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table"}, description = "Full table name (schema.table)",
            completionCandidates = FullTableNameCompleter.class,
            required = true)
    private String table;

    @CommandLine.Option(names = {"-b", "--begin"}, description = "Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD",
            required = true, converter = StringToLocalDateCliConverter.class)
    private LocalDate begin;

    @CommandLine.Option(names = {"-e", "--end"}, description = "End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD",
            required = true, converter = StringToLocalDateCliConverter.class)
    private LocalDate end;

    @CommandLine.Option(names = {"-day", "--daily-detailed"},
            description = "Should the period consider days of the time period [begin, end]")
    private boolean dailyDetailedSpan = false;

    @CommandLine.Option(names = {"-col", "--column"}, description = "Column name",
            completionCandidates = ColumnNameCompleter.class)
    private String column;

    @CommandLine.Option(names = {"-ch", "--check"}, description = "Data quality check name")
    private String check;

    @CommandLine.Option(names = {"-ct", "--check-type"}, description = "Data quality check type (adhoc, checkpoint, partitioned)")
    private CheckType checkType;

    @CommandLine.Option(names = {"-cat", "--category"}, description = "Check category name (standard, nulls, numeric, etc.)")
    private String checkCategory;

    @CommandLine.Option(names = {"-p", "--profiler"}, description = "Data quality profiler name")
    private String profiler;

    @CommandLine.Option(names = {"-pt", "--profiler-type"}, description = "Data quality profiler target type (table, column)")
    private ProfilerTargetType profilerType;

    @CommandLine.Option(names = {"-pcat", "--profiler-category"}, description = "Profiler category name (standard, nulls, numeric, etc.)")
    private String profilerCategory;

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
        deleteStoredDataJobParameters.setDeleteProfilingResults(this.deleteProfilingResults);
        deleteStoredDataJobParameters.setDeleteRuleResults(this.deleteRuleResults);
        deleteStoredDataJobParameters.setDeleteSensorReadouts(this.deleteSensorReadouts);
        
        deleteStoredDataJobParameters.setIgnoreDateDay(!this.dailyDetailedSpan);

        if (!Strings.isNullOrEmpty(this.checkCategory)) {
            deleteStoredDataJobParameters.setCheckCategory(this.checkCategory);
        }

        if (!Strings.isNullOrEmpty(this.check)) {
            deleteStoredDataJobParameters.setCheckName(this.check);
        }

        if (!Strings.isNullOrEmpty(this.profilerCategory)) {
            deleteStoredDataJobParameters.setProfilerCategory(this.profilerCategory);
        }

        if (!Strings.isNullOrEmpty(this.profiler)) {
            deleteStoredDataJobParameters.setProfilerName(this.profiler);
        }

        if (!Strings.isNullOrEmpty(this.column)) {
            deleteStoredDataJobParameters.setColumnName(this.column);
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

        if (this.profilerType != null) {
            deleteStoredDataJobParameters.setProfilerType(this.profilerType.name());
        }
        
        return deleteStoredDataJobParameters;
    }


    /**
     * Cleans the registry based on the supplied filters, or throws an exception if unable to do so.
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
