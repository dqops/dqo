# dqo run command
Starts DQOps in a server mode, continuously running a job scheduler that runs the data quality checks

This command is useful when you want to continuously monitor the quality of your data in real-time. The job scheduler runs in the background, allowing you to perform other tasks while the DQOps is running.



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
|<p id="run-fw">`-fw`</p><br/><p id="run--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="run--headless">`--headless`</p><br/><p id="run-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="run-h">`-h`</p><br/><p id="run--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="run-m">`-m`</p><br/><p id="run--mode">`--mode`</p><br/>|Check execution reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|<p id="run-of">`-of`</p><br/><p id="run--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="run-s">`-s`</p><br/><p id="run--synchronization-mode">`--synchronization-mode`</p><br/>|Reporting mode for the DQOps cloud synchronization (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="run-t">`-t`</p><br/><p id="run--time-limit">`--time-limit`</p><br/>|Optional execution time limit. DQOps will run for the given duration and gracefully shut down. Supported values are in the following format: 300s (300 seconds), 10m (10 minutes), 2h (run for up to 2 hours) or just a number that is the time limit in seconds.| ||





