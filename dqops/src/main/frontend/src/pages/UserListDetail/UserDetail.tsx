import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import Input from '../../components/Input'
import { DqoCloudUserModelAccountRoleEnum } from '../../api'
import Select from '../../components/Select'
import { useSelector } from 'react-redux'
import { getFirstLevelSensorState } from '../../redux/selectors'
import Button from '../../components/Button'
import { UsersApi } from '../../services/apiClient'
import { useActionDispatch } from '../../hooks/useActionDispatch'
import { closeFirstLevelTab } from '../../redux/actions/definition.actions'

export default function UserDetail() {
  const { create, email, role } = useSelector(getFirstLevelSensorState);
  const [userEmail, setUserEmail] = useState<string>(email)
  const [userRole, setUserRole] = useState<DqoCloudUserModelAccountRoleEnum>(role)
  const [isUpdated, setIsUpdated] = useState(false)
  const [message, setMessage] = useState<string>()

  const dispatch = useActionDispatch()

  const addDqoCloudUser = async () => {
    await UsersApi.createUser({email: userEmail, accountRole: userRole})
    .catch((err) => console.error(err)).then(() => 
    dispatch(
      closeFirstLevelTab('/definitions/user/new')
    ))
  }

  const editDqoCloudUser = async () => {
    await UsersApi.updateUser(String(email), {accountRole: userRole, email: String(email)})
    .catch((err) => console.error(err)).then(() => 
    dispatch(
      closeFirstLevelTab('/definitions/user/' + email)
    ))
  }

  useEffect(() => {
    if(create === true){
      if(userEmail && userEmail.length !== 0 && 
        !userEmail.match(/[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}/g  )){
        setMessage("Incorrect email format")
      }else{
        setMessage(undefined)
      }
    }
  }, [userEmail])


  return (
    <>
      <div className='w-full border-b border-b-gray-400 flex justify-end '>  
        <Button label={'Save'}
         color='primary'
         variant='contained'
         className=' w-40 mr-10 my-3'
         onClick={(create===true || email === undefined) ? addDqoCloudUser : editDqoCloudUser}
         disabled={!(isUpdated && userRole && userEmail && !message)}
      /></div>
      <div className='w-100 px-5 mt-5'>
        <Input 
          label='Email' 
          value={userEmail}
          onChange={(e) => {setIsUpdated(true), setUserEmail(e.target.value)}}
          disabled={create !== true || email !== undefined }
        />     
       {message ? <div className='text-red-500'>{message}</div> : null}
        <Select
          label='Select user role'
          options={Object.values(DqoCloudUserModelAccountRoleEnum).map((x) => ({label: x, value: x}))}
          value={userRole}
          onChange={(value) => {setIsUpdated(true), setUserRole(value)}}
          className='my-5 '
        />
      </div>
    </>
  )
}
