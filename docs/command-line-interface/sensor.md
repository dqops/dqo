---
title: dqo sensor command-line command
---
# dqo sensor command-line command
The reference of the **sensor** command in DQOps. Edit sensor template



___

## dqo sensor edit

Edit sensor that matches a given condition


**Description**


Allows you to modify the properties of a custom sensor that matches certain condition.




**Command-line synopsis**

```
$ dqo [dqo options...] sensor edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-p=<provider>]
             [-s=<name>]

```

**DQOps shell synopsis**

```
dqo> sensor edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-p=<provider>]
             [-s=<name>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="sensor edit-f" class="no-wrap-code">`-f`</div><div id="sensor edit--ext" class="no-wrap-code">`--ext`</div>|File type| |*JINJA2*<br/>*YAML*<br/>|
|<div id="sensor edit-fw" class="no-wrap-code">`-fw`</div><div id="sensor edit--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="sensor edit--headless" class="no-wrap-code">`--headless`</div><div id="sensor edit-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="sensor edit-h" class="no-wrap-code">`-h`</div><div id="sensor edit--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="sensor edit-of" class="no-wrap-code">`-of`</div><div id="sensor edit--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="sensor edit-p" class="no-wrap-code">`-p`</div><div id="sensor edit--provider" class="no-wrap-code">`--provider`</div>|Provider type| |*bigquery*<br/>*databricks*<br/>*mysql*<br/>*oracle*<br/>*postgresql*<br/>*duckdb*<br/>*presto*<br/>*redshift*<br/>*snowflake*<br/>*spark*<br/>*sqlserver*<br/>*trino*<br/>|
|<div id="sensor edit-s" class="no-wrap-code">`-s`</div><div id="sensor edit--sensor" class="no-wrap-code">`--sensor`</div>|Sensor name| ||





