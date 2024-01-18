# Access management
DQOps supports managing access to users, to enable splitting the roles between data quality editors, operation teams, and data stakeholders.

## Overview

DQOps supports managing users and assigning roles. The list of users and roles is stored in the DQOps Cloud environment.

DQOps instance can work in two modes.

- **personal** instance that is started locally and is accessible locally. A personal instance will not use any authentication.

- **production** instance that is accessible over the network and is used by multiple users. A production instance delegates authentication to DQOps Cloud.
  DQOps Cloud can be integrated with an SSO authentication provider that supports SAML 2.0 or OpenID Connect.


!!! note "Access management in multi-user environments"

    User management in DQOps is a feature provided in the DQOps Cloud platform.
    Only **TEAM** and **ENTERPRISE** DQOps licenses provide support for managing multiple users and granting roles
    Please see the [DQOps pricing](https://dqops.com/pricing/) for details.


## Roles
Each user can be assigned one of the built-in roles in the system.

| User role | Description                                                                                                                                 |
|:----------|:--------------------------------------------------------------------------------------------------------------------------------------------|
| Admin     | An Admin has complete control over the account and can manage users and perform all actions.                                                |
| Editor    | An Editor can connect new data sources, import table metadata, configure and run data quality checks.                                       |
| Operator  | An Operator can configure and run data quality checks but is unable to manage connections to data sources.                                  |
| Viewer    | A Viewer has read-only access to the data quality checks, viewing the configuration of data quality checks and all data quality dashboards. |
| None      | User is denied access to the system.                                                                                                        |


## Managing users

Users are managed in the **Configuration** section, under the **Manage users** tree on the left.
The **Manage users** screen shows a list of all users along with their roles and options to edit, delete, and change passwords. 
Note that only a user with an Admin role can change other users, and options to edit or delete would be inactive for 
other users. If you are using a single-user license, you will see only one user.

![Users-List](https://dqops.com/docs/images/working-with-dqo/users-managment/userList3.png)


## Add a new user

Only an Admin has the authority to add a new user. To do so, click on the **Add user** button. This will open a new tab 
where you can enter the email address and set the password. Once you have entered the details, click the **Add user** button
again to create the new user account.

![Add-user](https://dqops.com/docs/images/working-with-dqo/users-managment/addUser2.png)

## Change a user's role

Users with admin roles can edit existing user accounts by clicking on the edit button next to the user's name. This will
enable you to change the user's role. Please note that DQOps does not support changing a user's email address. Once you have
made the desired changes, click the Save button to save the new settings.

## Change a user's password
An administrator can also change a user's password by clicking on the change password button. This will open the
pop-up with a randomly generated password. You may choose to enter your own password, provided that it is at least eight characters long
and contains both uppercase and lowercase letters and digits.

![Change-users-password](https://dqops.com/docs/images/working-with-dqo/users-managment/changePassword.png)

## What's next

- Check the detailed manual of [how to run data quality checks using the check editor](run-data-quality-checks.md).
- Learn how to [review the data quality results on dashboards](review-the-data-quality-results-on-dashboards.md) to identify all tables and columns affected by issues. 