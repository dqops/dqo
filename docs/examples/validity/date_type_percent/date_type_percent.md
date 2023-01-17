# Date type percent

In this example we will check the data of `bigquery-public-data.labeled_patents.extracted_data` using
[`date_type_percent`](../../../check_reference/validity/date_type_percent/date_type_percent.md) check.
A column `publication_date` is `STRING` type and is formatted: `day.month.year`. We would like to test this column
and see the amount of records that satisfy this format.

## Adding connection
### GCP
You have to download and install [Google Cloud SDK](https://cloud.google.com/sdk/docs/install).
After installing Google Cloud CLI, log in to your GCP account (you can start one for free), by running:

```commandline
gcloud auth application-default login
```

Navigate to the example directory and run the check
=== "Windows"
    ```commandline
    cd examples/bigquery-column-date-type-percent
    ..\..\dqo.cmd
    ```

=== "Linux"
    ```bash
    cd examples/bigquery-column-date-type-percent
    ../../dqo
    ```

=== "MacOS"
    ```bash
    cd examples/bigquery-column-date-type-percent
    ../../dqo
    ```

After starting the example, run the following commands in the DQO shell:
```bash
cloud login
```
This command will let up login or sign up for the cloud.dqo.ai account.

```bash
check run
```
The data quality checks will be executed.
```bash
cloud sync data
```

The result files will be pushed to cloud.dqo.ai

Now, you can open the browser and navigate to [https://cloud.dqo.ai/](https://cloud.dqo.ai/)
and review the sensor results on the dashboards.