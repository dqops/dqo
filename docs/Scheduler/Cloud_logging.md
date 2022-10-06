Cloud is a platform with dashboards with results of the checks.

An application programming interface (API) key is a unique code used to identify and authenticate an application or user. It is generated when first register.
API key is stored in a YAML file named `.localsettings.dqosettings.yaml` in `userhome` catalogue.

There are 3 methods to log on the Cloud:

- Using `Cloud login` command

- CLI mode

- Server mode

Cloud synchronization works only if the application was first logged on, so a call to "cloud login" is required.

### Using `Cloud login` command

Open a Command Prompt, run dqo and log in to the DQO Cloud by using the command `cloud login` or if there's a question about logging in to the Cloud type `Y`.

```
(dqoai) C:\>dqo
      _                               _
   __| |   __ _    ___         __ _  (_)
  / _` |  / _` |  / _ \       / _` | | |
 | (_| | | (_| | | (_) |  _  | (_| | | |
  \__,_|  \__, |  \___/  (_)  \__,_| |_|
             |_|

 :: DQO.AI Data Quality Observer ::    (v0.1.0)

Log in to DQO Cloud? [Y,n]: Y
Opening the DQO Cloud API Key request, please log in or create your DQO Cloud account.
DQO Cloud API Key request may be opened manually by navigating to: https://cloud.dqo.ai/requestapikey/7a22697373223a22323xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
Please wait up to 30 seconds after signup/login or press any key to cancel
API Key: 7a22746964223xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
DQO Cloud API Key was retrieved and stored in the settings.
```

This opens the DQO Cloud API Key request, please log in or create a DQO Cloud account.

### CLI mode

In order to log on the Cloud in CLI mode run DQO and use following command:

```
--dqo.cloud.api-key=<ApiKey>
```

### Server mode

In order to log on the Cloud in server mode use following command:

```
set DQO_CLOUD_API-KEY=<ApiKey>
```

Having run the above command, now run:

```
dqo run
```