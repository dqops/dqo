# Use cases

We have provided a variety of examples to help you in using DQOps effectively. These examples use openly available 
datasets from [Google Cloud](https://cloud.google.com/datasets).

You can find the full list of examples at the bottom of this article. 

## Prerequisite

To use the examples you need:

- [Installed DQOps](../getting-started/installation/installation.md).
- A BiqQuery service account with **BigQuery > BigQuery Job User** permission. [You can create a free trial Google Cloud account here](https://cloud.google.com/free).
- A working [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) if you want to use [Google Application Credentials authentication](../working-with-dqo/adding-data-source-connection/bigquery/#using-google-application-credentials-authentication).).

After installing Google Cloud CLI, log in to your GCP account, by running:

```
gcloud auth application-default login
```

## Running the use cases

[Standard DQOps installation](../getting-started/installation/installation.md) comes with a set of examples, which can 
be found in the `example/` directory. You can view a complete list of the examples with links to detailed explanation by
scrolling to the bottom of the page.

The example directory contains two configuration files: `connection.dqoconnection.yaml`, which stores the data source
configuration, and `*.dqotable.yaml` file, which stores the columns and tables metadata and checks configuration.

While it is not necessary to manually add the connection in our examples, you can find information on how to do it in the
[Working with DQOps section](../working-with-dqo/adding-data-source-connection/index.md).

## Running the use cases

To run the examples, follow the steps below. 

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

    After installation, you will be asked whether to initialize the [DQOps user home folder](../dqo-concepts/home-folders/dqops-user-home.md) in the default location. Type Y to create the folder. . 

    ![Initializing DQOps user home folder](https://dqops.com/docs/images/getting-started/initializing-user-home-folder2.png)

    The DQOps user home folder locally stores data such as sensor and checkout readings, as well as data source configurations. [You can learn more about data storage here](../dqo-concepts/data-storage/data-storage.md).


3. Login to DQOps Cloud.

    To use DQOps features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
    must create a DQOps cloud account.
 
    After creating the [DQOps user home folder](../dqo-concepts/home-folders/dqops-user-home.md), you will be asked whether to log in to the DQOps cloud. 

    ![Log in to DQOps Cloud](https://dqops.com/docs/images/getting-started/log-in-to-dqops-cloud3.png)

    After typing Y, you will be redirected to [https://cloud.dqops.com/registration](https://cloud.dqops.com/registration), where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.
 
    During the first registration, a unique identification code (API Key) will be generated and automatically passed to the DQOps application.
    The API Key is now stored in the configuration file.

### **Executing the checks using the user interface**

You can execute the checks using the [user interface](../dqo-concepts/user-interface-overview/user-interface-overview.md).
Simply, open the DQOps User Interface Console (http://localhost:8888).

![Navigating to a list of checks](https://dqops.com/docs/images/examples/row-count-navigating-to-the-list-of-checks.png)

1. Go to the **Profiling** section.

    The Profiling section enables the configuration of advanced profiling data quality checks that are designed for the initial evaluation of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Profiling Checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../../dqo-concepts/user-interface-overview/user-interface-overview/#check-editor). On **Profiling** section, there is also a second tab [Basic data statistics](../working-with-dqo/basic-data-statistics/basic-data-statistics.md) that allows you to collect summary information about your tables and columns.


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/row-count-run-check.png)


5. Access the results by clicking the **Results** button and review it.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.

    ![Check details](https://dqops.com/docs/images/examples/row-count-check-details.png)

6. Review the results

    To see the results which you should expect, refer to the description of each example.


7. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

8. You can now [review the results on the data quality dashboards](../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md) as described in the Working with DQOps section.


### **Executing the checks using the DQOps Shell**

You can also execute the checks that were prepared in the example, using the DQOps Shell. 

1. Just run the following command in DQOps Shell:

    ```
    check run
    ```
    
    A summary table with the results will be displayed. To see the results which you should expect, refer to the description of each example.


2. After executing the checks, synchronize the results with your DQOps cloud account by running the following command.

    ```
    cloud sync all
    ``` 

3. You can now [review the results on the data quality dashboards](../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md) as described in the Working with DQOps section.


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
| [Percentage of rows containing USA zipcodes](data-validity/percentage-of-values-that-contains-usa-zipcode.md)               | This example shows how to detect that the percentage of rows that contains USA zipcode values in a column does not exceed a set threshold using [contains_usa_zipcode_percent](../checks/column/pii/contains-usa-zipcode-percent.md) check.                                                                           | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he)                |
| [Number of invalid emails](data-validity/number-of-invalid-emails.md)                                                       | This example shows how to detect that the number of invalid emails in a column does not exceed the maximum accepted count using [string_invalid_email_count](../checks/column/strings/string-invalid-email-count.md) check.                                                                                           | DQOps dataset                                                                                                    |
| [Percentage of valid latitude and longitude](data-validity/percentage-of-valid-latitude-and-longitude.md)                   | This example shows how to detect that the percentage of valid latitude and longitude values remain above a set threshold using [numeric_valid_latitude_percent](../checks/column/numeric/valid-latitude-percent.md) and [numeric_valid_longitude_percent](../checks/column/numeric/valid-longitude-percent.md)checks. | [Link](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he)                |
| [Number of invalid IP4 address](data-validity/number-of-invalid-IP4-address.md)                                             | This example shows how to detect that the number of invalid IP4 address in a column does not exceed a set threshold using [string_invalid_ip4_address_count](../checks/column/strings/string-invalid-ip4-address-count.md) check.                                                                                     | DQOps dataset                                                                                                    |
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
