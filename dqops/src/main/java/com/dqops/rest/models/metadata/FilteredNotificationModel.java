package com.dqops.rest.models.metadata;

import com.dqops.metadata.incidents.FilteredNotificationSpec;
import com.dqops.metadata.incidents.IncidentNotificationSpec;
import com.dqops.metadata.incidents.NotificationFilterSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Named filtered notification model that represents a single entry of the FilteredNotificationSpecMap.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "FilteredNotificationModel", description = "Filtered notification list model.")
public class FilteredNotificationModel {

    @JsonPropertyDescription("The name of the filtered notification.")
    private String filteredNotificationName;

    @JsonPropertyDescription("Notification filter specification for filtering the incident by the values of its fields.")
    private NotificationFilterSpec notificationFilter;

    @JsonPropertyDescription("Notification target addresses for each of the status.")
    private IncidentNotificationSpec notificationTarget;

    @JsonPropertyDescription("The priority of the notification. Notifications are sent to the first notification targets that matches the filters when processAdditionalFilters is not set.")
    private Integer priority;

    @JsonPropertyDescription("Flag to break sending next notifications. Setting to true allows to send next notification from the list in priority order that matches the filter.")
    private Boolean processAdditionalFilters;

    public static FilteredNotificationModel fromFilteredNotificationMapEntry(
            String filteredNotificationName,
            FilteredNotificationSpec filteredNotificationSpec) {
        return new FilteredNotificationModel(){{
            setFilteredNotificationName(filteredNotificationName);
            setNotificationFilter(filteredNotificationSpec.getNotificationFilter());
            setNotificationTarget(filteredNotificationSpec.getNotificationTarget());
            setPriority(filteredNotificationSpec.getPriority());
            setProcessAdditionalFilters(filteredNotificationSpec.getProcessAdditionalFilters());
        }};
    }

    public FilteredNotificationSpec toSpec() {
        return new FilteredNotificationSpec(){{
            setNotificationFilter(getNotificationFilter());
            setNotificationTarget(getNotificationTarget());
            setPriority(getPriority());
            setProcessAdditionalFilters(getProcessAdditionalFilters());
        }};
    }

}
