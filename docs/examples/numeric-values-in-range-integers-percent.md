# Values in range integers percent

In this example we will check the data of `bigquery-public-data.america_health_rankings.ahr` using `values_in_range_integers_percent` check.
Our goal is to set up a reasonableness check on `edition` column in order to check how many percent of values
are within the indicated by the user range, in this case this is `2021 - 2022`.

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
cd examples\bigquery-column-values-in-range-integers-percent
..\..\dqo.cmd
```

=== "Linux"
```bash
cd examples/bigquery-column-values-in-range-integers-percent
../../dqo
```

=== "MacOS"
```bash
cd examples/bigquery-column-values-in-range-integers-percent
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