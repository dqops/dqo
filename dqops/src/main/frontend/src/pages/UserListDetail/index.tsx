import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import { UsersApi } from '../../services/apiClient'
import { DqoCloudUserModel } from '../../api'
import Button from '../../components/Button'
import { useSelector } from 'react-redux'
import { IRootState } from '../../redux/reducers'
import ChangeUserPasswordDialog from './ChangeUserPasswordDialog'


export default function UserListDetail() {
    const { userProfile } = useSelector((state: IRootState) => state.job || {});
    const [dqoCloudUsers, setDqoCloudUsers] = useState<DqoCloudUserModel[]>([])
    const [reshreshUsersIndicator, setRefreshUsersIndicator] = useState<boolean>(false)
    const [selectedEmail, setSelectedEmail] = useState('')


    useEffect(() => {
       UsersApi.getAllUsers()
       .then((res) => setDqoCloudUsers(res.data))
       .catch((err) => console.error(err))
    }, [reshreshUsersIndicator])

    const editDqoCloudUser = (
        //TODO: opening new tab
    ) => {}

    const deleteDqoCloudUser = async (email: string) => {
       await UsersApi.deleteUser(email)
       .then(() => setRefreshUsersIndicator(!reshreshUsersIndicator))
       .catch((err) => console.error(err))
    }

    const changeDqoCloudUserPassword =async ( newEmail: string) => {
        await UsersApi.updateUser(selectedEmail, {email: newEmail })
        .then(() => setRefreshUsersIndicator(!reshreshUsersIndicator))
        .catch((err) => console.error(err))
    }

    const addDqoCloudUser = () => {}


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
                    <td className='px-6 py-2 text-left'><Button label='edit' variant='text' color='primary' onClick={editDqoCloudUser}/></td>
                    <td className="px-6 py-2 text-left"><Button label='delete' variant='text' color='primary' onClick={() => deleteDqoCloudUser(user.email ?? '')}/></td>
                    <td className="px-6 py-2 text-left"><Button label='change password' variant='text' color='primary' onClick={() => setSelectedEmail(user.email ?? '')} disabled={userProfile.account_role !== "admin"}/></td>
                </tr>
                )}
            </tbody>
        </table>
        <ChangeUserPasswordDialog open={selectedEmail.length!==0} onClose={() => setSelectedEmail('')} handleSubmit={changeDqoCloudUserPassword}  />
    </DefinitionLayout>
  )
}
