package com.dqops.rest.models.metadata;

import com.dqops.metadata.incidents.FilteredNotificationSpec;
import com.dqops.metadata.incidents.IncidentNotificationTargetSpec;
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
    private String name;

    @JsonPropertyDescription("Notification filter specification for filtering the incident by the values of its fields.")
    private NotificationFilterSpec filter;

    @JsonPropertyDescription("Notification target addresses for each of the status.")
    private IncidentNotificationTargetSpec target;

    @JsonPropertyDescription("The priority of the notification. Notifications are sent to the first notification targets that matches the filters when processAdditionalFilters is not set.")
    private Integer priority;

    @JsonPropertyDescription("Flag to break sending next notifications. Setting to true allows to send next notification from the list in priority order that matches the filter.")
    private Boolean processAdditionalFilters;

    @JsonPropertyDescription("Flag to turn off the notification filter.")
    private Boolean disabled;

    @JsonPropertyDescription("Description.")
    private String description;

    @JsonPropertyDescription("The doNotCreateIncidents flag that excludes from the creation of incidents that match the filters.")
    private Boolean doNotCreateIncidents;

    /**
     * Creates a FilteredNotificationModel that represents a single entry of the FilteredNotificationSpecMap.
     *
     * @param filteredNotificationName The map key.
     * @param filteredNotificationSpec The map value.
     * @return FilteredNotificationModel
     */
    public static FilteredNotificationModel fromFilteredNotificationMapEntry(
            String filteredNotificationName,
            FilteredNotificationSpec filteredNotificationSpec) {
        return new FilteredNotificationModel() {{
            setName(filteredNotificationName);
            setFilter(filteredNotificationSpec.getFilter());
            setTarget(filteredNotificationSpec.getTarget());
            setPriority(filteredNotificationSpec.getPriority());
            setProcessAdditionalFilters(filteredNotificationSpec.getProcessAdditionalFilters());
            setDisabled(filteredNotificationSpec.getDisabled());
            setDescription(filteredNotificationSpec.getDescription());
            setDoNotCreateIncidents(filteredNotificationSpec.getDoNotCreateIncidents());
        }};
    }

    /**
     * Creates a FilteredNotificationSpec from the FilteredNotificationModel.
     *
     * @return FilteredNotificationSpec
     */
    public FilteredNotificationSpec toSpec() {
        FilteredNotificationSpec spec = new FilteredNotificationSpec() {{
            setFilter(filter);
            setDescription(description);
            setTarget(target);
        }};

        if (priority != null) {
            spec.setPriority(priority);
        }

        if (processAdditionalFilters != null) {
            spec.setProcessAdditionalFilters(processAdditionalFilters);
        }

        if (disabled != null) {
            spec.setDisabled(disabled);
        }

        if (doNotCreateIncidents != null) {
            spec.setDoNotCreateIncidents(doNotCreateIncidents);
        }

        return spec;
    }
}
