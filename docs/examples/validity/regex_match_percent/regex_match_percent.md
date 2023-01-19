# Regex match percent

In this example we will check the data of `bigquery-public-data.austin_311.311_service_requests` using
[`regex_match_percent`](../../../check_reference/validity/regex_match_percent/regex_match_percent.md) check.
Our goal is to set up a validity check on `unique_key` column in order to check how many percent of data are correct.
In our case, we check how many unique_key from dataset have correct regex.

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
    cd examples\bigquery-column-regex-match-percent
    ..\..\dqo.cmd
    ```

=== "Linux"
    ```bash
    cd examples/bigquery-column-regex-match-percent
    ../../dqo
    ```

=== "MacOS"
    ```bash
    cd examples/bigquery-column-regex-match-percent
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