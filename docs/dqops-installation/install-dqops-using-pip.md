# Download DQOps using PIP
This guide shows how to quickly download DQOps from PyPi, and start a local development instance to analyze data quality or use the DQOps Client. 

## Overview

DQOps is available as a Python package [dqops](https://pypi.org/project/dqops/). This package contains a bootstrapper that
will download a full DQOps distribution from [DQO GitHub](https://github.com/dqops/dqo) and install a Java JRE 17 in a version
specific to the current platform. DQOps runs on Windows, Linux and MacOS. Both x64 and arm8 platforms are supported.

!!! note "Running DQOps as a server"

    DQOps python package is a quick option to run a local, non-production instance.
    Please choose a [Docker distribution](./run-dqops-as-docker-container.md) to run DQOps as a long-running production instance
    that can monitor the data quality at all times.


## Prerequisites

To use DQOps you need:

  - Python version 3.8 or greater (for details see [Python's documentation](https://www.python.org/doc/) and [download sites](https://www.python.org/downloads/)).

    To confirm that you have Python installed run the following command
  
    ```bash
    python --version
    ```
    
    To view the Python 3 version, run `python3 --version` instead.



  - Ability to install Python packages with pip.
    
    To confirm that you have pip installed run the following command

    ```bash
    pip --version
    ```
    
    Current version of pip should be displayed.


## Installation

DQOps is available on [PyPi repository](https://pypi.org/project/dqops/). 

1.  Create an empty folder where you want to create your `DQOps User Home`. `DQOps User Home` is a folder where
    DQOps will store the metadata of imported data sources, the configuration of activated data quality checks, and the
    data quality results.

    !!! danger "Starting DQOps in an empty folder is required"

        Please do not go directly to the next step. First, create an empty folder, then change the current folder
        to this new new directory that we are calling `DQOps User Home`. During the first run, DQOps will create
        the folder structure described in the [DQOps user home folder concept](../dqo-concepts/dqops-user-home-folder.md),
        creating DQOps files in an empty folder is advised.

         ```bash
         mkdir dqouserhome
         cd dqouserhome
         ```

2.  <span id="install-step-2">Open a terminal</span>, change the current folder to the created directory, and install DQOps via pip manager

    ```bash
    python -m pip install --user dqops
    ```

    !!! info "Upgrading already installed DQOps package"

        If you already have the `dqops` package installed in an earlier version, you can upgrade it to the most
        recent version using a `--upgrade` parameter as shown below.

        ```bash
        python -m pip install --upgrade --user dqops
        ```

3.  Verify if the installation was successful. 

    You should see a message that confirms a successful installation of the `dqops` package.

    ```asc
    Successfully installed dqops-1.0.0
    ```

    If you see additional two warning (shown in yellow on Microsoft Windows) that the default location
    where scripts are added is not included in the `PATH` environment variable, copy the path
    shown in the window (it was *C:\Users\&lt;yourlogin&gt;\AppData\Roaming\Python\Python39\Scripts* it the example below),
    and add it to the default `$PATH` environment variable. After changing the environment variables, you will need to open
    a new Command Prompt window that uses the new configuration.

    ``` { .asc hl_lines="1-2" }
    WARNING: The script dqo.exe is installed in 'C:\Users\<yourlogin>\AppData\Roaming\Python\Python39\Scripts' which is not on PATH.
    Consider adding this directory to PATH or, if you prefer to suppress this warning, use --no-warn-script-location.
    Successfully installed dqops-1.0.0
    ```

    After adding the Python's Scrips folder to the path, you will be able to run DQOps simply by typing `dqo` from the command line. 


## Solving certificate issues
You may receive an issue that the `dqops` package was not found, as shown below.

```
ERROR: Could not find a version that satisfies the requirement dqops (from version: none)
ERROR: No matching distribution found for dqops
```

The problem is related to an outdated configuration of root certificates that are bundled with Python.
Use the following command to add the host names of download locations to the list of trusted hosts.

```bash
python -m pip config set global.trusted-host "pypi.org files.pythonhosted.org pypi.python.org"
```

After adding the *PyPi* host names to the list of trusted locations, repeat the installation from the [2. step](#install-step-2).


## Start DQOps application

1.  Run DQOps to finalize the installation. This process may take some time as the DQOps package must download a full DQOps
    distribution and also install Java JRE 17.

    ```bash
    python -m dqops
    ```

    !!! note "Start DQOps using a startup script"

        DQOps package registers also a `dqo` script on the *PATH*. You can start DQOps simply by running a `dqo` command,
        if the Python location is correctly configured on the *PATH* variable.

        If you are lucky to have a proper Python installation, you can start DQOps simply from the command line as shown below.

        === "Windows"
            ```
            c:\users\<yourlogin>\dqouserhome> dqo
            ```

        === "MacOS/Linux"
            ```
            /home/<yourlogin>/dqouserhome$ dqo
            ```

2.  Initialize DQOps `DQOps User Home` folder.

    After installation, you will be asked whether to initialize the DQOps `DQOps User Home` folder in the default location.
    Type Y to initialize the folder content.  
    The `DQOps User Home` folder locally stores data such as sensor readouts and checkout results, as well as data source configurations.
    Read the [DQOps user home folder concept](../dqo-concepts/dqops-user-home-folder.md) to learn more.

    ![Initializing DQOps user home folder](https://dqops.com/docs/images/getting-started/initializing-user-home-folder2.png)

3.  Login to DQOps Cloud.
   
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

4.  Open the DQOps User Interface Console in your browser by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888) 
    or by copying the link. You will see the welcome screen as shown below.

    ![DQOps user interface](https://dqops.com/docs/images/getting-started/dqops-user-interface.png)


## What's next
- Read the installation manual of [starting DQOps as a docker container](run-dqops-as-docker-container.md), which is a preferred
  way to run DQOps in production mode, allowing other users and stakeholders to access the platform.
