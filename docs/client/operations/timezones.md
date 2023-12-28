Timezone management  


___  
## get_available_zone_ids  
Returns a list of available time zone ids  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/timezones/get_available_zone_ids.py)
  

**GET**
```
http://localhost:8888/api/timezones  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|string||List[string]|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/timezones^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    []
    ```


