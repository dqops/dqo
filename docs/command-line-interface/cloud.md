# cloud

___
### **dqo cloud login**

**Description**

Logs in or registers an account at DQO Cloud


**Command-line synopsis**
```
$ dqo [dqo options...] cloud login [-h] [-fw] [-hl] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> cloud login [-h] [-fw] [-hl] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync data**

**Description**

Synchronize local &quot;data&quot; folder with sensor readouts and rule results with DQO Cloud


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync data [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                 [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> cloud sync data [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                 [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync sources**

**Description**

Synchronize local &quot;sources&quot; connection and table level quality definitions with DQO Cloud


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync sources [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> cloud sync sources [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync sensors**

**Description**

Synchronize local &quot;sensors&quot; folder with custom sensor definitions with DQO Cloud


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync sensors [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> cloud sync sensors [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                    [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync rules**

**Description**

Synchronize local &quot;rules&quot; folder with custom rule definitions with DQO Cloud


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync rules [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                  [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> cloud sync rules [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                  [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo cloud sync all**

**Description**

Synchronize local files with DQO Cloud (sources, table rules, custom rules, custom sensors and data - sensor readouts and rule results)


**Command-line synopsis**
```
$ dqo [dqo options...] cloud sync all [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> cloud sync all [-h] [-fw] [-hl] [-d=<direction>] [-m=<mode>]
                [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-d`<br/>`--direction`<br/>|File synchronization direction| |full<br/>download<br/>upload<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, debug)| |silent<br/>summary<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|



