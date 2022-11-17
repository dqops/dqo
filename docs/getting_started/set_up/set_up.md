This section describes how to install and run DQO.ai

Since DQO.ai is available on:

- [PyPi](https://pypi.org/project/dqoai/)

- [GitHub](https://github.com/dqoai/dqo)

- it is also possible to download a [ZIP file](https://github.com/dqoai/dqo/releases/tag/v0.1.0)

feel free to navigate between chapters and choose the most suitable method for you.

### PyPi
DQO.ai is available on [PyPi repository](https://pypi.org/project/dqoai/).

!!! Info "Prerequisites"
    You will need `python >= 3.6`.

Create an empty catalogue. Open a terminal in the directory with the catalogue just created and create a [virtual environment](https://docs.python.org/3/library/venv.html) by running

=== "Windows"
    ```python
    py -m venv myvenv
    ```

=== "MacOS"
    ``` python
    python -m venv myvenv
    ```
=== "Linux"
    ``` python
    python -m venv myvenv
    ```
which will create a virtual environment named `myvenv` in the directory.

Once it is created, activate it by changing directory to `myvenv/Scripts` and running
=== "Windows"
    ```python 
    activate.bat
    ```
=== "MacOS"
    ``` python 
    activate
    ```
=== "Linux"
    ```python 
    activate
    ```

Now install DQO.ai via pip manager

=== "Windows"
    ```python
    py -m pip install dqoai
    ```
=== "MacOS"
    ``` python
    pip install dqoai
    ```
=== "Linux"
    ``` python
    pip install dqoai
    ```

and finally run

=== "Windows"
    ```python
    dqo
    ```
=== "MacOS"
    ``` python
    ./dqo
    ```
=== "Linux"
    ``` python
    ./dqo
    ```

this will download required files from GitHub. Here is what it should look like on Windows with virtual env called `dqoai`:

```
(dqoai) C:\dqoai>dqo
Trying to download DQO.ai version 0.1.0 from https://github.com/dqoai/dqo/releases/download/v0.1.0/dqo-distribution-0.1.0-bin.zip
Downloaded 1048576 of 223346570 bytes (0.47%)
Downloaded 2097152 of 223346570 bytes (0.94%)
Downloaded 3145728 of 223346570 bytes (1.41%)
...
Downloaded 222298112 of 223346570 bytes (99.53%)
Downloaded 223346570 of 223346570 bytes (100.00%)
Installing Java JRE 17 at C:\dqoai\Lib\site-packages\dqoai\jre17
```

In the first run DQO.ai will ask you if you
want to initialize `user home` at specified directory, and if you want to log in to the dqo cloud (this step is 
necessary to use `cloud` commands, there is a separate article on [how to push the data to the cloud](../push_data_to_the_cloud/push_data_to_the_cloud.md)).

```
(dqoai) C:\>dqo
      _                               _
   __| |   __ _    ___         __ _  (_)
  / _` |  / _` |  / _ \       / _` | | |
 | (_| | | (_| | | (_) |  _  | (_| | | |
  \__,_|  \__, |  \___/  (_)  \__,_| |_|
             |_|

 :: DQO.AI Data Quality Observer ::    (v0.1.0)
 
Initialize a DQO user home at C:\dqoai\. [Y,n]: y
Log in to DQO Cloud? [Y,n]: y
Opening the DQO Cloud API Key request, please log in or create your DQO Cloud account.
DQO Cloud API Key request may be opened manually by navigating to: https://cloud.dqo.ai/requestapikey/...
Please wait up to 30 seconds after signup/login or press any key to cancel
API Key: your-key
DQO Cloud API Key was retrieved and stored in the settings.
dqo.ai>
```

To launch the application use

=== "Windows"
    ```python
    dqo
    ```
=== "MacOS"
    ``` python
    ./dqo
    ```
=== "Linux"
    ``` python
    ./dqo
    ```

From now on you are ready to use DQO.ai.

### GitHub
!!! Info "Prerequisites"
    You will need `JDK 17`. To confirm version run `java --version` and you should see something similar to this
    ```
    java --version
    openjdk 17.0.2 2022-01-01
    ```
    you should also confirm that you have a java compiler.
    ```
    javac --version
    javac 17.0.2
    ```

Create an empty catalogue. Open a terminal in the directory with the catalogue just created and clone the DQO.ai repository on [GitHub](https://github.com/dqoai/dqo)
```
git clone https://github.com/dqoai/dqo.git
```

Change directory to the one with a local repository and run the following command

=== "Windows"
    ```
    dqo
    ```
=== "MacOS"
    ```
    ./dqo
    ```
=== "Linux"
    ```
    ./dqo
    ```
which will start the compilation process and launch DQO.ai.

In the first run DQO.ai will ask you if you
want to initialize `user home` at specified directory, and if you want to log in to the dqo cloud (this step is
necessary to use `cloud` commands, there is a separate article on [how to push the data to the cloud](../push_data_to_the_cloud/push_data_to_the_cloud.md)).

```
(dqoai) C:\>dqo
      _                               _
   __| |   __ _    ___         __ _  (_)
  / _` |  / _` |  / _ \       / _` | | |
 | (_| | | (_| | | (_) |  _  | (_| | | |
  \__,_|  \__, |  \___/  (_)  \__,_| |_|
             |_|

 :: DQO.AI Data Quality Observer ::    (v0.1.0)
 
Initialize a DQO user home at C:\dqoai\. [Y,n]: y
Log in to DQO Cloud? [Y,n]: y
Opening the DQO Cloud API Key request, please log in or create your DQO Cloud account.
DQO Cloud API Key request may be opened manually by navigating to: https://cloud.dqo.ai/requestapikey/...
Please wait up to 30 seconds after signup/login or press any key to cancel
API Key: your-key
DQO Cloud API Key was retrieved and stored in the settings.
dqo.ai>
```

To launch the application use

=== "Windows"
    ```python
    dqo
    ```
=== "MacOS"
    ``` python
    ./dqo
    ```
=== "Linux"
    ``` python
    ./dqo
    ```

From now on you are ready to use DQO.ai.

### ZIP

Go to [github](https://github.com/dqoai/dqo/releases/tag/v0.1.0) and download the ZIP file with DQO distribution.
Unpack everything to an empty catalogue.

Create a new empty catalogue. Open a terminal in the directory with the catalogue just created and change the directory to the `bin` catalogue which is in the catalogue with unpacked files.

Run the following command:

=== "Windows"
    ```
    dqo
    ```
=== "MacOS"
    ```
    ./dqo
    ```
=== "Linux"
    ```
    ./dqo
    ```
which will start the compilation process and launch DQO.ai.

In the first run DQO.ai will ask you if you
want to initialize `user home` at specified directory, and if you want to log in to the dqo cloud (this step is
necessary to use `cloud` commands, there is a separate article on [how to push the data to the cloud](../push_data_to_the_cloud/push_data_to_the_cloud.md)).

```
(dqoai) C:\>dqo
      _                               _
   __| |   __ _    ___         __ _  (_)
  / _` |  / _` |  / _ \       / _` | | |
 | (_| | | (_| | | (_) |  _  | (_| | | |
  \__,_|  \__, |  \___/  (_)  \__,_| |_|
             |_|

 :: DQO.AI Data Quality Observer ::    (v0.1.0)
 
Initialize a DQO user home at C:\dqoai\. [Y,n]: y
Log in to DQO Cloud? [Y,n]: y
Opening the DQO Cloud API Key request, please log in or create your DQO Cloud account.
DQO Cloud API Key request may be opened manually by navigating to: https://cloud.dqo.ai/requestapikey/...
Please wait up to 30 seconds after signup/login or press any key to cancel
API Key: your-key
DQO Cloud API Key was retrieved and stored in the settings.
dqo.ai>
```
To launch the application use

=== "Windows"
    ```python
    dqo
    ```
=== "MacOS"
    ``` python
    ./dqo
    ```
=== "Linux"
    ``` python
    ./dqo
    ```

From now on you are ready to use DQO.ai.