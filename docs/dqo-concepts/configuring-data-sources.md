# Configuring data sources
Read this guide to understand how DQOps stores the connection parameters to monitored data sources, and how to manage credentials.

## Overview
DQOps stores the configuration of data sources in YAML files. The files support auto-completion in Visual Studio Code and can be versioned in Git.    

!!! note "[`DQOps user home` folder](dqops-user-home-folder.md)"

    For the purpose of this guide, we will assume that DQOps was started in the current folder using the `python -m dqops` command.
    All files mentioned in this guide will be relative to the current folder, referred to as a [`$DQO_USER_HOME`](dqops-user-home-folder.md) in the examples below.


## DQOps YAML files structure
The structure of DQOps YAML configuration files is similar to the structure of Kubernetes specification files.
Additionally, the first line references a YAML schema file that is used by Visual Studio Code for code completion,
validation, and showing the documentation of checks. The concept of working with [YAML files](../integrations/visual-studio-code/index.md)
shows the editing experience in Visual Studio Code.

### **DQOps YAML file example**
The following code example is a fragment of a [DQOps data source configuration file](../reference/yaml/ConnectionYaml.md#connectionyaml),
showing all regular elements of all DQOps YAML files.

``` { .yaml .annotate linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json (1)
apiVersion: dqo/v1
kind: source # (2)!
spec: # (3)!
  provider_type: postgresql
  postgresql:
    host: localhost
    ...
```

1.  The YAML file schema identifier. Each type of DQOps configuration file points to its own YAML file schema. The YAML schema is used
    by text editors such as Visual Studio Code for the code completion and schema validation.
2.  The type of the file is identified in the `kind` element.
3.  The `spec` [connection specification](../reference/yaml/ConnectionYaml.md#connectionspec) object
    that describes the data source, and its connection parameters.

The first line of DQOps configuration files has an optional link to a YAML schema file. The YAML schema files
are used by text editors such as [Visual Studio Code for code completion and schema validation](../integrations/visual-studio-code/index.md).

The `kind` node identifies the type of DQOps file, and the `spec` node contains the specification, which is the real configuration.
For detailed file reference, consult the reference of the *DQOps YAML files schema* in the [DQOps reference](../reference/index.md) section.

### **YAML file extensions**
DQOps identifies the type of its YAML files by the file extension. The file extensions of files storing the metadata of data sources
are listed below.

| File name pattern                                                                     | File purpose                                                                              |
|---------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| *[connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md#connectionyaml)* | Data source connection configuration file with the connection details and credentials.    |
| *[\*.dqotable.yaml](../reference/yaml/TableYaml.md#tableyaml)*                        | Monitored table configuration file with the configuration of enabled data quality checks. |


## Data sources
The data sources can be registered in DQOps using the user interface
or creating DQOps YAML *[.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md#connectionyaml)* files directly in the data source folder.

### **Data sources folder**
The configuration of data sources and the metadata of all tables are defined in the **sources** folder,
inside the [`DQOps user home`](dqops-user-home-folder.md) folder, referred as `$DQO_USER_HOME`. 
The example below shows two data sources named *prod-landing-zone* and *prod-data-lake*.

``` { .asc .annotate hl_lines="4 7" }
$DQO_USER_HOME
├───...
└───sources(1)                                                                
    ├───prod-landing-zone(2)
    │   ├───connection.dqoconnection.yaml(3)
    │   └───...
    ├───prod-data-lake
    │   ├───connection.dqoconnection.yaml
    │   └───...
    └─...   
```

1.  The **sources** folder stores data sources as nested folders.
2.  Each folder inside the **sources** folder is a connection name to a data source.
3.  Each data source's folder contains a file [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md)
    which specifies the connection parameters to the data source.

The name of each child folder inside the **sources** folder is a connection name to a data source.
Each data source's folder contains one file named [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md),
that specifies the connection parameters to that data source.

### **Configuring data sources in YAML files**
The data source folder should contain exactly one file, that must be named [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md).
An example data source connection file for [PostgreSQL](../data-sources/postgresql.md) is shown below.

``` { .yaml .annotate linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source # (1)!
spec:
  provider_type: postgresql # (2)!
  postgresql: # (3)!
    host: localhost
    port: "52289"
    user: test
    password: test
    database: test
```

1.  The type of the file is identified in the `kind` element. Data sources use a `source` kind.
2.  The type of data sources. Use the documentation of the [ConnectionSpec](../reference/yaml/ConnectionYaml.md#connectionspec) object
    to find the names of other supported data sources, beyond the `postgresql` connection type shown in this example.
3.  The configuration node for [PostgreSQL](../data-sources/postgresql.md). Other supported data sources are configured in similar elements,
    named as the type of data source.

Each [connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md) must have two nodes filled:

- `provider_type` enumeration stores the type of the data source.
- *data_source_type* node (`postgresql` in this example) stores the type-safe configuration for that data source, supporting
  code completion in [Visual Studio Code](../integrations/visual-studio-code/index.md).

The full documentation of the `spec` element is provided in the
[ConnectionSpec](../reference/yaml/ConnectionYaml.md#connectionspec) object reference.
Examples of data source specific configurations are located in the [data sources](../data-sources/index.md) section of the documentation.

### **Configuring data sources from the user interface**
Each type of data source has its own connection configuration screen in the DQOps user interface.
Check out the [data sources](../data-sources/index.md) section to find the data source of interest,
and learn the details of configuring your connections from the DQOps UI.


## Using credentials and secrets
DQOps supports providing credentials from a separate location to avoid storing the *connection.dqoconnection.yaml* files
in the source repository.

### **Referencing environment variables**
Credentials are provided to a DQOps instance using environment variables.
Environment variables are referenced using as `${ENVIRONMENT_VARIABLE_NAME}` values. 

``` { .yaml .annotate linenums="1" hl_lines="9-10" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: postgresql
  postgresql:
    host: localhost
    port: "52289"
    user: ${MY_POSTGRESS_USER}
    password: ${MY_POSTGRESS_DATABASE}
    database: test
```

If you are [running DQOps as a docker container](../dqops-installation/run-dqops-as-docker-container.md), use the `-e` docker
parameter to pass additional environment variables.

### **Using shared credentials**
Credentials and secrets that are shared with the DQOps Cloud (and DQOps SaaS instances) are stored in the *.credentials* folder.
The name of the *secret* is the file name inside the *.credentials* folder. The following example shows two such secrets
named *my_postgress_user.txt* and *my_postgress_pass*.

``` { .asc .annotate hl_lines="4-5" }
$DQO_USER_HOME
├───...
├───.gitignore(1)
└───.credentials(2)                                                               
    ├───my_postgress_user.txt(3)
    ├───my_postgress_pass
    └─...   
```

1.  The default *.gitignore* file has a rule to ignore the *.credentials* folder and all files inside that folder.
2.  The hidden folder for shared credentials. This folder is added to the *.gitignore*, but is synchronized with DQOps Cloud
    if you want to use DQOps Cloud and synchronize the metadata between the SaaS environment and your local environment.
3.  The shared credentials are defined as files stored directly in the *.credentials* folder. The file name extension
    for credentials does not matter. This example uses a *.txt* file extension only for clarity.

The whole *.credentials* folder is added to the *.gitignore*, ensuring that the credentials are not pushed to the Git source code repository
by mistake.

The following example shows how to reference the shared credentials using a `${credential://shared_secret_name}` value.

``` { .yaml .annotate linenums="1" hl_lines="9-10" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: postgresql
  postgresql:
    host: localhost
    port: "52289"
    user: ${credential://my_postgress_user.txt}
    password: ${credential://my_postgress_pass}
    database: test
```

The shared credentials can be also edited in the **Configuration** section of the DQOps user interface
by users holding an [Admin](../working-with-dqo/access-management.md#roles) or
[Editor](../working-with-dqo/access-management.md#roles) role. Access management is enabled only in the
[*TEAM* and *ENTERPRISE* editions of DQOps](https://dqops.com/pricing/).

![Shared credentials configuration](https://dqops.com/docs/images/concepts/configuring-data-sources/shared-credentials-configuration.png)


## Additional connection configuration
This section describes the remaining configuration parameters defined
in the *[connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md)* file.

### **Job parallelism level**
DQOps runs data quality checks in parallel, running checks for each table in a separate thread.
The limit of parallel jobs per data source is configured in the `parallel_jobs_limit` parameter.

``` { .yaml .annotate linenums="1" hl_lines="6" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  provider_type: postgresql
  parallel_jobs_limit: 10  # (1)!
  postgresql:
    host: localhost
    port: "52289"
  ...
```

1.  The configuration parameter for configuring the maximum number of tables that are analyzed in parallel.

!!! note "DQOps license limits"

    The limit of parallel jobs supported by DQOps depends on the license level. Consult the [DQOps pricing](https://dqops.com/pricing/)
    for details.


### **Data quality check scheduling**
The DQOps CRON schedules for running each [type of data quality check](definition-of-data-quality-checks/index.md#types-of-checks)
is configured in the `schedules` section.

``` { .yaml .annotate linenums="1" hl_lines="8 10 12 14 16" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  ...
  schedules:  # (1)!
    profiling:
      cron_expression: 0 12 1 * *  # (2)!
    monitoring_daily:
      cron_expression: 0 12 * * *  # (3)!
    monitoring_monthly:
      cron_expression: 0 12 * * *  # (4)!
    partitioned_daily:
      cron_expression: 0 12 * * *  # (5)!
    partitioned_monthly:
      cron_expression: 0 12 * * *  # (6)!
```

1.  The data source scheduling configuration of running data quality checks.
2.  The CRON schedule for running [*profiling checks*](definition-of-data-quality-checks/data-profiling-checks.md).
3.  The CRON schedule for running [*daily monitoring checks*](definition-of-data-quality-checks/data-observability-monitoring-checks.md).
4.  The CRON schedule for running [*monthly monitoring checks*](definition-of-data-quality-checks/data-observability-monitoring-checks.md).
5.  The CRON schedule for running [*daily partition checks*](definition-of-data-quality-checks/partition-checks.md).
6.  The CRON schedule for running [*monthly partition checks*](definition-of-data-quality-checks/partition-checks.md).


When a new data source is added to DQOps, the configuration of CRON schedules is copied from the default schedules
that are stored in the [*settings/defaultschedules.dqoschedules.yaml*](../reference/yaml/DefaultSchedulesYaml.md) file.
The schedules shown in the example above are used as the initial configuration.

- [*profiling checks*](definition-of-data-quality-checks/data-profiling-checks.md) are run once a month, on the 1st day of the month
  at 12:00 PM.

- all other types of data quality checks ([*monitoring checks*](definition-of-data-quality-checks/data-observability-monitoring-checks.md),
  [*partition checks*](definition-of-data-quality-checks/partition-checks.md)) are run every day at 12:00 PM.

Consult the [data quality check scheduling manual](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md)
to see how to configure data quality scheduling from the [DQOps user interface](dqops-user-interface-overview.md).

The full documentation of the `schedules` element is provided in the
[DefaultSchedulesSpec](../reference/yaml/ConnectionYaml.md#defaultschedulesspec) object reference. 


### **Data quality issue to incident grouping**
DQOps [groups similar data quality issues into data quality incidents](grouping-data-quality-issues-to-incidents.md), the matching
method is described in the [data quality incidents](grouping-data-quality-issues-to-incidents.md) article.

The following example shows where the configuration is stored in the
*[connection.dqoconnection.yaml](../reference/yaml/ConnectionYaml.md)* file.

``` { .yaml .annotate linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  ...
  incident_grouping: # (1)!
    grouping_level: table_dimension_category # (2)!
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60 
```

1.  The `incident_grouping` node with the configuration of grouping data quality issues to incidents.
2.  The data quality issue mapping method. 

The full documentation of the `incident_grouping` element is provided in the
[ConnectionIncidentGroupingSpec](../reference/yaml/ConnectionYaml.md#connectionincidentgroupingspec) object reference.


### **Incident notification webhooks**
The urls of [webhooks](../integrations/webhooks/index.md) that DQOps should call
when new [data quality incidents](grouping-data-quality-issues-to-incidents.md) are identified, or the status
of an incident was changed.

This configuration overrides the default settings stored in the [*settings/defaultnotifications.dqonotifications.yaml*](../reference/yaml/DefaultNotificationsYaml.md) file
with data source specific configuration, allowing to send the notification to the data source owner or the right data engineering team.

``` { .yaml .annotate linenums="1" hl_lines="6 11-15"  }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ConnectionYaml-schema.json
apiVersion: dqo/v1
kind: source
spec:
  ...
  incident_grouping:
    grouping_level: table_dimension_category
    minimum_severity: warning
    max_incident_length_days: 60
    mute_for_days: 60 
    webhooks: # (1)!
      incident_opened_webhook_url: https://my.ticketingsystem.com/on_new_incident_detected # (2)!
      incident_acknowledged_webhook_url: https://my.ticketingsystem.com/on_incident_confirmed_by_ops_team # (3)!
      incident_resolved_webhook_url: https://my.ticketingsystem.com/on_incident_resolved # (4)!
      incident_muted_webhook_url: https://my.ticketingsystem.com/on_incident_muted # (5)!
```

1.  The webhook configuration node.
2.  The webhook url where DQOps POSTs [notifications](../reference/yaml/IncidentNotificationMessage.md) of new data quality incidents that were just detected.
3.  The webhook url where DQOps POSTs [notifications](../reference/yaml/IncidentNotificationMessage.md) of data quality incidents that were reviewed and assigned for resolution.
4.  The webhook url where DQOps POSTs [notifications](../reference/yaml/IncidentNotificationMessage.md) of data quality incidents that were resolved and the data quality checks can be run again to validate the fix.
5.  The webhook url where DQOps POSTs [notifications](../reference/yaml/IncidentNotificationMessage.md) of data quality incidents that were muted because the incident was identified as a low priority or out-of-scope.


The full documentation of the `incident_grouping.webhooks` element is provided in the
[IncidentWebhookNotificationsSpec](../reference/yaml/ConnectionYaml.md#incidentwebhooknotificationsspec) object reference.


## What's next

- Learn how the [table metadata](configuring-table-metadata.md) is stored in DQOps.
- If you want to see how to import the metadata of data sources using DQOps user interface, 
  go back to the *getting started* section, and read the [adding data source connection](../getting-started/add-data-source-connection.md) again.
- Learn how to [configure data quality checks and rules](configuring-data-quality-checks-and-rules.md) in the *.dqotable.yaml* files.
- Learn more about managing configuration in the [`DQOps user home` folder](dqops-user-home-folder.md).
- Review the list of [data sources supported by DQOps](../data-sources/index.md) to find a step-by-step configuration manual for each data source.
- Learn what extensions are needed to activate editing DQOps configuration files in
  [Visual Studio Code with code completion and validation](../integrations/visual-studio-code/index.md).
