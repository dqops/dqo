---
title: How to activate data observability for Azure
---

# How to activate data observability for Azure

This guide shows how to activate data observability by connecting DQOps to Azure. The example will use the Azure Blob Storage for storing data. 

# Prerequisites

- Data in CSV, JSON or Parquet format (compressed files allowed), located in a Storage Container in your Storage Account.
- [DQOps installation](https://dqops.com/docs/getting-started/installation/)

# Add connection to Azure using the user interface

### **Navigate to the connection settings**

To navigate to the Azure connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select the DuckDB connection.

    ![Selecting DuckDB connection type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-duckdb.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the Azure connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-json.png){ loading=lazy; width="1200px" }

Fill the **Connection name** any name you want.

To work with files located in Azure, change the **Files location** to **Azure Blob Storage**.

Select the **File Format** suitable to your files located in Azure.

To complete the configuration you need to set the **Azure authentication mode** and **Path** described below.


# Choose the Azure authentication mode

DQOps requires permissions to establish the connection to the Azure storage.

You can choose from a variety of authentication methods that will allow to connect to your data:

- Connection String
- Credential Chain
- Service Principal
- Default Credential

Below you can find how to get credentials for each of the authentication methods.

## Connection String

The connection string is created on the Storage Account level. 
It allows access to all files in each of the Storage Containers created in the Storage Account.

You can find the connection string in the Storage Account details. 
Open the Storage Account menu section in Azure Portal. Select the **Security + networking**, then **Access keys**.

![Connection string](https://dqops.com/docs/images/data-sources/azure/connection-string.png){ loading=lazy; width="1200px" }


## Credential Chain 

The credential chain uses environment variables and accounts stored locally used for applications running locally.
That is why it will work on local DQOps instance only.

You can sign in interactively to Azure with use of Azure CLI command: **az login**

After you succeed with the command restart the DQOps process allowing it to load the fresh account credentials.


## Service Principal 

This is the recommended authentication method.

The service principal is an impersonalized identity used specifically for a service with a proper permission.

This method needs to create a service account, generate a secret and add role assignment to the container.

To use this method you need to create a service account in Azure.
Open **Enterprise applications** and click the **New application**.

![New enterprise application](https://dqops.com/docs/images/data-sources/azure/new-enterprise-application.png){ loading=lazy; width="1200px" }

Then **Create your own application**.

![New your own enterprise application](https://dqops.com/docs/images/data-sources/azure/new-enterprise-application-your-own.png){ loading=lazy; width="1200px" }

Fill the name with your service account and create it.

![Create your own application](https://dqops.com/docs/images/data-sources/azure/on-right-create-your-own-application.png){ loading=lazy; width="1200px" }

Now the service account is ready but it does not have any credentials available to be used.

To create credentials open the **App registrations** in Azure Entra ID. 
Select **All applications**, then select the name of the service account.

![App registration](https://dqops.com/docs/images/data-sources/azure/app-registrations.png){ loading=lazy; width="1200px" }

Then navigate to **Certificates & secrets** and click the **New client secret**

![App registration](https://dqops.com/docs/images/data-sources/azure/create-new-client-secret.png){ loading=lazy; width="1200px" }

Then fill the name of a new client secret and create it.

Now the secret is ready. Save the **Value** of the key, which is your **Client Secret**.

![App registration](https://dqops.com/docs/images/data-sources/azure/client-secret.png){ loading=lazy; width="1200px" }

The last thing to be done is to add the permission of your service account to the storage account.

Open the container you will work with and select the **Access Control (IAM)**.
Click on **Add** and select the **Add role assignment**.

![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam.png){ loading=lazy; width="1200px" }

In Role tab, search for **Storage Blob Data Reader** and click on the present role below.
The role adds read permissions to the Storage Container.

![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam-role.png){ loading=lazy; width="1200px" }

In Members tab, click on the **Select members** and type the name of the service account, then click Enter.

The name of the service account will appear when the full name is typed.

Select it and click Select.

![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam-member.png){ loading=lazy; width="1200px" }

To add a connection in DQOps with use of Service Principal authentication mode you need the following:

- Storage Account Name
- Tenant ID
- Client ID
- Client Secret

The **Client Secret** you saved.

Tenant ID and Client ID are available in the App registrations Overview section of the Azure Entra ID.

![App registration](https://dqops.com/docs/images/data-sources/azure/credentials.png){ loading=lazy; width="1200px" }


## Default Credential 

With DQOps, you can configure credentials to access Azure Blob Storage directly in the platform.

Please note, that any credentials and secrets shared with the DQOps Cloud or DQOps SaaS instances are stored in the .credentials folder.
This folder also contains the default credentials files for Azure Blob Storage (**Azure_default_credentials**).

``` { .asc .annotate hl_lines="4" }
$DQO_USER_HOME
├───...
└───.credentials       
    ├───Azure_default_credentials
    └─...   
```

If you wish to use Azure authentication, you need service principal credentials that must be replaced in Azure file content.

To set the credential file for Azure in DQOps, follow steps:

1. Open the Configuration section.
2. Select Shared credentials from the tree view on the left.
3. Click the edit link on the “Azure_default_credentials” file.

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/azure-shared-credentials-ui2.png)

4. In the text area, edit the tenant_id, client_id, client_secret and account_name, replacing the placeholder text.

![Edit connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/edit-azure-shared-credential2.png)

5. Click the **Save** button, to save changes, go back to the main **Shared credentials** view.


# Set the Path

## Data structure

Let assume the files placed in Azure Storage Account named **mystorageaccount**, in this structure:

``` { .asc .annotate }
my-container
├───...
└───clients_data
    ├───reports
    │   ├───annual_report_2022.csv(1)
    │   ├───annual_report_2023.parquet
    │   ├───market_dictionary.json
    │   └───...
    ├───market_specification(2)
    │   ├───US.csv
    │   ├───Canada.csv
    │   ├───Germany.csv
    │   └───...
    └───sales(3)
        ├───d=2024-01
        │   ├───m=US
        │   ├───m=CA
        │   ├───m=GE
        │   ├───m=YP
        │   └───...
        ├───d=2024-02
        ├───d=2024-03
        └───...     
```

1.  Connect to a specific file - e.g. annual_report_2022.csv by setting prefix to **/my_container/clients_data/reports**. A selection of the file is available after saving the new connection configuration.
2.  Connect to all files in path - e.g. whole market_specification folder by setting prefix to **/my_container/clients_data**. A selection of the folder is available after saving the new connection configuration.
3.  Connect to partitioned data - e.g. sales folder with partitioning by date and market - set prefix to **/my_container/clients_data** and select **Hive partitioning** checkbox from Additional format options. A selection of the **sales** folder is available after saving the new connection configuration.

You can connect to a specific file, eg. annual_report_2022.csv (set prefix to **/usr/share/clients_data/reports**),
all files in path, eg. whole market_specification folder (set prefix to **/usr/share/clients_data**) 
or hive style partitioned data, eg. sales folder with partitioning by date and market - set prefix to **/usr/share/clients_data** and select **Hive partitioning** checkbox from Additional format options.

The path is a directory containing files. You cannot use a full file path. 
The prefix cannot contain the name of a file.

A selection of files or directories is available **after Saving the new connection**.

# Import metadata using the user interface

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import files.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the source schema name from which you want to import tables.

   ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-schemas.png){ loading=lazy; width="1200px" }

2. Select the tables (folders with files of previously selected file format or just the files) you want to import or import all tables using the buttons in the upper right corner.

   ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-csv.png){ loading=lazy; width="1200px" }

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-advisor-csv.png){ loading=lazy; width="1200px" }


# Detailed creation of a new connection

You can check more details on connecting the files of the following formats:

- [CSV](../csv.md)
- [JSON](../json.md)
- [Parquet](../parquet.md)
