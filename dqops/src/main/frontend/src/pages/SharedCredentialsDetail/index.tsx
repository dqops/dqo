import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { SharedCredentialListModel } from '../../api';
import Button from '../../components/Button';
import Loader from '../../components/Loader';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { updateTabLabel } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { SharedCredentialsApi } from '../../services/apiClient';
import SharedCredentailTable from './SharedCredentailTable';
import SingleSharedCredential from './SingleSharedCredential';

export default function SharedCredentialsDetail() {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const { activeTab } = useSelector((state: IRootState) => state.definition);

  const dispatch = useActionDispatch();

  const [sharedCredentialList, setSharedCredentialList] =
    useState<SharedCredentialListModel[]>();
  const [loading, setLoading] = useState(false);
  const [credentialToEdit, setCredentialToEdit] = React.useState<
    string | undefined
  >(undefined);
  const [createCredential, setCreateCredential] =
    React.useState<boolean>(false);
  const onBack = () => {
    setCredentialToEdit(undefined);
    setCreateCredential(false);
    dispatch(updateTabLabel('Shared credentials', activeTab ?? ''));
    getSharedCredentialList();
  };

  const getSharedCredentialList = async () => {
    await SharedCredentialsApi.getAllSharedCredentials()
      .then((res) => setSharedCredentialList(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false));
  };

  const addSharedCredential = async () => {
    setCreateCredential(true);
  };

  const deleteSharedCredential = async (credential: string) => {
    await SharedCredentialsApi.deleteSharedCredential(credential)
      .then(() => {
        setLoading(true);
        getSharedCredentialList();
      })
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    setLoading(true);
    getSharedCredentialList();
  }, []);

  if (loading) {
    return (
      <>
        <div className="w-full h-screen flex items-center justify-center">
          <Loader isFull={false} className="w-8 h-8 fill-green-700" />
        </div>
      </>
    );
  }

  return (
    <>
      {credentialToEdit || createCredential ? (
        <SingleSharedCredential
          onBack={onBack}
          credential_name={credentialToEdit}
        />
      ) : userProfile.can_manage_and_view_shared_credentials === true ? (
        <table className="w-full ">
          <thead className="border-b w-full border-b-gray-400 relative flex items-center text-sm">
            <th className="px-6 py-4 text-left block w-100">Credential name</th>
            <th className="px-6 py-4 text-left block w-100">Credential type</th>
            <th className="px-2 py-4 text-left block ml-11">Action</th>
            <Button
              label="Add credential"
              color="primary"
              variant="contained"
              className="absolute right-2 top-2 w-40"
              onClick={addSharedCredential}
            />
          </thead>
          <SharedCredentailTable
            sharedCredentialList={sharedCredentialList ?? []}
            deleteSharedCredential={deleteSharedCredential}
            setCredentialToEdit={setCredentialToEdit}
          />
        </table>
      ) : (
        <div className="w-full h-screen flex items-center justify-center text-red-500 text-2xl">
          Access denied
        </div>
      )}
    </>
  );
}
