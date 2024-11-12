# How to move configured checks between environments

This example shows how to transfer configured checks from one environment to another along with the connection configuration.

## Overview

In this example, you will learn how to move configured checks between environments.

**PROBLEM**

There may be situations where needed to transfer configured checks from one environment to another. 
One such example is a situation where using a separate test environment and a separate production environment. In this case, two separate [User home](../../dqo-concepts/dqops-user-home-folder.md) directories. 
At some point, we decide to place the checks from the test environment in the production environment.

1. In the production environment, a connection is configured using the PostgreSQL engine. The table 'table' from the 'public' schema has also been imported and monitoring is performed using appropriate checks.
    
    ![Production environmentview](https://dqops.com/docs/images/examples/navigate-to-connections-moving-environments-prod-view1.png){ loading=lazy; width="1200px" }

2. In the development environment, a connection is also configured using the PostgreSQL engine, but to a different database and a different table.
   
    ![Development environmentview](https://dqops.com/docs/images/examples/navigate-to-connections-moving-environments-dev-view1.png){ loading=lazy; width="1200px" }


**SOLUTION**

DQOps does not require manually configuring all checks again. 
If you need to transfer the entire production environment, just copy the **source** folder 
from the first [User home](../../dqo-concepts/dqops-user-home-folder.md) and paste it into the second one. 

If you need to transfer only some tables,
just create a new connection to the production data source.
Find the connection in the **sources** folder and copy all **.dqotable.yaml** files from the testing environment to the production environment.
If the tables were named differently in the production environment, just rename the file names.
The file name that stores all checks is named following a convention **<schema_name>.<table_name>.dqotable.yaml**
If the tables on the production environment are stored in a different schema (or dataset, database), just rename the files.

## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### Check what connection is configured on which the data quality checks are deployed

Go to the **Data sources** section and select the "house_price_prediction" connection from the tree view on the left.

![Navigating to connections](https://dqops.com/docs/images/examples/navigate-to-connections-moving-environments-prod-view1.png){ loading=lazy; width="1200px" }


### Copy the source directory from the development environment to the production environment

Using any file manager copy the source directory from the development environment to the production environment.

![Moving source folder using windows file explorer](https://dqops.com/docs/images/examples/moving-environments-using-windows-explorer2.png){ loading=lazy; width="1200px" }

### Refresh the application

After refreshing the application, both connections along with the deployed data quality checks are available in the environment.
Go to the **Data sources** section and select the "house_price_prediction" connection from the tree view on the left.

![Navigating to connections after moving source folder](https://dqops.com/docs/images/examples/navigate-to-connections-after-moving-environments-prod-view1.png){ loading=lazy; width="1200px" }

In this example, we have demonstrated how to transfer configured checks from one environment to another along with the connection configuration.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.