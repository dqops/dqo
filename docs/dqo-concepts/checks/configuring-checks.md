# Configuring data quality checks
Data quality checks are configured on monitored tables and columns in
[&lt;schema_name&gt;.&lt;table_name&gt;.dqotable.yaml](../../reference/yaml/TableYaml.md) YAML files.
These files are placed in the *[$DQO_USER_HOME/sources/&lt;connection_name&gt;/](../home-folders/dqops-user-home.md#monitored-tables)* folders
in the `DQOps user home`.
The role and layout of the `DQOps user home` folder is described on the [DQOps user home](../home-folders/dqops-user-home.md) page.


## DQOps table YAML file
The [\*.dqotable.yaml](../../reference/yaml/TableYaml.md) files are similar to Kubernetes specification files.
Additionally, the first line references a YAML schema file that is used by Visual Studio Code for code completion,
validation, and showing the documentation of checks. The concept of working with [YAML files](../yaml-files/yaml-files.md)
shows the editing experience in Visual Studio Code.

We are showing a [\*.dqotable.yaml](../../reference/yaml/TableYaml.md) file below, describing all important elements.



