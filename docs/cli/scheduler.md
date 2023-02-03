# scheduler

___
### **dqo scheduler start**

**Description**

Starts a background job scheduler. This operation should be called only from the shell mode. When the dqo is started as &#x27;dqo scheduler start&#x27; from the operating system, it will stop immediately.

**Summary (Shell)**
```
dqo.ai>  scheduler start [-h] [-fw] [-hl] [-crm=<checkRunMode>] [-of=<outputFormat>]
                 [-sm=<synchronizationMode>]

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-crm`<br/>`--check-run-mode`<br/>|Check execution reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-sm`<br/>`--synchronization-mode`<br/>|Reporting mode for the DQO cloud synchronization (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|



___
### **dqo scheduler stop**

**Description**

Stops a background job scheduler. This operation should be called only from the shell mode after the scheduler was started.

**Summary (Shell)**
```
dqo.ai>  scheduler stop [-h] [-fw] [-hl] [-of=<outputFormat>]

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|


