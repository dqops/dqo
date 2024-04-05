---
title: dqo scheduler command-line command
---
# dqo scheduler command-line command
The reference of the **scheduler** command in DQOps. Controls the repeating task scheduler by starting, stopping or running a foreground job scheduler.



___

## dqo scheduler start

Starts a background job scheduler


**Description**


This operation should be called only from the shell mode. When the DQOps is started as &#x27;dqo scheduler start&#x27; from the operating system, it will stop immediately.




**Command-line synopsis**

```
$ dqo [dqo options...] scheduler start [-h] [-fw] [-hl] [-crm=<checkRunMode>] [-of=<outputFormat>]
                 [-sm=<synchronizationMode>]

```

**DQOps shell synopsis**

```
dqo> scheduler start [-h] [-fw] [-hl] [-crm=<checkRunMode>] [-of=<outputFormat>]
                 [-sm=<synchronizationMode>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="scheduler start-crm" class="no-wrap-code">`-crm`</div><div id="scheduler start--check-run-mode" class="no-wrap-code">`--check-run-mode`</div>|Check execution reporting mode (silent, summary, info, debug)| |*silent*<br/>*summary*<br/>*info*<br/>*debug*<br/>|
|<div id="scheduler start-fw" class="no-wrap-code">`-fw`</div><div id="scheduler start--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="scheduler start--headless" class="no-wrap-code">`--headless`</div><div id="scheduler start-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="scheduler start-h" class="no-wrap-code">`-h`</div><div id="scheduler start--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="scheduler start-of" class="no-wrap-code">`-of`</div><div id="scheduler start--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="scheduler start-sm" class="no-wrap-code">`-sm`</div><div id="scheduler start--synchronization-mode" class="no-wrap-code">`--synchronization-mode`</div>|Reporting mode for the DQOps cloud synchronization (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|






___

## dqo scheduler stop

Stops a background job scheduler


**Description**


This operation should be called only from the shell mode after the scheduler was started.




**Command-line synopsis**

```
$ dqo [dqo options...] scheduler stop [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> scheduler stop [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="scheduler stop-fw" class="no-wrap-code">`-fw`</div><div id="scheduler stop--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="scheduler stop--headless" class="no-wrap-code">`--headless`</div><div id="scheduler stop-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="scheduler stop-h" class="no-wrap-code">`-h`</div><div id="scheduler stop--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="scheduler stop-of" class="no-wrap-code">`-of`</div><div id="scheduler stop--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|





