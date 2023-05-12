# Install DQO from Git

## Prerequisites

To use DQO you need:

  - Python version 3.6 or greater (for details see [Python's documentation](https://www.python.org/doc/) and [download sites](https://www.python.org/downloads/)).
  - JDK 17 and a java compiler

## Installation

1. Create an empty catalogue. 
2. Open a terminal, navigate to the created directory and clone the DQO repository from [GitHub](https://github.com/dqoai/dqo).
    
    ```
    git clone https://github.com/dqoai/dqo.git
    ```

3. Change the directory to the one with the downloaded repository and start the compilation by running the following command.

    === "Windows"
        ```
        dqo
        ```
    === "MacOS/Linux"
        ```
        ./dqo
        ```

4. Create DQO `userhome` folder.

   After installation, you will be asked whether to initialize the DQO userhome folder in the default location. Type Y to create the folder.  
   The userhome folder locally stores data such as sensor and checkout readings, as well as data source configurations. [You can learn more about data storage here](../../dqo-concepts/data-storage/data-storage.md).

5. Login to DQO Cloud.

   To use DQO features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
   must create a DQO cloud account.

   After creating a userhome folder, you will be asked whether to log in to the DQO cloud. After typing Y, you will be
   redirected to https://cloud.dqo.ai/registration, where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.

   During the first registration, a unique identification code (API Key) will be generated and automatically passed to the DQO application.
   The API Key is now stored in the configuration file.

6. Open the DQO User Interface Console by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888)
   or by copying the link to your browser.