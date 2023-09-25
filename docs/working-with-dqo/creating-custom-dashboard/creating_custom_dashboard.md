# Creating a custom dashboard

DQOps allows you to use dashboards to help visualise your results and make it easier to detect 
anomalies in your data.
Using the dashboards provided by DQOps, you can create your own dashboard to house your data.
This description will show you step-by-step through the various steps of creating dashboards 
in DQOps, starting with creating an account in Looker Studio, setting up the necessary permissions, 
authorisation, and copying the finished dashboard on which to place your data.

## Creating an account in Looker Studio

To get started with Looker Studio and the dashboards you need a Looker Studio account.
If you have a Google account with a gmail address or another domain, you can use this account to log in to looker studio.
You can also create a new account. 

Go to the Looker Studio website to create an account [Looker_Account](https://lookerstudio.google.com/overview).
When you click use it for free, you will be redirected to the Looker Studio account creation panel or select an email address with which to log in to Looker Studio. Step by step, fill in the field with your name, surname, date of birth, etc. Then log in to Looker Studio using the Email address you created.

![sign_in_to_looker_studio](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/sign-in-to-looker-studio-o.png)

## Authorisation and essential settings

After logging into the looker studio or creating a new account, select in the DQOps application the dashboard on
which you would like to see your company data, for example.
Go to the dashboard of your choice in the app. Scroll down to the bottom of the page and click in the bottom right corner on Looker Studio.

![Looker Studio](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/looker-studio.png)

You are redirected to the Looker studio dashboard.  On this page you will edit the dashboard you have selected. In the beginning, however, it is necessary to grant all accesses so that editing is possible.  To do this, let us go through the steps of granting access. 
We can see the grant access icon in the top right corner and we click on it, as we want to access to copy the dashboard
    
![Grant access](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/grant-access-a.png)

 A box appears stating that we do not have access. As this is the first time we want to gain access we will go through the authorisation steps. 
 
![Access_for_users_and_groups](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/access-for-users-and-groups-a.png)

In this situation, we should request the owner to add our email address to the editor group. The editor group is a group that can be set up for each Google account. People in this group can be permitted to edit or view the dashboard.
The owner decides who has access to what.

 Once our e-mail address has been added by the owner, the grant access icon will disappear and we can continue with the configuration.

 So we want to copy the dashboard and create a new one based on it. Then select the three dots and then Make a copy. 
 
![dashboard_make_a_copy](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/dashboard-make-a-copy.png)

 If this is the first time you have made a dashboard, fill in your account settings step by step. Settings are only necessary for the first time.
 Select your country name and enter your company name.

 ![country_and_company](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/country-and-company.png)

Once the country and company are set, we tick the consents.
As you can see in the image below, these concern consent to receive emails, updates or looker studio improvements. Tick the ones you think you need.

![points of agreement](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/points-of-agreement.png)

## Copying the dashboard and setting the required parameters 

 To copy the dashboard on the basis of which you will create your own, select 3 dots again, make a copy and click ok to generate your dashboard 
 
![generate_your_dashboard](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/generate-your-dashboard.png)

 A dashboard template should appear but you don't see any data on it. Access to data is unauthorised, so you must authorise access.
 
![no_data](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/no-data.png)

Go to the resources tab located in the top left corner. In Resources, you can manage the data sources you have added. Select this option to go to the next page and edit your data sources for authorisation.
 
![manage_added_data_sources](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/manage-added-data-sources.png)

To manage the added data sources, go to the edit option to go through the subsequent authorisation steps. The editing panel is responsible for setting the parameters. The parameters are related to Allowing "Enter DQOps API Key for Looker Studio" to be modified in reports.
 
![edit](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/edit.png)
 
 We do not have access. As you can see Looker studio requires authorisation to connect to data. Click on Authorize.

 You will be prompted to select the account you want to log in with. Choose your LookerStudio account, which you selected at the beginning. 

 Select Authorize again and confirm access to the account by selecting 'Allow'

![select_authorize-s](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/select-authorize-s.png)


This is followed by a frame with the parameter settings. 
Select Allow' Enter DQO AI API Key for Looker Studio' to be modified in reports and click on reconnect in the top right corner.

![allow](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-s.png)

 A popup should appear asking' Allow parameter sharing?' if you want to agree to report editors will be able to modify the parameter values you have allowed and request additional or different data. Then choose allow
 
![allow_parameter_sharing](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-parameter-sharing.png)
 
 Another popup appears, "Apply Connection Changes?" click on Apply 
 
![apply_connection_changes](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/apply-connection-changes.png)
 
A page opens up where you can see the filters and parameters for the dashboard. The full list of data that can be used in dashboards is available in check results. If you want to see the data structure, how the data are stored in the dashboards go to check results [check_results](../../reference/parquetfiles/check_results.md)

![dimensions](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/dimensions.png)

 Click on DONE in the top right corner to return to the dashboard view.
 The next step for the correct operation and handling of the dashboard is to set the parameters correctly. To do this, go back to the Resources tab
 and select:' Manage report URL parameters'
 
![manage_report_URL_parameters](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/manage-report-url-parameters.png)
 
All parameters should be marked 'Allow to be modified in report URL'. Parameters can be used to create and maintain report view URLs. Then select close and return to the dashboard view. 

![allow_to_be_modified_in_report_URL](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/allow-to-be-modified-in-report-url.png)

 You should also specify who will have access to edit the dashboard and who will only have the viewer option. When you click on share, a popup will appear in the top right-hand corner
 allowing you to add email addresses. Alternatively, you can create an editing group and add employee addresses to the group and enter the group's email address in the popup 

![groups](https://dqops.com/docs/images/working-with-dqo/creating-custom-dashboard/groups.png)
 
Once you have gone through the configuration steps, you can edit the dasboard to suit your needs.

The last step, if we want our configured dashboard to be visible, is to add it to a file in DQOps. The current path to add the dashboard looks as follows: home/settings/dashboardslist.dqodashboards.yaml
This will make the dashboard visible in the user interface. 