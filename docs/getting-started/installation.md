---
title: DQOps local installation with pip
---
# DQOps local installation with pip
This guide will tell you how to quickly install DQOps locally from PyPI and create a DQOps Cloud account to enable the data quality dashboards.

## Overview
DQOps is available in the  [PyPi repository](https://pypi.org/project/dqops/) as a `dqops` package. 
You can also look at the [installation of DQOps using `pip`](../dqops-installation/install-dqops-using-pip.md),
if you have any issues when installing DQOps using a simplified installation process described in this article.

You can also [start DQOps as a docker container](../dqops-installation/run-dqops-as-docker-container.md), which is
a preferred option for production installation.


## Prerequisites

To start DQOps locally, you need:

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


## Install DQOps with pip manager

1.  Create an **empty folder** that will be used to store the configuration. 
    This folder is called the **`DQOps user home`** and is documented in the
    [DQOps user home concept](../dqo-concepts/dqops-user-home-folder.md) guide.
    DQOps will store the configuration of data sources, configured data quality checks and the data quality check
    results in this folder.

    !!! danger "Starting DQOps in an empty folder is required"

        Please do not go directly to the next step. First, create an empty folder, then change the current folder
        to this new new directory that we are calling `DQOps User Home`. During the first run, DQOps will create
        the folder structure described in the [DQOps user home folder concept](../dqo-concepts/dqops-user-home-folder.md),
        creating DQOps files in an empty folder is advised.

        ```bash
        mkdir dqouserhome
        cd dqouserhome
        ```

2.  Open a terminal, change the working folder to our `DQOps user home`, and install DQOps via pip manager

    ```
    python -m pip install --user dqops
    ```


## Start DQOps command-line shell

1.  Run `dqops` Python package to finalize the installation. This process may take some time as the DQOps distribution will be downloaded
    and the Python environment will be initialized in the DQOps home directory. DQOps will also download and install Adoptium Java JDK 17.

    ```
    python -m dqops
    ```

    DQOps will download the full [DQOps release package](../dqops-installation/install-dqops-from-release-package.md)
    from GitHub, or some alternative mirrors.

    When the download of the DQOps distribution starts (a ~400MB zip file), you should see the progress.

    ![dqops starts downloading full distribution](https://dqops.com/docs/images/getting-started/dqops-download-by-pip-started-min.png)

    After the distribution is downloaded and unzipped, DQOps will also download and install Java JRE 17.
    Once it is done, you should see the following screen.

    ![dqops finishes downloading full distribution](https://dqops.com/docs/images/getting-started/dqops-download-by-pip-finished-min.png)

    In case that download stops due to network issues, DQOps will retry the download, or use an alternative mirror.

2.  Initialize your `DQOps user home` folder.

    After installation, you will be asked whether to initialize the `DQOps user home` folder in the default location. 
    This is the empty folder mentioned before in which you started DQOps. 
    Type Y to initialize the folder structure. Please remember that the current working folder must be empty.  

    ![Initializing DQOps user home folder](https://dqops.com/docs/images/getting-started/initializing-user-home-folder2.png)

3.  Login to DQOps Cloud.
   
    To use all DQOps features, such as storing data quality definitions and results in the cloud
    and use [data quality dashboards](../dqo-concepts/types-of-data-quality-dashboards.md), you
    must create a DQOps Cloud account.
 
    !!! info "Registration of a DQOps Cloud account is optional"

        You can skip this step and start DQOps without registering a DQOps Cloud account.
        However, having a DQOps Cloud is required to use [data quality dashboards](../dqo-concepts/types-of-data-quality-dashboards.md) and activate SSO login. 
        
        All other DQOps features will work fine because DQOps is an open-source data quality solution.
 
        The open-source version supports the following features for free:
 
         - Data quality checks for all supported [data sources](../data-sources/index.md). There is no paywall to limit access to data sources for enterprise data sources.

         - The user interface for [managing data sources and configuring data quality checks](../dqo-concepts/dqops-user-interface-overview.md) works locally.

         - [Data quality REST API and Python Client](../client/index.md) that you can integrate into the data pipelines.

         - DQOps is the only [data observability platform that supports anomaly detection](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) for free because [historical data quality results](../dqo-concepts/data-storage-of-data-quality-results.md) are stored locally on your machine.

    Once the `DQOps user home` folder is initialized, DQOps will ask you to log in to your DQOps Cloud account.

    ![Log in to DQOps Cloud](https://dqops.com/docs/images/getting-started/log-in-to-dqops-cloud3.png)

    After typing Y, DQOps will open your browser and navigate to the DQOps Cloud registration page.
    If the browser did not open correctly, you can copy the link from the console and open it in your browser of choice.
    The DQOps Cloud account registration page is [https://cloud.dqops.com/registration](https://cloud.dqops.com/registration).

    You can create your account by signing in with your Google account or use a custom email/password authentication.
    You can also log in to your existing DQOps Cloud account.

    During the first registration, DQOps Cloud will generate your DQOps Cloud Pairing Key that is used to connect your local
    DQOps instance to the [data quality dashboards](../dqo-concepts/types-of-data-quality-dashboards.md).
    You don't need to copy the DQOps Cloud Pairing Key. DQOps is smart enough to pick the DQOps Cloud API Key for you.

    ![DQOps API Key retrieved](https://dqops.com/docs/images/getting-started/dqops-api-key-retrieved.png)

    In case that you cancelled the registration procedure, you can copy the DQOps Cloud Pairing Key and configure your DQOps instance
    by using the following command from the [DQOps command-line shell](../dqo-concepts/command-line-interface.md)

    ```
    dqo> settings apikey set <your DQOps Cloud Pairing API Key>
    ```

4.  Open the DQOps User Interface Console in your browser by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888) 
    or by copying the link. You will see the welcome screen as shown below. 

    ![DQOps user interface](https://dqops.com/docs/images/getting-started/dqops-user-interface.png){ loading=lazy; width="1200px" }

## Next step

Now that you have installed and opened DQOps, it is time to
[connect your first data source](add-data-source-connection.md).

## Other installation options
- [Install as a Python package](../dqops-installation/install-dqops-using-pip.md) describes the process of installing DQOps from a PyPI package in details.
- [Run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md) shows how to run DQOps in Docker, it is the recommended way for running DQOps for production use.
- You can [download a DQOps release package](../dqops-installation/install-dqops-from-release-package.md), unzip it and run directly on your machine.
- You can also check out [DQOps from GitHub and compile it from the source code](../dqops-installation/install-dqops-from-github.md).
