# rule

___
### **dqo rule edit**

Edit rule that matches a given condition

**Description**

This command can be used to update the rule. It is important to use caution when using this command, as it can impact the execution of data quality checks.


**Command-line synopsis**
```
$ dqo [dqo options...] rule edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-r=<name>]

```
**DQO Shell synopsis**
```
dqo.ai> rule edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-r=<name>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-f`<br/>`--ext`<br/>|File type| |PYTHON<br/>YAML<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-r`<br/>`--rule`<br/>|Rule name| ||



