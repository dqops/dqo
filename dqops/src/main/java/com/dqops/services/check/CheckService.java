/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.RunChecksTarget;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.services.check.mapping.models.AllChecksModel;
import com.dqops.services.check.models.AllChecksPatchParameters;
import com.dqops.services.check.models.BulkCheckDeactivateParameters;

import java.util.List;

/**
 * Service called to run checks or operate on checks.
 */
public interface CheckService {
    /**
     * Runs checks given the filters.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilterParameters Optional user provided time window parameters, limits the time period that is analyzed.
     * @param collectErrorSamples Collect error samples while running the checks.
     * @param checkExecutionProgressListener Progress listener that will report the progress.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @param principal Principal that will be used to run the job.
     * @return Check execution summary.
     */
    CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters,
                                    TimeWindowFilterParameters timeWindowFilterParameters,
                                    boolean collectErrorSamples,
                                    CheckExecutionProgressListener checkExecutionProgressListener,
                                    boolean dummyRun,
                                    RunChecksTarget executionTarget,
                                    DqoUserPrincipal principal);

    /**
     * Runs error samplers for the data quality checks identified by a check search filter.
     *
     * @param checkSearchFilters             Check search filters.
     * @param timeWindowFilterParameters     Optional user provided time window parameters, limits the time period that is analyzed.
     * @param errorSamplesDataScope          Error sampling scope (whole table, or per data grouping).
     * @param errorSamplerProgressListener   Progress listener that will report the progress.
     * @param errorSamplesDataScope          Error sampling scope (whole table, or per data grouping).
     * @param dummyRun                       Run the sensors in a dummy mode (sensors are not executed).
     * @param principal                      Principal that will be used to run the job.
     * @return Error sampler execution summary.
     */
    ErrorSamplerExecutionSummary collectErrorSamples(CheckSearchFilters checkSearchFilters,
                                                     TimeWindowFilterParameters timeWindowFilterParameters,
                                                     ErrorSamplesDataScope errorSamplesDataScope,
                                                     ErrorSamplerExecutionProgressListener errorSamplerProgressListener,
                                                     boolean dummyRun,
                                                     DqoUserPrincipal principal);

    /**
     * Deletes existing checks matching the provided filters.
     *
     * @param parameters Bulk check disable parameters.
     * @param principal User principal who called the operation.
     */
    void deleteChecks(BulkCheckDeactivateParameters parameters, DqoUserPrincipal principal);

    /**
     * Disable existing checks matching the provided filters.
     *
     * @param parameters Bulk check disable parameters.
     * @param principal User principal who called the operation.
     */
    void disableChecks(BulkCheckDeactivateParameters parameters, DqoUserPrincipal principal);

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @param principal  User principal.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    List<AllChecksModel> activateOrUpdateAllChecks(AllChecksPatchParameters parameters, DqoUserPrincipal principal);
}
