# Sensors
Sensor are a predefined and customizable Jinja templates whose goal is to render SQL query in accordance with provider's
SQL dialect.

Dialects (Jinja2 macros) are variables obtained from the table metadata and macros performing various functions, written
in Jinja2.

User can configure basic statements as `where`, `group by`, `order by`, [time series](../../dqo_concept/time_series/time_series.md) mode,
and different parameters
characteristic for the individual sensors (see the [examples](sensors.md#examples)).

!!! tip
    When changing configuration in YAML file in your editor, you can use code completion and check possible fields.
    We highly recommend using [Visual Studio Code](https://code.visualstudio.com/) with installed
    [Better Jinja](https://marketplace.visualstudio.com/items?itemName=samuelcolvin.jinjahtml) by Samuel Colvin and
    [YAML](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-yaml) by Red Hat plugins.

# Sensor types and categories

Sensors are divided into types:

- column,
- table,

and [dimensions](../../dqo_concept/dimensions.md):

- validity,
- timeliness,
- completeness,
- relevance,
- uniqueness,
- consistency.

Each check adds value to the level indicated by the type. All timeliness checks, for example,
are table type since they correspond to the various time aspects of the whole table, whereas column
level checks only provide value to the data inside solitary columns.

Each check category relates to a distinct set of business requirements. Validity tests, for example, ensure that
the data follows particular guidelines, such as numerical numbers being inside a certain range.

## Choosing sensor for a check
Check configuration is very simple, in fact it is almost the same both for column and table type.
To configure quality checks on a table level, you have to add field `checks` under table configuration, choose the
category and desired sensor. In case of column level checks, the configuration is the same, only done under chosen
column configuration.

=== "Table level"
    ```yaml hl_lines="11-21" linenums="1"
    --8<-- "docs/dqo_concept/sensors/yamls/sample_table_check.yml"
    ```

=== "Column level"
    ```yaml hl_lines="20-37" linenums="1"
    --8<-- "docs/dqo_concept/sensors/yamls/sample_column_check.yml"
    ```

## Examples
Configuration of a column level validity check `values_in_range_date_percent` with `count_equals` rule.


YAML file:

```yaml
--8<-- "docs/dqo_concept/sensors/yamls/sample_column_check.yml"
```

```SQL
{{ process_template_request(get_request("docs/dqo_concept/sensors/requests/sample_column_check.json")) }}
```