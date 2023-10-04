import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { closeFirstLevelTab } from '../../redux/actions/definition.actions';
import { SharedCredentailsApi } from '../../services/apiClient';
import RadioButton from '../../components/RadioButton';
import Button from '../../components/Button';
import { SharedCredentialModel, SharedCredentialModelTypeEnum } from '../../api';
import Input from '../../components/Input';
import FieldTypeTextarea from '../../components/Connection/ConnectionView/FieldTypeTextarea';

export default function SingleSharedCredential() {
    const { credential_name } = useSelector(getFirstLevelSensorState);
    const [credentialName, setCredentialName] = useState("")
    const [editingCredential, setEditingCredential] = useState<SharedCredentialModel>()
    const [type, setType] = useState<SharedCredentialModelTypeEnum>("text")
    const [textAreaValue, setTextAreaValue] = useState<string>("")

    const dispatch = useActionDispatch()

    const addSharedCredential = async () => {
      await SharedCredentailsApi.createSharedCredential()
      .catch((err) => console.error(err))
      .then(() => 
      dispatch(
        closeFirstLevelTab('/definitions/shared-credentail/new')
      ))
    }
  
    const editSharedCredential = async () => {
        await SharedCredentailsApi.updateSharedCredential(credential_name)
      .catch((err) => console.error(err))
      .then(() => 
      dispatch(
        closeFirstLevelTab('/definitions/shared-credentail/' + credential_name)
      ))
    }

    useEffect(() => {
        if(credential_name){
           async () => await SharedCredentailsApi.getSharedCredential(credential_name)
                .then((res) => setEditingCredential(res.data))
                .catch((err) => console.error(err))
            }
    }, [])

    useEffect(() => {
        if(editingCredential){
            setType(editingCredential.type ?? "text")
            setTextAreaValue((editingCredential.type === "text" ? 
            editingCredential.text_value : editingCredential.binary_value) ?? "")
            }
    }, [credential_name, editingCredential])

    

  return (
    <DefinitionLayout>
        <div className='w-full border-b border-b-gray-400 flex justify-end '>  
        <Button label={credential_name ? 'Edit shared credential' : 'Add shared credential'}
         color='primary'
         variant='contained'
         className='w-55 mr-10 my-3'
         onClick={credential_name ? editSharedCredential : addSharedCredential}
        />
      </div>
      <div className='w-100 px-5 '>
        <div className='text-lg py-3'>
          Credential name:  
        </div>
        <Input value={credentialName} onChange={(e) => setCredentialName(e.target.value)}/>
        <div className='text-lg py-3'>
          Type of credential:  
        </div>
        <div className='flex items-center space-x-4'>
            <RadioButton label='Text' checked={type === "text"} onClick={() => setType("text")}/>
            <RadioButton label='Binary' checked={type === "binary"} onClick={() => setType("binary")}/>
        </div>
        <FieldTypeTextarea className='w-300 h-300 mt-4' value={textAreaValue} onChange={(value) => setTextAreaValue(value)}/>
    </div>
    </DefinitionLayout>
  )
}
