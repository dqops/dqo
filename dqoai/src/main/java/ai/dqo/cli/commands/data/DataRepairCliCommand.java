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
package ai.dqo.cli.commands.data;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.completion.completers.FullTableNameCompleter;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.RepairStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.RepairStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.RepairStoredDataQueueJobResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "data repair" 2nd level CLI command that deletes corrupted files from stored data, leaving valid parquet files.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "repair", header = "Verify integrity of stored data and repair corrupted files", description = "Verify integrity of parquet files present in the stored data and delete the corrupted ones. It is important to use caution when using this command, as it will permanently delete the selected data and cannot be undone.")
public class DataRepairCliCommand extends BaseCommand implements ICommand {
    private DqoJobQueue dqoJobQueue;
    private DqoQueueJobFactory dqoQueueJobFactory;

    public DataRepairCliCommand() {
    }

    /**
     * Dependency injection constructor.
     * @param dqoJobQueue Job queue.
     * @param dqoQueueJobFactory Job queue factory.
     */
    @Autowired
    public DataRepairCliCommand(DqoJobQueue dqoJobQueue,
                                DqoQueueJobFactory dqoQueueJobFactory) {
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
    }

    @CommandLine.Option(names = {"-er", "--errors"}, description = "Repair the errors")
    private boolean repairErrors = false;

    @CommandLine.Option(names = {"-st", "--statistics"}, description = "Repair the statistics")
    private boolean repairStatistics = false;

    @CommandLine.Option(names = {"-cr", "--check-results"}, description = "Repair the check results")
    private boolean repairCheckResults = false;

    @CommandLine.Option(names = {"-sr", "--sensor-readouts"}, description = "Repair the sensor readouts")
    private boolean repairSensorReadouts = false;

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            completionCandidates = ConnectionNameCompleter.class,
            required = true)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table"}, description = "Full table name (schema.table), supports wildcard patterns 'sch*.tab*'",
            completionCandidates = FullTableNameCompleter.class)
    private String table;


    protected RepairStoredDataQueueJobParameters createRepairParameters() {
        RepairStoredDataQueueJobParameters repairStoredDataQueueJobParameters = new RepairStoredDataQueueJobParameters(
                this.connection,
                this.table
        );

        if (!this.repairErrors && !this.repairStatistics && !this.repairCheckResults && !this.repairSensorReadouts) {
            // Repair everything by default
            repairStoredDataQueueJobParameters.setRepairErrors(true);
            repairStoredDataQueueJobParameters.setRepairStatistics(true);
            repairStoredDataQueueJobParameters.setRepairCheckResults(true);
            repairStoredDataQueueJobParameters.setRepairSensorReadouts(true);
        } else {
            repairStoredDataQueueJobParameters.setRepairErrors(this.repairErrors);
            repairStoredDataQueueJobParameters.setRepairStatistics(this.repairStatistics);
            repairStoredDataQueueJobParameters.setRepairCheckResults(this.repairCheckResults);
            repairStoredDataQueueJobParameters.setRepairSensorReadouts(this.repairSensorReadouts);
        }

        return repairStoredDataQueueJobParameters;
    }


    /**
     * Cleans the data based on the supplied filters, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        RepairStoredDataQueueJobParameters repairParameters = this.createRepairParameters();

        RepairStoredDataQueueJob repairStoredDataJob = this.dqoQueueJobFactory.createRepairStoredDataJob();
        repairStoredDataJob.setRepairParameters(repairParameters);
        PushJobResult<RepairStoredDataQueueJobResult> pushJobResult = this.dqoJobQueue.pushJob(repairStoredDataJob);
        RepairStoredDataQueueJobResult jobResult = pushJobResult.getFuture().get();
        return 0;
    }
}
