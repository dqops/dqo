# What is DQO

DQO is an open-source data observability software designed to monitor the data quality in your database, 
data warehouse, data pipeline, etc. DQO comes with predefined and customizable data quality checks grouped into 
[data quality dimensions](/dqo_concept/dimensions/). When we refer to the data quality checks we think of a pair: 
[sensor](/dqo_concept/sensors/sensors/) and
[rules](/dqo_concept/rules/rules.md)

You can choose quality checks on table and column level by selecting sensors, their parameters and rules that 
evaluate queries results,
assigning them [severity levels](/dqo_concept/rules/rules/#severity-level).
The summary of readings and alerts is available on a dashboards on [DQO cloud]().

DQO uses [connections](/commands/connection/connection/) to access tables and utilize query engine on provider's
platform. 

To access table configuration, where you can define quality checks along with checks parameters and rules, 
DQO launches your default code editor.


#### Contents
- [List of data quality checks](/check_reference/list_of_checks/list_of_checks/)
- [Managing connections](/commands/connection/connection/)
- [Editing table configuration](/commands/table/table/#edit)
- [Data quality dimensions](/dqo_concept/dimensions/)