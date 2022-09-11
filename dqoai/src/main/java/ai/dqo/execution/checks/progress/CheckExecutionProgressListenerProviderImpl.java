package ai.dqo.execution.checks.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Returns a correct implementation of the check run progress listener that prints the progress to the screen.
 */
@Component
public class CheckExecutionProgressListenerProviderImpl implements CheckExecutionProgressListenerProvider {
    private SilentCheckExecutionProgressListener silentCheckExecutionProgressListener;
    private SummaryCheckExecutionProgressListener summaryCheckExecutionProgressListener;
    private InfoCheckExecutionProgressListener infoCheckExecutionProgressListener;
    private DebugCheckExecutionProgressListener debugCheckExecutionProgressListener;

    @Autowired
    public CheckExecutionProgressListenerProviderImpl(SilentCheckExecutionProgressListener silentCheckExecutionProgressListener,
                                                      SummaryCheckExecutionProgressListener summaryCheckExecutionProgressListener,
                                                      InfoCheckExecutionProgressListener infoCheckExecutionProgressListener,
                                                      DebugCheckExecutionProgressListener debugCheckExecutionProgressListener) {
        this.silentCheckExecutionProgressListener = silentCheckExecutionProgressListener;
        this.summaryCheckExecutionProgressListener = summaryCheckExecutionProgressListener;
        this.infoCheckExecutionProgressListener = infoCheckExecutionProgressListener;
        this.debugCheckExecutionProgressListener = debugCheckExecutionProgressListener;
    }

    /**
     * Returns a check execution progress listener for the requested reporting level.
     * @param reportingMode Reporting level.
     * @return Check execution progress listener.
     */
    @Override
    public CheckExecutionProgressListener getProgressListener(CheckRunReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return this.silentCheckExecutionProgressListener;
            case summary:
                return this.summaryCheckExecutionProgressListener;
            case info:
                return this.infoCheckExecutionProgressListener;
            case debug:
                return this.debugCheckExecutionProgressListener;
            default:
                throw new IllegalArgumentException("reportingMode");
        }
    }
}
