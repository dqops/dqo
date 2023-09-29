import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import { UsersApi } from '../../services/apiClient'
import { DqoCloudUserModel } from '../../api'
import Button from '../../components/Button'


export default function UserListDetail() {
    const [dqoCloudUsers, setDqoCloudUsers] = useState<DqoCloudUserModel[]>()

    useEffect(() => {
       UsersApi.getAllUsers()
       .then((res) => setDqoCloudUsers(res.data))
       .catch((err) => console.error(err))
       , []
    })

    const editDqoCloudUser = () => {}

    const deleteDqoCloudUser = () => {}

    const changeDqoCloudUserPassword = () => {}

  return (
    <DefinitionLayout>
        <table className='w-full'>
            <thead className='border-b w-full border-b-gray-400'>
                <th className="px-6 py-4 text-left">User email</th>
                <th className="px-6 py-4 text-left">User role</th>
            </thead>
            <tbody>
                {dqoCloudUsers?.map((x, index) => 
                <tr key={index}>
                    <td className='px-6 py-2 text-left'>{x.email}</td>
                    <td className='px-6 py-2 text-left'>{x.accountRole}</td>
                    <td className='px-6 py-2 text-left'><Button label='edit' onClick={editDqoCloudUser}/></td>
                    <td className="px-6 py-2 text-left"><Button label='delete' onClick={deleteDqoCloudUser}/></td>
                    <td className="px-6 py-2 text-left"><Button label='change password' onClick={changeDqoCloudUserPassword}/></td>
                </tr>
                )}
            </tbody>
        </table>
    </DefinitionLayout>
  )
}
