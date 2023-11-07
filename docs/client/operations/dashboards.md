Provides access to data quality dashboards  


___  
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
|dashboards_folder_spec||List[[DashboardsFolderSpec](/docs/client/models/dashboards/#dashboardsfolderspec)]|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/dashboards^
		-H "Accept: application/json"

    ```


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
|[authenticated_dashboard_model](/docs/client/models/dashboards/#authenticateddashboardmodel)||[AuthenticatedDashboardModel](/docs/client/models/dashboards/#authenticateddashboardmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder|Root folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_0/sample_dashboard^
		-H "Accept: application/json"

    ```


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
|[authenticated_dashboard_model](/docs/client/models/dashboards/#authenticateddashboardmodel)||[AuthenticatedDashboardModel](/docs/client/models/dashboards/#authenticateddashboardmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder1|Root folder name|string|:material-check-bold:|
|folder2|Second level folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_dashboard^
		-H "Accept: application/json"

    ```


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
|[authenticated_dashboard_model](/docs/client/models/dashboards/#authenticateddashboardmodel)||[AuthenticatedDashboardModel](/docs/client/models/dashboards/#authenticateddashboardmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder1|Root folder name|string|:material-check-bold:|
|folder2|Second level folder name|string|:material-check-bold:|
|folder3|Third level folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_dashboard^
		-H "Accept: application/json"

    ```


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
|[authenticated_dashboard_model](/docs/client/models/dashboards/#authenticateddashboardmodel)||[AuthenticatedDashboardModel](/docs/client/models/dashboards/#authenticateddashboardmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder1|Root folder name|string|:material-check-bold:|
|folder2|Second level folder name|string|:material-check-bold:|
|folder3|Third level folder name|string|:material-check-bold:|
|folder4|Fourth level folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_folder_4/sample_dashboard^
		-H "Accept: application/json"

    ```


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
|[authenticated_dashboard_model](/docs/client/models/dashboards/#authenticateddashboardmodel)||[AuthenticatedDashboardModel](/docs/client/models/dashboards/#authenticateddashboardmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder1|Root folder name|string|:material-check-bold:|
|folder2|Second level folder name|string|:material-check-bold:|
|folder3|Third level folder name|string|:material-check-bold:|
|folder4|Fourth level folder name|string|:material-check-bold:|
|folder5|Fifth level folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_folder_4/sample_folder_5/sample_dashboard^
		-H "Accept: application/json"

    ```


