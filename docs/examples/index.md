---
title: List of data quality use cases and examples
---
# List of data quality use cases and examples
Check various examples demonstrating how the DQOps platform can detect data quality issues, and help you evaluate results on data quality dashboards.

## List of data quality use cases

Here is a comprehensive list of examples with links to the relevant documentation section with detailed descriptions.
These examples use openly available datasets from [Google Cloud](https://cloud.google.com/datasets).

### **Data accuracy**

| **Name of the example**                                                                                                       | **Description**                                                                                                                                                                                         | **Link to the dataset description**                                   |
|:------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------|
| [Integrity check between columns in different tables](./data-accuracy/integrity-check-between-columns-in-different-tables.md) | This example shows how to check the referential integrity of a column against a column in another table using [lookup_key_found_percent](../checks/column/integrity/lookup-key-found-percent.md) check. | [Link](https://www.census.gov/library/reference/code-lists/ansi.html) |

### **Data availability**

| **Name of the example**                                                                     | **Description**                                                                                                                                                                                           | **Link to the dataset description**                                          |
|:--------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------|
| [Detect table availability issues](./data-availability/detect-table-availability-issues.md) | This example shows how to verify that a query can be executed on a table and that the server does not return errors using [table_availability](../checks/table/availability/table-availability.md) check. | [Link](https://www.americashealthrankings.org/about/methodology/our-reports) |

### **Data completeness**

| **Name of the example**                                                                       | **Description**                                                                                                                                   | **Link to the dataset description**                                          |
|:----------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------|
| [Detect incomplete columns](./data-completeness/detect-null-values.md)                        | This example shows how to incomplete columns that have too many null values using the [nulls_count](../checks/column/nulls/nulls-count.md) check. | [Link](https://www.americashealthrankings.org/about/methodology/our-reports) |
| [Detect empty or incomplete tables](./data-completeness/detect-empty-or-incomplete-tables.md) | This example shows how to find empty or too small tables using the [row_count](../checks/table/volume/row-count.md) check.                        | [Link](https://www.americashealthrankings.org/about/methodology/our-reports) |

### **Data consistency**

| **Name of the example**                                                                                              | **Description**                                                                                                                                                                               | **Link to the dataset description**                                       |
|:---------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------|
| [Percentage of rows having only accepted values](./data-consistency/percentage-of-rows-with-a-text-found-in-set.md)  | This example shows how to verify that a text column contains only accepted values using the [text_found_in_set_percent](../checks/column/accepted_values/text-found-in-set-percent.md) check. | [Link](https://www.kaggle.com/datasets/whenamancodes/student-performance) |

### **Data reasonability**

| **Name of the example**                                                                              | **Description**                                                                                                                                                                                                             | **Link to the dataset description**                                                                              |
|:-----------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------|
| [Percentage of false boolean values](./data-reasonability/percentage-of-false-values.md)             | This example shows how to detect that the percentage of false values remains above a set threshold using [false_percent](../checks/column/bool/false-percent.md) check.                                                     | [Link](https://console.cloud.google.com/marketplace/product/federal-communications-commission/fcc-political-ads) |
| [Percentage of values in range](./data-reasonability/percentage-of-integer-values-in-range.md)       | This example shows how to detect that the percentage of values within a set range in a column does not exceed a set threshold using [integer_in_range_percent](../checks/column/numeric/integer-in-range-percent.md) check. | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |
| [A text not exceeding a maximum length](./data-reasonability/text-not-exceeding-a-maximum-length.md) | This example shows how to check that the length of the text does not exceed the maximum value using [text_max_length](../checks/column/text/text-length-above-max-length-percent.md) check.                                 | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |

### **Data uniqueness**

| **Name of the example**                                                   | **Description**                                                                                                                                                                                                         | **Link to the dataset description**                                                               |
|:--------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------|
| [Percentage of duplicates](./data-uniqueness/percentage-of-duplicates.md) | This example shows how to detect that the percentage of duplicate values in a column does not exceed the maximum accepted percentage using [duplicate_percent](../checks/column/uniqueness/duplicate-percent.md) check. | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) |

### **Data validity**

| **Name of the example**                                                                                          | **Description**                                                                                                                                                                                                                                                                                       | **Link to the dataset description**                                                               |
|:-----------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------|
| [Detect invalid emails](./data-validity/detect-invalid-emails.md)                                                | This example shows how to detect that the number of invalid emails in a column does not exceed the maximum accepted count using [invalid_email_format_found](../checks/column/patterns/invalid-email-format-found.md) check.                                                                          | DQOps dataset                                                                                     |
| [Detect invalid IP4 address](./data-validity/detect-invalid-ip4-addresses.md)                                    | This example shows how to detect that the number of invalid IP4 address in a column does not exceed a set threshold using [invalid_ip4_address_format_found](../checks/column/patterns/invalid-ip4-address-format-found.md) check.                                                                    | DQOps dataset                                                                                     |
| [Percentage of negative values](./data-validity/percentage-of-negative-values.md)                                | This example shows how to detect that the percentage of negative values in a column does not exceed a set threshold using [negative_values_percent](../checks/column/numeric/negative-values-percent.md) check.                                                                                       | [Link](https://www.worldometers.info/world-population/population-by-country/)                     |
| [Percentage of rows passing SQL condition](./data-validity/percentage-of-rows-passing-sql-condition.md)          | This example shows how to detect that the percentage of passed sql condition in a column does not fall below a set threshold using [sql_condition_passed_percent](../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)  check.                                                        | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                      |
| [Percentage of texts not matching a date pattern](./data-validity/percentage-of-texts-matching-date-regex.md)    | This example shows how to detect that the percentage of texts matching the date format regex in a column does not exceed a set threshold using [text_not_matching_date_pattern_percent](../checks/column/patterns/text-not-matching-date-pattern-percent.md) check.                                   | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                      |
| [Percentage of valid currency codes](./data-validity/percentage-of-valid-currency-codes.md)                      | This example shows how to detect that the percentage of valid currency codes in a column does not fall below a set threshold using [text_valid_currency_code_percent](../checks/column/accepted_values/text-valid-currency-code-percent.md) check.                                                    | DQOps dataset                                                                                     |
| [Percentage of valid latitude and longitude](./data-validity/percentage-of-valid-latitude-and-longitude.md)      | This example shows how to detect that the percentage of valid latitude and longitude values remain above a set threshold using [valid_latitude_percent](../checks/column/numeric/valid-latitude-percent.md) and [valid_longitude_percent](../checks/column/numeric/valid-longitude-percent.md)checks. | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) |
| [Percentage of invalid UUID](./data-validity/percentage-of-valid-uuid.md)                                        | This example shows how to detect that the percentage of valid UUID values in a column does not fall below a set threshold using [invalid_uuid_format_percent](../checks/column/patterns/invalid-uuid-format-percent.md) check.                                                                        | DQOps dataset                                                                                     |
| [Percentage of rows containing USA zip codes](./data-validity/percentage-of-values-that-contains-usa-zipcode.md) | This example shows how to detect USA zip codes in text columns by measuring the percentage of rows containing a zip code using the [contains_usa_zipcode_percent](../checks/column/pii/contains-usa-zipcode-percent.md) check.                                                                        | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) |

### **Schema**

| **Name of the example**                                                | **Description**                                                                                     | **Link to the dataset description**                                          |
|:-----------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------|
| [Detect table schema changes](./schema/detect-table-schema-changes.md) | This example shows how to detect schema changes on the table using several schema detection checks. | [Link](https://www.americashealthrankings.org/about/methodology/our-reports) |

### **Data quality monitoring**

| **Name of the example**                                                                         | **Description**                                                                      | **Link to the dataset description**                                                                 |
|:------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------|
| [Detect empty tables](./data-quality-monitoring/detect-empty-tables.md)                         | This example shows how to detect empty tables using the default data quality checks. | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he)   |
| [Running checks with a scheduler](./data-quality-monitoring/running-checks-with-a-scheduler.md) | This example shows how to set different schedules on multiple checks.                | [Link](https://console.cloud.google.com/marketplace/product/bigquery-public-data/thelook-ecommerce) |


## Prerequisite

To use the examples you need:

- [Installed DQOps](../getting-started/installation.md).
- A BiqQuery service account with **BigQuery > BigQuery Job User** permission. [You can create a free trial Google Cloud account here](https://cloud.google.com/free).
- A working [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) if you want to use [Google Application Credentials authentication](../data-sources/bigquery.md#using-google-application-credentials-authentication).

After installing Google Cloud CLI, log in to your GCP account, by running:

```
gcloud auth application-default login
```

## Location of the examples

[Standard DQOps installation](../getting-started/installation.md) comes with a set of examples, which can 
be found in the `example/` directory. You can view a complete list of the examples with links to detailed explanation above.

The example directory contains two configuration files: `connection.dqoconnection.yaml`, which stores the data source
configuration, and `*.dqotable.yaml` file, which stores the columns and tables metadata configuration.

While it is not necessary to manually add the connection in our examples, you can find information on how to do it in the
[Working with DQOps section](../data-sources/index.md).

## Start DQOps

To start the DQOps application with the example, follow the steps below. 

1. Go to the directory where you installed DQOps and navigate, for example, to 
    `examples/data-completeness/number-of-rows-in-the-table-bigquery`.  
    
    Run the command provided below in the terminal. This will install DQOps on your computer. 

    === "Windows"

        ```
        run_dqo
        ```
    === "MacOS/Linux"

        ```
        ./run_dqo
        ```

2. Create DQOps `DQOps user home` folder.

    After installation, you will be asked whether to initialize the [DQOps user home folder](../dqo-concepts/dqops-user-home-folder.md) in the default location. Type Y to create the folder. . 

    ![Initializing DQOps user home folder](https://dqops.com/docs/images/getting-started/initializing-user-home-folder2.png){ loading=lazy }

    The DQOps user home folder locally stores data such as sensor and checkout readings, as well as data source configurations. [You can learn more about data storage here](../dqo-concepts/data-storage-of-data-quality-results.md).


3. Login to DQOps Cloud.

    To use DQOps features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
    must create a DQOps cloud account.
 
    After creating the [DQOps user home folder](../dqo-concepts/dqops-user-home-folder.md), you will be asked whether to log in to the DQOps cloud. 

    ![Log in to DQOps Cloud](https://dqops.com/docs/images/getting-started/log-in-to-dqops-cloud3.png){ loading=lazy }

    After typing Y, you will be redirected to [https://cloud.dqops.com/registration](https://cloud.dqops.com/registration), where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.
 
    During the first registration, a unique identification code (API Key) will be generated and automatically passed to the DQOps application.
    The API Key is now stored in the configuration file.