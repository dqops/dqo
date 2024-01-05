# Creating a custom dashboard

## Overview

With DQOps, you can use dashboards to visualize your results and simplify the detection of anomalies in your data. DQOps
provides the tools for creating your own customized dashboard to house your data.

This guide will walk you through creating a dashboard in DQOps, starting with setting up an account in Looker Studio, 
granting the necessary permissions, authorizations, and finally copying the completed dashboard to add your data.

## Creating an account in Looker Studio

To begin using Looker Studio and creating dashboards, you will need a Looker Studio account. If you have a Google account
with a Gmail or another address, you may use this account to log in to Looker Studio.

Alternatively, you can create a new account by visiting the [Looker Studio website](https://lookerstudio.google.com/overview)
and clicking **Use it for free**. This will redirect you to the Looker Studio account creation panel where you can enter your details such as your name, 
surname, and date of birth. Once your account is created, log in to Looker Studio using the email address you provided

![Signing in to Looker Studio](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/sign-in-to-looker-studio-o.png)

## Granting permissions

After logging in to Looker Studio, open the DQOps application, go to the Data Quality Dashboards section, and select the
dashboard that you want to modify. Once you have chosen your dashboard, scroll down to the bottom of the page and click 
on Looker Studio in the bottom right corner.

![Looker Studio](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/looker-studio.png)

You will be redirected to the Looker Studio dashboard, where you can edit the dashboard you selected. However, 
before you can edit, you need to grant all necessary accesses. To do this, click on the grant access icon 
located in the top right corner. This will allow you to access and copy the dashboard.
    
![Grant access](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/grant-access-a.png)

If you receive a message that you do not have access, you should ask the owner to include our email address in the editor group.

![Access for users and groups](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/access-for-users-and-groups-a.png)

An editor group is a group that can be set up for each Google account. The owner is responsible for granting access to the
group members according to their roles. Once the owner adds your email address to the editor group, the grant access icon
will disappear, and you can proceed with the configuration process.

## Copying the dashboard and authorize accesses

Our next step is to duplicate the dashboard and create a new one based on it. To do so, we need to click on the vertical
three dots icon and select **Make a copy**.
 
![Copying dashboard](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/dashboard-make-a-copy.png)

If this is your first time creating a dashboard, you need to complete your account setup by filling in your country and company name.

![country_and_company](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/country-and-company.png)

After setting the country and company, you need to select the consents. As shown in the image below, these consents include
permission to receive tips and recommendations, product updates, etc. We should mark the consents that we feel are necessary.

![points of agreement](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/points-of-agreement.png)

After completing the account setup, select the vertical three dots icon and select **Make a copy**.
 
![generate_your_dashboard](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/generate-your-dashboard.png)

Now, you should see a copy if the selected dashboard but without any data. Now you need to authorize access to the data.

![no_data](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/no-data.png)

Select **Resources** in the menu in the top and then select **Manage added the data sources**.

![manage_added_data_sources](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/manage-added-data-sources.png)

This will take you to the editing panel where you can set the parameters. Click the **Edit** option to go through the subsequent authorization steps.

![edit](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/edit.png)

Looker Studio requires authorization to connect to data, so click on the **Authorize** button.
You will then be prompted to select the account you want to log in with - choose the Looker Studio account that you selected
at the beginning. After that, select **Authorize** again and confirm access to the account by selecting **Allow**.

![select_authorize-s](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/select-authorize-s.png)

On the screen with parameter settings, select **Allow Enter DQOps AI API Key for Looker Studio** option, enter the parameter value as 0, and click on the **Reconnect** button in the top right corner.

![Allow](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-s.png)

A pop-up window should appear asking **Allow parameter sharing?**. Select **Allow** to give permission for report editors
to modify the parameter values.

![allow_parameter_sharing](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-parameter-sharing.png)
 
Confirm connection changes by clicking **Apply** on a pop-up window that will appear.
 
![apply_connection_changes](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/apply-connection-changes.png)

## Configuring the data model

After confirmation, a screen will open where you can see the data model and parameters in the dashboard. 
The dashboard can use the [check_results](../../reference/parquetfiles/check_results.md) table as a source.
Alternative, the [sensor_results](../../reference/parquetfiles/sensor_readouts.md) and other parquet tables
replicated from the *[DQOps user home](../../dqo-concepts/home-folders/dqops-user-home.md)/.data* folder
to the DQOps Cloud Data Lake are available for querying.

The list of columns in these tables is described in 
the [Parquet files references](../../reference/parquetfiles/check_results.md) section for the *check_results* parquet table.

![Looker studio data source columns for DQOps](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/dimensions.png)

To return to the dashboard view, click **Done** in the top right corner.


## Setting up dashboards parameters

DQOps user interface passes parameters to the dashboards. The most important parameter is `ds0.token`, 
which receives an access token to the DQOps Cloud Data Quality Data Warehouse.

To be able to modify the parameters on copied dashboard, select the **Resources** from the top menu
and choose **Manage report URL parameters**.  
 
![manage_report_URL_parameters](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/manage-report-url-parameters.png)

All parameters must be marked as **Allow to be modified in report URL**, as they are used to create and maintain report view URLs.
Afterward, select **Close** and return to the dashboard view.

![allow_to_be_modified_in_report_URL](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-to-be-modified-in-report-url.png)

It is also essential to specify who can edit the dashboard and who can only view it.
To do this, click on Share, and a po-pup will appear in the top right-hand corner where you can add email addresses. 
Alternatively, create an editing group and add the employees' email address in the popup.

You have to ensure that the *Link settings* configuration allows *Anyone on the internet with the link can view*
to have a **Viewer** permission. Otherwise, other users who were not directly granted access to the dashboards
will not be able to use it. Despite that the access to the dashboard will be granted to everyone, the dashboard
is usable only when DQOps uses interface passes the access token to access the DQOps Cloud Data Quality Data Warehouse.

![groups](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/groups.png)

Once you have completed the configuration steps, you can edit the dashboard and complete the dashboard according to your needs.


## Getting the embeddable dashboard url
DQOps shows the Looker Studio dashboards embedded in an IFRAME HTML element.
Looker Studio dashboards cannot be embedded inside an IFRAME if the default dashboard url is copied from the browser
when editing the dashboard. When using a non-embeddable dashboard url, the dashboard will not open correctly in DQOps.

Instead, you need to get the alternative, embeddable dashboard url. Go to the Looker Studio main menu and open
the "File -> Embed report" screen shown below. 

![Get embeddable url from Looker Studio](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/embed_URL.jpg)

You will need this embeddable url in the next step to add the dashboard to the dashboards list file.


## Adding the dashboard to DQOps

Finally, to make the dashboard visible, you need to add it to the dashboard list YAML configuration file in DQOps. 
It is the *settings/dashboardslist.dqodashboards.yaml* file in
the [DQOps user home](../../dqo-concepts/home-folders/dqops-user-home.md) folder.
The file format of the dashboard list specification file
is defined in the [dasboards YAML reference](../../reference/yaml/DashboardYaml.md) documentation.
When the dashboard's url is added to the dashboard list file, it will become visible in the user interface.

Open the *settings/dashboardslist.dqodashboards.yaml* file in Visual Studio Code. An empty file should be present
in the *[DQOps user home](../../dqo-concepts/home-folders/dqops-user-home.md)/settings* folder
that is initialized when DQOps is started for the first time.

The example below shows the default configuration of all DQOps built-in dashboards.
The file can be found in [GitHub](https://github.com/dqops/dqo/blob/develop/home/settings/dashboardslist.dqodashboards.yaml).

``` { .yaml .annotate linenums="1" hl_lines="6 8 10 11 12 14" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/DashboardYaml-schema.json
apiVersion: dqo/v1
kind: dashboards
spec:

- folder_name: Profiling # (1)!
  folders:
    - folder_name: Table profiling status # (2)!
      dashboards:
        - dashboard_name: Table profiling status per data quality dimension # (3)!
          url: https://lookerstudio.google.com/embed/reporting/b4c33286-a5d1-4948-a8f3-70b20612380e/page/c5B8C # (4)!
          width: 1400 # (5)!
          height: 1400
          parameters: # (6)!
            ds0.token: '%DQO_CLOUD_TOKEN%'
            ds0.p_check_type: profiling
            ds0.p_quality_dimension: all
            ds0.p_check_category: all
        - dashboard_name: Table profiling status per check category
          url: https://lookerstudio.google.com/embed/reporting/4ff08521-65d9-448d-9d78-fd3fe415a557/page/c5B8C
          width: 1400
          height: 1400
          parameters:
            ds0.token: '%DQO_CLOUD_TOKEN%'
            ds0.p_check_type: profiling
            ds0.p_quality_dimension: all
            ds0.p_check_category: all
        - dashboard_name: Column profiling status per data quality dimension
          url: https://lookerstudio.google.com/embed/reporting/2e72e940-3f39-4a82-ba31-6994a03dc005/page/c5B8C
          width: 1400
          height: 1760
          parameters:
            ds0.token: '%DQO_CLOUD_TOKEN%'
            ds0.p_check_type: profiling
            ds0.p_quality_dimension: all
            ds0.p_check_category: all
        - dashboard_name: Column profiling status per check category
          url: https://lookerstudio.google.com/embed/reporting/20c67e41-5830-4242-aac4-fe4131d2bb92/page/c5B8C
          width: 1400
          height: 1760
          parameters:
            ds0.token: '%DQO_CLOUD_TOKEN%'
            ds0.p_check_type: profiling
            ds0.p_quality_dimension: all
            ds0.p_check_category: all
```

1. Folder name: Main folder which may contain subfolders
2. Folder name: The subfolder where we put the dashboards
3. Dashboard name: The name of the dashboard that will be visible in the application
4. URL: The URL of a given dashboard, it must be the embeddable url identified by the "embed" element in the url
5. Size: The size of the dashboard, which is set in Looker Studio in the Theme and Layout section
6. Parameters: List of parameters that we pass to the dashboard. We must pass the *ds0.token* parameter to access the
   private DQOps Cloud Data Quality Data Warehouse.

The dashboards are organized into folders. The maximum folder nesting level supported by DQOps is 5 folders deep.
Each dashboard in the tree has the following elements:

- `dashboard_name` is the name that is shown in the **Data Quality Dashboards** section in the user interface
- `url` is the url to the Looker Studio dashboard, it must be a special url allowed for embedding
  inside an IFRAME HTML element. Urls to embeddable dashboards have an additional "embed/" section in the URL
  just after the *https://lookerstudio.google.com/* base url.


As you can see below, the `spec` node contains an array of folders.
Each folder is identified by a `folder_name` field with the name of the folder in the tree.
Just below the first folder named *Profiling*, you will see a `folders` node that is a container of nested subfolder.

A folder node has three types of child elements:

- `folder_name` stores the name of the folder
- `folders` is a list (array) of nested subfolders, each nested folder has the same structure and must have a `folder_name` element
- `dashboards` is a list of dashboards stored in the folder

When editing the dashboard list YAML file, please be very careful about maintaining correct indentations in the file.
If the file is corrupted, the *Dashboards* screen will be empty in the DQOps user interface. Any errors will be reported
to the local *[DQOps user home](../../dqo-concepts/home-folders/dqops-user-home.md)/.logs* logging folder.
DQOps will report the exact file location (line and column) where the issue was found while reading the dashboard list file.

A single dashboard has the following structure:

``` { .yaml .annotate linenums="1" hl_lines="9-17" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/DashboardYaml-schema.json
apiVersion: dqo/v1
kind: dashboards
spec:
- folder_name: Profiling
  folders:
    - folder_name: Table profiling status
      dashboards:
        - dashboard_name: Table profiling status per data quality dimension # (1)!
          url: https://lookerstudio.google.com/embed/reporting/b4c33286-a5d1-4948-a8f3-70b20612380e/page/c5B8C # (2)!
          width: 1400 # (3)!
          height: 1400 # (4)!
          parameters: # (5)!
            ds0.token: '%DQO_CLOUD_TOKEN%' # (6)!
            ds0.p_check_type: profiling # (7)!
            ds0.p_quality_dimension: all
            ds0.p_check_category: all
```

1.  The dashboard's name.
2.  The url to the embeddable version of the Looker Studio dashboard.
3.  The width of the dashboard.
4.  The height of the dashboard.
5.  The container of parameters that are passed to the dashboard by encoding them in the dashboard's url.
6.  The required DQOps Cloud access key used to access the data quality data warehouse. It must be a `'%DQO_CLOUD_TOKEN%'` token.
7.  Additional, report specific parameters.

The elements of a dashboard node are:

- `dashboard_name` is the name of the dashboard that will be shown in the DQOps user interface, it does not need to be the same
  as the dashboard name defined in Looker Studio

- `url` stores the embeddable dashboard url that you should now copy from the *Embed report* screen shown
  in the [Getting the embeddable dashboard url](#getting-the-embeddable-dashboard-url) section

- `width` and `height` are the width and height of the dashboard  which is set in Looker Studio in the *Theme and Layout* section

- `parameters` are the names of parameters passed in an encoded format as parameters appended to the dashboard. 
  Please notice that all the parameters share the same `ds0.` prefix which is the prefix for the data source in Looker Studio.
  Parameters in Looker Studio that can be passed in the url are defined at a data source level, that is why they use this prefix.
  If your dashboard uses multiple connections to the DQOps Cloud Data Quality Data Warehouse to access or blend different tables,
  you must pass the `ds0.token: '%DQO_CLOUD_TOKEN%'` parameter using the prefix of each data source.

- `ds0.token` is a special parameter that is used by the DQOps Cloud Looker Studio Community Connector to pass a special
  DQOps Cloud Looker Studio authentication token used by the connector to request a real authentication token for BigQuery.
  Use the default `'%DQO_CLOUD_TOKEN%'` value which is replaced with the token when DQOps builds the final dashboard url.

After completing the yaml file with the list of dashboards, save the changes.
Your dashboard will be visible in the **Data Quality Dashboards** section in the DQOps user interface.


## Changing built-in dashboards
If any of the DQOps built-in dashboard does not serve your purpose and you need to change the layout, 
reorder the filters or add new sections, simply make a copy of the dashboard following this manual up to the
section of [Adding the dashboard to DQOps](#adding-the-dashboard-to-dqops). 

Copy the whole folder tree where the dashboard is found from the 
[default dashboard list file](https://github.com/dqops/dqo/blob/develop/home/settings/dashboardslist.dqodashboards.yaml)
available on GitHub.
Remove all folders and dashboards that will not be modified. Keep only the dashboard that will be replaced
and a full folder path leading to this dashboard.

Then, replace the dashboard's `url` with the new Looker Studio embeddable report url of your version of the dashboard
and update the `width` and `height` parameters if they changed.
In order to effectively override a built-in dashboard, all the folder names in the `folder_name` elements
and the dashboard name specified in the `dashboard_name` element be the same as the overwritten built-in dashboard.
When the file is saved and the DQOps **Data Quality Dashboards** screen is refreshed in the browser,
your version of the dashboard will be shown instead.


## Dashboard sample data
While editing the dashboard in Looker Studio, you will see sample data that does not match the data in your
DQOps Cloud Data Quality Data Warehouse. The connection names will be different. Only a few tables will be registered.

The data that is provided to Looker Studio by the DQOps Looker Studio Community Connector uses a complimentary
demo data quality data warehouse provided by DQOps for testing purposes.
In order to use your real data quality results, you have to set the default value of the `ds0.token` parameter
to the value of the *DQOps Cloud Looker Studio Community Connector API Key* shown on the
[https://cloud.dqops.com/account](https://cloud.dqops.com/account) screen in the DQOps Cloud account management interface.
The `ds0.token` parameter must be changed back to `0` before publishing the report. Otherwise, a report available 
for any user who knows the report url will be accessible to anybody, allowing to see your private data.

The *DQOps Cloud Looker Studio Community Connector API Key* is available only for paid customers of DQOps.
