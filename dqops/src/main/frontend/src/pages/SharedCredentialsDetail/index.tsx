import React, { useEffect, useState } from 'react'
import DefinitionLayout from '../../components/DefinitionLayout'
import { SharedCredentailsApi } from '../../services/apiClient'
import { SharedCredentialListModel } from '../../api'
import Button from '../../components/Button';

export default function SharedCredentailsDetail() {
  
  const [sharedCredentailList, setSharedCredentailList] = useState<SharedCredentialListModel[]>();


  const getSharedCredentailList = async () => {
    await SharedCredentailsApi.getAllSharedCredentials()
      .then((res) => setSharedCredentailList(res.data))
  }

  const addSharedCredential = async () => {
    await SharedCredentailsApi.createSharedCredential({credential_name: "xyz", type: "text", text_value: "1234567"})
  }



  useEffect(() => {
    getSharedCredentailList()
  }, [])

  console.log(sharedCredentailList)

  return (
    <DefinitionLayout>
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
                {sharedCredentailList?.map((credential, index) => 
                <tr key={index}>
                    <td className='px-6 py-2 text-left'>{credential.credential_name}</td>
                    <td className='px-6 py-2 text-left'>{credential.type}</td>
                    <td className='px-6 py-2 text-left'>
                        <Button label='edit' variant='text' color='primary' />
                    </td>
                    <td className="px-6 py-2 text-left">
                        <Button label='delete' variant='text' color='primary' />
                    </td>
                    <td className="px-6 py-2 text-left">
                        <Button label='downland' variant='text' color='primary'  />
                    </td>
                </tr>
                )}
            </tbody>
        </table>
    </DefinitionLayout>
  )
}
