---
title: DQOps REST API users models reference
---
# DQOps REST API users models reference
The references of all objects used by [users](../operations/users.md) REST API operations are listed below.


## DqoUserRolesModel
DQOps user model - identifies a user in a multi-user DQOps deployment and the user&#x27;s roles.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`email`</span>|User's email that identifies the user.|*string*|
|<span class="no-wrap-code">[`account_role`](./environment.md#dqouserrole)</span>|User role at the whole account level. This role is applicable to all data domains.|*[DqoUserRole](./environment.md#dqouserrole)*|
|<span class="no-wrap-code">`data_domain_roles`</span>|User roles within each data domain. Data domains are supported in an ENTERPRISE version of DQOps and they are managed by the SaaS components of DQOps Cloud.|*Dict[string, [DqoUserRole](./environment.md#dqouserrole)]*|


___

