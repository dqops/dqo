---
title: column count match data quality checks
---
# column count match data quality checks

Table level comparison check that compares the column count of the current (parent) table with the column count of the reference table.


___
The **column count match** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile column count match


**Check description**

Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_column_count_match`</span>|[comparisons](../../../categories-of-data-quality-checks/how-to-reconcile-data-and-detect-differences.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Accuracy](../../../dqo-concepts/data-quality-dimensions.md#data-accuracy)|[*column_count*](../../../reference/sensors/table/schema-table-sensors.md#column-count)|[*diff_percent*](../../../reference/rules/Comparison.md#diff-percent)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile column count match data quality check.

??? example "Managing profile column count match check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_column_count_match --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_column_count_match --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_column_count_match --enable-warning
                            -Wmax_diff_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_column_count_match --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_column_count_match --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_column_count_match --enable-error
                            -Emax_diff_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_column_count_match* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_column_count_match
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_column_count_match
        ```

        You can also run this check on all tables  on which the *profile_column_count_match* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_column_count_match
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="16-25"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  table_comparisons:
    compare_to_source_of_truth_table:
      reference_table_connection_name: <source_of_truth_connection_name>
      reference_table_schema_name: <source_of_truth_schema_name>
      reference_table_name: <source_of_truth_table_name>
      check_type: profiling
      grouping_columns:
      - compared_table_column_name: country
        reference_table_column_name: country_column_name_on_reference_table
      - compared_table_column_name: state
        reference_table_column_name: state_column_name_on_reference_table
  profiling_checks:
    comparisons:
      compare_to_source_of_truth_table:
        profile_column_count_match:
          warning:
            max_diff_percent: 0.0
          error:
            max_diff_percent: 1.0
          fatal:
            max_diff_percent: 5.0
  columns:
    country:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison
    state:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_count](../../../reference/sensors/table/schema-table-sensors.md#column-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## daily column count match


**Check description**

Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_column_count_match`</span>|[comparisons](../../../categories-of-data-quality-checks/how-to-reconcile-data-and-detect-differences.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Accuracy](../../../dqo-concepts/data-quality-dimensions.md#data-accuracy)|[*column_count*](../../../reference/sensors/table/schema-table-sensors.md#column-count)|[*diff_percent*](../../../reference/rules/Comparison.md#diff-percent)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily column count match data quality check.

??? example "Managing daily column count match check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_column_count_match --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_column_count_match --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_column_count_match --enable-warning
                            -Wmax_diff_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_column_count_match --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_column_count_match --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_column_count_match --enable-error
                            -Emax_diff_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_column_count_match* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_column_count_match
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_column_count_match
        ```

        You can also run this check on all tables  on which the *daily_column_count_match* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_column_count_match
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="16-26"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  table_comparisons:
    compare_to_source_of_truth_table:
      reference_table_connection_name: <source_of_truth_connection_name>
      reference_table_schema_name: <source_of_truth_schema_name>
      reference_table_name: <source_of_truth_table_name>
      check_type: profiling
      grouping_columns:
      - compared_table_column_name: country
        reference_table_column_name: country_column_name_on_reference_table
      - compared_table_column_name: state
        reference_table_column_name: state_column_name_on_reference_table
  monitoring_checks:
    daily:
      comparisons:
        compare_to_source_of_truth_table:
          daily_column_count_match:
            warning:
              max_diff_percent: 0.0
            error:
              max_diff_percent: 1.0
            fatal:
              max_diff_percent: 5.0
  columns:
    country:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison
    state:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_count](../../../reference/sensors/table/schema-table-sensors.md#column-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___


## monthly column count match


**Check description**

Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_column_count_match`</span>|[comparisons](../../../categories-of-data-quality-checks/how-to-reconcile-data-and-detect-differences.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Accuracy](../../../dqo-concepts/data-quality-dimensions.md#data-accuracy)|[*column_count*](../../../reference/sensors/table/schema-table-sensors.md#column-count)|[*diff_percent*](../../../reference/rules/Comparison.md#diff-percent)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly column count match data quality check.

??? example "Managing monthly column count match check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_column_count_match --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_column_count_match --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_column_count_match --enable-warning
                            -Wmax_diff_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_column_count_match --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_column_count_match --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_column_count_match --enable-error
                            -Emax_diff_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_column_count_match* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_column_count_match
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_column_count_match
        ```

        You can also run this check on all tables  on which the *monthly_column_count_match* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_column_count_match
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="16-26"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  table_comparisons:
    compare_to_source_of_truth_table:
      reference_table_connection_name: <source_of_truth_connection_name>
      reference_table_schema_name: <source_of_truth_schema_name>
      reference_table_name: <source_of_truth_table_name>
      check_type: profiling
      grouping_columns:
      - compared_table_column_name: country
        reference_table_column_name: country_column_name_on_reference_table
      - compared_table_column_name: state
        reference_table_column_name: state_column_name_on_reference_table
  monitoring_checks:
    monthly:
      comparisons:
        compare_to_source_of_truth_table:
          monthly_column_count_match:
            warning:
              max_diff_percent: 0.0
            error:
              max_diff_percent: 1.0
            fatal:
              max_diff_percent: 5.0
  columns:
    country:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison
    state:
      labels:
      - column used as the first grouping key for calculating aggregated values used
        for the table comparison

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [column_count](../../../reference/sensors/table/schema-table-sensors.md#column-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
