# Numerical type percent

In this example we will check the data of `bigquery-public-data.austin_311.311_service_requests` using
[`numerical_type_percent`](/check_reference/validity/numerical_type_percent/numerical_type_percent/) check.
Our goal is to set up a validity check on `street_number` column in order to check how many records of data have numerical type.

## Adding connection
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
    ```
    
=== "Linux"
    ```bash
    export GCP_PROJECT={here is your GCP billing project}
    ```

=== "MacOS"
    ```bash
    export GCP_PROJECT={here is your GCP billing project}
    ```

Navigate to the example directory and run the check
=== "Windows"
    ```commandline
    cd examples\bigquery-column-numerical-type-percent
    ..\..\dqo.cmd
    ```

=== "Linux"
    ```bash
    cd examples/bigquery-column-numerical-type-percent
    ../../dqo
    ```

=== "MacOS"
    ```bash
    cd examples/bigquery-column-numerical-type-percent
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