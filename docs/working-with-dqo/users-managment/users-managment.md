# Manage users

In configuration section you are able to manage DQO users. 

![Users-List](https://dqops.com/docs/images/working-with-dqo/users-managment/usersList2.png)

**There are 5 types of user roles:**

| User role                        | Description                                                                                                                                                                                                                   |
|:----------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Admin              | Administrator of the account who can manage users and perform all actions                     |
| Editor            | Editor who can configure and run data quality checksmaintenance.                                                                                                      |
| Operator           | The user can run data quality checks, but cannot make changes                 |
| Viewer               | Read-only viewer     |
| None | No access rights role |


Admin can add new users by clicking Add new user button, that will open then tab where it is possible to set users email and role. To add the user click the Save button

![Add-user](https://dqops.com/docs/images/working-with-dqo/users-managment/addUser2.png)

Users with admin role can also edit acounts by clinking edit button on existing user, then it will be possible to change role. DQO does not support changing users emails. After making changes click the Save button. Admin can also delete each user and change each user password by clicking change password button. This action will open the pop-up with random generated password. It is possible to provide your own password, if it will be at least 8 characters long and contain small letters, capital letters and digits. 

![Change-users-password](https://dqops.com/docs/images/working-with-dqo/users-managment/changePassword.png)