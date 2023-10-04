import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import { SharedCredentailsApi } from '../../services/apiClient'
import { SharedCredentialListModel } from '../../api'
import Button from '../../components/Button';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { ROUTES } from '../../shared/routes';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import SvgIcon from '../../components/SvgIcon';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

export default function SharedCredentailsDetail() {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const dispatch = useActionDispatch()
  
  const [sharedCredentialList, setSharedCredentialList] = useState<SharedCredentialListModel[]>();
  const [selectedSharedCredentialToDelete, setSelectedSharedCredentialToDelete] = useState<string>("")
  const [reshreshIndicator, setRefreshIndicator] = useState<boolean>(false)
  const [loading, setLoading] = useState(false)

  const getSharedCredentailList = async () => {

    await SharedCredentailsApi.getAllSharedCredentials()
      .then((res) => setSharedCredentialList(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false))
  }

  const addSharedCredential = async () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SHARED_CREDENTAILS_DETAIL("new"),
        value: ROUTES.SHARED_CREDENTAILS_DETAIL_VALUE("new"),
        label: "Add credential",
      })
    );
  }

  const updateSharedCredential = async (credential_name : string) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SHARED_CREDENTAILS_DETAIL(credential_name),
        value: ROUTES.SHARED_CREDENTAILS_DETAIL_VALUE(credential_name),
        label: "Edit credential",
        state: {
          credential_name
        }
      })
    );
  }

  const deleteSharedCredentail = async () => {
    await SharedCredentailsApi.deleteSharedCredential(selectedSharedCredentialToDelete ?? "")
    .then(() => setRefreshIndicator(!reshreshIndicator))
    .catch((err) => console.error(err))
      
  }

  const downlandSharedCredentail = async (credential: string) => {
    await SharedCredentailsApi.downloadSharedCredential(credential)
    .then((res) => console.log(res.data))
    .catch((err) => console.error(err))
      
  }

  useEffect(() => {
    setLoading(true)
    getSharedCredentailList()
  }, [reshreshIndicator])

  console.log(sharedCredentialList)

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
      {userProfile.can_manage_and_view_shared_credentials === true ? 
          <table className='w-full '>
            <thead className='border-b w-full border-b-gray-400 relative'>
                <th className="px-6 py-4 text-left">Credential name</th>
                <th className="px-6 py-4 text-left">Credential type</th>
                <Button label='Add credential'
                 color='primary'
                 variant='contained'
                 className='absolute right-2 top-2 w-40'
                  onClick={addSharedCredential}
                />
            </thead>
            <tbody>
                {sharedCredentialList?.map((credential, index) => 
                <tr key={index}>
                    <td className='px-6 py-2 text-left'>{credential.credential_name}</td>
                    <td className='px-6 py-2 text-left'>{credential.type}</td>
                    <td className='px-6 py-2 text-left'>
                        <Button label='edit' variant='text' color='primary' 
                        onClick={() => updateSharedCredential(credential.credential_name ?? "")}
                        />
                    </td>
                    <td className="px-6 py-2 text-left">
                        <Button label='delete' variant='text' color='primary' 
                        onClick={() => setSelectedSharedCredentialToDelete(credential.credential_name ?? "")}/>
                    </td>
                    <td className="px-6 py-2 text-left">
                        <Button label='downland' variant='text' color='primary' 
                        onClick={() => downlandSharedCredentail(credential.credential_name ?? "")} />
                    </td>
                </tr>
                )}
            </tbody>
        </table>
        : 
        <div className='w-full h-screen flex items-center justify-center text-red-500 text-2xl'>
         Access denied
        </div> 
      }
        <ConfirmDialog open={selectedSharedCredentialToDelete?.length!==0} onClose={() => setSelectedSharedCredentialToDelete('')} onConfirm={deleteSharedCredentail} message={`Are you sure you want to delete ${selectedSharedCredentialToDelete} credential?`}/>
    </DefinitionLayout>
  )
}
