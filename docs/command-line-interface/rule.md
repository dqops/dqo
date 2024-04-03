# dqo rule command
The reference of the **rule** command in DQOps. Edit sensor template



___

## dqo rule edit

Edit rule that matches a given condition


**Description**


This command can be used to update the rule. It is important to use caution when using this command, as it can impact the execution of data quality checks.




**Command-line synopsis**

```
$ dqo [dqo options...] rule edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-r=<name>]

```

**DQOps shell synopsis**

```
dqo> rule edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-r=<name>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="rule edit-f" class="no-wrap-code">`-f`</div><div id="rule edit--ext" class="no-wrap-code">`--ext`</div>|File type| |*PYTHON*<br/>*YAML*<br/>|
|<div id="rule edit-fw" class="no-wrap-code">`-fw`</div><div id="rule edit--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="rule edit--headless" class="no-wrap-code">`--headless`</div><div id="rule edit-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="rule edit-h" class="no-wrap-code">`-h`</div><div id="rule edit--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="rule edit-of" class="no-wrap-code">`-of`</div><div id="rule edit--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="rule edit-r" class="no-wrap-code">`-r`</div><div id="rule edit--rule" class="no-wrap-code">`--rule`</div>|Rule name| ||





