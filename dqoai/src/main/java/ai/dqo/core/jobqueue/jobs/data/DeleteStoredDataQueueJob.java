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

import ai.dqo.connectors.*;
import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.data.ruleresults.services.RuleResultsDeleteService;
import ai.dqo.data.ruleresults.services.models.RuleResultsFragmentFilter;
import ai.dqo.metadata.search.TableSearchFilters;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Queue job that deletes data stored in user's ".data" directory.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeleteStoredDataQueueJob extends DqoQueueJob<DeleteStoredDataQueueJobResult> {
    private RuleResultsDeleteService ruleResultsDeleteService;
    private DeleteStoredDataQueueJobParameters deletionParameters;

    @Autowired
    public DeleteStoredDataQueueJob(RuleResultsDeleteService ruleResultsDeleteService) {
        this.ruleResultsDeleteService = ruleResultsDeleteService;
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


    protected RuleResultsFragmentFilter getRuleResultsFragmentFilter() {
        return new RuleResultsFragmentFilter() {{
            setTableSearchFilters(new TableSearchFilters() {{
                setConnectionName(deletionParameters.getConnectionName());
                setSchemaTableName(deletionParameters.getSchemaTableName());
            }});
            setDateStart(deletionParameters.getDateStart());
            setDateEnd(deletionParameters.getDateEnd());
            setIgnoreDateDay(deletionParameters.isIgnoreDateDay());
            setCheckCategory(deletionParameters.getCheckCategory());
            setCheckName(deletionParameters.getCheckName());
            setCheckType(deletionParameters.getCheckType());
            setColumnName(deletionParameters.getColumnName());
            setDataStreamName(deletionParameters.getDataStreamName());
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
        if (this.deletionParameters.isDeleteRuleResults()) {
            this.ruleResultsDeleteService.deleteSelectedRuleResultsFragment(this.getRuleResultsFragmentFilter());
        }

        return new DeleteStoredDataQueueJobResult();
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
