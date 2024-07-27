---
title: dqo settings command-line command
---
# dqo settings command-line command
The reference of the **settings** command in DQOps. Show or set settings



___

## dqo settings editor set

Set editor settings


**Description**


Set the settings for the editor. It allows user to set the editor to use a specific output format.




**Command-line synopsis**

```
$ dqo [dqo options...] settings editor set [-h] [-fw] [-hl] [-n=<editorName>] [-of=<outputFormat>]
                     [-p=<editorPath>]

```

**DQOps shell synopsis**

```
dqo> settings editor set [-h] [-fw] [-hl] [-n=<editorName>] [-of=<outputFormat>]
                     [-p=<editorPath>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings editor set-fw" class="no-wrap-code">`-fw`</div><div id="settings editor set--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings editor set--headless" class="no-wrap-code">`--headless`</div><div id="settings editor set-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings editor set-h" class="no-wrap-code">`-h`</div><div id="settings editor set--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings editor set-n" class="no-wrap-code">`-n`</div><div id="settings editor set--name" class="no-wrap-code">`--name`</div>|Editor name| |*VSC*<br/>*IntelliJ*<br/>*Eclipse*<br/>*PyCharm*<br/>|
|<div id="settings editor set-of" class="no-wrap-code">`-of`</div><div id="settings editor set--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="settings editor set-p" class="no-wrap-code">`-p`</div><div id="settings editor set--path" class="no-wrap-code">`--path`</div>|Editor path| ||






___

## dqo settings editor remove

Remove editor settings


**Description**


Remove the editor settings from your configuration. This will revert the editor to its default settings.




**Command-line synopsis**

```
$ dqo [dqo options...] settings editor remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings editor remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings editor remove-fw" class="no-wrap-code">`-fw`</div><div id="settings editor remove--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings editor remove--headless" class="no-wrap-code">`--headless`</div><div id="settings editor remove-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings editor remove-h" class="no-wrap-code">`-h`</div><div id="settings editor remove--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings editor remove-of" class="no-wrap-code">`-of`</div><div id="settings editor remove--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings editor show

Show editor settings


**Description**


Display the current editor settings.




**Command-line synopsis**

```
$ dqo [dqo options...] settings editor show [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings editor show [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings editor show-fw" class="no-wrap-code">`-fw`</div><div id="settings editor show--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings editor show--headless" class="no-wrap-code">`--headless`</div><div id="settings editor show-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings editor show-h" class="no-wrap-code">`-h`</div><div id="settings editor show--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings editor show-of" class="no-wrap-code">`-of`</div><div id="settings editor show--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings init

Initialize settings file in UserHome directory


**Description**


Settings file in your UserHome directory. This file stores configuration options for the DQOps.




**Command-line synopsis**

```
$ dqo [dqo options...] settings init [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings init [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings init-fw" class="no-wrap-code">`-fw`</div><div id="settings init--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings init--headless" class="no-wrap-code">`--headless`</div><div id="settings init-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings init-h" class="no-wrap-code">`-h`</div><div id="settings init--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings init-of" class="no-wrap-code">`-of`</div><div id="settings init--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings remove

Remove settings file from UserHome directory


**Description**


Removes the settings file from your UserHome directory.




**Command-line synopsis**

```
$ dqo [dqo options...] settings remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings remove-fw" class="no-wrap-code">`-fw`</div><div id="settings remove--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings remove--headless" class="no-wrap-code">`--headless`</div><div id="settings remove-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings remove-h" class="no-wrap-code">`-h`</div><div id="settings remove--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings remove-of" class="no-wrap-code">`-of`</div><div id="settings remove--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings apikey set

Set API key


**Description**


Set the API key used for accessing external services. This key is used to authenticate requests to the service.




**Command-line synopsis**

```
$ dqo [dqo options...] settings apikey set [-h] [-fw] [-hl] [-of=<outputFormat>] <key>

```

**DQOps shell synopsis**

```
dqo> settings apikey set [-h] [-fw] [-hl] [-of=<outputFormat>] <key>

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings apikey set-fw" class="no-wrap-code">`-fw`</div><div id="settings apikey set--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings apikey set--headless" class="no-wrap-code">`--headless`</div><div id="settings apikey set-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings apikey set-h" class="no-wrap-code">`-h`</div><div id="settings apikey set--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings apikey set-of" class="no-wrap-code">`-of`</div><div id="settings apikey set--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings apikey remove

Remove API key


**Description**


Remove the API key used for accessing external services.




**Command-line synopsis**

```
$ dqo [dqo options...] settings apikey remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings apikey remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings apikey remove-fw" class="no-wrap-code">`-fw`</div><div id="settings apikey remove--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings apikey remove--headless" class="no-wrap-code">`--headless`</div><div id="settings apikey remove-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings apikey remove-h" class="no-wrap-code">`-h`</div><div id="settings apikey remove--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings apikey remove-of" class="no-wrap-code">`-of`</div><div id="settings apikey remove--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings apikey show

Show API key


**Description**


Display the current API key used for accessing external services.




**Command-line synopsis**

```
$ dqo [dqo options...] settings apikey show [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings apikey show [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings apikey show-fw" class="no-wrap-code">`-fw`</div><div id="settings apikey show--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings apikey show--headless" class="no-wrap-code">`--headless`</div><div id="settings apikey show-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings apikey show-h" class="no-wrap-code">`-h`</div><div id="settings apikey show--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings apikey show-of" class="no-wrap-code">`-of`</div><div id="settings apikey show--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings timezone set

Set the default time zone


**Description**


Set the default time zone used by the DQOps.




**Command-line synopsis**

```
$ dqo [dqo options...] settings timezone set [-h] [-fw] [-hl] [-of=<outputFormat>] <timeZone>

```

**DQOps shell synopsis**

```
dqo> settings timezone set [-h] [-fw] [-hl] [-of=<outputFormat>] <timeZone>

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings timezone set-fw" class="no-wrap-code">`-fw`</div><div id="settings timezone set--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings timezone set--headless" class="no-wrap-code">`--headless`</div><div id="settings timezone set-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings timezone set-h" class="no-wrap-code">`-h`</div><div id="settings timezone set--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings timezone set-of" class="no-wrap-code">`-of`</div><div id="settings timezone set--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings timezone remove

Remove time zone


**Description**


Remove the custom time zone from your settings. Once removed, the time zone for your account will be set to the system default.




**Command-line synopsis**

```
$ dqo [dqo options...] settings timezone remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings timezone remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings timezone remove-fw" class="no-wrap-code">`-fw`</div><div id="settings timezone remove--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings timezone remove--headless" class="no-wrap-code">`--headless`</div><div id="settings timezone remove-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings timezone remove-h" class="no-wrap-code">`-h`</div><div id="settings timezone remove--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings timezone remove-of" class="no-wrap-code">`-of`</div><div id="settings timezone remove--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings timezone show

Show the default time zone


**Description**


Displays the default time zone that is currently set in your settings. This time zone will be used to display all date and time values in the application.




**Command-line synopsis**

```
$ dqo [dqo options...] settings timezone show [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings timezone show [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings timezone show-fw" class="no-wrap-code">`-fw`</div><div id="settings timezone show--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings timezone show--headless" class="no-wrap-code">`--headless`</div><div id="settings timezone show-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings timezone show-h" class="no-wrap-code">`-h`</div><div id="settings timezone show--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings timezone show-of" class="no-wrap-code">`-of`</div><div id="settings timezone show--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings smtp set

Set SMTP config


**Description**


Set SMTP server configuration for incident notifications.




**Command-line synopsis**

```
$ dqo [dqo options...] settings smtp set -s [-h] [-fw] [-hl] -ho=<host> [-of=<outputFormat>]
                   -p=<port> -ps=<password> -u=<username>

```

**DQOps shell synopsis**

```
dqo> settings smtp set -s [-h] [-fw] [-hl] -ho=<host> [-of=<outputFormat>]
                   -p=<port> -ps=<password> -u=<username>

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings smtp set-fw" class="no-wrap-code">`-fw`</div><div id="settings smtp set--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings smtp set--headless" class="no-wrap-code">`--headless`</div><div id="settings smtp set-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings smtp set-h" class="no-wrap-code">`-h`</div><div id="settings smtp set--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings smtp set-ho" class="no-wrap-code">`-ho`</div><div id="settings smtp set--host" class="no-wrap-code">`--host`</div>|SMTP server host|:material-check-bold:||
|<div id="settings smtp set-of" class="no-wrap-code">`-of`</div><div id="settings smtp set--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="settings smtp set-ps" class="no-wrap-code">`-ps`</div><div id="settings smtp set--password" class="no-wrap-code">`--password`</div>|SMTP server password|:material-check-bold:||
|<div id="settings smtp set-p" class="no-wrap-code">`-p`</div><div id="settings smtp set--port" class="no-wrap-code">`--port`</div>|SMTP server port|:material-check-bold:||
|<div id="settings smtp set-s" class="no-wrap-code">`-s`</div><div id="settings smtp set--use-ssl" class="no-wrap-code">`--use-ssl`</div>|SMTP server use SSL|:material-check-bold:||
|<div id="settings smtp set-u" class="no-wrap-code">`-u`</div><div id="settings smtp set--username" class="no-wrap-code">`--username`</div>|SMTP server user name|:material-check-bold:||






___

## dqo settings smtp remove

Remove SMTP server config


**Description**


Remove the API key used for accessing external services.




**Command-line synopsis**

```
$ dqo [dqo options...] settings smtp remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings smtp remove [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings smtp remove-fw" class="no-wrap-code">`-fw`</div><div id="settings smtp remove--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings smtp remove--headless" class="no-wrap-code">`--headless`</div><div id="settings smtp remove-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings smtp remove-h" class="no-wrap-code">`-h`</div><div id="settings smtp remove--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings smtp remove-of" class="no-wrap-code">`-of`</div><div id="settings smtp remove--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo settings smtp show

Show SMTP server configuration


**Description**


Display the current SMTP server configuration for incident notifications.




**Command-line synopsis**

```
$ dqo [dqo options...] settings smtp show [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> settings smtp show [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="settings smtp show-fw" class="no-wrap-code">`-fw`</div><div id="settings smtp show--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="settings smtp show--headless" class="no-wrap-code">`--headless`</div><div id="settings smtp show-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="settings smtp show-h" class="no-wrap-code">`-h`</div><div id="settings smtp show--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="settings smtp show-of" class="no-wrap-code">`-of`</div><div id="settings smtp show--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|





