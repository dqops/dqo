import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { DqoUserRolesModelAccountRoleEnum } from '../../api';
import Button from '../../components/Button';
import DataDomains from '../../components/DataDomains/DataDomains';
import Input from '../../components/Input';
import Loader from '../../components/Loader';
import Select from '../../components/Select';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { updateTabLabel } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { UsersApi } from '../../services/apiClient';

export default function UserDetail() {
  const { create, email } = useSelector(getFirstLevelSensorState);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const [userEmail, setUserEmail] = useState<string>(email);
  const [userRole, setUserRole] = useState<
    DqoUserRolesModelAccountRoleEnum | undefined
  >(undefined);
  const [userDataDomainRoles, setUserDataDomainRoles] = useState<
    | {
        [key: string]: string;
      }
    | undefined
  >({});
  const [isUpdated, setIsUpdated] = useState(false);
  const [message, setMessage] = useState<string>();
  const [creating, setCreating] = useState(false);
  const [loading, setLoading] = useState(false);
  const onCHangeDataDomainRoles = (dataDomainRoles: {
    [key: string]: string;
  }) => {
    setUserDataDomainRoles(dataDomainRoles);
    setIsUpdated(true);
  };
  const dispatch = useActionDispatch();

  const addDqoCloudUser = async () => {
    setCreating(true);
    await UsersApi.createUser({
      email: userEmail,
      accountRole: userRole,
      dataDomainRoles: userDataDomainRoles
    })
      .catch((err) => console.error(err))
      .then(() =>
        UsersApi.getUser(userEmail).then((res) => {
          dispatch(
            updateTabLabel(`Edit ${userEmail}`, '/definitions/user/new', {
              create: false,
              dataDomainRoles: res.data.dataDomainRoles,
              role: res.data.accountRole,
              email: userEmail
            })
          );
          setCreating(false);
          setIsUpdated(false);
        })
      );
  };

  const editDqoCloudUser = async () => {
    await UsersApi.updateUser(String(email), {
      accountRole: userRole,
      email: String(email),
      dataDomainRoles: userDataDomainRoles
    })
      .catch((err) => console.error(err))
      .then(() => setIsUpdated(false));
  };

  useEffect(() => {
    if (create === true) {
      if (
        userEmail &&
        userEmail.length !== 0 &&
        !userEmail.match(/[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}/g)
      ) {
        setMessage('Incorrect email format');
      } else {
        setMessage(undefined);
      }
    } else {
      setLoading(true);
      UsersApi.getUser(userEmail)
        .then((res) => {
          setUserRole(res.data?.accountRole);
          setUserDataDomainRoles(res.data?.dataDomainRoles);
        })
        .finally(() => setLoading(false));
    }
  }, [userEmail]);

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  return (
    <div className="text-sm">
      <div className="w-full border-b border-b-gray-400 flex justify-end ">
        <Button
          label={'Save'}
          color={isUpdated ? 'primary' : 'secondary'}
          variant="contained"
          className=" w-40 mr-10 my-3"
          onClick={
            create === true || email === undefined
              ? addDqoCloudUser
              : editDqoCloudUser
          }
          disabled={!(userRole && userEmail && !message)}
          loading={creating}
        />
      </div>
      <div className="w-100 px-5 mt-5">
        <Input
          label="Email"
          value={userEmail}
          onChange={(e) => {
            setIsUpdated(true), setUserEmail(e.target.value);
          }}
          disabled={create !== true || email !== undefined}
        />
        {message ? <div className="text-red-500">{message}</div> : null}
        <Select
          label="Select user role"
          options={Object.values(DqoUserRolesModelAccountRoleEnum).map((x) => ({
            label: x,
            value: x
          }))}
          value={userRole}
          onChange={(value) => {
            setIsUpdated(true), setUserRole(value);
          }}
          className="my-5 "
          menuClassName="top-[56px]"
        />
      </div>
      {userProfile.can_use_data_domains && (
        <DataDomains
          dataDomainRoles={userDataDomainRoles}
          onChange={onCHangeDataDomainRoles}
        />
      )}
    </div>
  );
}
