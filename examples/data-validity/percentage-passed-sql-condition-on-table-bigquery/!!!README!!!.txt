This folder provides you with an example that can assist you in effectively using DQO.
The example is based on datasets that are accessible on Google Cloud (https://cloud.google.com/datasets).

Inside the example "sources" directory, you'll find two configuration files: `connection.dqoconnection.yaml`, which stores
the data source configuration, and `*.dqotable.yaml` file, which stores the columns and tables metadata and checks configuration.

You do not need to add the connection and checks manually for our examples.
For a more detailed explanation of each example, please refer to the Examples section in the documentation.

To use the examples, you need:

- A BiqQuery service account with BigQuery > BigQuery Job User permission. You can create a Google Cloud account for free by visiting https://cloud.google.com/free.
- A working Google Cloud CLI (https://cloud.google.com/sdk/docs/install) if you want to use Google Application Credentials authentication.

Once you have installed the Google Cloud CLI, log in to your GCP account by running: gcloud auth application-default login.

To run the examples, follow the steps below or refer to the Examples section in the documentation.

1. Go to the directory where the example is located and run the command provided below.

    === "Windows"

        ```
        run_dqo
        ```
    === "MacOS/Linux"

        ```
        ./run_dqo
        ```


2. Create DQO `userhome` folder.

    After installation, you will be asked whether to initialize the DQO userhome folder in the default location.
    The userhome folder stores configuration files for data sources and checks as well as sensor and checkout readouts.
    Type Y to create the folder.



3. Login to DQO Cloud.

    To use DQO features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
    must create a DQO cloud account.

    After creating an userhome folder, you will be asked whether to log in to the DQO cloud. After typing Y, you will be
    redirected to https://cloud.dqo.ai/registration, where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.

    During the first registration, a unique identification code (API Key) will be generated and automatically passed to the DQO application.
    The API Key is now stored in the configuration file.


4. To execute the checks that were prepared in the example, run the following command in DQO Shell:

    ```
    dqo> check run
    ```

    For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
    following command:
    ```
    dqo> check run --mode=debug
    ```

    You can also run the check using the graphic interface.


5. After executing the checks, synchronize the results with your DQO cloud account by running the following command.

    ```
    dqo> cloud sync all
    ```

6. You can now review the results on the data quality dashboards.