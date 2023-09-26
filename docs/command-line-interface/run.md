# run

___
### **dqo run**

Starts DQO in a server mode, continuously running a job scheduler that runs the data quality checks

**Description**

This command is useful when you want to continuously monitor the quality of your data in real-time. The job scheduler runs in the background, allowing you to perform other tasks while the DQO is running.


**Command-line synopsis**
```
$ dqo [dqo options...] run [-h] [-fw] [-hl] [-m=<checkRunMode>] [-of=<outputFormat>]
     [-s=<synchronizationMode>] [-t=<timeLimit>]

```
**DQO Shell synopsis**
```
dqo> run [-h] [-fw] [-hl] [-m=<checkRunMode>] [-of=<outputFormat>]
     [-s=<synchronizationMode>] [-t=<timeLimit>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Check execution reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-s`<br/>`--synchronization-mode`<br/>|Reporting mode for the DQO cloud synchronization (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-t`<br/>`--time-limit`<br/>|Optional execution time limit. DQO will run for the given duration and gracefully shut down. Supported values are in the following format: 300s (300 seconds), 10m (10 minutes), 2h (run for up to 2 hours) or just a number that is the time limit in seconds.| ||



