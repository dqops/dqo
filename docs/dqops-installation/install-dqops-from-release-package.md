# Install DQOps from release package
This guide shows how to download a DQOps release package that is available as a .zip package on GitHub. You can set up your DQOps installation for your needs.

## Prerequisites

To use DQOps you need:

-  Python version 3.8 or greater (for details see [Python's documentation](https://www.python.org/doc/) and [download sites](https://www.python.org/downloads/)).

   To confirm that you have Python installed run the following command
  
   ```
   python --version
   ```
  
   To view the Python 3 version, run `python3 --version` instead.



-  Ability to install Python packages with pip.

   To confirm that you have pip installed run the following command

   ```
   pip --version
   ```
  
   Current version of pip should be displayed.


-  Installed JDK software (version 17) and set the JAVA_HOME environment variable.

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

1.  Create an empty catalogue.

2.  Download zipped source code archive from [DQOps GitHub releases](https://github.com/dqops/dqo/releases). 

    You can learn how to download the source code archives in [GitHub Docs](https://docs.github.com/en/repositories/working-with-files/using-files/downloading-source-code-archives).

3.  Extract the content of the zipped file. 

4.  Change the directory to the one with the extracted repository and start the compilation by running the following command.

    === "Windows"
        ```
        dqo
        ```
    === "MacOS/Linux"
        ```
        ./dqo
        ```

5.  Create DQOps `DQOps user home` folder.

    After installation, you will be asked whether to initialize the [DQOps user home folder](../dqo-concepts/dqops-user-home-folder.md) in the default location. Type Y to create the folder.  
    The DQOps user home folder locally stores data such as sensor and checkout readings, as well as data source configurations.
    Read the [DQOps user home folder concept](../dqo-concepts/dqops-user-home-folder.md) to learn more.

    ![Initializing DQOps user home folder](https://dqops.com/docs/images/getting-started/initializing-user-home-folder2.png)

6.  Login to DQOps Cloud.

    To use DQOps features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
    must create a DQOps cloud account.

    Once the `DQOps user home` folder is initialized, DQOps will ask you to log in to your DQOps Cloud account. 

    ![Log in to DQOps Cloud](https://dqops.com/docs/images/getting-started/log-in-to-dqops-cloud3.png)
 
    After typing Y, you will be redirected to [https://cloud.dqops.com/registration](https://cloud.dqops.com/registration), where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.
 
    During the first registration, a unique identification code (API Key) will be generated and automatically passed to the DQOps application.
    The API Key is now stored in the configuration file.

    ![DQOps API Key retrieved](https://dqops.com/docs/images/getting-started/dqops-api-key-retrieved.png)

7.  Open the DQOps User Interface Console by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888/)
    or by copying the link to your browser. You will see the welcome screen as shown below.

    ![DQOps user interface](https://dqops.com/docs/images/getting-started/dqops-user-interface.png){ loading=lazy; width="1200px" }
