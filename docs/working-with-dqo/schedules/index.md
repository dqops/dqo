# Schedules overview

With DQO, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

To set up schedule you can use the graphical interface as described below or manually modify the YAML configuration file as [described here.](./configuring-schedules-by-modifying-yaml-file.md)

Different types of checks, such as Profiling, Monitoring, and Partitioned, have their own schedules. For more information
on these different check types, please refer to the [DQO Concepts section](../../dqo-concepts/checks/index.md).  

## Configuring a schedule at connection and table level

To set up a schedule for the entire connection, follow these steps:

1. Navigate to the **Data Source** section.

2. Choose the connection you want to schedule from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the check type:
    - Profiling
    - Monitoring Daily
    - Monitoring Monthly
    - Partitioned Daily
    - Partitioned Monthly
   
5. Specify the schedule using a [Unix cron expression](./cron-formatting.md) or select one of the options provided.

6. Once you have set the schedule, click on the **Save** button to save your changes.

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across 
all tables associated with that connection. If you wish to disable the schedule for a specific table, you can simply do 
so by checking the "Disable schedule" checkbox.

To set up a schedule for a specific table, simply select the desired table from the tree view on the left, then follow 
the steps above beginning at step 3.
Please note that any changes made to the schedule on the table level will override the schedule set for the entire connection.

## Configuring a schedule at check level.

To set up a schedule for a specific check, follow these steps:

1. Navigate to the section with a check type of interest (Profiling, Monitoring Checks or Partition Checks).

2. Choose table or column of interest from the tree view on the left.

3. Enable the check of interest then click the "Setting" button and go to the "**Schedule Override**" tab.

4. Specify the schedule using a Unix cron expression or select one of the options provided.

6. Once you have set the schedule, click the **Save** button to save your changes.

Please note that any changes made to the schedule at the check level will override the schedule set for the entire 
connection or table.

## Starting a scheduler

To initiate a scheduler in the DQO Shell, simply enter the command `scheduler start`. To stop the scheduler, use the 
command `scheduler stop`.

You can also use the graphical interface to start the scheduler. Simply enable **Jobs scheduler** located in the Notifications
on the [right side of the navigation bar.](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md)

For further information on the `scheduler` commands, please refer to the [Command-line interface section](../../command-line-interface/scheduler.md). 

Scheduler can also be started in a server mode that continuously run a job scheduler and synchronize the data every 10 minutes.
To do this, simply enter the command below in your terminal:
```
$ dqo run
```
To terminate dqo running in the background, simply use the Ctrl+C.

For more information on the `run` command, please refer to the [Command-line interface section](../../command-line-interface/run.md).


## Synchronizing data

All the YAML configuration files with data source metadata and schedules configuration are stored in `/sources` folder. 
You can read more about the [data storage in DQO here](../../dqo-concepts/data-storage/data-storage.md). 

DQO allows you to modify the frequency of data synchronization when the scheduler is run in a server mode

In order to configure how often the scheduler will synchronize the local copy of the metadata with DQO Cloud and detect
new schedules, start dqo with the following parameter:

```
$ dqo --dqo.scheduler.scan-metadata-cron-schedule=<Unix cron expression>
```
Please use quotation marks when defining a frequency in cron format.

You can also configure this parameter by setting `DQO_SCHEDULER_SCAN_METADATA_CRON_SCHEDULE=<Unix cron expression>` 
environment variable.


To modify whether the job scheduler will sync configuration files and results with DQO Cloud, simply launch dqo 
with the following parameter:

```
$ dqo --dqo.scheduler.enable-cloud-sync=<true/false>
```
To enable synchronization type `true`, to disable it type `false`.

You can also configure this parameter by setting `DQO_SCHEDULER_ENABLE_CLOUD_SYNC=<TRUE/FALSE>` environment variable.