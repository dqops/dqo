# run

___
### **dqo run**

**Description**

Starts DQO in a server mode, continuously running a job scheduler that runs the data quality checks.

**Summary (Shell)**
```
dqo.ai>  run [-h] [-fw] [-hl] [-m=<checkRunMode>] [-of=<outputFormat>]
     [-s=<synchronizationMode>] [-t=<timeLimit>]

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Check execution reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-s`<br/>`--synchronization-mode`<br/>|Reporting mode for the DQO cloud synchronization (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-t`<br/>`--time-limit`<br/>|Optional execution time limit. DQO will run for the given duration and gracefully shut down. Supported values are in the following format: 300s (300 seconds), 10m (10 minutes), 2h (run for up to 2 hours) or just a number that is the time limit in seconds.| ||


