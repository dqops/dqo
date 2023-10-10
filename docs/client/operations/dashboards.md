
## get_all_dashboards  
Returns a list of root folders with dashboards  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_all_dashboards.py)
  

**GET**
```
api/dashboards  
```





___  

## get_dashboard_level1  
Returns a single dashboard in the tree of folder with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level1.py)
  

**GET**
```
api/dashboards/{folder}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|






___  

## get_dashboard_level2  
Returns a single dashboard in the tree of folders with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level2.py)
  

**GET**
```
api/dashboards/{folder1}/{folder2}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|






___  

## get_dashboard_level3  
Returns a single dashboard in the tree of folders with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level3.py)
  

**GET**
```
api/dashboards/{folder1}/{folder2}/{folder3}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|






___  

## get_dashboard_level4  
Returns a single dashboard in the tree of folders with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level4.py)
  

**GET**
```
api/dashboards/{folder1}/{folder2}/{folder3}/{folder4}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|






___  

## get_dashboard_level5  
Returns a single dashboard in the tree of folders with a temporary authenticated url  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level5.py)
  

**GET**
```
api/dashboards/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{dashboardName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](\docs\client\operations\dashboards\#authenticateddashboardmodel)||[AuthenticatedDashboardModel](\docs\client\operations\dashboards\#authenticateddashboardmodel)|






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

___  

## AuthenticatedDashboardModel  
Model that describes a single authenticated dashboard.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|folder_path|Folder path|string| | | |
|[dashboard](#dashboardspec)|Dashboard model with an unauthenticated url|[dashboard](#dashboardspec)| | | |
|authenticated_dashboard_url|Dashboard authenticated url with a short lived refresh token|string| | | |

___  

