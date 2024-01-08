# Download DQOps using PIP

## Overview

DQOps is available as a Python package [dqops](https://pypi.org/project/dqops/). This package contains a bootstrapper that
will download a full DQOps distribution from [DQO GitHub](https://github.com/dqops/dqo) and install a Java JRE 17 in a version
specific to the current platform. DQOps runs on Windows, Linux and MacOS. Both x64 and arm8 platforms are supported.

!!! note "Running DQOps as a server"

    DQOps python package is a quick option to run a local, non-production instance.
    Please choose a [Docker distribution](run-dqo-as-docker-container.md) to run DQOps as a long-running production instance
    that can monitor the data quality at all times.


## Prerequisites

To use DQOps you need:

  - Python version 3.8 or greater (for details see [Python's documentation](https://www.python.org/doc/) and [download sites](https://www.python.org/downloads/)).

    To confirm that you have Python installed run the following command
    ```
    python --version
    ```
    To view the Python 3 version, run `python3 --version` instead.



  - Ability to install Python packages with pip.
    
    To confirm that you have pip installed run the following command
    ```
    pip --version
    ```
    Current version of pip should be displayed.


## Installation

DQOps is available on [PyPi repository](https://pypi.org/project/dqops/). 

1. Create an empty folder where you want to create your `DQOps User Home`. `DQOps User Home` is a folder where
   DQOps will store the metadata of imported data sources, the configuration of activated data quality checks, and the
   data quality results.

2. Open a terminal, change the current folder to the created directory, and install DQOps via pip manager

    ```
    python -m pip install --user dqops
    ```

## Start DQOps application

1. Run DQOps to finalize the installation. This process may take some time as the DQOps package must download a full DQOps
   distribution and also install Java JRE 17.

    ```
    python -m dqops
    ```

2. Initialize DQOps `DQOps User Home` folder.

    After installation, you will be asked whether to initialize the DQOps `DQOps User Home` folder in the default location.
    Type Y to initialize the folder content.  
    The `DQOps User Home` folder locally stores data such as sensor readouts and checkout results, as well as data source configurations.
    [You can learn more about data storage here](../../dqo-concepts/data-storage/data-storage.md). 

    ![Initializing DQOps user home folder](https://dqops.com/docs/images/getting-started/initializing-user-home-folder2.png)

3. Login to DQOps Cloud.
   
    To use all DQOps features, such as storing data quality definitions and results in the cloud and the data quality dashboards, you
    must create a DQOps Cloud account.

    Once the `DQOps user home` folder is initialized, DQOps will ask you to log in to your DQOps Cloud account. 

    ![Log in to DQOps Cloud](https://dqops.com/docs/images/getting-started/log-in-to-dqops-cloud3.png)

    After typing Y, you will be redirected to [https://cloud.dqops.com/registration](https://cloud.dqops.com/registration), 
    where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account. 

    During the first registration, your DQOps Cloud pairing API key will be generated and automatically retrieved by DQOPS application.
    The API Key is now stored in the configuration file. You can close the browser window that shows your
    [https://cloud.dqops.com/](https://cloud.dqops.com/) account. Since now on, your local DQOps instance is fully functional
    and can work mostly offline.

    ![DQOps API Key retrieved](https://dqops.com/docs/images/getting-started/dqops-api-key-retrieved.png)

4. Open the DQOps User Interface Console in your browser by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888) 
    or by copying the link. You will see the welcome screen as shown below.

    ![DQOps user interface](https://dqops.com/docs/images/getting-started/dqops-user-interface.png)
