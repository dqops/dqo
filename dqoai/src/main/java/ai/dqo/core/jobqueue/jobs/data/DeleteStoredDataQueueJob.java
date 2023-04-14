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
package ai.dqo.core.jobqueue.jobs.data;

import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import ai.dqo.data.checkresults.models.CheckResultsFragmentFilter;
import ai.dqo.data.checkresults.services.CheckResultsDeleteService;
import ai.dqo.data.errors.models.ErrorsFragmentFilter;
import ai.dqo.data.errors.services.ErrorsDeleteService;
import ai.dqo.data.readouts.models.SensorReadoutsFragmentFilter;
import ai.dqo.data.readouts.services.SensorReadoutsDeleteService;
import ai.dqo.data.statistics.models.StatisticsResultsFragmentFilter;
import ai.dqo.data.statistics.services.StatisticsDeleteService;
import ai.dqo.metadata.search.TableSearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Queue job that deletes data stored in user's ".data" directory.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeleteStoredDataQueueJob extends DqoQueueJob<DeleteStoredDataQueueJobResult> {
    private ErrorsDeleteService errorsDeleteService;
    private StatisticsDeleteService statisticsDeleteService;
    private CheckResultsDeleteService checkResultsDeleteService;
    private SensorReadoutsDeleteService sensorReadoutsDeleteService;
    private DeleteStoredDataQueueJobParameters deletionParameters;

    @Autowired
    public DeleteStoredDataQueueJob(ErrorsDeleteService errorsDeleteService,
                                    StatisticsDeleteService statisticsDeleteService,
                                    CheckResultsDeleteService checkResultsDeleteService,
                                    SensorReadoutsDeleteService sensorReadoutsDeleteService) {
        this.errorsDeleteService = errorsDeleteService;
        this.statisticsDeleteService = statisticsDeleteService;
        this.checkResultsDeleteService = checkResultsDeleteService;
        this.sensorReadoutsDeleteService = sensorReadoutsDeleteService;
    }

    /**
     * Returns the deletion parameters object.
     * @return Deletion parameters object.
     */
    public DeleteStoredDataQueueJobParameters getDeletionParameters() {
        return deletionParameters;
    }

    /**
     * Sets the parameters object for the job that specify the data that needs to be removed from ".data" directory.
     * @param deletionParameters Deletion parameters to store.
     */
    public void setDeletionParameters(DeleteStoredDataQueueJobParameters deletionParameters) {
        this.deletionParameters = deletionParameters;
    }


    protected ErrorsFragmentFilter getErrorsFragmentFilter() {
        return new ErrorsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnectionName(deletionParameters.getConnectionName());
                setSchemaTableName(deletionParameters.getSchemaTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setCheckCategory(deletionParameters.getCheckCategory());
            setCheckName(deletionParameters.getCheckName());
            setCheckType(deletionParameters.getCheckType());
            setColumnNames(deletionParameters.getColumnNames());
            setSensorName(deletionParameters.getSensorName());
            setQualityDimension(deletionParameters.getQualityDimension());
            setTimeGradient(deletionParameters.getTimeGradient());
        }};
    }

    protected StatisticsResultsFragmentFilter getStatisticsResultsFragmentFilter() {
        return new StatisticsResultsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnectionName(deletionParameters.getConnectionName());
                setSchemaTableName(deletionParameters.getSchemaTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setCollectorCategory(deletionParameters.getCollectorCategory());
            setCollectorName(deletionParameters.getCollectorName());
            setCollectorTarget(deletionParameters.getCollectorTarget());
            setColumnNames(deletionParameters.getColumnNames());
            setDataStreamName(deletionParameters.getDataStreamName());
            setSensorName(deletionParameters.getSensorName());
        }};
    }

    protected CheckResultsFragmentFilter getRuleResultsFragmentFilter() {
        return new CheckResultsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnectionName(deletionParameters.getConnectionName());
                setSchemaTableName(deletionParameters.getSchemaTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setCheckCategory(deletionParameters.getCheckCategory());
            setCheckName(deletionParameters.getCheckName());
            setCheckType(deletionParameters.getCheckType());
            setColumnNames(deletionParameters.getColumnNames());
            setDataStreamName(deletionParameters.getDataStreamName());
            setSensorName(deletionParameters.getSensorName());
            setQualityDimension(deletionParameters.getQualityDimension());
            setTimeGradient(deletionParameters.getTimeGradient());
        }};
    }

    protected SensorReadoutsFragmentFilter getSensorReadoutsFragmentFilter() {
        return new SensorReadoutsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnectionName(deletionParameters.getConnectionName());
                setSchemaTableName(deletionParameters.getSchemaTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setCheckCategory(deletionParameters.getCheckCategory());
            setCheckName(deletionParameters.getCheckName());
            setCheckType(deletionParameters.getCheckType());
            setColumnNames(deletionParameters.getColumnNames());
            setDataStreamName(deletionParameters.getDataStreamName());
            setSensorName(deletionParameters.getSensorName());
            setQualityDimension(deletionParameters.getQualityDimension());
            setTimeGradient(deletionParameters.getTimeGradient());
        }};
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public DeleteStoredDataQueueJobResult onExecute(DqoJobExecutionContext jobExecutionContext) {
        DeleteStoredDataQueueJobResult result = new DeleteStoredDataQueueJobResult();

        if (this.deletionParameters.isDeleteErrors()) {
            this.errorsDeleteService.deleteSelectedErrorsFragment(this.getErrorsFragmentFilter());
        }
        if (this.deletionParameters.isDeleteStatistics()) {
            this.statisticsDeleteService.deleteSelectedStatisticsResultsFragment(this.getStatisticsResultsFragmentFilter());
        }
        if (this.deletionParameters.isDeleteCheckResults()) {
            this.checkResultsDeleteService.deleteSelectedCheckResultsFragment(this.getRuleResultsFragmentFilter());
        }
        if (this.deletionParameters.isDeleteSensorReadouts()) {
            this.sensorReadoutsDeleteService.deleteSelectedSensorReadoutsFragment(this.getSensorReadoutsFragmentFilter());
        }

        return result;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.DELETE_STORED_DATA;
    }

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel()
        {{
            setDeleteStoredDataParameters(deletionParameters);
        }};
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint getConcurrencyConstraint() {
        DeleteStoredDataQueueJobConcurrencyTarget target = new DeleteStoredDataQueueJobConcurrencyTarget(
                this.deletionParameters.getConnectionName());
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(ConcurrentJobType.DELETE_STORED_DATA, target);
        return new JobConcurrencyConstraint(concurrencyTarget, 1);
    }
}
