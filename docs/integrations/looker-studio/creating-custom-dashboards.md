# Creating a custom dashboard

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

After confirmation, a screen will open where you can see the filters and parameters for the dashboard. 
The full list of data
that can be used in dashboards is available in check results. For more information about the data structure,
and how the data are stored in the dashboards
see the [Parquet files references](../../reference/parquetfiles/check_results.md) for the *check_results* parquet table.

![dimensions](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/dimensions.png)

To return to the dashboard view, click **Done** in the top right corner.


## Setting up dashboards parameters

DQOps user interface passes parameters to the dashboards. The most important parameter is `ds0.token`, 
which receives an access token to the DQOps Cloud Data Quality Data Warehouse.

To be able to modify the parameters on copied dashboard, select the **Resources** from the top menu
and choose **Manage report URL parameters**.  
 
![manage_report_URL_parameters](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/manage-report-url-parameters.png)

All parameters must be marked as **Allow to be modified in report URL**, as they are used to create and maintain report view URLs. Afterward, select **Close** and return to the dashboard view.

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

## Adding the dashboard to the tree

Finally, to make the dashboard visible, you need to add it to the dashboard list YAML configuration file in DQOps. 
It is the *settings/dashboardslist.dqodashboards.yaml* file in
the [DQOps user home](../../dqo-concepts/home-folders/dqops-user-home.md) folder.
The file format of the dashboard list specification file
is defined in the [YAML reference](../../reference/yaml/DashboardYaml.md) documentation.
When the dashboard's url is added to the dashboard list file, it will become visible in the user interface.

Open the *settings/dashboardslist.dqodashboards.yaml* file in Visual Studio Code. An empty file should be present
in the *$DQO_USER_HOME/settings* folder that is initialized when DQOps is started for the first time.

The example below shows the default configuration of all DQOps built-in dashboards.
The file can be found in [GitHub](https://github.com/dqops/dqo/blob/develop/home/settings/dashboardslist.dqodashboards.yaml).

The dashboards are organized into folders. The maximum folder nesting level supported by DQOps is 5 folders deep.
Each dashboard in the tree has the following elements:

- `dashboard_name` is the name that is shown in the **Dashboards** section in the user interface
- `url` is the url to the Looker Studio dashboard, it must be a special url allowed for embedding
   inside an IFRAME node. Urls to embeddable dashboards have an additional "embed/" section in the URL
   just after the *https://lookerstudio.google.com/* base url.

As you can see below,  
the dashboard names, the url (the one generated in Looker Studio, with the name 'embed'),
the dashboard size (the one that is set in Looker Studio in the Theme and Layout section)
have also been added. You should also add parameters if you want to pass them in.
You can copy the following section of the yaml file, edit it and create your own yaml with the dashboard list based on it.
It is important to note all indentation in the text.
Each folder should be indented at the same tab level as the previous one. Similarly, subfolders, 
dashboard names and the rest of the yaml file. If you add incorrectly, 
you may not see the dashboard on the dqops page.
To do this, it is a good idea to go into the logs and see where ( in which line of the yaml file) the error is.


The following example shows the default list of dashboards. 
The file can be also reviewed in [GitHub](https://github.com/dqops/dqo/blob/develop/home/settings/dashboardslist.dqodashboards.yaml).

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
4. URL: The URL of a given dashboard, substituting "embed" in the name
5. Size: The size of the dashboard, which is set in Looker studio in the Theme and Layout section
6. Parameters: List of parameters that we pass to the yaml file

**Brief description of the yaml file elements:**

*folder_name: Profiling* - Main folder which may contain subfolders 

*folder_name: Table profiling status* - The subfolder where we put the dashboards 

*dashboard_name: Table profiling status per data quality dimension* - The name of the dashboard that will be visible in the application

*url: https://lookerstudio.google.com/embed/reporting/b4c33286-a5d1-4948-a8f3-70b20612380e/page/c5B8C* - The URL of a given dashboard, substituting "embed" in the name

*width: 1400 height: 1400* - The size of the dashboard, which is set in Looker studio in the Theme and Layout section

*parameters:* List of parameters that we pass to the yaml file


We add a folder where the dashboard will be placed. Enter the name of the dashboard, the url generated in Looker(see below for a description of how to generate URL).
When creating a dashboard in a yaml file, you should also specify its width and height. These parameters can be found in Looker studio in the edition of the dashboard by clicking on Layout.

Here we can pass various parameters and provide a token. We specify, among other things, whether our dashboard will be in the profiling, monitoring or partitioned section.
It is very important that the link to the dashboard in the yaml file contains the "embed" part in the name. The best way is to generate the link in Looker. Go into edit dashboard, then file, embed report and select embed url.

![embed_URL](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/embed_URL.jpg)

After completing the yaml file with the list of dashboards, commit the changes. Your dashboard will be visible in the app.