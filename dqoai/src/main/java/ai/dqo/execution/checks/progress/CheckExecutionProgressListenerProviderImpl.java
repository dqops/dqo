package ai.dqo.execution.checks.progress;

import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Returns a correct implementation of the check run progress listener that prints the progress to the screen.
 */
@Component
public class CheckExecutionProgressListenerProviderImpl implements CheckExecutionProgressListenerProvider {
    private TerminalWriter terminalWriter;
    private JsonSerializer jsonSerializer;

    /**
     * Creates a new instance of a listener provider.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public CheckExecutionProgressListenerProviderImpl(TerminalWriter terminalWriter,
                                                      JsonSerializer jsonSerializer) {
        this.terminalWriter = terminalWriter;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Creates a new instance (prototype) of a progress listener given the reporting mode. The listener is unconfigured.
     * @param reportingMode Reporting mode.
     * @return Unconfigured progress listener.
     */
    protected CheckExecutionProgressListener createProgressListenerInstance(CheckRunReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return new SilentCheckExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            case summary:
                return new SummaryCheckExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            case info:
                return new InfoCheckExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            case debug:
                return new DebugCheckExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            default:
                throw new IllegalArgumentException("reportingMode");
        }
    }

    /**
     * Returns a check execution progress listener for the requested reporting level.
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the checks to the console.
     * @return Check execution progress listener.
     */
    @Override
    public CheckExecutionProgressListener getProgressListener(CheckRunReportingMode reportingMode, boolean writeSummaryToConsole) {
        CheckExecutionProgressListener progressListenerInstance = this.createProgressListenerInstance(reportingMode);
        progressListenerInstance.setShowSummary(writeSummaryToConsole);
        return progressListenerInstance;
    }
}
