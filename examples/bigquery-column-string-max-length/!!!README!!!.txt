This is a example DQO_USER_HOME folder with an example.
The sample uses BigQuery public datasets hosted in bigquery-public-data public GCP project.
You have to download and install Google Cloud CLI (https://cloud.google.com/sdk/docs/install).
After installing Google Cloud CLI, log in to your GCP account (you can start one for free), by running:
gcloud auth application-default login


Windows
-------
..\..\dqo.cmd


Linux
-------
../../dqo


MacOS
-------
../../dqo

After starting the example, run the following commands in the DQO shell:

dqo> cloud login
This command will let up login or sign up for the cloud.dqo.ai account.

dqo> check run
The data quality checks will be executed.

dqo> cloud sync
The result files will be pushed to cloud.dqo.ai

Now, you can open the browser and navigate to https://cloud.dqo.ai/ and review the sensor results on the dashboards.

