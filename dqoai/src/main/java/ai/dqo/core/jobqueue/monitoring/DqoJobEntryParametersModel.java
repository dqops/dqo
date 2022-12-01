package ai.dqo.core.jobqueue.monitoring;

import ai.dqo.core.dqocloud.synchronization.SynchronizeRootFolderDqoQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.metadata.ImportSchemaQueueJobParameters;
import ai.dqo.core.scheduler.schedules.RunChecksCronSchedule;
import ai.dqo.execution.checks.RunChecksQueueJobParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Model object returned to UI that has typed fields for each supported job parameter type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DqoJobEntryParametersModel {
    private SynchronizeRootFolderDqoQueueJobParameters synchronizeRootFolderParameters;
    private RunChecksCronSchedule runScheduledChecksParameters;
    private RunChecksQueueJobParameters runChecksParameters;
    private ImportSchemaQueueJobParameters importSchemaParameters;
}
