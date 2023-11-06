# DQOps integration
DQOps integrates with multiple tools, using both the REST API interface and by using file formats based on open standards.

- **[Airflow](./airflow/index.md)**

    DQOps provides operators for Apache Airflow for running data quality checks and the detecting
    the data quality status of a any table, before the table is used as a data source.

    The DQOps Airflow operators can be used in a DAG before or after a data loading job. The DQOps operator
    can perform a circuit breaking to stop the pipeline, and prevent loading invalid data
    downstream when fatal severity issues are detected. 

    The [DQOps Python package](https://pypi.org/project/dqops/) is available on PyPI.


- **[Slack](./slack/configuring-slack-notifications.md)**

    Notifications of new or updated [data quality incidents](../working-with-dqo/incidents-and-notifications/incidents.md)
    can be published to a Slack channel.
    DQOps also supports incident workflows, sending different messages to different channels.
    The notifications of new incidents can be sent to a data quality team, the data quality team evaluates the incidents
    and assigns the incident for resolution. The data engineering team receives a notification only about a verified
    data incident that needs resolving.


- **[Visual Studio Code](./visual-studio-code/index.md)**
  
    [YAML files](../dqo-concepts/checks/configuring-checks.md) used by DQOps to store the configuration of
    data sources and data quality checks are fully documented using a published YAML/JSON schema.

    By installing a Visual Studio Code extension for editing YAML files, code completion, inline help about
    data quality checks and syntax highlighting is enabled.

  
- **[Webhooks](./webhooks/index.md)**

    Any changes to the [data quality incidents](../working-with-dqo/incidents-and-notifications/incidents.md)
    can be also published by posting the [IncidentNotificationMessage](../reference/yaml/IncidentNotificationMessage.md)
    message to a third-party listener.

    The webhooks can be used to create and change the status of incidents created in issue management platforms,
    such as Jira, Azure DevOps, ServiceNow and others.

