
## DqoUserRole  
DQOps user role within a data domain or a whole account level.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|EDITOR<br/>OPERATOR<br/>VIEWER<br/>ADMIN<br/>NONE<br/>|

___  

## DqoCloudUserModel  
DQOps Cloud user model - identifies a user in a multi-user DQOps deployment.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|email|User&#x27;s email that identifies the user.|string|
|account_role|Account role.|[DqoUserRole](#null)|


___  

