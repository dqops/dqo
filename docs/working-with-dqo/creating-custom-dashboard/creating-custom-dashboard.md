# Creating a custom dashboard

With DQO, you can use dashboards to visualize your results and simplify the detection of anomalies in your data. DQO
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

On the screen with parameter settings, select **Allow Enter DQO AI API Key for Looker Studio** option, enter the parameter value as 0, and click on the **Reconnect** button in the top right corner.

![Allow](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-s.png)

A pop-up window should appear asking **Allow parameter sharing?**. Select **Allow** to give permission for report editors
to modify the parameter values.

![allow_parameter_sharing](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-parameter-sharing.png)
 
Confirm connection changes by clicking **Apply** on a pop-up window that will appear.
 
![apply_connection_changes](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/apply-connection-changes.png)
 
After confirmation, a screen will open where you can see the filters and parameters for the dashboard. The full list of data
that can be used in dashboards is available in check results. For more information about the data structure, and how the data are stored in the dashboards see the [Parquet files references](../../reference/parquetfiles/check_results.md).

![dimensions](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/dimensions.png)

To return to the dashboard view, click **Done** in the top right corner.

## Setting up dashboards parameters

To be able to modify the parameters on copied dashboard, select the **Resources** from the top menu and choose **Manage report URL parameters**.  
 
![manage_report_URL_parameters](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/manage-report-url-parameters.png)

All parameters must be marked as **Allow to be modified in report URL**, as they are used to create and maintain report view URLs. Afterward, select **Close** and return to the dashboard view.

![allow_to_be_modified_in_report_URL](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-to-be-modified-in-report-url.png)

It is also essential to specify who can edit the dashboard and who can only view it.
To do this, click on Share, and a po-pup will appear in the top right-hand corner where you can add email addresses. 
Alternatively, create an editing group and add the employees' email address in the popup.

![groups](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/groups.png)

Once you have completed the configuration steps, you can edit the dashboard to meet your requirements.

Finally, to make the dashboard visible, you need to add it to YAML configuration file in DQOps, which is located in DQOps
home catalog (home/settings/dashboardslist.dqodashboards.yaml). This will make the dashboard visible in the user interface.

The section of the yaml file that you complete looks like this:
![yaml_file](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/yaml_file.png)

We add a folder where the dashboard will be placed. Enter the name of the dashboard, the url generated in Looker(see below for a description of how to generate URL).
When creating a dashboard in a yaml file, you should also specify its width and height. These parameters can be found in Looker studio in the edition of the dashboard by clicking on Layout.

Here we can pass various parameters and provide a token. We specify, among other things, whether our dashboard will be in the profiling, monitoring or partitioned section.
It is very important that the link to the dashboard in the yaml file contains the "embed" part in the name. The best way is to generate the link in Looker. Go into edit dashboard, then file, embed report and select embed url.

![embed_URL](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/embed_URL.jpg)

After completing the yaml file with the list of dashboards, commit the changes. Your dashboard will be visible in the app.