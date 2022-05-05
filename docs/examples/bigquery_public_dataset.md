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

##