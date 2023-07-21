# Use cases

We have provided a variety of examples to help you in using DQO effectively. These examples use openly available 
datasets from [Google Cloud](https://cloud.google.com/datasets).

## Prerequisite

To use the examples you need:

- A BiqQuery service account with **BigQuery > BigQuery Job User** permission. [You can create a free trial Google Cloud account here](https://cloud.google.com/free).
- A working [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) if you want to use [Google Application Credentials authentication](./#using-google-application-credentials-authentication).

After installing Google Cloud CLI, log in to your GCP account, by running:

```
gcloud auth application-default login
```

## Running the use cases

[Standard DQO installation](../getting-started/installation/installation.md) comes with a set of examples, which can 
be found in the `example/` directory. You can view a complete list of the examples with links to detailed explanation by
scrolling to the bottom of the page.

The example directory contains two configuration files: `connection.dqoconnection.yaml`, which stores the data source
configuration, and `*.dqotable.yaml` file, which stores the columns and tables metadata and checks configuration.

While it is not necessary to manually add the connection in our examples, you can find information on how to do it in the
[Working with DQO section](../working-with-dqo/adding-data-source-connection/index.md).


To run the examples, follow the steps below. 

1. If you have not installed DQO, create an empty catalogue.


2. Open a terminal, navigate to the created directory and clone the DQO repository from [GitHub](https://github.com/dqops/dqo).

    ```
    git clone https://github.com/dqops/dqo.git
    ```

3. To run a specific example, go to the directory where it is located. For instance, you can navigate to 
    `examples/data-reasonability/percentage-of-false-values-bigquery`.  
    
    Run the command provided below. 

    === "Windows"

        ```
        run_dqo
        ```
    === "MacOS/Linux"

        ```
        ./run_dqo
        ```

4. Create DQO `userhome` folder.

    After installation, you will be asked whether to initialize the DQO userhome folder in the default location. Type Y to create the folder.  
    The userhome folder locally stores data such as sensor and checkout readings, as well as data source configurations. [You can learn more about data storage here](../dqo-concepts/data-storage/data-storage.md).


5. Login to DQO Cloud.

    To use DQO features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
    must create a DQO cloud account.
 
    After creating an userhome folder, you will be asked whether to log in to the DQO cloud. After typing Y, you will be
    redirected to https://cloud.dqo.ai/registration, where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.
 
    During the first registration, a unique identification code (API Key) will be generated and automatically passed to the DQO application.
    The API Key is now stored in the configuration file.


6. To execute the checks that were prepared in the example, run the following command in DQO Shell:

    ```
    check run
    ```
    You can also execute the checks using the graphical interface.


7. After executing the checks, synchronize the results with your DQO cloud account by running the following command.

    ```
    cloud sync all
    ``` 

8. You can now review the results on the data quality dashboards as described in [getting started section](../getting-started/review-results-on-dashboards/review-results-on-dashboards.md).

## List of the use cases

Here is a comprehensive list of examples with links to the relevant documentation section with detailed descriptions.

| **Name of the example**                                                                                                     | **Description**                                                                                                                                                                                                                                                                                                       | **Link to the dataset description**                                                                              |
|:----------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------|
| **Data accuracy**                                                                                                           |                                                                                                                                                                                                                                                                                                                       |                                                                                                                  |
| [Integrity check between columns in different tables](data-accuracy/integrity-check-between-columns-in-different-tables.md) | This example shows how to check the referential integrity of a column against a column in another table using [foreign-key-match-percent](../checks/column/integrity/foreign-key-match-percent.md) check.                                                                                                             | [Link](https://www.census.gov/library/reference/code-lists/ansi.html)                                            |
| **Data completeness**                                                                                                       |                                                                                                                                                                                                                                                                                                                       |                                                                                                                  |
| [Number of rows in the table](data-completeness/number-of-rows-in-the-table.md)                                             | This example shows how to check that the number of rows in a table does not exceed the minimum accepted count using [row_count](../checks/table/volume/row-count.md) check.                                                                                                                                           | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |
| [Number of null values](data-completeness/number-of-null-values.md)                                                         | This example shows how to detect that the number of null values in a column does not exceed the maximum accepted count using [nulls_cont](../checks/column/nulls/nulls-count.md) check.                                                                                                                               | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |
| **Data uniqueness**                                                                                                         |                                                                                                                                                                                                                                                                                                                       |                                                                                                                  |
| [Percentage of duplicates](data-uniqueness/percentage-of-duplicates.md)                                                     | This example shows how to detect that the percentage of duplicate values in a column does not exceed the maximum accepted percentage using [duplicate_percent](../checks/column/uniqueness/duplicate-percent.md) check.                                                                                               | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he)                |
| **Data validity**                                                                                                           |                                                                                                                                                                                                                                                                                                                       |                                                                                                                  |
| [Percentage of valid USA zipcodes](data-validity/percentage-of-valid-usa-zipcodes.md)                                       | This example shows how to detect that the percentage of valid USA zip code in a column does not fall below a set threshold using [valid_usa_zipcode_percent](../checks/column/pii/valid-usa-zipcode-percent.md) check.                                                                                                | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he)                |
| [Percentage of valid emails](data-validity/percentage-of-valid-emails.md)                                                   | This example shows how to detect that the percentage of valid email values in a column does not exceed the maximum accepted percentage using [valid_email_percent](../checks/column/pii/valid-email-percent.md) check.                                                                                                | DQOps dataset                                                                                                    |
| [Percentage of valid latitude and longitude](data-validity/percentage-of-valid-latitude-and-longitude.md)                   | This example shows how to detect that the percentage of valid latitude and longitude values remain above a set threshold using [numeric_valid_latitude_percent](../checks/column/numeric/valid-latitude-percent.md) and [numeric_valid_longitude_percent](../checks/column/numeric/valid-longitude-percent.md)checks. | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he)                |
| [Percentage of valid IP4 address](data-validity/percentage-of-valid-IP4-address.md)                                         | This example shows how to detect that the percentage of valid IP4 address in a column does not fall below a set threshold using [valid_ip4_address_percent](../checks/column/pii/valid-ip4-address-percent.md) check.                                                                                                 | DQOps dataset                                                                                                    |
| [Percentage of strings matching date regex](data-validity/percentage-of-strings-matching-date-regex.md)                     | This example shows how to detect that the percentage of strings matching the date format regex in a column does not exceed a set threshold using [string_match_date_regex_percent](../checks/column/strings/string-match-date-regex-percent.md) check.                                                                | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |
| [Percentage of negative values](data-validity/percentage-of-negative-values.md)                                             | This example shows how to detect that the percentage of negative values in a column does not exceed a set threshold using [negative_percent](../checks/column/numeric/negative-percent.md) check.                                                                                                                     | [Link](https://www.worldometers.info/world-population/population-by-country/)                                    |
| [Percentage of valid currency codes](data-validity/percentage-of-valid-currency-codes.md)                                   | This example shows how to detect that the percentage of valid currency codes in a column does not fall below a set threshold using [string_valid_currency_code_percent](../checks/column/strings/string-valid-currency-code-percent.md) check.                                                                        | DQOps dataset                                                                                                    |
| [Percentage of rows passing SQL condition](data-validity/percentage-of-rows-passing-sql-condition.md)                       | This example shows how to detect that the percentage of passed sql condition in a column does not fall below a set threshold using [sql_condition_passed_percent](../checks/table/sql/sql-condition-passed-percent-on-table.md)  check.                                                                               | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |
| [Percentage of valid UUID](data-validity/percentage-of-valid-uuid.md)                                                       | This example shows how to detect that th percentage of valid UUID values in a column does not fall below a set threshold using [string_valid_uuid_percent](../checks/column/strings/string-valid-uuid-percent.md) check.                                                                                              | DQOps dataset                                                                                                    |
| **Data reasonability**                                                                                                      |                                                                                                                                                                                                                                                                                                                       |                                                                                                                  |
| [Percentage of values in range](data-reasonability/percentage-of-integer-values-in-range.md)                                | This example shows how to detect that the percentage of values within a set range in a column does not exceed a set threshold using [values_in_range_integers_percent](../checks/column/numeric/values-in-range-integers-percent.md) check.                                                                           | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |
| [A string not exceeding a set length](data-reasonability/string-not-exceeding-a-set-length.md )                             | This example shows how to check that the length of the string does not exceed the indicated value using [string_max_length](../checks/column/strings/string-length-above-max-length-percent.md) check.                                                                                                                | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |
| [Percentage of false values](data-reasonability/percentage-of-false-values.md)                                              | This example shows how to detect that the percentage of false values remains above a set threshold using [bool_false_percent](../checks/column/bool/false-percent.md) check.                                                                                                                                          | [Link](https://console.cloud.google.com/marketplace/product/federal-communications-commission/fcc-political-ads) |
| **Stability**                                                                                                               |                                                                                                                                                                                                                                                                                                                       |                                                                                                                  |
| [Table availability](stability/table-availability.md)                                                                       | This example shows how to verify that a query can be executed on a table and that the server does not return errors using [table_availability](../checks/table/availability/table-availability.md) check.                                                                                                             | [Link](https://www.americashealthrankings.org/about/methodology/our-reports)                                     |
| **Data quality monitoring**                                                                                                 |                                                                                                                                                                                                                                                                                                                       |                                                                                                                  |
| [Running checks with a scheduler](data-quality-monitoring/running-checks-with-a-scheduler.md)                               | This example shows how to set different schedules on multiple checks.                                                                                                                                                                                                                                                 | [Link](https://console.cloud.google.com/marketplace/product/bigquery-public-data/thelook-ecommerce)              |
| **Data consistency**                                                                                                        |                                                                                                                                                                                                                                                                                                                       |                                                                                                                  |
| [Percent of rows having a string column value in an expected set](data-consistency/percent-of-string-in-set.md)             | This example shows how to verify that the percentage of strings from a set in a column does not fall below a set threshold using [string_value_in_set_percent](../checks/column/strings/string-value-in-set-percent.md) check.                                                                                        | [Link](https://www.kaggle.com/datasets/whenamancodes/student-performance)                                        |
