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
package com.dqops.core.jobqueue.jobs.data;

import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.models.CheckResultsFragmentFilter;
import com.dqops.data.checkresults.services.CheckResultsDeleteService;
import com.dqops.data.errors.models.ErrorsFragmentFilter;
import com.dqops.data.errors.services.ErrorsDeleteService;
import com.dqops.data.errorsamples.models.ErrorsSamplesFragmentFilter;
import com.dqops.data.errorsamples.services.ErrorSamplesDeleteService;
import com.dqops.data.incidents.models.IncidentsFragmentFilter;
import com.dqops.data.incidents.services.IncidentsDeleteService;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.readouts.models.SensorReadoutsFragmentFilter;
import com.dqops.data.readouts.services.SensorReadoutsDeleteService;
import com.dqops.data.statistics.models.StatisticsResultsFragmentFilter;
import com.dqops.data.statistics.services.StatisticsDeleteService;
import com.dqops.metadata.search.TableSearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Queue job that deletes data stored in user's ".data" directory.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeleteStoredDataQueueJob extends DqoQueueJob<DeleteStoredDataResult> {
    private final ErrorsDeleteService errorsDeleteService;
    private final StatisticsDeleteService statisticsDeleteService;
    private final CheckResultsDeleteService checkResultsDeleteService;
    private final SensorReadoutsDeleteService sensorReadoutsDeleteService;
    private final ErrorSamplesDeleteService errorSamplesDeleteService;
    private final IncidentsDeleteService incidentsDeleteService;
    private DeleteStoredDataQueueJobParameters deletionParameters;

    @Autowired
    public DeleteStoredDataQueueJob(ErrorsDeleteService errorsDeleteService,
                                    StatisticsDeleteService statisticsDeleteService,
                                    CheckResultsDeleteService checkResultsDeleteService,
                                    SensorReadoutsDeleteService sensorReadoutsDeleteService,
                                    ErrorSamplesDeleteService errorSamplesDeleteService,
                                    IncidentsDeleteService incidentsDeleteService) {
        this.errorsDeleteService = errorsDeleteService;
        this.statisticsDeleteService = statisticsDeleteService;
        this.checkResultsDeleteService = checkResultsDeleteService;
        this.sensorReadoutsDeleteService = sensorReadoutsDeleteService;
        this.errorSamplesDeleteService = errorSamplesDeleteService;
        this.incidentsDeleteService = incidentsDeleteService;
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
                setConnection(deletionParameters.getConnection());
                setFullTableName(deletionParameters.getFullTableName());
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
            setTableComparisonName(deletionParameters.getTableComparisonName());
        }};
    }

    protected StatisticsResultsFragmentFilter getStatisticsResultsFragmentFilter() {
        return new StatisticsResultsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnection(deletionParameters.getConnection());
                setFullTableName(deletionParameters.getFullTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setCollectorCategory(deletionParameters.getCollectorCategory());
            setCollectorName(deletionParameters.getCollectorName());
            setCollectorTarget(deletionParameters.getCollectorTarget());
            setColumnNames(deletionParameters.getColumnNames());
            setDataStreamName(deletionParameters.getDataGroupTag());
            setSensorName(deletionParameters.getSensorName());
        }};
    }

    protected CheckResultsFragmentFilter getRuleResultsFragmentFilter() {
        return new CheckResultsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnection(deletionParameters.getConnection());
                setFullTableName(deletionParameters.getFullTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setCheckCategory(deletionParameters.getCheckCategory());
            setCheckName(deletionParameters.getCheckName());
            setCheckType(deletionParameters.getCheckType());
            setColumnNames(deletionParameters.getColumnNames());
            setDataStreamName(deletionParameters.getDataGroupTag());
            setSensorName(deletionParameters.getSensorName());
            setQualityDimension(deletionParameters.getQualityDimension());
            setTimeGradient(deletionParameters.getTimeGradient());
            setTableComparisonName(deletionParameters.getTableComparisonName());
        }};
    }

    protected SensorReadoutsFragmentFilter getSensorReadoutsFragmentFilter() {
        return new SensorReadoutsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnection(deletionParameters.getConnection());
                setFullTableName(deletionParameters.getFullTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setCheckCategory(deletionParameters.getCheckCategory());
            setCheckName(deletionParameters.getCheckName());
            setCheckType(deletionParameters.getCheckType());
            setColumnNames(deletionParameters.getColumnNames());
            setDataStreamName(deletionParameters.getDataGroupTag());
            setSensorName(deletionParameters.getSensorName());
            setQualityDimension(deletionParameters.getQualityDimension());
            setTimeGradient(deletionParameters.getTimeGradient());
            setTableComparisonName(deletionParameters.getTableComparisonName());
        }};
    }

    protected ErrorsSamplesFragmentFilter getErrorsSamplesFragmentFilter(){
        return new ErrorsSamplesFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnection(deletionParameters.getConnection());
                setFullTableName(deletionParameters.getFullTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setColumnNames(deletionParameters.getColumnNames());
            setCheckCategory(deletionParameters.getCheckCategory());
            setCheckName(deletionParameters.getCheckName());
            setCheckType(deletionParameters.getCheckType());
            setSensorName(deletionParameters.getSensorName());
            setQualityDimension(deletionParameters.getQualityDimension());
            setTableComparisonName(deletionParameters.getTableComparisonName());
        }};
    }

    protected IncidentsFragmentFilter getIncidentsFragmentFilter() {
        return new IncidentsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnection(deletionParameters.getConnection());
                setFullTableName(deletionParameters.getFullTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setCheckCategory(deletionParameters.getCheckCategory());
            setCheckName(deletionParameters.getCheckName());
            setCheckType(deletionParameters.getCheckType());
            setColumnNames(deletionParameters.getColumnNames());
            setDataStreamName(deletionParameters.getDataGroupTag());
            setQualityDimension(deletionParameters.getQualityDimension());
            setStatusName(deletionParameters.getIncidentStatusName());
//            setTableName();
        }};
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public DeleteStoredDataResult onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        if (this.deletionParameters.getConnection() == null) {
            throw new IllegalArgumentException("Connection not specified for data delete job.");
        }

        DeleteStoredDataResult result = new DeleteStoredDataResult();
        UserDomainIdentity userIdentity = this.getPrincipal().getDataDomainIdentity();

        if (this.deletionParameters.isDeleteErrors()) {
            DeleteStoredDataResult errorsResult = this.errorsDeleteService.deleteSelectedErrorsFragment(this.getErrorsFragmentFilter(), userIdentity);
            result.concat(errorsResult);
        }
        if (this.deletionParameters.isDeleteStatistics()) {
            DeleteStoredDataResult statisticsResult = this.statisticsDeleteService.deleteSelectedStatisticsResultsFragment(this.getStatisticsResultsFragmentFilter(), userIdentity);
            result.concat(statisticsResult);
        }
        if (this.deletionParameters.isDeleteCheckResults()) {
            DeleteStoredDataResult checkResultsResult = this.checkResultsDeleteService.deleteSelectedCheckResultsFragment(this.getRuleResultsFragmentFilter(), userIdentity);
            result.concat(checkResultsResult);
        }
        if (this.deletionParameters.isDeleteSensorReadouts()) {
            DeleteStoredDataResult sensorReadoutsResult = this.sensorReadoutsDeleteService.deleteSelectedSensorReadoutsFragment(this.getSensorReadoutsFragmentFilter(), userIdentity);
            result.concat(sensorReadoutsResult);
        }
        if (this.deletionParameters.isDeleteErrorSamples()){
            DeleteStoredDataResult errorSamplesResult = this.errorSamplesDeleteService.deleteSelectedErrorSamplesFragment(this.getErrorsSamplesFragmentFilter(), userIdentity);
            result.concat(errorSamplesResult);
        }
        if (this.deletionParameters.isDeleteIncidents()){
            DeleteStoredDataResult incidentsResult = this.incidentsDeleteService.deleteSelectedIncidentsFragment(this.getIncidentsFragmentFilter(), userIdentity);
            result.concat(incidentsResult);
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
        return DqoJobType.delete_stored_data;
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
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        DeleteStoredDataQueueJobConcurrencyTarget target = new DeleteStoredDataQueueJobConcurrencyTarget(
                this.deletionParameters.getConnection());
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(ConcurrentJobType.DELETE_STORED_DATA, target);
        JobConcurrencyConstraint deleteLimit = new JobConcurrencyConstraint(concurrencyTarget, 1);
        return new JobConcurrencyConstraint[] { deleteLimit };
    }
}
