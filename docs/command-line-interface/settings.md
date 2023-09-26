# settings

___
### **dqo settings editor set**

Set editor settings

**Description**

Set the settings for the editor. It allows user to set the editor to use a specific output format.


**Command-line synopsis**
```
$ dqo [dqo options...] settings editor set [-h] [-fw] [-hl] [-n=<editorName>] [-of=<outputFormat>]
                     [-p=<editorPath>]

```
**DQO Shell synopsis**
```
dqo> settings editor set [-h] [-fw] [-hl] [-n=<editorName>] [-of=<outputFormat>]
                     [-p=<editorPath>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--name`<br/>|Editor name| |VSC<br/>IntelliJ<br/>Eclipse<br/>PyCharm<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-p`<br/>`--path`<br/>|Editor path| ||




___
### **dqo settings editor remove**

Remove editor settings

**Description**

Remove the editor settings from your configuration. This will revert the editor to its default settings.


**Command-line synopsis**
```
$ dqo [dqo options...] settings editor remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> settings editor remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings editor show**

Show editor settings

**Description**

Display the current editor settings.


**Command-line synopsis**
```
$ dqo [dqo options...] settings editor show [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> settings editor show [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings init**

Initialize settings file in UserHome directory

**Description**

Settings file in your UserHome directory. This file stores configuration options for the DQO.


**Command-line synopsis**
```
$ dqo [dqo options...] settings init [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> settings init [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings remove**

Remove settings file from UserHome directory

**Description**

Removes the settings file from your UserHome directory.


**Command-line synopsis**
```
$ dqo [dqo options...] settings remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> settings remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings apikey set**

Set API key

**Description**

Set the API key used for accessing external services. This key is used to authenticate requests to the service.


**Command-line synopsis**
```
$ dqo [dqo options...] settings apikey set [-h] [-fw] [-hl] [-of=<outputFormat>] <key>

```
**DQO Shell synopsis**
```
dqo> settings apikey set [-h] [-fw] [-hl] [-of=<outputFormat>] <key>

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings apikey remove**

Remove API key

**Description**

Remove the API key used for accessing external services.


**Command-line synopsis**
```
$ dqo [dqo options...] settings apikey remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> settings apikey remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings apikey show**

Show API key

**Description**

Display the current API key used for accessing external services.


**Command-line synopsis**
```
$ dqo [dqo options...] settings apikey show [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> settings apikey show [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings timezone set**

Set the default time zone

**Description**

Set the default time zone used by the DQO.


**Command-line synopsis**
```
$ dqo [dqo options...] settings timezone set [-h] [-fw] [-hl] [-of=<outputFormat>] <timeZone>

```
**DQO Shell synopsis**
```
dqo> settings timezone set [-h] [-fw] [-hl] [-of=<outputFormat>] <timeZone>

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings timezone remove**

Remove time zone

**Description**

Remove the custom time zone from your settings. Once removed, the time zone for your account will be set to the system default.


**Command-line synopsis**
```
$ dqo [dqo options...] settings timezone remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> settings timezone remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo settings timezone show**

Show the default time zone

**Description**

Displays the default time zone that is currently set in your settings. This time zone will be used to display all date and time values in the application.


**Command-line synopsis**
```
$ dqo [dqo options...] settings timezone show [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo> settings timezone show [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|



