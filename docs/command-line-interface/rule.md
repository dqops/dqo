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
|<p id="rule edit-f">`-f`</p><br/><p id="rule edit--ext">`--ext`</p><br/>|File type| |PYTHON<br/>YAML<br/>|
|<p id="rule edit-fw">`-fw`</p><br/><p id="rule edit--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="rule edit--headless">`--headless`</p><br/><p id="rule edit-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="rule edit-h">`-h`</p><br/><p id="rule edit--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="rule edit-of">`-of`</p><br/><p id="rule edit--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="rule edit-r">`-r`</p><br/><p id="rule edit--rule">`--rule`</p><br/>|Rule name| ||





