# dqo cloud command
The reference of the **cloud** command in DQOps. Manage the DQOps Cloud account. This group of commands supports connecting this DQOps instance to a DQOps Cloud account, changing the password and synchronizing local data with the Data Quality Data Warehouse hosted by DQOps Cloud



___

## dqo cloud login

Log in or register an account at the DQOps Cloud


**Description**


Allow user to provide login credentials if the user already has an account.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud login [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud login [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud login-fw" class="no-wrap-code">`-fw`</div><div id="cloud login--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud login--headless" class="no-wrap-code">`--headless`</div><div id="cloud login-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud login-h" class="no-wrap-code">`-h`</div><div id="cloud login--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud login-of" class="no-wrap-code">`-of`</div><div id="cloud login--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud password

Changes the user&#x27;s password in DQOps Cloud


**Description**


Allows the user to change the password that is used to log in to DQOps Cloud account using the email and password.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud password [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud password [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud password-fw" class="no-wrap-code">`-fw`</div><div id="cloud password--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud password--headless" class="no-wrap-code">`--headless`</div><div id="cloud password-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud password-h" class="no-wrap-code">`-h`</div><div id="cloud password--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud password-of" class="no-wrap-code">`-of`</div><div id="cloud password--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync enable

Enables synchronization with DQOps Cloud if it was disabled before


**Description**


For this command description is not provided yet.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync enable [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync enable [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync enable-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync enable--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync enable--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync enable-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync enable-h" class="no-wrap-code">`-h`</div><div id="cloud sync enable--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync enable-of" class="no-wrap-code">`-of`</div><div id="cloud sync enable--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync disable

Disable synchronization with DQOps Cloud, allowing to work offline, but without access to the data quality dashboards


**Description**


For this command description is not provided yet.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync disable [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync disable [-h] [-fw] [-hl] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync disable-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync disable--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync disable--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync disable-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync disable-h" class="no-wrap-code">`-h`</div><div id="cloud sync disable--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync disable-of" class="no-wrap-code">`-of`</div><div id="cloud sync disable--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync data

Synchronize local &quot;data&quot; folder with sensor readouts and rule results with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;data&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync data [-hr] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                 [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync data [-hr] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                 [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync data-d" class="no-wrap-code">`-d`</div><div id="cloud sync data--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync data-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync data--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync data--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync data-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync data-h" class="no-wrap-code">`-h`</div><div id="cloud sync data--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync data-m" class="no-wrap-code">`-m`</div><div id="cloud sync data--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync data-of" class="no-wrap-code">`-of`</div><div id="cloud sync data--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="cloud sync data-r" class="no-wrap-code">`-r`</div><div id="cloud sync data--refresh-data-warehouse" class="no-wrap-code">`--refresh-data-warehouse`</div>|Force refresh a whole table in the data quality data warehouse| ||






___

## dqo cloud sync sources

Synchronize local &quot;sources&quot; connection and table level quality definitions with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;sources&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync sources [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync sources [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync sources-d" class="no-wrap-code">`-d`</div><div id="cloud sync sources--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync sources-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync sources--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync sources--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync sources-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync sources-h" class="no-wrap-code">`-h`</div><div id="cloud sync sources--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync sources-m" class="no-wrap-code">`-m`</div><div id="cloud sync sources--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync sources-of" class="no-wrap-code">`-of`</div><div id="cloud sync sources--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync sensors

Synchronize local &quot;sensors&quot; folder with custom sensor definitions with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;sensors&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync sensors [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync sensors [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync sensors-d" class="no-wrap-code">`-d`</div><div id="cloud sync sensors--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync sensors-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync sensors--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync sensors--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync sensors-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync sensors-h" class="no-wrap-code">`-h`</div><div id="cloud sync sensors--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync sensors-m" class="no-wrap-code">`-m`</div><div id="cloud sync sensors--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync sensors-of" class="no-wrap-code">`-of`</div><div id="cloud sync sensors--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync rules

Synchronize local &quot;rules&quot; folder with custom rule definitions with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;rules&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync rules [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                  [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync rules [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                  [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync rules-d" class="no-wrap-code">`-d`</div><div id="cloud sync rules--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync rules-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync rules--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync rules--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync rules-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync rules-h" class="no-wrap-code">`-h`</div><div id="cloud sync rules--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync rules-m" class="no-wrap-code">`-m`</div><div id="cloud sync rules--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync rules-of" class="no-wrap-code">`-of`</div><div id="cloud sync rules--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync checks

Synchronize local &quot;checks&quot; folder with custom check definitions with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;checks&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync checks [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                   [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync checks [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                   [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync checks-d" class="no-wrap-code">`-d`</div><div id="cloud sync checks--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync checks-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync checks--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync checks--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync checks-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync checks-h" class="no-wrap-code">`-h`</div><div id="cloud sync checks--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync checks-m" class="no-wrap-code">`-m`</div><div id="cloud sync checks--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync checks-of" class="no-wrap-code">`-of`</div><div id="cloud sync checks--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync settings

Synchronize local &quot;settings&quot; folder with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;settings&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync settings [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                     [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync settings [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                     [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync settings-d" class="no-wrap-code">`-d`</div><div id="cloud sync settings--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync settings-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync settings--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync settings--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync settings-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync settings-h" class="no-wrap-code">`-h`</div><div id="cloud sync settings--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync settings-m" class="no-wrap-code">`-m`</div><div id="cloud sync settings--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync settings-of" class="no-wrap-code">`-of`</div><div id="cloud sync settings--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync credentials

Synchronize local &quot;.credentials&quot; folder that stores shared credentials with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;.credentials&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync credentials [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                        [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync credentials [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                        [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync credentials-d" class="no-wrap-code">`-d`</div><div id="cloud sync credentials--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync credentials-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync credentials--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync credentials--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync credentials-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync credentials-h" class="no-wrap-code">`-h`</div><div id="cloud sync credentials--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync credentials-m" class="no-wrap-code">`-m`</div><div id="cloud sync credentials--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync credentials-of" class="no-wrap-code">`-of`</div><div id="cloud sync credentials--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync dictionaries

Synchronize local &quot;dictionaries&quot; folder that stores data dictionary CSV files with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;dictionaries&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync dictionaries [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                         [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync dictionaries [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                         [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync dictionaries-d" class="no-wrap-code">`-d`</div><div id="cloud sync dictionaries--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync dictionaries-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync dictionaries--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync dictionaries--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync dictionaries-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync dictionaries-h" class="no-wrap-code">`-h`</div><div id="cloud sync dictionaries--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync dictionaries-m" class="no-wrap-code">`-m`</div><div id="cloud sync dictionaries--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync dictionaries-of" class="no-wrap-code">`-of`</div><div id="cloud sync dictionaries--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync patterns

Synchronize local &quot;patterns&quot; folder that stores default check patters files with DQOps Cloud


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;patterns&quot; folder.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync patterns [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                     [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync patterns [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                     [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync patterns-d" class="no-wrap-code">`-d`</div><div id="cloud sync patterns--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync patterns-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync patterns--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync patterns--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync patterns-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync patterns-h" class="no-wrap-code">`-h`</div><div id="cloud sync patterns--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync patterns-m" class="no-wrap-code">`-m`</div><div id="cloud sync patterns--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync patterns-of" class="no-wrap-code">`-of`</div><div id="cloud sync patterns--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo cloud sync all

Synchronize local files with DQOps Cloud (sources, table rules, custom rules, custom sensors and data - sensor readouts and rule results)


**Description**


Uploads any local changes to the cloud and downloads any changes made to the cloud versions of the folders.




**Command-line synopsis**

```
$ dqo [dqo options...] cloud sync all [-hr] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> cloud sync all [-hr] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="cloud sync all-d" class="no-wrap-code">`-d`</div><div id="cloud sync all--direction" class="no-wrap-code">`--direction`</div>|File synchronization direction| |*full*<br/>*download*<br/>*upload*<br/>|
|<div id="cloud sync all-fw" class="no-wrap-code">`-fw`</div><div id="cloud sync all--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="cloud sync all--headless" class="no-wrap-code">`--headless`</div><div id="cloud sync all-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="cloud sync all-h" class="no-wrap-code">`-h`</div><div id="cloud sync all--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="cloud sync all-m" class="no-wrap-code">`-m`</div><div id="cloud sync all--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, debug)| |*silent*<br/>*summary*<br/>*debug*<br/>|
|<div id="cloud sync all-of" class="no-wrap-code">`-of`</div><div id="cloud sync all--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="cloud sync all-r" class="no-wrap-code">`-r`</div><div id="cloud sync all--refresh-data-warehouse" class="no-wrap-code">`--refresh-data-warehouse`</div>|Force refresh a whole table in the data quality data warehouse| ||





