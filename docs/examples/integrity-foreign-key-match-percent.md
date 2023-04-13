# Foreign key match percent

In this example we will check the data of `bigquery-public-data.census_utility.fips_codes_all` using `foreign_key_match_percent` check.
Our goal is to set up an integrity check on `state_fips_code` column in order to check how many percent of data in this column
matches the values in the indicated by user column in foreign table in this case this is `bigquery-public-data.census_utility.fips_codes_states` column `state_fips_code`.

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
cd examples\bigquery-column-foreign-key-match-percent
..\..\dqo.cmd
```

=== "Linux"
```bash
cd examples/bigquery-column-foreign-key-match-percent
../../dqo
```

=== "MacOS"
```bash
cd examples/bigquery-column-foreign-key-match-percent
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