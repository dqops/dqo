# Regex match percent

## Description
The `regex_match_percent` check calculates the percentage of matching records with a specified regex.
User has to specify the regex for the query. It is done with parameters, `named_regex` 
- [predefined regexes](regex_match_percent.md#list-of-built-in-regexes)
and `custom_regex` - user defined regex passed as string.

!!! Info
    Regex parameter is required in a certain sense. No regex provided will result with matching an empty string, so all
    the data will be valid, giving score of 100%.
___

## When to use
This check is most useful when you need to check correct formatting of your data, for example if emails contain `@` 
sign, or they contain only allowed symbols.

___

## Used sensor

[__Regex match percent__](../../../sensor_reference/validity/regex_match_percent/regex_match_percent.md)
___

## Accepted rules
[__Min count__](../../../rule_reference/comparison/min_count.md)

[__Count equals__](../../../rule_reference/comparison/count_equals.md)
___

## Parameters
This checks has two optional parameters that configure sensor:

- `named_regex`: _str_
  <br/>predefined regex, regexes are listed in [here]() 
- `custom_regex`: _String_
  <br/>used for defining custom regexes, provided as strings, e.g. `custom regex = "^[0-9 -]{11}$"`

!!! note "`named_regex` vs. `custom_regex`"
    When defining a check keep in mind that `named_regex` overrides `custom_regex`. Meaning that if you specify both
    `named_regex` and `custom_regex`, a query will run with `named_regex` only.
### List of built-in regexes

| Name          | Regex                                                    |
|---------------|----------------------------------------------------------|
| `email`       | '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$' |
| `phoneNumber` | '^[pP]?[tT]?[nN]?[oO]?[0-9]{7,11}$'                      |


___

## How to use

### Email regex
Table configuration in YAML file for column check `regex_match_percent` on a column `emails` in `table` table in
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/regex_match_percent/yamls/email_configuration.yaml"
```
In this case user provided `namedRegex` as `email`.
The rendered query is
```SQL linenums="1"
{{ process_template_request(get_request("docs/check_reference/validity/regex_match_percent/requests/email_configuration.json")) }}
```

### PhoneNumber regex
Table configuration in YAML file for column check `regex_match_percent` on a column `phoneNumbers` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/regex_match_percent/yamls/phoneNumber_configuration.yaml"
```
In this case user provided `namedRegex` as `phoneNumber`.
The rendered query is
```SQL linenums="1"
{{ process_template_request(get_request("docs/check_reference/validity/regex_match_percent/requests/phoneNumber_configuration.json")) }}
```

### Custom regex
Table configuration in YAML file for column check `regex_match_percent` on a column `flatKey` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/regex_match_percent/yamls/custom_configuration.yaml"
```
In this case user provided `customRegex` as `^[0-9 -]{11}$`.
The rendered query is
```SQL linenums="1"
{{ process_template_request(get_request("docs/check_reference/validity/regex_match_percent/requests/custom_configuration.json")) }}
```

### Custom and named regex
Table configuration in YAML file for column check `regex_match_percent` on a column `column` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule where both `named_regex` and `custom_regex` are
provided, looks like this

```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/regex_match_percent/yamls/custom_and_named_params.yaml"
```
The rendered query is
```SQL linenums="1"
{{ process_template_request(get_request("docs/check_reference/validity/regex_match_percent/requests/custom_and_named_params.json")) }}
```