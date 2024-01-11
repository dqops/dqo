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
|<p id="cloud login-fw">`-fw`</p><br/><p id="cloud login--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud login--headless">`--headless`</p><br/><p id="cloud login-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud login-h">`-h`</p><br/><p id="cloud login--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud login-of">`-of`</p><br/><p id="cloud login--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="cloud password-fw">`-fw`</p><br/><p id="cloud password--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud password--headless">`--headless`</p><br/><p id="cloud password-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud password-h">`-h`</p><br/><p id="cloud password--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud password-of">`-of`</p><br/><p id="cloud password--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="cloud sync data-d">`-d`</p><br/><p id="cloud sync data--direction">`--direction`</p><br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|<p id="cloud sync data-fw">`-fw`</p><br/><p id="cloud sync data--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud sync data--headless">`--headless`</p><br/><p id="cloud sync data-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud sync data-h">`-h`</p><br/><p id="cloud sync data--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud sync data-m">`-m`</p><br/><p id="cloud sync data--mode">`--mode`</p><br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="cloud sync data-of">`-of`</p><br/><p id="cloud sync data--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="cloud sync data-r">`-r`</p><br/><p id="cloud sync data--refresh-data-warehouse">`--refresh-data-warehouse`</p><br/>|Force refresh a whole table in the data quality data warehouse| ||






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
|<p id="cloud sync sources-d">`-d`</p><br/><p id="cloud sync sources--direction">`--direction`</p><br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|<p id="cloud sync sources-fw">`-fw`</p><br/><p id="cloud sync sources--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud sync sources--headless">`--headless`</p><br/><p id="cloud sync sources-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud sync sources-h">`-h`</p><br/><p id="cloud sync sources--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud sync sources-m">`-m`</p><br/><p id="cloud sync sources--mode">`--mode`</p><br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="cloud sync sources-of">`-of`</p><br/><p id="cloud sync sources--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="cloud sync sensors-d">`-d`</p><br/><p id="cloud sync sensors--direction">`--direction`</p><br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|<p id="cloud sync sensors-fw">`-fw`</p><br/><p id="cloud sync sensors--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud sync sensors--headless">`--headless`</p><br/><p id="cloud sync sensors-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud sync sensors-h">`-h`</p><br/><p id="cloud sync sensors--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud sync sensors-m">`-m`</p><br/><p id="cloud sync sensors--mode">`--mode`</p><br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="cloud sync sensors-of">`-of`</p><br/><p id="cloud sync sensors--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="cloud sync rules-d">`-d`</p><br/><p id="cloud sync rules--direction">`--direction`</p><br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|<p id="cloud sync rules-fw">`-fw`</p><br/><p id="cloud sync rules--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud sync rules--headless">`--headless`</p><br/><p id="cloud sync rules-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud sync rules-h">`-h`</p><br/><p id="cloud sync rules--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud sync rules-m">`-m`</p><br/><p id="cloud sync rules--mode">`--mode`</p><br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="cloud sync rules-of">`-of`</p><br/><p id="cloud sync rules--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="cloud sync checks-d">`-d`</p><br/><p id="cloud sync checks--direction">`--direction`</p><br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|<p id="cloud sync checks-fw">`-fw`</p><br/><p id="cloud sync checks--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud sync checks--headless">`--headless`</p><br/><p id="cloud sync checks-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud sync checks-h">`-h`</p><br/><p id="cloud sync checks--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud sync checks-m">`-m`</p><br/><p id="cloud sync checks--mode">`--mode`</p><br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="cloud sync checks-of">`-of`</p><br/><p id="cloud sync checks--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="cloud sync settings-d">`-d`</p><br/><p id="cloud sync settings--direction">`--direction`</p><br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|<p id="cloud sync settings-fw">`-fw`</p><br/><p id="cloud sync settings--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud sync settings--headless">`--headless`</p><br/><p id="cloud sync settings-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud sync settings-h">`-h`</p><br/><p id="cloud sync settings--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud sync settings-m">`-m`</p><br/><p id="cloud sync settings--mode">`--mode`</p><br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="cloud sync settings-of">`-of`</p><br/><p id="cloud sync settings--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="cloud sync credentials-d">`-d`</p><br/><p id="cloud sync credentials--direction">`--direction`</p><br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|<p id="cloud sync credentials-fw">`-fw`</p><br/><p id="cloud sync credentials--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud sync credentials--headless">`--headless`</p><br/><p id="cloud sync credentials-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud sync credentials-h">`-h`</p><br/><p id="cloud sync credentials--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud sync credentials-m">`-m`</p><br/><p id="cloud sync credentials--mode">`--mode`</p><br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="cloud sync credentials-of">`-of`</p><br/><p id="cloud sync credentials--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="cloud sync all-d">`-d`</p><br/><p id="cloud sync all--direction">`--direction`</p><br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|<p id="cloud sync all-fw">`-fw`</p><br/><p id="cloud sync all--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="cloud sync all--headless">`--headless`</p><br/><p id="cloud sync all-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="cloud sync all-h">`-h`</p><br/><p id="cloud sync all--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="cloud sync all-m">`-m`</p><br/><p id="cloud sync all--mode">`--mode`</p><br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|<p id="cloud sync all-of">`-of`</p><br/><p id="cloud sync all--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="cloud sync all-r">`-r`</p><br/><p id="cloud sync all--refresh-data-warehouse">`--refresh-data-warehouse`</p><br/>|Force refresh a whole table in the data quality data warehouse| ||





