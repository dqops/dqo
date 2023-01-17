# How to configure rules
When we want to evaluate query results of data quality checks, we have to config rule that is supposed to be used.
We can do it in `yaml` files which consist of dataset's metadata.

An example `yaml` file has a structure presented below. You can see three columns' metadata `name_of_timestamp_column1`, `name_of_timestamp_column2` and `emails`. 

```yaml linenums="1" 
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: test_data
    table_name: string_dates
  time_series: 
    mode: timestamp_column
    time_gradient: day
    timestamp_column: name_of_timestamp_column1
  checks: {}
  columns:
    name_of_timestamp_column1:
      type_snapshot:
        column_type: DATE
        nullable: true
    name_of_timestamp_column2:
      type_snapshot:
        column_type: DATE
        nullable: true
    emails:
      type_snapshot:
        column_type: STRING
        nullable: true                
```

You can configure checks for a whole table or a specified column. Let's see how to do it.
###Rule configuration for table
Rule configuration for table takes place at a table level, like below.
```yaml hl_lines="12-31" linenums="1" 
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: test_data
    table_name: string_dates
  time_series: 
    mode: timestamp_column
    time_gradient: day
    timestamp_column: name_of_timestamp_column1
  checks:
    validity:
        timeliness:
            datetime_difference:
            parameters:
                column1: name_of_timestamp_column1
                column2: name_of_timestamp_column2
                time_scale: day
                max_difference: 2
            rules:
                moving_average:
                    low:
                        max_percent_above: 5.0
                        max_percent_below: 5.0
                    medium:
                        max_percent_above: 10.0
                        max_percent_below: 10.0
                    high:
                        max_percent_above: 15.0
                        max_percent_below: 15.0
  columns:
    name_of_timestamp_column1:
      type_snapshot:
        column_type: DATE
        nullable: true
    name_of_timestamp_column2:
      type_snapshot:
        column_type: DATE
        nullable: true
    emails:
      type_snapshot:
        column_type: STRING
        nullable: true                
```
##Rule configuration for column
If you want to config a rule for a chosen column, you can do it at a column level. A code presented below shows how to config a `min_count` rule for validity check in `regex_match_percent` sensor.   
```yaml hl_lines="21-37" linenums="1" 
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: test_data
    table_name: string_dates
  time_series: 
    mode: current_time
    time_gradient: day
  checks: {}
  columns:
    name_of_timestamp_column1:
      type_snapshot:
        column_type: DATE
        nullable: true
    name_of_timestamp_column2:
      type_snapshot:
        column_type: DATE
        nullable: true
    emails:
      type_snapshot:
        column_type: STRING
        nullable: true    
      checks:   
        validity:
          regex_match_percent:
            parameters:
                named_regex: email
            rules:
                min_count:
                    low:
                        min_value: 90.0
                    medium:
                        min_value: 80.0
                    high:
                        min_value: 70.0         
```
