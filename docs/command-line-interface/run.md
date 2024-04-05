---
title: dqo run command-line command
---
# dqo run command-line command
The reference of the **run** command in DQOps. This command is useful when you want to continuously monitor the quality of your data in real-time. The job scheduler runs in the background, allowing you to perform other tasks while the DQOps is running.



___

## dqo run

Starts DQOps in a server mode, continuously running a job scheduler that runs the data quality checks


**Description**


This command is useful when you want to continuously monitor the quality of your data in real-time. The job scheduler runs in the background, allowing you to perform other tasks while the DQOps is running.




**Command-line synopsis**

```
$ dqo [dqo options...] run [-h] [-fw] [-hl] [-m=<checkRunMode>] [-of=<outputFormat>]
     [-s=<synchronizationMode>] [-t=<timeLimit>]

```

**DQOps shell synopsis**

```
dqo> run [-h] [-fw] [-hl] [-m=<checkRunMode>] [-of=<outputFormat>]
     [-s=<synchronizationMode>] [-t=<timeLimit>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="run-fw" class="no-wrap-code">`-fw`</div><div id="run--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="run--headless" class="no-wrap-code">`--headless`</div><div id="run-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="run-h" class="no-wrap-code">`-h`</div><div id="run--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="run-m" class="no-wrap-code">`-m`</div><div id="run--mode" class="no-wrap-code">`--mode`</div>|Check execution reporting mode (silent, summary, info, debug)| |*silent*<br/>*summary*<br/>*info*<br/>*debug*<br/>|
|<div id="run-of" class="no-wrap-code">`-of`</div><div id="run--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="run-s" class="no-wrap-code">`-s`</div><div id="run--synchronization-mode" class="no-wrap-code">`--synchronization-mode`</div>|Reporting mode for the DQOps cloud synchronization (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="run-t" class="no-wrap-code">`-t`</div><div id="run--time-limit" class="no-wrap-code">`--time-limit`</div>|Optional execution time limit. DQOps will run for the given duration and gracefully shut down. Supported values are in the following format: 300s (300 seconds), 10m (10 minutes), 2h (run for up to 2 hours) or just a number that is the time limit in seconds.| ||





