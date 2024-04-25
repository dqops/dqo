---
title: How to detect PII (Personal Identifiable Information) data
---
# How to detect PII (Personal Identifiable Information) data
Read this guide to learn how to detect the presence of Personal Identifiable Information such as emails or phone numbers in tables.

The data quality checks that detect PII values are configured in the `pii` category in DQOps.

## What is PII data
Personal Identifiable Information (PII) is any information that permits the identity of an individual to whom the information applies.

The typical information that can lead to revealing the identity of an individual are:

- Phone number.

- Email address.

- IP address.

Additionally, an individual's identity can be inferred by using a combination of multiple elements, 
such as a ZIP code. 

Accidental exposure of PII data can have severe consequences for an organization. 
Companies that operate in the European Union must comply with the GDPR regulations.
An exposure or just sharing of sensitive information to a third party without 
the individual's explicit approval is considered a data leak. 
An organization that does not protect the personal data of its customers and employees
is subject to a fine as high as 10% of the worldwide annual revenue. 

Even if an organization is not under the GDPR law, exposing sensitive information by mistake
may lead to losing the trust of its customers and business partners.

### When sensitive data can leak
In the age of data mesh and data sharing across organizations, many data assets are shared without control. 
The tables that are accessible to any user without control should not contain sensitive information.

The best way to ensure that no PII data is present is by running data quality checks that use
patterns to find possible emails or phone numbers in columns that are not supposed to store these values.
Personal information is often found in free-text columns, such as comments or descriptions.

Scanning the following types of tables and columns for possible exposure of PII data is advisable.

- Tables that are shared across the organization with unlimited access to run queries.

- Tables that are shared with external vendors, suppliers or customers.

- Columns containing comments and descriptions.

- Columns that store information captured in free-form fields. Sometimes, the personnel uses these fields to store additional comments.

### How DQOps finds PII data
DQOps supports several dedicated data quality checks that search for patterns inside text fields.

The following table shows the possible content of a comments column with sensitive information.

| Comment                                                                                              |
|------------------------------------------------------------------------------------------------------|
| The customer requested to be notified by email **john.smith@gmail.com** when the package is shipped. |
| The customer left his private phone number for the courier: **123-456-7890**                         |

## Data profiling
The selection of columns that should be monitored for sensitive data begins on the column's profiling screen.
The following screenshot shows samples from a public dataset of *311 service requests* line in Austin.
We are looking at the **complaint_description** column, a free-form field that could contain some sensitive data.

![Data profiling results of a column without sensitive pii data](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/profile-table-without-pii-values-min.png){ loading=lazy; width="1200px" }

So far, the data samples do not show anything sensitive, but we will prove it later with a data quality check.

The next sample comes from an *incident_address* column.We can instantly notice addresses and phone numbers.
Please be aware that this table is public, and we do not see any visible proof of anonymization.

![Data profiling results of a column without sensitive pii data](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/profile-table-with-pii-values-phone-zip-min.png){ loading=lazy; width="1200px" }

## Enabling PII detection in DQOps
DQOps contains several data quality checks that run SQL queries to identify the most common PII values.

- [*contains_usa_phone_percent*](../checks/column/pii/contains-usa-phone-percent.md) check detects US phone numbers.

- [*contains_email_percent*](../checks/column/pii/contains-email-percent.md) check detects emails.

- [*contains_usa_zipcode_percent*](../checks/column/pii/contains-usa-zipcode-percent.md) detects US zip codes.

- [*contains_ip4_percent*](../checks/column/pii/contains-ip4-percent.md) detects IP4 internet addresses.

- [*contains_ip6_percent*](../checks/column/pii/contains-ip6-percent.md) detects common forms of IP6 internet addresses.


### Activate PII checks in UI
The data quality check editor in DQOps shows the Personal Identifiable Information checks in the PII category.
The following example shows the result of detecting phone numbers, emails, and zip codes in the **complaint_description** column.
DQOps did not detect sensitive data inside any value stored in the column.

![Data quality check editor with PII checks enabled and valid results](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/pii-data-quality-checks-valid-results-min.png){ loading=lazy; width="1200px" }

The next example shows the result of running the same checks on the **incident_address** column.
We can see that this public dataset contains a few phone numbers and emails.

![Data quality checks editor with PII checks that found sensitive data](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/pii-check-detecting-emails-and-phones-found-min.png){ loading=lazy; width="1200px" }


### Activate PII checks in YAML
The PII checks are configured by setting the *max_percent* parameter.

``` { .yaml linenums="1" hl_lines="13-21" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    complaint_description:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          pii:
            daily_contains_usa_phone_percent:
              error:
                max_percent: 0.0
            daily_contains_email_percent:
              error:
                max_percent: 0.0
            daily_contains_usa_zipcode_percent:
              error:
                max_percent: 0.0
```

### Detecting other forms of PII data
DQOps contains only data quality checks to detect the five most common and reliable PII patterns.
If you need to detect other types of PII data, please follow the manual for 
[configuring custom data quality checks](../working-with-dqo/creating-custom-data-quality-checks.md).
You can make a copy of one of the built-in PII checks and adapt the regular expression to find alternative patterns.


## Use cases
| **Name of the example**                                                                                                    | **Description**                                                                                                                                                                                                                |
|:---------------------------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Percentage of rows containing USA zip codes](../examples/data-validity/percentage-of-values-that-contains-usa-zipcode.md) | This example shows how to detect USA zip codes in text columns by measuring the percentage of rows containing a zip code using the [contains_usa_zipcode_percent](../checks/column/pii/contains-usa-zipcode-percent.md) check. |


## List of PII checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*contains_usa_phone_percent*](../checks/column/pii/contains-usa-phone-percent.md)|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects USA phone numbers inside text columns. It measures the percentage of columns containing a phone number and raises a data quality issue when too many rows contain phone numbers.|:material-check-bold:|
|[*contains_email_percent*](../checks/column/pii/contains-email-percent.md)|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects emails inside text columns. It measures the percentage of columns containing an email and raises a data quality issue when too many rows contain emails.|:material-check-bold:|
|[*contains_usa_zipcode_percent*](../checks/column/pii/contains-usa-zipcode-percent.md)|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects USA zip code inside text columns. It measures the percentage of columns containing a zip code and raises a data quality issue when too many rows contain zip codes.| |
|[*contains_ip4_percent*](../checks/column/pii/contains-ip4-percent.md)|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects IP4 addresses inside text columns. It measures the percentage of columns containing an IP4 address and raises a data quality issue when too many rows contain IP4 addresses.| |
|[*contains_ip6_percent*](../checks/column/pii/contains-ip6-percent.md)|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|This check detects IP6 addresses inside text columns. It measures the percentage of columns containing an IP6 address and raises a data quality issue when too many rows contain IP6 addresses.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/pii](../checks/column/pii/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
