Log shipping controller that accepts logs sent from a web application or external tools and aggregates them in the local DQOps instance logs.  


___  
## log_debug  
Logs an information message in the server&#x27;s logs as a debug severity log entry.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/log_shipping/log_debug.py)
  

**POST**
```
http://localhost:8888/api/logs/debug  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Log entry|[ExternalLogEntry]()|false|



___  
## log_error  
Logs an information message in the server&#x27;s logs as an error severity log entry.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/log_shipping/log_error.py)
  

**POST**
```
http://localhost:8888/api/logs/error  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Log entry|[ExternalLogEntry]()|false|



___  
## log_info  
Logs an information message in the server&#x27;s logs as an info severity log entry.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/log_shipping/log_info.py)
  

**POST**
```
http://localhost:8888/api/logs/info  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Log entry|[ExternalLogEntry]()|false|



___  
## log_warn  
Logs an information message in the server&#x27;s logs as a warn severity log entry.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/log_shipping/log_warn.py)
  

**POST**
```
http://localhost:8888/api/logs/warn  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Log entry|[ExternalLogEntry]()|false|



