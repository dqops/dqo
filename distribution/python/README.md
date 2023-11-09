# DQOps Data Quality Operations Center

DQOps is an DataOps friendly data quality monitoring platform with customizable data quality checks and data quality dashboards.
DQOps comes with around 150 predefined data quality checks which helps you monitor the quality of your data.

![DQOps screens](https://dqops.com/docs/images/dqo-screens.gif)

## Key features
- Intuitive graphical interface and access via CLI
- Support of a number of different data sources: BigQuery, Snowflake, PostgreSQL, Redshift, SQL Server and MySQL
- ~600 build-in table and column checks with easy customization
- Table and column-level checks which allows writing your own SQL queries
- Daily and monthly date partition testing
- Data grouping by up to 9 different data grouping levels
- Build-in scheduling
- Calculation of data quality KPIs which can be displayed on multiple built-in data quality dashboards
- Incident analysis

## Installation

To use DQOps you need:

- Python version 3.8 or greater (for details see [Python's documentation](https://www.python.org/doc/) and [download sites](https://www.python.org/downloads/)).
- Ability to install Python packages with pip.


DQOps is available on [PyPi repository](https://pypi.org/project/dqops/).

1. To install DQOps via pip manager just run

    ```
    python -m pip install --user dqops
    ```

   If you prefer to work with the source code, just clone our GitHub repository [https://github.com/dqops/dqo](https://github.com/dqops/dqo)
   and run

2. Run dqops module to finalize the installation.

    ```
    python -m dqops
    ```

3. Create DQOps userhome folder.

   After installation, you will be asked whether to initialize the DQO user's home folder in the default location. Type Y to create the folder.  
   The user's home folder locally stores data such as sensor readouts and the data quality check results, as well as data source configurations. [You can learn more about data storage here](https://dqops.com/docs/dqo-concepts/data-storage/data-storage/).

4. Login to DQOps Cloud.

   To use DQOps features, such as storing data quality definitions and results in the cloud or data quality dashboards, you
   must create a DQOps cloud account.

   After creating a user's home folder, you will be asked whether to log in to the DQO cloud. After typing Y, you will be
   redirected to [https://cloud.dqops.com/registration](https://cloud.dqops.com/registration), where you can create a new account, use Google single sign-on (SSO) or log in if you already have an account.

   During the first registration, a unique identification code (API Key) will be generated and automatically retrieved by DQO application.
   The API Key is now stored in the configuration file.

5. Open the DQOps User Interface Console in your browser by CTRL-clicking on the link displayed on the command line (for example http://localhost:8888)
   or by copying the link.

## Documentation

For full documentation with guides and use cases, visit https://dqops.com/docs/

The [getting started](https://dqops.com/docs/getting-started/) guide shows how to start using DQOps.

Also, read the [DQOps concept](https://dqops.com/docs/dqo-concepts/) guide to know how DQOps operates,
and how to configure data quality checks.

## DQOps client
The package contains also a remote DQO client that can connect to a DQOps instance and perform all operations supported by the user interface.
The DQOps client could be used inside data pipelines or data preparation code to verify the quality of tables.

Usage of the DQOps client.

```python
from dqops.client import Client

client = Client(base_url="http://localhost:8888")
```

If the endpoints you're going to hit require authentication, use `AuthenticatedClient` instead:

```python
from dqops.client import AuthenticatedClient

client = AuthenticatedClient(base_url="http://localhost:8888", token="Your DQO API Key")
```

Now call your endpoint and use your models:

```python
from dqops.client.models import MyDataModel
from dqops.client.api.my_tag import get_my_data_model
from dqops.client.types import Response

my_data: MyDataModel = get_my_data_model.sync(client=client)
# or if you need more info (e.g. status_code)
response: Response[MyDataModel] = get_my_data_model.sync_detailed(client=client)
```

Or do the same thing with an async version:

```python
from dq_ops_client.models import MyDataModel
from dq_ops_client.api.my_tag import get_my_data_model
from dq_ops_client.types import Response

my_data: MyDataModel = await get_my_data_model.asyncio(client=client)
response: Response[MyDataModel] = await get_my_data_model.asyncio_detailed(client=client)
```

By default, when you're calling an HTTPS API it will attempt to verify that SSL is working correctly. Using certificate verification is highly recommended most of the time, but sometimes you may need to authenticate to a server (especially an internal server) using a custom certificate bundle.

```python
client = AuthenticatedClient(
    base_url="https://tenantinstance.us.dqops.com/", 
    token="SuperSecretToken",
    verify_ssl="/path/to/certificate_bundle.pem",
)
```

You can also disable certificate validation altogether, but beware that **this is a security risk**.

```python
client = AuthenticatedClient(
    base_url="https://tenantinstance.us.dqops.com/", 
    token="SuperSecretToken", 
    verify_ssl=False
)
```

There are more settings on the generated `Client` class which let you control more runtime behavior, check out the docstring on that class for more info.

Things to know:
1. Every path/method combo becomes a Python module with four functions:
   1. `sync`: Blocking request that returns parsed data (if successful) or `None`
   1. `sync_detailed`: Blocking request that always returns a `Request`, optionally with `parsed` set if the request was successful.
   1. `asyncio`: Like `sync` but async instead of blocking
   1. `asyncio_detailed`: Like `sync_detailed` but async instead of blocking

1. All path/query params, and bodies become method arguments.
1. If your endpoint had any tags on it, the first tag will be used as a module name for the function (my_tag above)
1. Any endpoint which did not have a tag will be in `dqops.client.api.default`


## Contact and issues

If you find any issues with the tool, just post it here:

https://github.com/dqops/dqo/issues

or contact us via https://dqops.com/
