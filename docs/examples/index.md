# Examples

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

## Running the examples

[Standard DQO installation](../getting-started/installation/installation.md) comes with a set of examples, which can 
be found in the `example/` directory. You can view a complete list of the examples with links to detailed explanation by
scrolling to the bottom of the page.

The example directory contains two configuration files: `connection.dqoconnection.yaml`, which stores the data source
configuration, and `*.dqotable.yaml` file, which stores the columns and tables metadata and checks configuration.

While it is not necessary to manually add the connection in our examples, you can find information on how to do it in the
[Working with DQO section](../working-with-dqo/adding-data-source-connection/index.md).


To run the examples, follow the steps below. 

1. If you have not installed DQO, create an empty catalogue.


2. Open a terminal, navigate to the created directory and clone the DQO repository from [GitHub](https://github.com/dqoai/dqo).

    ```
    git clone https://github.com/dqoai/dqo.git
    ```

3. To run a specific example, go to the directory where it is located. For instance, you can navigate to 
    `example/bigquery-column-bool-true-percent`.  
    
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
 
    After creating a userhome folder, you will be asked whether to log in to the DQO cloud. After typing Y, you will be
    redirected to https://cloud.dqo.ai/registration, where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.
 
    During the first registration, a unique identification code (API Key) will be generated and automatically passed to the DQO application.
    The API Key is now stored in the configuration file.


6. To execute the checks that were prepared in the example, run the following command in DQO Shell:

    ```
    check run
    ```
    You can also execute the checks using the graphic interface.


7. After executing the checks, synchronize the results with your DQO cloud account by running the following command.

    ```
    cloud sync all
    ``` 

8. You can now review the results on the data quality dashboards as described in [getting started section](../getting-started/review-results-on-dashboards/review-results-on-dashboards.md).

## List of the examples

Here is a comprehensive list of examples with links to the relevant documentation section with detailed descriptions.

| **Name of the example**                                                                         | **Description**                                                                                                                                                                                                     | **Link to the dataset description**                                                                              |
|:------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| [Percentage of false values](./bool-false-percent.md)                                           | This example shows how to detect that the percentage of false values remains above a set threshold using [bool_false_percent](../checks/column/bool/false-percent.md) check.                                        | [Link](https://console.cloud.google.com/marketplace/product/federal-communications-commission/fcc-political-ads) |
| [Integrity check between columns in different tables](./integrity-foreign-key-match-percent.md) | This example shows how to check the referential integrity of a column against a column in another table using [foreign-key-match-percent](../checks/column/integrity/foreign-key-match-percent.md) check.           | [Link](https://www.census.gov/library/reference/code-lists/ansi.html)                                            |
| [nulls-not-nulls-count](./nulls-not-nulls-count.md)                                             | Set up a completeness [nulls_not_nulls_cont](../checks/column/nulls/not-nulls-count.md) check on the "measure_name" column are verified  whether the number of not null values does not exceed the indicated        |                                                                                                                  |
| [numeric-valid-latitude-percent](./numeric-valid-latitude-percent.md)                           | Set up a validity [numeric-valid-latitude-percent]() check on the "latitude" column are verified whether the percentage of values within the latitude range does not exceed the indicated thresholds.               |                                                                                                                  |
| [numeric-values-in-range-integers-percent](./numeric-values-in-range-integers-percent.md)       | Set up a reasonableness [values_in_range_integers_percent]() check on the "edition" column are verified whether the percentage of values within the indicated range (2021 - 2022) reaches the indicated thresholds. |                                                                                                                  |
| [pii-valid-usa-zipcode-percent](./pii-valid-usa-zipcode-percent.md)                             | Set up a check on the "incident_zip" column are verified whether the percentage of USA zip-code values reaches the indicated thresholds.                                                                            |                                                                                                                  |
| [string-match-date-regex-percent](./string-match-date-regex-percent.md)                         | Set up a check on the "source_date" column are verified whether the percentage of Set up a check on the indicated format (YYYY-MM-DD) reaches the indicated thresholds.                                             |                                                                                                                  |
| [string-max-length](./string-max-length.md )                                                    | Set up a check on the "measure_name" column are verified whether the length of values does not exceed the indicated thresholds.                                                                                     |                                                                                                                  |
| [uniqueness-duplicate-percent](./uniqueness-duplicate-percent.md)                               | Set up a check on the "unique_key" column are verified  whether the percentage of duplicated values does not exceed the indicated thresholds.                                                                       |                                                                                                                  |
