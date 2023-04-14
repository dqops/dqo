# scheduler

___
### **dqo scheduler start**

Starts a background job scheduler

**Description**

This operation should be called only from the shell mode. When the DQO is started as &#x27;dqo scheduler start&#x27; from the operating system, it will stop immediately.


**Command-line synopsis**
```
$ dqo [dqo options...] scheduler start [-h] [-fw] [-hl] [-crm=<checkRunMode>] [-of=<outputFormat>]
                 [-sm=<synchronizationMode>]

```
**DQO Shell synopsis**
```
dqo.ai> scheduler start [-h] [-fw] [-hl] [-crm=<checkRunMode>] [-of=<outputFormat>]
                 [-sm=<synchronizationMode>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-crm`<br/>`--check-run-mode`<br/>|Check execution reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-sm`<br/>`--synchronization-mode`<br/>|Reporting mode for the DQO cloud synchronization (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|




___
### **dqo scheduler stop**

Stops a background job scheduler

**Description**

This operation should be called only from the shell mode after the scheduler was started.


**Command-line synopsis**
```
$ dqo [dqo options...] scheduler stop [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> scheduler stop [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|



