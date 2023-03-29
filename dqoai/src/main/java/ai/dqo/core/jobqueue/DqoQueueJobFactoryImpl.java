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
package ai.dqo.core.jobqueue;

import ai.dqo.core.jobqueue.jobs.data.RepairStoredDataQueueJob;
import ai.dqo.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJob;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.schema.ImportSchemaQueueJob;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJob;
import ai.dqo.core.scheduler.runcheck.RunScheduledChecksDqoJob;
import ai.dqo.core.scheduler.scan.RunPeriodicMetadataSynchronizationDqoJob;
import ai.dqo.execution.checks.RunChecksQueueJob;
import ai.dqo.execution.statistics.CollectStatisticsCollectionQueueJob;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DQO job instance factory. Creates new instances of jobs that could be configured before they are submitted to the queue.
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
     * Creates a job that runs profilers.
     *
     * @return New run profilers job.
     */
    @Override
    public CollectStatisticsCollectionQueueJob createRunProfilersJob() {
        return this.beanFactory.getBean(CollectStatisticsCollectionQueueJob.class);
    }

    /**
     * Creates a DQO Cloud synchronization job that will synchronize one folder in the user home.
     *
     * @return Cloud synchronization job for one folder.
     */
    @Override
    public SynchronizeRootFolderDqoQueueJob createSynchronizeRootFolderJob() {
        return this.beanFactory.getBean(SynchronizeRootFolderDqoQueueJob.class);
    }

    /**
     * Creates a DQO job that runs a scheduled (every 10 minutes by default) metadata synchronization and detection of new cron schedules.
     *
     * @return Periodic metadata synchronization job.
     */
    @Override
    public RunPeriodicMetadataSynchronizationDqoJob createRunPeriodicMetadataSynchronizationJob() {
        return this.beanFactory.getBean(RunPeriodicMetadataSynchronizationDqoJob.class);
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
