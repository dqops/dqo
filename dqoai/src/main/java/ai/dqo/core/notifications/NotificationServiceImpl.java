package ai.dqo.core.notifications;

import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.checkresults.factory.CheckResultsColumnNames;
import ai.dqo.data.checkresults.snapshot.CheckResultsSnapshot;
import ai.dqo.metadata.notifications.NotificationSettingsSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import com.google.common.base.Strings;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;
import tech.tablesaw.table.TableSlice;
import tech.tablesaw.table.TableSliceGroup;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Notification service that sends notifications when new data quality issues are detected.
 */
@Component
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    public NotificationServiceImpl() {
    }

    /**
     * Detects new data quality issues and sends a notification about a table that is affected.
     * @param connectionSpec Connection specification.
     * @param tableSpec Table specification.
     * @param checkResultsSnapshot Rule results snapshot with existing and new rule results.
     */
    @Override
    public Mono<Void> detectNewIssuesAndSendNotification(ConnectionSpec connectionSpec,
                                                         TableSpec tableSpec,
                                                         CheckResultsSnapshot checkResultsSnapshot) {
        NotificationSettingsSpec notificationSettings = connectionSpec.getNotifications();
        if (notificationSettings == null || Strings.isNullOrEmpty(notificationSettings.getWebhookUrl())) {
            return Mono.empty(); // the notifications not configured
        }

        Table newOrChangedRows = checkResultsSnapshot.getTableDataChanges().getNewOrChangedRows();
        Selection hasAlertSelection = newOrChangedRows.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME).isGreaterThan(0.0);
        Table rowsWithQualityIssues = newOrChangedRows.where(hasAlertSelection);
        TableSliceGroup newAlertsByTimeSeries = rowsWithQualityIssues.splitOn(SensorReadoutsColumnNames.TIME_SERIES_ID_COLUMN_NAME);
        Table allOldRuleResults = checkResultsSnapshot.getAllData();
        Table allOldRuleResultsWithAlerts = allOldRuleResults.where(
                allOldRuleResults.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME).isGreaterThan(0.0));
        StringColumn oldRuleResultTimeSeriesIdColumn = allOldRuleResultsWithAlerts.stringColumn(SensorReadoutsColumnNames.TIME_SERIES_ID_COLUMN_NAME);
        Set<String> checksWithNewAlerts = new LinkedHashSet<>();

        for (TableSlice newAlertTimeSeriesSlice : newAlertsByTimeSeries) {
            Table newAlertsTimeSeriesData = newAlertTimeSeriesSlice.getTable();
            String timeSeriesId = newAlertsTimeSeriesData.stringColumn(SensorReadoutsColumnNames.TIME_SERIES_ID_COLUMN_NAME).get(0);

            Table oldResultsForTimeSeries = allOldRuleResultsWithAlerts.where(oldRuleResultTimeSeriesIdColumn.isEqualTo(timeSeriesId));
            if (oldResultsForTimeSeries.rowCount() > 0) {
                // find when we raised the last issue
                Instant newAlertExecutedAt = newAlertsTimeSeriesData.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME).get(0);
                Instant mostRecentIssueExecutionTimestamp = oldResultsForTimeSeries.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME).max();
                Instant skipAlertsSince = newAlertExecutedAt.minus(notificationSettings.getHoursSinceLastAlert(), ChronoUnit.HOURS);

                if (!mostRecentIssueExecutionTimestamp.isBefore(skipAlertsSince)) {
                    continue;
                }
            }

            String checkName = newAlertsTimeSeriesData.stringColumn(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME).get(0);
            checksWithNewAlerts.add(checkName);
        }

        if (checksWithNewAlerts.size() == 0) {
            return Mono.empty();
        }

        return sendNotification(connectionSpec, tableSpec, checksWithNewAlerts);
    }

    /**
     * Sends a notification to the registered notification endpoint.
     * @param connectionSpec Connection specification.
     * @param tableSpec Table specification of the affected table.
     * @param checksWithNewAlerts Names of checks that were affected.
     * @return Mono to wait for the call to finish.
     */
    protected Mono<Void> sendNotification(ConnectionSpec connectionSpec, TableSpec tableSpec, Set<String> checksWithNewAlerts) {
        NewIssueOnTableNotificationMessage notificationMessage = new NewIssueOnTableNotificationMessage();
        notificationMessage.setConnection(connectionSpec.getConnectionName());
        notificationMessage.setSchema(tableSpec.getTarget().getSchemaName());
        notificationMessage.setTable(tableSpec.getTarget().getTableName());
        notificationMessage.setQualityChecks(checksWithNewAlerts);

        NotificationSettingsSpec notificationSettings = connectionSpec.getNotifications();
        WebClient webClient = WebClient.builder()
                .baseUrl(notificationSettings.getWebhookUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        WebClient.ResponseSpec responseSpec = webClient.method(HttpMethod.POST)
                .body(Mono.just(notificationMessage), NewIssueOnTableNotificationMessage.class)
                .retrieve();

        Mono<ResponseEntity<Void>> responseEntityMono = responseSpec.toBodilessEntity();
        return (Mono<Void>)(Mono<?>)responseEntityMono;
    }
}
