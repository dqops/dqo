# Installation

## Prerequisites

To use DQO you need:

  - Python version 3.6 or greater (for details see [Python's documentation](https://www.python.org/doc/) and [download sites](https://www.python.org/downloads/)).
  - Ability to install Python packages with pip.
  - Installed JDK software (version 17) and set JAVA_HOME environment variable.

## Install DQO with pip manager

DQO is available on [PyPi repository](https://pypi.org/project/dqoai/). 

If you want to install DQO directly from Git go to [this section](../../working-with-dqo/install-dqo-from-git/install-dqo-from-git.md).

1. Create an empty catalogue where you want to install DQO.
2. Open a terminal, navigate to the created directory, and create a [virtual environment](https://docs.python.org/3/library/venv.html) by running:

    === "Windows"
        ```python
        py -m venv myvenv
        ```
    === "MacOS/Linux"
        ``` python
        python -m venv myvenv
        ```

3. Activate virtual environment by changing directory to `myvenv/Scripts` and run

    === "Windows"
        ```python
        activate.bat
        ```
    === "MacOS/Linux"
        ``` python
        activate
        ```

4. Install DQO via pip manager

    === "Windows"
        ```python
        py -m pip install dqoai
        ```
    === "MacOS/Linux"
        ``` python
        pip install dqoai
        ```

## Start DQO application

1. Run dqo app to finalize the installation. 

    === "Windows"
        ```python
        dqo
        ```
    === "MacOS/Linux"
        ``` python
        ./dqo
        ```

2. Create DQO `userhome` folder.

    After installation, you will be asked whether to initialize the DQO `userhome` folder in the default location. Type Y to create the folder.  
    The `userhome` folder locally stores data such as sensor readouts and checkout results, as well as data source configurations. [You can learn more about data storage here](../../dqo-concepts/data-storage/data-storage.md). 

3. Login to DQO Cloud.
   
    To use DQO features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
    must create a DQO cloud account.

    After creating a userhome folder, you will be asked whether to log in to the DQO cloud. After typing Y, you will be 
    redirected to [https://cloud.dqo.ai/registration](https://cloud.dqo.ai/registration), where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account. 

    During the first registration, a unique identification code (API Key) will be generated and automatically retrieved by DQO application.
    The API Key is now stored in the configuration file. 

4. Open the DQO User Interface Console in your browser by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888) 
    or by copying the link.

## Next step

Now that you have installed and opened DQO, it is time to [connect your first data source](../adding-data-source-connection/adding-data-source-connection.md).