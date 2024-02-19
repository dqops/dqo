# Editing DQOps YAML files with Visual Studio Code
Read this article to learn how to edit DQOps data quality definition files (data contracts) directly in VS Code, using code completion and validation.

## YAML schema overview
In DQOps, the configuration of [data quality checks](../../dqo-concepts/configuring-data-quality-checks-and-rules.md)
is defined in the YAML files.
YAML is a human-readable data serialization language that is often used for writing configuration files. 

Defining data quality checks in the YAML files allows to store the data quality check configuration
in a source code repository. The data quality checks can be  
versioned along with any other pipeline code or machine learning code.

When the DQOps YAML files are edited in Visual Studio Code, syntax highlighting and code completion is supported. 

In order to enable support for the DQOps file schema in Visual Studio Code, the first line of the YAML file
must have a reference to a YAML file schema that is published by DQOps, as showing in the following example.
Visual Studio Code uses the schema file for validation and syntax highlighting.

``` { .yaml .annotate hl_lines="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        nulls:
          nulls_percent:
            warning:
              max_percent: 1.0
      labels:
        - This is the column that is analyzed for data quality issues
```

Because DQOps validates the `apiVersion` and the `kind` fields, the best way to create a new DQOps compliant
YAML file is by using the [DQOps user inteface](../../dqo-concepts/dqops-user-interface-overview.md)
to create a new file, and edit it in Visual Studio Code later.


## List of schema files
The urls to all supported schema files are listed below. The file paths are relative to
the [DQOps user home](../../dqo-concepts/dqops-user-home-folder.md) folder.

| Type                                                                                        | File location                                              | YAML schema url                                                                                                                                                          |
|---------------------------------------------------------------------------------------------|------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Data source connection](../../reference/yaml/ConnectionYaml.md)                            | *sources/&lt;connection&gt;/connection.dqoconnection.yaml* | [https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json)                                 |
| [Table schema](../../reference/yaml/TableYaml.md)                                           | *sources/&lt;connection&gt;/\*.dqotable.yaml*              | [https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json)                                           |
| [Custom dashboard list](../../reference/yaml/DashboardYaml.md)                              | *settings/dashboardslist.dqodashboards.yaml*               | [https://cloud.dqops.com/dqo-yaml-schema/DashboardYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/DashboardYaml-schema.json)                                   |
| [Default notification webhooks](../../reference/yaml/DefaultNotificationsYaml.md)           | *settings/defaultnotifications.dqonotifications.yaml*      | [https://cloud.dqops.com/dqo-yaml-schema/DefaultNotificationsYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/DefaultNotificationsYaml-schema.json)             |
| [Default data observability checks](../../reference/yaml/DefaultObservabilityChecksYaml.md) | *settings/default.dqodefaultchecks.yaml*                    | [https://cloud.dqops.com/dqo-yaml-schema/DefaultObservabilityChecksYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/DefaultObservabilityChecksYaml-schema.json) |
| [Default CRON schedules](../../reference/yaml/DefaultSchedulesYaml.md)                      | *settings/defaultschedules.dqoschedules.yaml*              | [https://cloud.dqops.com/dqo-yaml-schema/DefaultSchedulesYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/DefaultSchedulesYaml-schema.json)                     |
| [Local settings](../../reference/yaml/LocalSettingsYaml.md)                                 | *.localsettings.dqosettings.yaml*                          | [https://cloud.dqops.com/dqo-yaml-schema/LocalSettingsYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/LocalSettingsYaml-schema.json)                           |
| [Sensor definition](../../reference/yaml/SensorDefinitionYaml.md)                           | *sensors/\*\*/\*.dqosensor.yaml*                           | [https://cloud.dqops.com/dqo-yaml-schema/SensorDefinitionYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/SensorDefinitionYaml-schema.json)                     |
| [Database specific sensor definition](../../reference/yaml/ProviderSensorYaml.md)           | *sensors/\*\*/\*.dqoprovidersensor.yaml*                   | [https://cloud.dqops.com/dqo-yaml-schema/ProviderSensorYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/ProviderSensorYaml-schema.json)                         |
| [Data quality rule definition](../../reference/yaml/RuleDefinitionYaml.md)                  | *rules/\*/\*.dqorule.yaml*                                 | [https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/RuleDefinitionYaml-schema.json)                         |
| [Custom check definition](../../reference/yaml/CheckDefinitionYaml.md)                      | *checks/\*\*/\*.dqocheck.yaml*                             | [https://cloud.dqops.com/dqo-yaml-schema/CheckDefinitionYaml-schema.json](https://cloud.dqops.com/dqo-yaml-schema/CheckDefinitionYaml-schema.json)                       |                       


## Preparing Visual Studio Code
Before Visual Studio Code can use the DQOps YAML schema for validation and syntax highlighting, a few
extensions must be installed.

First, please install the [YAML](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-yaml) extension
by Red Hat as shown in the following example.

![YAML extension](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/yaml-extension.png)

If you intend to create custom [data quality sensors](../../dqo-concepts/definition-of-data-quality-sensors.md),
please also install the [Better Jinja](https://marketplace.visualstudio.com/items?itemName=samuelcolvin.jinjahtml)
extension by Samuel Colvin.

DQOps sensors are defined as Jinja2 templates of SQL queries and are easier to edit with this extension.

![Better Jinja extension](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/better-jinja-extension.png)


## Code completion 
The following screenshot shows how the [.dqotable.yaml](../../reference/yaml/TableYaml.md) file is shown 
in Visual Studio Code when the YAML schema support is enabled.

![Validated DQOps YAML file in Visual Studio Code](https://dqops.com/docs/images/integrations/visual-studio-code/dqotable-yaml-viewed-in-visual-studio-code.png)


### **Table-level elements**
To add a new table-level node, make one empty line, place the cursor in the line at the column where a sibling
or a child node should be placed. 

Press `CTRL+Space` to expand the code completion dialog, showing available elements at this level.

![Edit DQOps table YAML in VS Code](https://dqops.com/docs/images/integrations/visual-studio-code/objects-at-table-level-node.png)

The above example shows how to add a `comments` section at the table level. Comments are used to track changes inside
the YAML file and are presented in the DQOps user interface.


### **Add a check category**
Data quality checks are grouped in categories as described in the [configuring checks](../../dqo-concepts/configuring-data-quality-checks-and-rules.md)
article.

The list of check type nodes where categories are listed below. Those nodes are available both at a table root level
to configure table-level checks and below a named column to configure column-level checks.

- `profiling_checks`
- `monitoring_checks.daily`
- `monitoring_checks.monthly`
- `partitioned_checks.daily`
- `partitioned_checks.monthly`

When the cursor is within one of the nodes for check types, press `CTRL+Space` to expand the list of categories
available for that check type.

![Expanding list of DQOps check categories in VS Code](https://dqops.com/docs/images/integrations/visual-studio-code/adding-profiling-accuracy-check.png)


### **Add a data quality check**
The data quality checks are defined below the category nodes.

Place the cursor below the category node, adding required indenting. Press `CTRL+Space` to see a list of checks
available within that category.

![Adding data quality checks in DQOps using VS Code](https://dqops.com/docs/images/integrations/visual-studio-code/adding-profile-table-availability-check.png)

You may notice an extra node `custom_checks` that was suggested at this level. The `custom_checks`
is a dictionary of custom checks defined by the user within that category. When configuring a custom check, 
the element that is added below the `custom_checks` is the name of the custom check. The name must match
the name of a custom check defined in the **Configuration** section in the DQOps user interface.
Code completion for custom checks is limited to the structure, the list of possible sensor's and rule's parameter
values are not validated.

### **Adding an alerting threshold**
The data quality issue alerting thresholds are defined by configuring [data quality rules](../../dqo-concepts/definition-of-data-quality-rules.md).
The data quality rules to configure are:

- `warning` configures the least severe, **warning** severity rule
- `error` configures a regular, **error** severity rule
- `fatal` configures a **fatal** severity rule used to pause a data pipeline

Place the cursor below the check node, adding required indenting. Press `CTRL+Space` to see a list of available nodes.

![Adding error severity rule in DQOps using VS Code](https://dqops.com/docs/images/integrations/visual-studio-code/adding-error-threshold.png)

Each data quality check has several parameters, allowing to customize the check further.
The following elements are supported, not including the data quality issue thresholds presented before.

- `comments` supports managing a list of comments that are usable to track changes within the file and in the DQOps UI
- `data_grouping` supports configuring a custom [data grouping](../../dqo-concepts/measuring-data-quality-with-data-grouping.md)
  for that check. Data grouping adds a **GROUP BY** clause to the SQL queries, capturing multiple check results,
  separately for each group of rows.
- `disabled` enables disabling a configured check temporarily from running, but preserving the configuration in the YAML file
- `exclude_from_kpi` boolean flag when set to *true* will set a reverse value, *false* in the *include_in_kpi* field
  stored in the [check_results](../../reference/parquetfiles/check_results.md) parquet table, not counting the result
  of this check in the [data quality KPI](../../dqo-concepts/definition-of-data-quality-kpis.md).
- `include_in_sla` boolean flag is the value stored in the *include_in_sla* field
  stored in the [check_results](../../reference/parquetfiles/check_results.md) parquet table. Data quality SLAs
  can be used to group data quality checks that must pass to meet a Data Contract.
- **`parameters`** is an important node that contains 
  the [data quality sensor](../../dqo-concepts/definition-of-data-quality-sensors.md)'s parameters. Not all sensors used by data quality
  checks have parameters and the node does not need to be configured.
- `quality_dimension` is a text field used to override the default value of the [data quality dimension](../../dqo-concepts/data-quality-dimensions.md)
  stored in the parquet tables. Changing the default data quality dimension name allows to report some issues under a different dimension.
- `schedule_override` is a configuration of the CRON schedule for a single data quality check. The check can be configured
  to run using its own schedule, more or less frequently than the default scheduling configuration at the table or connection levels. 


### **Configuring rule parameters**
Most of the data quality rules have parameters. The list of parameters is expanded within the rule's node.

After pressing `CTRL+Space` inside the `warning`, `error`, or `fatal` nodes, Visual Studio Code will show the available
parameters.

The following example shows how to configure rules for 
the [table availability](../../checks/table/availability/table-availability.md) check.

![Configuring data quality rule parameters in DQOps using VS Code](https://dqops.com/docs/images/integrations/visual-studio-code/adding-max-failures-threshold.png)

The assigned value to the rule parameter is shown below.

When setting **0** to the `max_failures` in the
[table availability](../../checks/table/availability/table-availability.md) check, DQOps will raise a data quality
issue instantly when an error is detected while trying to run a special availability testing query on the table.

![Configuring table availability check in DQOps using VS Code](https://dqops.com/docs/images/integrations/visual-studio-code/adding-max-failures-threshold-value.png)


## Getting help
All nodes defined in the DQOps YAML schema are documented, allowing to preview the definition of
the data quality checks and their parameters directly in Visual Studio Code.

The help is shown when the mouse cursor is placed over a node for a while.

![Data quality check documentation preview in VS Code](https://dqops.com/docs/images/integrations/visual-studio-code/check-tooltip.png)


## Invalid syntax highlighting
When a node added to a YAML file is invalid and not included in the DQOps YAML schema, Visual Studio Code
will underline the invalid node as shown below.

![Syntax issues when configuring data quality check with DQOps using VS Code](https://dqops.com/docs/images/integrations/visual-studio-code/not-allowed-object.png)


## What's next
- Learn how [configure data quality checks](../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps YAML files.
- Read how the YAML files are organized in the [DQOps user home](../../dqo-concepts/dqops-user-home-folder.md) folder.