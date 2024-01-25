import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import { SharedCredentialsApi } from '../../services/apiClient'
import { SharedCredentialListModel } from '../../api'
import Button from '../../components/Button';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { ROUTES } from '../../shared/routes';
import SvgIcon from '../../components/SvgIcon';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import SharedCredentailTable from './SharedCredentailTable';

export default function SharedCredentialsDetail() {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const dispatch = useActionDispatch()
  
  const [sharedCredentialList, setSharedCredentialList] = useState<SharedCredentialListModel[]>();
  const [loading, setLoading] = useState(false)

  const getSharedCredentialList = async () => {

    await SharedCredentialsApi.getAllSharedCredentials()
      .then((res) => setSharedCredentialList(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false))
  }

  const addSharedCredential = async () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SHARED_CREDENTIALS_DETAIL("new"),
        value: ROUTES.SHARED_CREDENTIALS_DETAIL_VALUE("new"),
        label: "Add credential",
      })
    );
  }

  const deleteSharedCredential = async (credential: string) => {
    await SharedCredentialsApi.deleteSharedCredential(credential)
    .then(() => {
      setLoading(true)
      getSharedCredentialList()
    })
    .catch((err) => console.error(err))
      
  }

  useEffect(() => {
    setLoading(true)
    getSharedCredentialList()
  }, [])


  if (loading) {
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
            <thead className='border-b w-full border-b-gray-400 relative flex items-center'>
                <th className="px-6 py-4 text-left block w-100">Credential name</th>
                <th className="px-6 py-4 text-left block w-100">Credential type</th>
                <Button label='Add credential'
                 color='primary'
                 variant='contained'
                 className='absolute right-2 top-2 w-40'
                  onClick={addSharedCredential}
                />
            </thead>
            <SharedCredentailTable sharedCredentialList={sharedCredentialList ?? []} deleteSharedCredential={deleteSharedCredential}/> 
        </table>
        : 
        <div className='w-full h-screen flex items-center justify-center text-red-500 text-2xl'>
         Access denied
        </div> 
      }
    </DefinitionLayout>
  )
}
