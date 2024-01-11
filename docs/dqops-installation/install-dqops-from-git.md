# Install DQOps from Git

## Prerequisites

To pull **[dqops/dqo](https://github.com/dqops/dqo.git)** project from Git and compile it locally, you need:

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

    === "Windows"
        ```
        echo %JAVA_HOME%
        ```

    === "MacOS/Linux"
        ```
        echo $JAVA_HOME
        ```

## Installation

1.  Create an empty folder. 

2.  Open a terminal, navigate to the created directory and clone the DQOps repository from [GitHub](https://github.com/dqops/dqo).
    
    ```
    git clone https://github.com/dqops/dqo.git
    ```

3.  Change the directory to the one with the downloaded repository and start the compilation by running the following command.

    === "Windows"
        ```
        dqo
        ```
    === "MacOS/Linux"
        ```
        ./dqo
        ```

    The first compilation should take a few minutes. DQOps will be started instantly after it was compiled.

4.  Initialize DQOps `DQOps User Home` folder.

    After installation, you will be asked whether to initialize the DQOps `DQOps User Home` folder in the default location.
    Type Y to initialize the folder content.  
    The `DQOps User Home` folder locally stores data such as sensor readouts and checkout results, as well as data source configurations.
    [You can learn more about data storage here](../dqo-concepts/data-storage/data-storage.md).

    ![Initializing DQOps user home folder](https://dqops.com/docs/images/getting-started/initializing-user-home-folder2.png)

5.  Login to DQOps Cloud.

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

6.  Open the DQOps User Interface Console in your browser by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888)
    or by copying the link. You will see the welcome screen as shown below.

    ![DQOps user interface](https://dqops.com/docs/images/getting-started/dqops-user-interface.png)
