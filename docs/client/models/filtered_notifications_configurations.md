---
title: DQOps REST API filtered_notifications_configurations models reference
---
# DQOps REST API filtered_notifications_configurations models reference
The references of all objects used by [filtered_notifications_configurations](../operations/filtered_notifications_configurations.md) REST API operations are listed below.


## FilteredNotificationModel
Named filtered notification model that represents a single entry of the FilteredNotificationSpecMap.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`name`</span>|The name of the filtered notification.|*string*|
|<span class="no-wrap-code">[`filter`](../../reference/yaml/ConnectionYaml.md#notificationfilterspec)</span>|Notification filter specification for filtering the incident by the values of its fields.|*[NotificationFilterSpec](../../reference/yaml/ConnectionYaml.md#notificationfilterspec)*|
|<span class="no-wrap-code">[`target`](../../reference/yaml/ConnectionYaml.md#incidentnotificationtargetspec)</span>|Notification target addresses for each of the status.|*[IncidentNotificationTargetSpec](../../reference/yaml/ConnectionYaml.md#incidentnotificationtargetspec)*|
|<span class="no-wrap-code">`priority`</span>|The priority of the notification. Notifications are sent to the first notification targets that matches the filters when processAdditionalFilters is not set.|*integer*|
|<span class="no-wrap-code">`process_additional_filters`</span>|Flag to break sending next notifications. Setting to true allows to send next notification from the list in priority order that matches the filter.|*boolean*|
|<span class="no-wrap-code">`disabled`</span>|Flag to turn off the notification filter.|*boolean*|
|<span class="no-wrap-code">`description`</span>|Description.|*string*|


___

