### GCP
You have to download and install [Google Cloud CLI](https://cloud.google.com/sdk/docs/install).
After installing Google Cloud CLI, log in to your GCP account (you can start one for free), by running:

```commandline
gcloud auth application-default login
```

After setting up your GCP account, create your GCP project. That will be your GCP billing project
used to run SQL sensors on the public datasets provided by Google.

The examples are using a name of your GCP billing project, received as an environment variable GCP_PROJECT.
You have to set and export this variable before starting DQO shell.

=== "Windows"
    ```commandline
    set GCP_PROJECT={here is your GCP billing project}
    ..\..\dqo.cmd
    ```

=== "Linux"
    ```bash
    export GCP_PROJECT={here is your GCP billing project}
    ../../dqo
    ```

=== "MacOS"
    ```bash
    export GCP_PROJECT={here is your GCP billing project}
    ../../dqo
    ```

After starting the example, run the following commands in the DQO shell:
```bash
dqo.ai> cloud login
```
This command will let up login or sign up for the cloud.dqo.ai account.

```bash
dqo> check run
```
The data quality checks will be executed.
```bash
dqo> cloud sync
```
The result files will be pushed to cloud.dqo.ai

Now, you can open the browser and navigate to [https://cloud.dqo.ai/](https://cloud.dqo.ai/)
and review the sensor results on the dashboards.

## Bigquery public dataset

In this example we will set up data quality checks on austin_crime.crime table which can be found in
bigquery-public-data.

Let's add a connection to get access to the table 

```bash
dqo.ai> connection add -n=austin_crime -t=bigquery
Source GCP project ID (-Pbigquery-source-project-id) [dqo-ai]: bigquery-public-data
Billing GCP project ID (-Pbigquery-billing-project-id) [dqo-ai]: dqo-ai-testing
GCP Authentication Mode [google_application_credentials]:
 [ 1] google_application_credentials (default)
 [ 2] json_key_content
 [ 3] json_key_path
Please enter one of the [] values: 1
GCP quota (billing) project ID (-Pbigquery-quota-project-id) [dqo-ai-testing]: dqo-ai-testing
Connection austin_crime was successfully added.
Run 'table import -c=austin_crime' to import tables.
dqo.ai> table import -c=austin_crime
Select the schema (database, etc.) from which tables will be imported:
  [  1]  austin_311
  [  2]  austin_bikeshare
  [  3]  austin_crime
  [  4]  austin_incidents
  [  5]  austin_waste
   ...
  [187]  world_bank_intl_education
  [188]  world_bank_wdi
  [189]  worldpop
Please enter one of the [] values: 3

The following tables were imported:
+------------+----------+------------+
|Schema name |Table name|Column count|
+------------+----------+------------+
|austin_crime|crime     |18          |
+------------+----------+------------+
```
