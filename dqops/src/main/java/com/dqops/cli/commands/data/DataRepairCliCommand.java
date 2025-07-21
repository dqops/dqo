/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.data;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.completion.completers.FullTableNameCompleter;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.RepairStoredDataQueueJob;
import com.dqops.core.jobqueue.jobs.data.RepairStoredDataQueueJobParameters;
import com.dqops.core.jobqueue.jobs.data.RepairStoredDataQueueJobResult;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
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
@CommandLine.Command(name = "repair", header = "Verify integrity of parquet files used to store data and removes corrupted files", description = "Verify integrity of parquet files used to store data and removes corrupted files. Be careful when using this command, as it permanently deletes the selected data and cannot be undone.")
public class DataRepairCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private DqoJobQueue dqoJobQueue;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoUserPrincipalProvider principalProvider;

    public DataRepairCliCommand() {
    }

    /**
     * Dependency injection constructor.
     * @param dqoJobQueue Job queue.
     * @param dqoQueueJobFactory Job queue factory.
     * @param principalProvider Principal provider.
     */
    @Autowired
    public DataRepairCliCommand(DqoJobQueue dqoJobQueue,
                                DqoQueueJobFactory dqoQueueJobFactory,
                                DqoUserPrincipalProvider principalProvider) {
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.principalProvider = principalProvider;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name",
            completionCandidates = ConnectionNameCompleter.class,
            required = true)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table", "--full-table-name"}, description = "Full table name (schema.table), supports wildcard patterns 'sch*.tab*'",
            completionCandidates = FullTableNameCompleter.class)
    private String table;

    @CommandLine.Option(names = {"-er", "--errors"}, description = "Repair the execution errors")
    private boolean repairErrors = false;

    @CommandLine.Option(names = {"-st", "--statistics"}, description = "Repair the statistics")
    private boolean repairStatistics = false;

    @CommandLine.Option(names = {"-cr", "--check-results"}, description = "Repair the check results")
    private boolean repairCheckResults = false;

    @CommandLine.Option(names = {"-sr", "--sensor-readouts"}, description = "Repair the sensor readouts")
    private boolean repairSensorReadouts = false;


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
        DqoUserPrincipal principal = this.principalProvider.getLocalUserPrincipal();
        PushJobResult<RepairStoredDataQueueJobResult> pushJobResult = this.dqoJobQueue.pushJob(repairStoredDataJob, principal);
        RepairStoredDataQueueJobResult jobResult = pushJobResult.getFinishedFuture().get();
        return 0;
    }
}
