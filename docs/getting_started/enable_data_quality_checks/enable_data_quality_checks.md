This section describes how to set up a basic data quality check

### Setting up a check
To set up a basic data quality check, table editing information needs to be provided. 
To do this, use the command below :
```
table edit -c=connection_1 --table=austin_311.311_service_requests
```
Following message appears:
```
dqo.ai> table edit -c=connection_1 --table=austin_311.311_service_requests
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. 

Now the YAML file can be modified to set up a data quality check.

Here is how the YAML file looks like:
```yaml linenums="1" hl_lines="16-26"
--8<-- "docs/getting_started/yamls/example.yaml"
```
Checks are added below a column and its descriptions that is chosen to be checked. 

In our case it is the column named "unique_key".
Those are the highlighted lines. They define used sensor along with [min_count](../../rule_reference/comparison/min_count.md) rule. 

Firstly write `checks:` below a chosen column, then write a [dimension](../../dqo_concept/sensors/sensors.md) name and a [sensor](../../sensor_reference/what_is_a_sensor.md) name in our case this is `uniqueness` and [distinct_count_percent](../../sensor_reference/uniqueness/distinct_count_percent/distinct_count_percent.md).

Next write `rules:` and define it, in this example this is [min_count](../../rule_reference/comparison/min_count.md). 

Save the file.

Here is a rendered query that is later sent to BigQuery.

```SQL
{{ process_template_request( get_request( "docs/getting_started/requests/example.json" ) ) }}
```