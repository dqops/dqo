# Column datetime difference percent

In this example - check the data timeliness of
`bigquery-public-data.austin_crime.crime` using
[`column_datetime_difference_percent`](../../../check_reference/timeliness/column_datetime_difference_percent/column_datetime_difference_percent.md) check.
The goal is to set up a timeliness check and verify percent timestamp difference between two columns.

## Adding connection
### GCP
Download and install [Google Cloud CLI](https://cloud.google.com/sdk/docs/install).
After installing Google Cloud CLI, log in to your GCP account (you can start one for free), by running:

```commandline
gcloud auth application-default login
```

Navigate to the example directory and run the check

After setting up the GCP account, create a GCP project. That will be the GCP billing project used to run SQL sensors
on the public datasets provided by Google.

The examples are using the name of the GCP billing project, received as an environment variable `GCP_PROJECT`.
Set and export this variable before starting DQO shell.

=== "Windows"
    ```commandline
    cd examples\bigquery-table-column-datetime-difference-percent
    ..\..\dqo.cmd
    ```

=== "Linux"
    ```bash
    cd examples/bigquery-table-column-datetime-difference-percent
    ../../dqo
    ```

=== "MacOS"
    ```bash
    cd examples/bigquery-table-column-datetime-difference-percent
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
cloud sync
```
The result files will be pushed to cloud.dqo.ai

Now, you can open the browser and navigate to [https://cloud.dqo.ai/](https://cloud.dqo.ai/)
and review the sensor results on the dashboards.