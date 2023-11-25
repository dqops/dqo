# User and access management

DQOps allows you to manage user roles which determine who can operate data quality checks and edit them. The level of 
access control varies depending on the type of license and is only available in multi-user accounts.

**There are five types of user roles available:**

| User role | Description                                                                                  |
|:----------|:---------------------------------------------------------------------------------------------|
| Admin     | An Admin has complete control over the account and can manage users and perform all actions. |
| Editor    | An Editor can configure and run data quality checks.                                         |
| Operator  | An Operator can run data quality checks but is unable to make changes.                       |
| Viewer    | Viewer has read-only access to the data quality checks.                                      |
| None      | And None represents no access rights role.                                                   |

To manage user, go to the **Configuration** section and click on **Manage users** from the tree view on the left.
This will display a list of all users along with their roles, and options to edit, delete and change passwords. 
Note that only a user with an Admin role can change other users, and options to edit or delete would be inactive for 
other users. If you are using a single-user license, you will see only one user.


![Users-List](https://dqops.com/docs/images/working-with-dqo/users-managment/userList3.png)


## Add a new user

Only an Admin has the authority to add a new user. To do so, click on the **Add user** button. This will open a new tab 
where you can enter a new email address and set a password. Once you have entered the details, click the **Add user** button
again to create the new user account.


![Add-user](https://dqops.com/docs/images/working-with-dqo/users-managment/addUser2.png)

## Change user role

Users with admin roles can edit existing user accounts by clicking on the edit button next to the user's name. This will
enable you to change the user's role. Please note that DQO does not support changing a user's email address. Once you have
made the desired changes, click the Save button to save the new settings.

## Delete a user

An admin can also delete user and change user's password by clicking on the change password button. This will open the
pop-up with a random generated password. You may choose to enter your own password, provided that it is at least 8 characters
long and contains both uppercase and lowercase letters as well as digits.

![Change-users-password](https://dqops.com/docs/images/working-with-dqo/users-managment/changePassword.png)