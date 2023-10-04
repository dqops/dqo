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
import { IRootState } from '../../redux/reducers';

export default function SingleSharedCredential() {
    const { userProfile } = useSelector((state: IRootState) => state.job || {});
    const { credential_name } = useSelector(getFirstLevelSensorState);
    const [credentialName, setCredentialName] = useState("")
    const [editingCredential, setEditingCredential] = useState<SharedCredentialModel>()
    const [type, setType] = useState<SharedCredentialModelTypeEnum>("text")
    const [textAreaValue, setTextAreaValue] = useState<string>("")
    const [incorrectBinaryText, setIncorrectBinaryText] = useState(false)

    const dispatch = useActionDispatch()

    const addSharedCredential = async () => {
        if (type ==="text"){
            await SharedCredentailsApi.createSharedCredential({credential_name: credentialName, type, text_value: textAreaValue})
            .catch((err) => console.error(err))
        } else {
            await SharedCredentailsApi.createSharedCredential({credential_name: credentialName, type, binary_value: textAreaValue})
            .catch((err) => console.error(err))

        }
        dispatch(closeFirstLevelTab('/definitions/shared-credentail/new'))
    }
  
    const editSharedCredential = async () => {
        if (type ==="text"){
            await SharedCredentailsApi.updateSharedCredential(credential_name, {credential_name, type, text_value: textAreaValue})
            .catch((err) => console.error(err))

        } else {
                await SharedCredentailsApi.updateSharedCredential(credential_name, {credential_name, type, binary_value: textAreaValue})
                .catch((err) => console.error(err)) 
           
        }
      
      dispatch(
        closeFirstLevelTab('/definitions/shared-credentail/' + credential_name)
      )
    }

    const getSharedCredential = async () => {
       await SharedCredentailsApi.getSharedCredential(credential_name)
            .then((res) => setEditingCredential(res.data))
            .catch((err) => console.error(err))
    }


    useEffect(() => {
        if (credential_name) {
            getSharedCredential()
        }
    }, [])

    useEffect(() => {
        if (editingCredential) {
            setCredentialName(credential_name)
            setType(editingCredential.type ?? "text")
            setTextAreaValue((editingCredential.type === "text" ? 
                editingCredential.text_value : editingCredential.binary_value) ?? "")
        }
    }, [credential_name, editingCredential])

    useEffect(() => {
        isBinaryString(textAreaValue)
    }, [textAreaValue, type])


    const isBinaryString = (input: string) => {
        const binaryPattern = /^[01]+$/;
        if (binaryPattern.test(input) === false && type === "binary") {
            setIncorrectBinaryText(true)
        } else {
            setIncorrectBinaryText(false)
        } 
    }
    

  return (
    <DefinitionLayout>
    {userProfile.can_manage_and_view_shared_credentials === true ? 
    <>
        <div className='w-full border-b border-b-gray-400 flex justify-between'>
        <div className="text-xl font-semibold truncate flex items-center pl-5 space-x-2">
            <div>
                Shared credential: 
            </div>
                <Input value={credentialName} onChange={(e) => setCredentialName(e.target.value)} disabled={credential_name}/>
        </div>
        <Button label={credential_name ? 'Edit shared credential' : 'Add shared credential'}
         color='primary'
         variant='contained'
         className='w-55 mr-10 my-3'
         onClick={credential_name ? editSharedCredential : addSharedCredential}
         disabled={incorrectBinaryText === true}
        />
      </div>
      <div className='w-100 px-5 '>
        <div className='text-lg py-3'>
          Type of credential:  
        </div>
        <div className='flex items-center space-x-4'>
            <RadioButton label='Text' checked={type === "text"} onClick={() => setType("text")}/>
            <RadioButton label='Binary' checked={type === "binary"} onClick={() => setType("binary")}/>
        </div>
        {incorrectBinaryText ? <div className='text-red-500 pt-5 text-xl'>Incorrect binary code</div> : null}
        <FieldTypeTextarea className='w-300 h-300 mt-4' value={textAreaValue} onChange={(value) => setTextAreaValue(value)}/>
        </div>
    </>
    : 
    <div className='w-full h-screen flex items-center justify-center text-red-500 text-2xl'>
     Access denied
    </div> 
  }
    </DefinitionLayout>
  )
}
