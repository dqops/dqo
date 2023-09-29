import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import { UsersApi } from '../../services/apiClient'
import { DqoCloudUserModel } from '../../api'
import Button from '../../components/Button'
import { useSelector } from 'react-redux'
import { IRootState } from '../../redux/reducers'
import ChangeUserPasswordDialog from './ChangeUserPasswordDialog'
import SvgIcon from '../../components/SvgIcon'
import { useActionDispatch } from '../../hooks/useActionDispatch'
import { addFirstLevelTab } from '../../redux/actions/definition.actions'
import { ROUTES } from '../../shared/routes'


export default function UserListDetail() {
    const { userProfile } = useSelector((state: IRootState) => state.job || {});
    const [dqoCloudUsers, setDqoCloudUsers] = useState<DqoCloudUserModel[]>([])
    const [reshreshUsersIndicator, setRefreshUsersIndicator] = useState<boolean>(false)
    const [selectedEmail, setSelectedEmail] = useState('')
    const [loading, setLoading] = useState(false)

    const dispatch = useActionDispatch()


    useEffect(() => {
        setLoading(true)
       UsersApi.getAllUsers()
       .then((res) => setDqoCloudUsers(res.data))
       .catch((err) => console.error(err))
       .finally(() => setLoading(false))
    }, [reshreshUsersIndicator])

    const editDqoCloudUser = (email: string) => {
        dispatch(
            addFirstLevelTab({
              url: ROUTES.USER_DETAIL(email),
              value: ROUTES.USER_DETAIL_VALUE(email),
              label: `Edit user ${email}`,
              state: {create: false, 
             email
              }
            })
          )
    }

    const deleteDqoCloudUser = async (email: string) => {
       await UsersApi.deleteUser(email)
       .then(() => setRefreshUsersIndicator(!reshreshUsersIndicator))
       .catch((err) => console.error(err))
    }

    //WRONG API REQUEST, FIX IT
    const changeDqoCloudUserPassword = async (password: string) => {
        await UsersApi.changeUserPassword(selectedEmail, password)
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
            <thead className='border-b w-full border-b-gray-400 relative'>
                <th className="px-6 py-4 text-left">User email</th>
                <th className="px-6 py-4 text-left">User role</th>
                <Button label='Add user'
                 color='primary'
                 variant='contained'
                 className='absolute right-2 top-2 w-40'
                 onClick={addDqoCloudUser}
                 disabled={!!(userProfile.users_limit && userProfile.users_limit <= dqoCloudUsers?.length)} 
                />
            </thead>
            <tbody>
                {dqoCloudUsers?.map((user, index) => 
                <tr key={index}>
                    <td className='px-6 py-2 text-left'>{user.email}</td>
                    <td className='px-6 py-2 text-left'>{user.accountRole}</td>
                    <td className='px-6 py-2 text-left'>
                        <Button label='edit' variant='text' color='primary' 
                        onClick={() =>user.email ?  editDqoCloudUser(user.email) : null}/>
                    </td>
                    <td className="px-6 py-2 text-left">
                        <Button label='delete' variant='text' color='primary' 
                        onClick={() => deleteDqoCloudUser(user.email ?? '')}/>
                    </td>
                    <td className="px-6 py-2 text-left">
                        <Button label='change password' variant='text' color='primary' onClick={() => setSelectedEmail(user.email ?? '')} 
                        disabled={userProfile.account_role !== "admin"}/>
                    </td>
                </tr>
                )}
            </tbody>
        </table>
        <ChangeUserPasswordDialog open={selectedEmail.length!==0} onClose={() => setSelectedEmail('')} handleSubmit={changeDqoCloudUserPassword}  />
    </DefinitionLayout>
  )
}
