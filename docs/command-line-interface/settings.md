# dqo settings command


Show or set settings



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
|<p id="settings editor set-fw">`-fw`</p><br/><p id="settings editor set--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings editor set--headless">`--headless`</p><br/><p id="settings editor set-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings editor set-h">`-h`</p><br/><p id="settings editor set--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings editor set-n">`-n`</p><br/><p id="settings editor set--name">`--name`</p><br/>|Editor name| |VSC<br/>IntelliJ<br/>Eclipse<br/>PyCharm<br/>|
|<p id="settings editor set-of">`-of`</p><br/><p id="settings editor set--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="settings editor set-p">`-p`</p><br/><p id="settings editor set--path">`--path`</p><br/>|Editor path| ||






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
|<p id="settings editor remove-fw">`-fw`</p><br/><p id="settings editor remove--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings editor remove--headless">`--headless`</p><br/><p id="settings editor remove-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings editor remove-h">`-h`</p><br/><p id="settings editor remove--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings editor remove-of">`-of`</p><br/><p id="settings editor remove--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings editor show-fw">`-fw`</p><br/><p id="settings editor show--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings editor show--headless">`--headless`</p><br/><p id="settings editor show-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings editor show-h">`-h`</p><br/><p id="settings editor show--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings editor show-of">`-of`</p><br/><p id="settings editor show--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings init-fw">`-fw`</p><br/><p id="settings init--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings init--headless">`--headless`</p><br/><p id="settings init-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings init-h">`-h`</p><br/><p id="settings init--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings init-of">`-of`</p><br/><p id="settings init--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings remove-fw">`-fw`</p><br/><p id="settings remove--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings remove--headless">`--headless`</p><br/><p id="settings remove-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings remove-h">`-h`</p><br/><p id="settings remove--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings remove-of">`-of`</p><br/><p id="settings remove--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings apikey set-fw">`-fw`</p><br/><p id="settings apikey set--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings apikey set--headless">`--headless`</p><br/><p id="settings apikey set-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings apikey set-h">`-h`</p><br/><p id="settings apikey set--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings apikey set-of">`-of`</p><br/><p id="settings apikey set--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings apikey remove-fw">`-fw`</p><br/><p id="settings apikey remove--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings apikey remove--headless">`--headless`</p><br/><p id="settings apikey remove-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings apikey remove-h">`-h`</p><br/><p id="settings apikey remove--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings apikey remove-of">`-of`</p><br/><p id="settings apikey remove--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings apikey show-fw">`-fw`</p><br/><p id="settings apikey show--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings apikey show--headless">`--headless`</p><br/><p id="settings apikey show-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings apikey show-h">`-h`</p><br/><p id="settings apikey show--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings apikey show-of">`-of`</p><br/><p id="settings apikey show--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings timezone set-fw">`-fw`</p><br/><p id="settings timezone set--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings timezone set--headless">`--headless`</p><br/><p id="settings timezone set-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings timezone set-h">`-h`</p><br/><p id="settings timezone set--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings timezone set-of">`-of`</p><br/><p id="settings timezone set--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings timezone remove-fw">`-fw`</p><br/><p id="settings timezone remove--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings timezone remove--headless">`--headless`</p><br/><p id="settings timezone remove-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings timezone remove-h">`-h`</p><br/><p id="settings timezone remove--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings timezone remove-of">`-of`</p><br/><p id="settings timezone remove--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="settings timezone show-fw">`-fw`</p><br/><p id="settings timezone show--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="settings timezone show--headless">`--headless`</p><br/><p id="settings timezone show-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="settings timezone show-h">`-h`</p><br/><p id="settings timezone show--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="settings timezone show-of">`-of`</p><br/><p id="settings timezone show--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|





