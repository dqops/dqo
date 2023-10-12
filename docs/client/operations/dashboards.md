
## get_all_dashboards  
Returns a list of root folders with dashboards  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_all_dashboards.py)
  

**GET**
```
http://localhost:8888/api/dashboards  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dashboards_folder_spec]()||[DashboardsFolderSpec]()|






___  

## get_dashboard_level1  
Returns a single dashboard in the tree of folder with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level1.py)
  

**GET**
```
http://localhost:8888/api/dashboards/{folder}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|folder|Root folder name|string|true|
|dashboard_name|Dashboard name|string|true|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string|false|




___  

## get_dashboard_level2  
Returns a single dashboard in the tree of folders with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level2.py)
  

**GET**
```
http://localhost:8888/api/dashboards/{folder1}/{folder2}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|folder1|Root folder name|string|true|
|folder2|Second level folder name|string|true|
|dashboard_name|Dashboard name|string|true|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string|false|




___  

## get_dashboard_level3  
Returns a single dashboard in the tree of folders with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level3.py)
  

**GET**
```
http://localhost:8888/api/dashboards/{folder1}/{folder2}/{folder3}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|folder1|Root folder name|string|true|
|folder2|Second level folder name|string|true|
|folder3|Third level folder name|string|true|
|dashboard_name|Dashboard name|string|true|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string|false|




___  

## get_dashboard_level4  
Returns a single dashboard in the tree of folders with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level4.py)
  

**GET**
```
http://localhost:8888/api/dashboards/{folder1}/{folder2}/{folder3}/{folder4}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|folder1|Root folder name|string|true|
|folder2|Second level folder name|string|true|
|folder3|Third level folder name|string|true|
|folder4|Fourth level folder name|string|true|
|dashboard_name|Dashboard name|string|true|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string|false|




___  

## get_dashboard_level5  
Returns a single dashboard in the tree of folders with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level5.py)
  

**GET**
```
http://localhost:8888/api/dashboards/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|folder1|Root folder name|string|true|
|folder2|Second level folder name|string|true|
|folder3|Third level folder name|string|true|
|folder4|Fourth level folder name|string|true|
|folder5|Fifth level folder name|string|true|
|dashboard_name|Dashboard name|string|true|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string|false|




___  

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
|disable_thumbnail|Disables showing a thumbnail. A thumbnail url for Looker Studio dashboards is generated by adding /thumbnail to the end of the dashboard&#x27;s url. It is a Google generated thumbnail of the dashboard.|boolean| | | |

___  

## AuthenticatedDashboardModel  
Model that describes a single authenticated dashboard.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|folder_path|Folder path|string| | | |
|[dashboard](#dashboardspec)|Dashboard model with an unauthenticated url|[DashboardSpec](#dashboardspec)| | | |
|authenticated_dashboard_url|Dashboard authenticated url with a short lived refresh token|string| | | |

___  

