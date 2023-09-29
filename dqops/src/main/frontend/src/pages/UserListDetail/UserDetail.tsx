import React, { useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import Input from '../../components/Input'
import { DqoCloudUserModelAccountRoleEnum } from '../../api'
import Select from '../../components/Select'
import { useSelector } from 'react-redux'
import { getFirstLevelSensorState } from '../../redux/selectors'
import Button from '../../components/Button'
import { UsersApi } from '../../services/apiClient'

export default function UserDetail() {
  const { create, email } = useSelector(
    getFirstLevelSensorState
  );
  const [userEmail, setUserEmail] = useState(email ?? "")
  const [userRole, setUserRole] = useState<DqoCloudUserModelAccountRoleEnum>()
  console.log(create, email, userEmail, userRole)

  const addDqoCloudUser = async () => {
    await UsersApi.createUser({email: userEmail, accountRole: userRole})
    .catch((err) => console.error(err))
  }

  const editDqoCloudUser = async () => {
    await UsersApi.updateUser(email, {accountRole: userRole})
    .catch((err) => console.error(err))
  }

  return (
    <DefinitionLayout>
      <div className='w-full border-b border-b-gray-400 flex justify-end '>  
        <Button label={(create===true || email === undefined) ? 'Add user' : 'Edit user'}
         color='primary'
         variant='contained'
         className=' w-40 mr-10 my-3'
         onClick={(create===true || email === undefined) ? addDqoCloudUser : editDqoCloudUser}
      /></div>
      <div className='w-100 px-5 mt-5'>
        <Input 
          label='Email' 
          value={userEmail}
          onChange={(e) => setUserEmail(e.target.value)}
          disabled={create !== true || email !== undefined }
        />     
        <Select
          label='Select user role'
          options={Object.values(DqoCloudUserModelAccountRoleEnum).map((x) => ({label: x, value: x}))}
          value={userRole}
          onChange={(value) => setUserRole(value)}
          className='my-5 '
        />
      </div>
    </DefinitionLayout>
  )
}
