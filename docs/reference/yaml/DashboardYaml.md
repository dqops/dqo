
## DashboardListSpec  
List of dashboards.  
  
















___  

## DashboardsFolderListSpec  
List of dashboard folders.  
  






___  

## DashboardsFolderSpec  
Description of a folder with multiple dashboards or other folders.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|folder_name|Folder name|string| | | |
|[dashboards](#dashboardlistspec)|List of data quality dashboard at this level.|[DashboardListSpec](#dashboardlistspec)| | | |
|[folders](#dashboardsfolderlistspec)|List of data quality dashboard folders at this level.|[DashboardsFolderListSpec](#dashboardsfolderlistspec)| | | |









___  

## DashboardYaml  
Data quality dashboard definition YAML schema for a data quality dashboards list specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>dashboards<br/>source<br/>sensor<br/>check<br/>rule<br/>file_index<br/>settings<br/>provider_sensor<br/>| | |
|[spec](#dashboardsfolderlistspec)||[DashboardsFolderListSpec](#dashboardsfolderlistspec)| | | |









___  

## DashboardSpec  
Description of a single dashboard that is available in the platform.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|dashboard_name|Dashboard name|string| | | |
|url|Dashboard url|string| | | |
|width|Dashboard width (px)|integer| | | |
|height|Dashboard height (px)|integer| | | |









___  

