# Current delay

In this example we will check the data timeliness of `bigquery-public-data.austin_crime.crime` using
[`average_delay`](../../../check_reference/timeliness/average_delay/average_delay.md) check.
Our goal is to set up a timeliness check and verify average timestamp difference between two columns.

## Adding connection
### GCP
You have to download and install [Google Cloud CLI](https://cloud.google.com/sdk/docs/install).
After installing Google Cloud CLI, log in to your GCP account (you can start one for free), by running:

```commandline
gcloud auth application-default login
```

Navigate to the example directory and run the check
=== "Windows"
    ```commandline
    cd examples\bigquery-table-average-delay
    ..\..\dqo.cmd
    ```

=== "Linux"
    ```bash
    cd examples/bigquery-table-average-delay
    ../../dqo
    ```

=== "MacOS"
    ```bash
    cd examples/bigquery-table-average-delay
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

Now, you can open the browser and navigate to https://cloud.dqo.ai/ and review the sensor results on the dashboards.

The result files will be pushed to cloud.dqo.ai

Now, you can open the browser and navigate to [https://cloud.dqo.ai/](https://cloud.dqo.ai/)
and review the sensor results on the dashboards.