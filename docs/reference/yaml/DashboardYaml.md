---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## DashboardYaml
Data quality dashboard definition YAML schema for a data quality dashboards list specification.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>|DQOps YAML schema version|*string*| |dqo/v1| |
|<span class="no-wrap-code ">`kind`</span>|File type|*enum*|*source*<br/>*table*<br/>*sensor*<br/>*provider_sensor*<br/>*rule*<br/>*check*<br/>*settings*<br/>*file_index*<br/>*connection_similarity_index*<br/>*dashboards*<br/>*default_schedules*<br/>*default_checks*<br/>*default_table_checks*<br/>*default_column_checks*<br/>*default_notifications*<br/>|dashboards| |
|<span class="no-wrap-code ">[`spec`](./DashboardYaml.md#dashboardsfolderlistspec)</span>|The data quality dashboards folder tree with the definition of custom dashboards|*[DashboardsFolderListSpec](./DashboardYaml.md#dashboardsfolderlistspec)*| | | |



___

## DashboardsFolderListSpec
List of dashboard folders.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*List[[DashboardsFolderSpec](./DashboardYaml.md#dashboardsfolderspec)]*| | | |



___

## DashboardsFolderSpec
Description of a folder with multiple dashboards or other folders.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`folder_name`</span>|Folder name|*string*| | | |
|<span class="no-wrap-code ">`standard`</span>|Always shows this schema tree node because it contains standard dashboards. Set the value to false to show this folder only when advanced dashboards are enabled.|*boolean*| | | |
|<span class="no-wrap-code ">[`dashboards`](./DashboardYaml.md#dashboardlistspec)</span>|List of data quality dashboard at this level.|*[DashboardListSpec](./DashboardYaml.md#dashboardlistspec)*| | | |
|<span class="no-wrap-code ">[`folders`](./DashboardYaml.md#dashboardsfolderlistspec)</span>|List of data quality dashboard folders at this level.|*[DashboardsFolderListSpec](./DashboardYaml.md#dashboardsfolderlistspec)*| | | |
|<span class="no-wrap-code ">`hide_folder`</span>|Hides the whole folder and all nested dashboards from the navigation tree. If you want to hide some of the build-in folders, update the settings/dashboardslist.dqodashboards.yaml file in the DQOps user home folder, create an empty folder with the same name as a built-in folder, and set the value of this field to true.|*boolean*| | | |



___

## DashboardListSpec
List of dashboards.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*List[[DashboardSpec](./DashboardYaml.md#dashboardspec)]*| | | |



___

## DashboardSpec
Description of a single dashboard that is available in the platform.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`dashboard_name`</span>|Dashboard name|*string*| | | |
|<span class="no-wrap-code ">`url`</span>|Dashboard url|*string*| | | |
|<span class="no-wrap-code ">`width`</span>|Dashboard width (px)|*integer*| | | |
|<span class="no-wrap-code ">`height`</span>|Dashboard height (px)|*integer*| | | |
|<span class="no-wrap-code ">`standard`</span>|Shows the dashboard always in the data quality dashboard section. The dashboards that are not &#x27;standard&#x27; are advanced dashboards, hidden initially.|*boolean*| | | |
|<span class="no-wrap-code ">`disable_thumbnail`</span>|Disables showing a thumbnail. A thumbnail url for Looker Studio dashboards is generated by adding /thumbnail to the end of the dashboard&#x27;s url. It is a Google generated thumbnail of the dashboard.|*boolean*| | | |
|<span class="no-wrap-code ">`parameters`</span>|Key/value dictionary of additional parameters to be passed to the dashboard|*Dict[string, string]*| | | |



___

