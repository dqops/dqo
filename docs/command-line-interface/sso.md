---
title: dqo sso command-line command
---
# dqo sso command-line command
The reference of the **sso** command in DQOps. Commands related to configuring Single Sign-On authentication



___

## dqo sso provision

Provisions a realm in Keycloak


**Description**


Creates and configures a new realm in Keycloak, configuring all required settings. This command is supported only in paid versions of DQOps. Please contact DQOps sales for details.




**Command-line synopsis**

```
$ dqo [dqo options...] sso provision [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> sso provision [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="sso provision-fw" class="no-wrap-code">`-fw`</div><div id="sso provision--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="sso provision--headless" class="no-wrap-code">`--headless`</div><div id="sso provision-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="sso provision-h" class="no-wrap-code">`-h`</div><div id="sso provision--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="sso provision-of" class="no-wrap-code">`-of`</div><div id="sso provision--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|





