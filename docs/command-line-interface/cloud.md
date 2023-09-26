# cloud

___
### **dqo cloud login**

Log in or register an account at the DQO Cloud

**Description**

Allow user to provide login credentials if the user already has an account.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud login [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud login [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync data**

Synchronize local &quot;data&quot; folder with sensor readouts and rule results with DQO Cloud

**Description**

Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;data&quot; folder.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync data [-hr] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                 [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud sync data [-hr] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                 [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-r`<br/>`--refresh-data-warehouse`<br/>|Force refresh a whole table in the data quality data warehouse| ||




___
### **dqo cloud sync sources**

Synchronize local &quot;sources&quot; connection and table level quality definitions with DQO Cloud

**Description**

Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;sources&quot; folder.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync sources [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud sync sources [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync sensors**

Synchronize local &quot;sensors&quot; folder with custom sensor definitions with DQO Cloud

**Description**

Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;sensors&quot; folder.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync sensors [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud sync sensors [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync rules**

Synchronize local &quot;rules&quot; folder with custom rule definitions with DQO Cloud

**Description**

Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;rules&quot; folder.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync rules [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                  [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud sync rules [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                  [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync checks**

Synchronize local &quot;checks&quot; folder with custom check definitions with DQO Cloud

**Description**

Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;checks&quot; folder.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync checks [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                   [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud sync checks [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                   [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync settings**

Synchronize local &quot;settings&quot; folder with DQO Cloud

**Description**

Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;settings&quot; folder.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync settings [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                     [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud sync settings [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                     [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync credentials**

Synchronize local &quot;.credentials&quot; folder that stores shared credentials with DQO Cloud

**Description**

Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;.credentials&quot; folder.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync credentials [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                        [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud sync credentials [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                        [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync all**

Synchronize local files with DQO Cloud (sources, table rules, custom rules, custom sensors and data - sensor readouts and rule results)

**Description**

Uploads any local changes to the cloud and downloads any changes made to the cloud versions of the folders.


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync all [-hr] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> cloud sync all [-hr] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-r`<br/>`--refresh-data-warehouse`<br/>|Force refresh a whole table in the data quality data warehouse| ||



