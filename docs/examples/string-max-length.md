# String max length

In this example we will check the data of `bigquery-public-data.america_health_rankings.ahr` using `string_max_length` check.
Our goal is to set up a reasonableness check on `measure_name` column in order to check whether the length of values does not exceed the indicated by the user thresholds.
In this case this is:
    error:
        max_value: 5.0
    warning:
        max_value: 6.0
    fatal:
        max_value: 8.0

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
cd examples\bigquery-column-string-max-length
..\..\dqo.cmd
```

=== "Linux"
```bash
cd examples/bigquery-column-string-max-length
../../dqo
```

=== "MacOS"
```bash
cd examples/bigquery-column-string-max-length
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