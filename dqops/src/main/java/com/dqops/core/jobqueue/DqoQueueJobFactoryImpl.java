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
package com.dqops.core.jobqueue;

import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import com.dqops.core.jobqueue.jobs.data.RepairStoredDataQueueJob;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJob;
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJob;
import com.dqops.core.scheduler.runcheck.RunScheduledChecksDqoJob;
import com.dqops.core.synchronization.jobs.SynchronizeMultipleFoldersDqoQueueJob;
import com.dqops.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJob;
import com.dqops.execution.checks.jobs.RunChecksOnTableQueueJob;
import com.dqops.execution.checks.jobs.RunChecksQueueJob;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesOnTableQueueJob;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesQueueJob;
import com.dqops.execution.statistics.jobs.CollectStatisticsOnTableQueueJob;
import com.dqops.execution.statistics.jobs.CollectStatisticsQueueJob;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DQOps job instance factory. Creates new instances of jobs that could be configured before they are submitted to the queue.
 */
@Component
public class DqoQueueJobFactoryImpl implements DqoQueueJobFactory {
    private BeanFactory beanFactory;

    /**
     * Creates a new instance of a job factory using a spring bean factory. Job classes should be configured
     * as prototype beans.
     * @param beanFactory Bean factory.
     */
    @Autowired
    public DqoQueueJobFactoryImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Creates a run checks job.
     * @return New run checks job.
     */
    @Override
    public RunChecksQueueJob createRunChecksJob() {
        return this.beanFactory.getBean(RunChecksQueueJob.class);
    }

    /**
     * Creates a run checks on table job (that runs checks on a single table, within the parallel operations per table limits).
     *
     * @return New run checks on table job.
     */
    @Override
    public RunChecksOnTableQueueJob createRunChecksOnTableJob() {
        return this.beanFactory.getBean(RunChecksOnTableQueueJob.class);
    }

    /**
     * Creates a job that collects statistics.
     *
     * @return New colect statistics job.
     */
    @Override
    public CollectStatisticsQueueJob createCollectStatisticsJob() {
        return this.beanFactory.getBean(CollectStatisticsQueueJob.class);
    }

    /**
     * Creates a child job that runs profilers (collects statistics) on a single table.
     *
     * @return New collect statistics on table child job.
     */
    @Override
    public CollectStatisticsOnTableQueueJob createCollectStatisticsOnTableJob() {
        return this.beanFactory.getBean(CollectStatisticsOnTableQueueJob.class);
    }

    /**
     * Creates a job that runs error samplers (collects error samples).
     *
     * @return New collect error samples parent job.
     */
    @Override
    public CollectErrorSamplesQueueJob createCollectErrorSamplesJob() {
        return this.beanFactory.getBean(CollectErrorSamplesQueueJob.class);
    }

    /**
     * Creates a child job that runs error samplers (collects error samples) on a single table.
     *
     * @return New collect error samples on table child job.
     */
    @Override
    public CollectErrorSamplesOnTableQueueJob createCollectErrorSamplesOnTableJob() {
        return this.beanFactory.getBean(CollectErrorSamplesOnTableQueueJob.class);
    }

    /**
     * Creates a DQOps Cloud synchronization job that will synchronize one folder in the user home.
     *
     * @return Cloud synchronization job for one folder.
     */
    @Override
    public SynchronizeRootFolderDqoQueueJob createSynchronizeRootFolderJob() {
        return this.beanFactory.getBean(SynchronizeRootFolderDqoQueueJob.class);
    }

    /**
     * Creates a DQOps Cloud synchronization parent job that will start multiple child jobs to synchronize multiple DQOps User home folders in parallel.
     *
     * @return Cloud synchronization job for multiple folders.
     */
    @Override
    public SynchronizeMultipleFoldersDqoQueueJob createSynchronizeMultipleFoldersJob() {
        return this.beanFactory.getBean(SynchronizeMultipleFoldersDqoQueueJob.class);
    }

    /**
     * Creates a job that executes all checks scheduled for one cron expression.
     *
     * @return Run scheduled checks job.
     */
    @Override
    public RunScheduledChecksDqoJob createRunScheduledChecksJob() {
        return this.beanFactory.getBean(RunScheduledChecksDqoJob.class);
    }

    /**
     * Creates a job that imports tables from a schema in a source database.
     *
     * @return Schema import job.
     */
    @Override
    public ImportSchemaQueueJob createImportSchemaJob() {
        return this.beanFactory.getBean(ImportSchemaQueueJob.class);
    }

    /**
     * Creates a job that imports tables from a schema in a source database.
     *
     * @return Import tables import job.
     */
    @Override
    public ImportTablesQueueJob createImportTablesJob() {
        return this.beanFactory.getBean(ImportTablesQueueJob.class);
    }

    /**
     * Creates a job that data from user's ".data" directory.
     *
     * @return Delete stored data job.
     */
    @Override
    public DeleteStoredDataQueueJob createDeleteStoredDataJob() {
        return this.beanFactory.getBean(DeleteStoredDataQueueJob.class);
    }

    /**
     * Creates a job that repairs data in user's ".data" directory.
     * @return Repair stored data job.
     */
    @Override
    public RepairStoredDataQueueJob createRepairStoredDataJob() {
        return this.beanFactory.getBean(RepairStoredDataQueueJob.class);
    }
}
