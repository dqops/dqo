
## is_healthy  
Checks if the DQOps instance is healthy and operational. Returns a text &quot;OK&quot; and a HTTP status code 200 when the service is active and can accept jobs,  or returns a text &quot;UNAVAILABLE&quot; and a HTTP status code 503 when the service is still starting or is shutting down.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/healthcheck/is_healthy.py)
  

**GET**
```
http://localhost:8888/api/ishealthy  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|string||string|






___  

___  

