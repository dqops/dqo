/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.errorsampling.progress;

import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Error sampler execution progress listener provider (factory). Returns progress listeners for the given reporting mode.
 */
@Component
public class ErrorSamplerExecutionProgressListenerProviderImpl implements ErrorSamplerExecutionProgressListenerProvider {
    private TerminalWriter terminalWriter;
    private JsonSerializer jsonSerializer;

    /**
     * Creates a profiler factory given the dependencies that are passed to each progress listener.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public ErrorSamplerExecutionProgressListenerProviderImpl(TerminalWriter terminalWriter,
                                                             JsonSerializer jsonSerializer) {
        this.terminalWriter = terminalWriter;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Creates a new instance (prototype) of a progress listener given the reporting mode. The listener is unconfigured.
     * @param reportingMode Reporting mode.
     * @return Unconfigured progress listener.
     */
    protected ErrorSamplerExecutionProgressListener createProgressListenerInstance(ErrorSamplerExecutionReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return new SilentErrorSamplerExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            default:
                throw new IllegalArgumentException("reportingMode");
        }
    }

    /**
     * Returns an error sampler execution progress listener for the requested reporting level.
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the error sampler to the console.
     * @return Error sampler execution progress listener.
     */
    @Override
    public ErrorSamplerExecutionProgressListener getProgressListener(ErrorSamplerExecutionReportingMode reportingMode,
                                                                     boolean writeSummaryToConsole) {
        ErrorSamplerExecutionProgressListener progressListenerInstance = this.createProgressListenerInstance(reportingMode);
        progressListenerInstance.setShowSummary(writeSummaryToConsole);
        return progressListenerInstance;
    }
}
