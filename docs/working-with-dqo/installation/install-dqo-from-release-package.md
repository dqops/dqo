# Install DQOps from release package

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


- Installed JDK software (version 17) and set the JAVA_HOME environment variable.

  To confirm that Java is installed on your system, please run this command:
  ```
  java --version
  ```
  A Java version should be displayed. If the above command shows 'java' is not recognized, it means Java is not
  installed or the path is not properly set.

  To ensure that the JAVA_HOME environmental variables are properly set, please run the following command:
  ```
  echo %JAVA_HOME%
  ```

## Installation

1. Create an empty catalogue.

2. Download zipped source code archive from [DQOps GitHub](https://github.com/dqops/dqo). 

    You can learn how to download the source code archives in [GitHub Docs](https://docs.github.com/en/repositories/working-with-files/using-files/downloading-source-code-archives).

3. Extract the content of the zipped file. 

4. Change the directory to the one with the extracted repository and start the compilation by running the following command.

    === "Windows"
        ```
        dqo
        ```
    === "MacOS/Linux"
        ```
        ./dqo
        ```

5. Create DQOps `userhome` folder.

    After installation, you will be asked whether to initialize the DQOps userhome folder in the default location. Type Y to create the folder.  
    The userhome folder locally stores data such as sensor and checkout readings, as well as data source configurations. [You can learn more about data storage here](../../dqo-concepts/data-storage/data-storage.md).

6. Login to DQOps Cloud.

    To use DQOps features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
    must create a DQOps cloud account.
 
    After creating a userhome folder, you will be asked whether to log in to the DQOps cloud. After typing Y, you will be
    redirected to [https://cloud.dqops.com/registration](https://cloud.dqops.com/registration), where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.
 
    During the first registration, a unique identification code (API Key) will be generated and automatically passed to the DQOps application.
    The API Key is now stored in the configuration file.

7. Open the DQOps User Interface Console by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888)
    or by copying the link to your browser.