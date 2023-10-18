import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import { UsersApi } from '../../services/apiClient'
import { DqoCloudUserModel, DqoCloudUserModelAccountRoleEnum } from '../../api'
import Button from '../../components/Button'
import { useSelector } from 'react-redux'
import { IRootState } from '../../redux/reducers'
import ChangeUserPasswordDialog from './ChangeUserPasswordDialog'
import SvgIcon from '../../components/SvgIcon'
import { useActionDispatch } from '../../hooks/useActionDispatch'
import { addFirstLevelTab } from '../../redux/actions/definition.actions'
import { ROUTES } from '../../shared/routes'
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog'


export default function UserListDetail() {
    const { userProfile } = useSelector((state: IRootState) => state.job || {});
    const [dqoCloudUsers, setDqoCloudUsers] = useState<DqoCloudUserModel[]>([])
    const [reshreshUsersIndicator, setRefreshUsersIndicator] = useState<boolean>(false)
    const [selectedEmailToChangePassword, setSelectedEmailToChangePassword] = useState('')
    const [selectedEmailToDelete, setSelectedEmailToDelete] = useState('')
    const [loading, setLoading] = useState(false)

    const dispatch = useActionDispatch()


    useEffect(() => {
        setLoading(true)
        UsersApi.getAllUsers()
        .then((res) => setDqoCloudUsers(res.data))
        .catch((err) => console.error(err))
        .finally(() => setLoading(false))
    }, [reshreshUsersIndicator])

    const editDqoCloudUser = (email: string, role?: DqoCloudUserModelAccountRoleEnum) => {
        dispatch(
            addFirstLevelTab({
              url: ROUTES.USER_DETAIL(email),
              value: ROUTES.USER_DETAIL_VALUE(email),
              label: `Edit user ${email}`,
              state: {create: false, 
             email,
             role: role
              }
            })
          )
    }

    const deleteDqoCloudUser = async () => {
       await UsersApi.deleteUser(selectedEmailToDelete)
       .then(() => setRefreshUsersIndicator(!reshreshUsersIndicator))
       .catch((err) => console.error(err))
    }

    const changeDqoCloudUserPassword = async (password: string) => {
        await UsersApi.changeUserPassword(selectedEmailToChangePassword, password)
        .then(() => setRefreshUsersIndicator(!reshreshUsersIndicator))
        .catch((err) => console.error(err))
    }

    const addDqoCloudUser = () => {
         dispatch(
           addFirstLevelTab({
             url: ROUTES.USER_DETAIL("new"),
             value: ROUTES.USER_DETAIL_VALUE("new"),
             label: "Create User",
             state: {create: true
             }
           })
         )
    }
    if(loading){
        return(
        <DefinitionLayout>
            <div className='w-full h-screen flex items-center justify-center'>
             <SvgIcon name='sync' className='w-6 h-6 animate-spin'/>
            </div>
        </DefinitionLayout>
    )}

  return (
    <DefinitionLayout>
        <table className='w-full '>
            <thead className='border-b w-full border-b-gray-400 relative flex items-center'>
                <th className="px-6 py-4 text-left block w-100">User email</th>
                <th className="px-6 py-4 text-left block w-50">User role</th>
                <Button label='Add user'
                 color='primary'
                 variant='contained'
                 className='absolute right-2 top-2 w-40'
                 onClick={addDqoCloudUser}
                 disabled={!!(userProfile.users_limit && userProfile.users_limit <= dqoCloudUsers?.length  &&
                 (userProfile.license_type === 'TEAM' || userProfile.license_type === 'ENTERPRISE') || userProfile.can_manage_users !== true)} 
                />
            </thead>
            <tbody>
                {dqoCloudUsers?.map((user, index) => 
                <tr key={index} className='flex items-center'>
                    <td className='px-6 py-2 text-left block w-100'>{user.email}</td>
                    <td className='px-6 py-2 text-left block w-50'>{user.accountRole}</td>
                    <td className='px-6 py-2 text-left block max-w-100'>
                        <Button label='edit' variant='text' color='primary' 
                        onClick={() =>user.email ?  editDqoCloudUser(user.email, user.accountRole) : null}
                        disabled={!(userProfile.license_type === 'TEAM' || userProfile.license_type === 'ENTERPRISE' || userProfile.can_manage_users === true)}
                        />
                    </td>
                    <td className="px-6 py-2 text-left block max-w-100">
                        <Button label='delete' variant='text' color='primary' 
                        onClick={() => setSelectedEmailToDelete(user.email ?? '')}
                        disabled={!(userProfile.license_type === 'TEAM' || userProfile.license_type === 'ENTERPRISE' || userProfile.can_manage_users === true)}/>
                    </td>
                    <td className="px-6 py-2 text-left block max-w-100">
                        <Button label='change password' variant='text' color='primary' onClick={() => setSelectedEmailToChangePassword(user.email ?? '')} 
                        disabled={userProfile.account_role !== "admin" && !(userProfile.license_type === 'TEAM' || userProfile.license_type === 'ENTERPRISE' || userProfile.can_manage_users === true)}/>
                    </td>
                </tr>
                )}
            </tbody>
        </table>
        <ConfirmDialog open={selectedEmailToDelete.length!==0} onClose={() => setSelectedEmailToDelete('')} onConfirm={deleteDqoCloudUser} message={`Are you sure you want to delete ${selectedEmailToDelete} user?`}/>
        <ChangeUserPasswordDialog open={selectedEmailToChangePassword.length!==0} onClose={() => setSelectedEmailToChangePassword('')} handleSubmit={changeDqoCloudUserPassword}  />
    </DefinitionLayout>
  )
}
