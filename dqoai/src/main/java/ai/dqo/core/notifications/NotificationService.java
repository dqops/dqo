package ai.dqo.core.notifications;

import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshot;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import reactor.core.publisher.Mono;

/**
 * Notification service that sends notifications when new data quality issues are detected.
 */
public interface NotificationService {
    /**
     * Detects new data quality issues and sends a notification about a table that is affected.
     * @param connectionSpec Connection specification.
     * @param tableSpec Table specification.
     * @param ruleResultsSnapshot Rule results snapshot with existing and new rule results.
     */
    Mono<Void> detectNewIssuesAndSendNotification(ConnectionSpec connectionSpec,
                                                  TableSpec tableSpec,
                                                  RuleResultsSnapshot ruleResultsSnapshot);
}
