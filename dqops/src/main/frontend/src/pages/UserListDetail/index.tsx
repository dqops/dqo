import { IconButton, Tooltip } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { DqoUserRolesModel, DqoUserRolesModelAccountRoleEnum } from '../../api';
import Button from '../../components/Button';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import Loader from '../../components/Loader';
import ClientSidePagination from '../../components/Pagination/ClientSidePagination'; // Import pagination component
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { UsersApi } from '../../services/apiClient';
import { ROUTES } from '../../shared/routes';
import ChangeUserPasswordDialog from './ChangeUserPasswordDialog';

export default function UserListDetail() {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const [dqoCloudUsers, setDqoCloudUsers] = useState<DqoUserRolesModel[]>([]);
  const [displayedUsers, setDisplayedUsers] = useState<DqoUserRolesModel[]>([]); // State for displayed users
  const [reshreshUsersIndicator, setRefreshUsersIndicator] =
    useState<boolean>(false);
  const [selectedEmailToChangePassword, setSelectedEmailToChangePassword] =
    useState('');
  const [selectedEmailToDelete, setSelectedEmailToDelete] = useState('');
  const [loading, setLoading] = useState(false);

  const dispatch = useActionDispatch();

  useEffect(() => {
    setLoading(true);
    UsersApi.getAllUsers()
      .then((res) => setDqoCloudUsers(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false));
  }, [reshreshUsersIndicator]);

  const editDqoCloudUser = (
    email: string,
    role?: DqoUserRolesModelAccountRoleEnum,
    dataDomainRoles?: { [key: string]: string }
  ) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.USER_DETAIL(email),
        value: ROUTES.USER_DETAIL_VALUE(email),
        label: `Edit user ${email}`,
        state: { create: false, email, role: role, dataDomainRoles }
      })
    );
  };

  const deleteDqoCloudUser = async () => {
    await UsersApi.deleteUser(selectedEmailToDelete)
      .then(() => setRefreshUsersIndicator(!reshreshUsersIndicator))
      .catch((err) => console.error(err));
  };

  const changeDqoCloudUserPassword = async (password: string) => {
    await UsersApi.changeUserPassword(selectedEmailToChangePassword, password)
      .then(() => setRefreshUsersIndicator(!reshreshUsersIndicator))
      .catch((err) => console.error(err));
    setSelectedEmailToChangePassword('');
  };

  const changePrincipalPassword = async (password: string) => {
    await UsersApi.changeCallerPassword(selectedEmailToChangePassword, password)
      .then(() => setRefreshUsersIndicator(!reshreshUsersIndicator))
      .catch((err) => console.error(err));
    setSelectedEmailToChangePassword('');
  };

  const addDqoCloudUser = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.USER_DETAIL('new'),
        value: ROUTES.USER_DETAIL_VALUE('new'),
        label: 'Create User',
        state: { create: true }
      })
    );
  };

  const canUserPerformActions =
    userProfile.license_type === 'TEAM' ||
    userProfile.license_type === 'ENTERPRISE' ||
    userProfile.can_manage_users === true;

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  return (
    <>
      <table className="w-full ">
        <thead className="border-b w-full border-b-gray-400 relative flex items-center text-sm">
          <th className="px-6 py-4 text-left block w-80">User email</th>
          <th className="px-6 py-4 text-left block w-40">User role</th>
          <th className="px-6 py-4 text-left block w-40 ml-10">Action</th>
          {userProfile.license_type?.toLowerCase() !== 'free' ? (
            <Button
              label="Add user"
              color="primary"
              variant="contained"
              className="absolute right-2 top-2 w-40"
              onClick={addDqoCloudUser}
              disabled={
                !!(
                  userProfile.users_limit &&
                  userProfile.users_limit <= dqoCloudUsers?.length &&
                  canUserPerformActions
                )
              }
            />
          ) : null}
        </thead>
        <tbody>
          {displayedUsers?.map((user, index) => (
            <tr key={index} className="flex items-center text-sm">
              <td className="px-6 py-2 text-left block w-80">{user.email}</td>
              <td className="px-6 py-2 text-left block w-40">
                {user.accountRole}
              </td>
              <td className="px-6 py-2 text-left block max-w-100">
                <div className="flex items-center gap-x-4 relative">
                  {userProfile.license_type?.toLowerCase() !== 'free' ? (
                    <IconButton
                      size="sm"
                      onClick={() =>
                        user.email
                          ? editDqoCloudUser(
                              user.email,
                              user.accountRole,
                              user.dataDomainRoles
                            )
                          : null
                      }
                      ripple={false}
                      color="teal"
                      className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                      disabled={!canUserPerformActions}
                    >
                      <Tooltip content="Modify">
                        <div>
                          <SvgIcon name="edit" className="w-4" />
                        </div>
                      </Tooltip>
                    </IconButton>
                  ) : (
                    <div className="w-24"></div>
                  )}

                  <IconButton
                    size="sm"
                    onClick={() =>
                      setSelectedEmailToChangePassword(user.email ?? '')
                    }
                    disabled={
                      userProfile.account_role !== 'admin' &&
                      !canUserPerformActions
                    }
                    color="teal"
                    className="!shadow-none hover:!shadow-none hover:bg-[#028770] relative"
                  >
                    <Tooltip content="Change password" className="">
                      <div>
                        <SvgIcon name="password_change" className="w-4" />
                      </div>
                    </Tooltip>
                  </IconButton>
                  <IconButton
                    size="sm"
                    onClick={() => setSelectedEmailToDelete(user.email ?? '')}
                    disabled={
                      !canUserPerformActions ||
                      !(
                        userProfile.user !== user.email &&
                        userProfile.license_type?.toLowerCase() !== 'free'
                      )
                    }
                    color="teal"
                    className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                  >
                    <Tooltip content="Delete">
                      <div>
                        <SvgIcon name="delete" className="w-4" />
                      </div>
                    </Tooltip>
                  </IconButton>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <ClientSidePagination
        items={dqoCloudUsers} // Pass the full list of users
        onChangeItems={setDisplayedUsers} // Update the displayed users based on pagination
      />
      <ConfirmDialog
        open={selectedEmailToDelete.length !== 0}
        onClose={() => setSelectedEmailToDelete('')}
        onConfirm={deleteDqoCloudUser}
        message={`Are you sure you want to delete ${selectedEmailToDelete} user?`}
      />
      <ChangeUserPasswordDialog
        open={selectedEmailToChangePassword.length !== 0}
        onClose={() => setSelectedEmailToChangePassword('')}
        handleSubmit={
          userProfile.user !== selectedEmailToChangePassword
            ? changeDqoCloudUserPassword
            : changePrincipalPassword
        }
      />
    </>
  );
}
