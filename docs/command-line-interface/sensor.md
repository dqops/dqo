# sensor

___
### **dqo sensor edit**

Edit sensor that matches a given condition

**Description**

Allows you to modify the properties of a custom sensor that matches certain condition.


**Command-line synopsis**
```
$ dqo [dqo options...] sensor edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-p=<provider>]
             [-s=<name>]

```
**DQO Shell synopsis**
```
dqo> sensor edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-p=<provider>]
             [-s=<name>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-f`<br/>`--ext`<br/>|File type| |JINJA2<br/>YAML<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-p`<br/>`--provider`<br/>|Provider type| |bigquery<br/>snowflake<br/>postgresql<br/>redshift<br/>sqlserver<br/>mysql<br/>oracle<br/>|
|`-s`<br/>`--sensor`<br/>|Sensor name| ||



