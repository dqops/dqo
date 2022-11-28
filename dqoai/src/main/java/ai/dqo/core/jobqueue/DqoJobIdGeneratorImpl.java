package ai.dqo.core.jobqueue;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Job ID generation service, creates increasing job ids using the current time.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DqoJobIdGeneratorImpl implements DqoJobIdGenerator {
    private long lastEpochSeconds = Instant.now().toEpochMilli() / 1000L;
    private int jobsInCurrentSecond = 0;

    /**
     * Creates a next incremental id.
     * @return Next incremental id.
     */
    public synchronized long generateNextIncrementalId() {
        long currentEpochSeconds = Instant.now().toEpochMilli() / 1000L;
        if (currentEpochSeconds > this.lastEpochSeconds) {
            this.lastEpochSeconds = currentEpochSeconds;
            this.jobsInCurrentSecond = 0;
        }

        long nextJobId = this.lastEpochSeconds * 1000000L +  this.jobsInCurrentSecond++;
        return nextJobId;
    }

    /**
     * Creates a new job id, assigning a new incremental id.
     *
     * @return New job id.
     */
    @Override
    public DqoQueueJobId createNewJobId() {
        long jobId = this.generateNextIncrementalId();
        return new DqoQueueJobId(jobId);
    }
}
