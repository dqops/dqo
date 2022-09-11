# Value in range date percent 

In this example we will check the data of `bigquery-public-data.covid_italy.national_trends` using
[`current_delay`](../../../check_reference/timeliness/current_delay/current_delay.md) check.
The data is updated daily. Our goal is to set up a timeliness check and verify that the last update was performed
less than 24 hours ago.

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
    cd examples\bigquery-column-value-in-range-date-percent
    ..\..\dqo.cmd
    ```

=== "Linux"
    ```bash
    cd examples/bigquery-column-value-in-range-date-percent
    ../../dqo
    ```

=== "MacOS"
    ```bash
    cd examples/bigquery-column-value-in-range-date-percent
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

Now, you can open the browser and navigate to https://cloud.dqo.ai/ and review the sensor results on the dashboards.

The result files will be pushed to cloud.dqo.ai

Now, you can open the browser and navigate to [https://cloud.dqo.ai/](https://cloud.dqo.ai/)
and review the sensor results on the dashboards.