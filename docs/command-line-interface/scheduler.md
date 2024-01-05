# scheduler

___
### **dqo scheduler start**

Starts a background job scheduler

**Description**

This operation should be called only from the shell mode. When the DQOps is started as &#x27;dqo scheduler start&#x27; from the operating system, it will stop immediately.


**Command-line synopsis**
```
$ dqo [dqo options...] scheduler start [-h] [-fw] [-hl] [-crm=<checkRunMode>] [-of=<outputFormat>]
                 [-sm=<synchronizationMode>]

```
**DQOps Shell synopsis**
```
dqo> scheduler start [-h] [-fw] [-hl] [-crm=<checkRunMode>] [-of=<outputFormat>]
                 [-sm=<synchronizationMode>]

```

**Options**

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="scheduler start-crm">`-crm`</p><br/><p id="scheduler start--check-run-mode">`--check-run-mode`</p><br/>|Check execution reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|<p id="scheduler start-fw">`-fw`</p><br/><p id="scheduler start--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="scheduler start--headless">`--headless`</p><br/><p id="scheduler start-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="scheduler start-h">`-h`</p><br/><p id="scheduler start--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="scheduler start-of">`-of`</p><br/><p id="scheduler start--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="scheduler start-sm">`-sm`</p><br/><p id="scheduler start--synchronization-mode">`--synchronization-mode`</p><br/>|Reporting mode for the DQOps cloud synchronization (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|




___
### **dqo scheduler stop**

Stops a background job scheduler

**Description**

This operation should be called only from the shell mode after the scheduler was started.


**Command-line synopsis**
```
$ dqo [dqo options...] scheduler stop [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQOps Shell synopsis**
```
dqo> scheduler stop [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="scheduler stop-fw">`-fw`</p><br/><p id="scheduler stop--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="scheduler stop--headless">`--headless`</p><br/><p id="scheduler stop-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="scheduler stop-h">`-h`</p><br/><p id="scheduler stop--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="scheduler stop-of">`-of`</p><br/><p id="scheduler stop--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|



