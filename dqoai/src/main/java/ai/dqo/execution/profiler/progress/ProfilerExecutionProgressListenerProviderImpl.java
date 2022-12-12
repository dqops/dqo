package ai.dqo.execution.profiler.progress;

import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Profiler execution progress listener provider (factory). Returns progress listeners for the given reporting mode.
 */
@Component
public class ProfilerExecutionProgressListenerProviderImpl implements ProfilerExecutionProgressListenerProvider {
    private TerminalWriter terminalWriter;
    private JsonSerializer jsonSerializer;

    /**
     * Creates a profiler factory given the dependencies that are passed to each progress listener.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public ProfilerExecutionProgressListenerProviderImpl(TerminalWriter terminalWriter,
                                                         JsonSerializer jsonSerializer) {
        this.terminalWriter = terminalWriter;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Creates a new instance (prototype) of a progress listener given the reporting mode. The listener is unconfigured.
     * @param reportingMode Reporting mode.
     * @return Unconfigured progress listener.
     */
    protected ProfilerExecutionProgressListener createProgressListenerInstance(ProfilerExecutionReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return new SilentProfilerExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            default:
                throw new IllegalArgumentException("reportingMode");
        }
    }

    /**
     * Returns a profiler execution progress listener for the requested reporting level.
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the profiler to the console.
     * @return Profiler execution progress listener.
     */
    @Override
    public ProfilerExecutionProgressListener getProgressListener(ProfilerExecutionReportingMode reportingMode,
                                                                 boolean writeSummaryToConsole) {
        ProfilerExecutionProgressListener progressListenerInstance = this.createProgressListenerInstance(reportingMode);
        progressListenerInstance.setShowSummary(writeSummaryToConsole);
        return progressListenerInstance;
    }
}
