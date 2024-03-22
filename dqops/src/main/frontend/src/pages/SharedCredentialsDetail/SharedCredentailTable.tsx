import React, { useState } from 'react';
import { SharedCredentialListModel } from '../../api';
import Button from '../../components/Button';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { ROUTES } from '../../shared/routes';

type TSharedCredentialTableProps = {
  sharedCredentialList: SharedCredentialListModel[];
  deleteSharedCredential: (credential: string) => Promise<void>;
  filter?: boolean;
};

export default function SharedCredentailTable({
  sharedCredentialList,
  deleteSharedCredential
}: TSharedCredentialTableProps) {
  const [
    selectedSharedCredentialToDelete,
    setSelectedSharedCredentialToDelete
  ] = useState<string>('');
  const dispatch = useActionDispatch();
  const updateSharedCredential = async (credential_name: string) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SHARED_CREDENTIALS_DETAIL(credential_name),
        value: ROUTES.SHARED_CREDENTIALS_DETAIL_VALUE(credential_name),
        label: 'Edit credential',
        state: {
          credential_name
        }
      })
    );
  };

  return (
    <tbody>
      {sharedCredentialList?.map((credential, index) => (
        <tr key={index} className="flex items-center text-sm">
          <td className="px-6 py-2 text-left block w-100">
            {credential.credential_name}
          </td>
          <td className="px-6 py-2 text-left block w-100">{credential.type}</td>
          <td className="px-6 py-2 text-left block max-w-100">
            <Button
              label="edit"
              variant="text"
              color="primary"
              onClick={() =>
                updateSharedCredential(credential.credential_name ?? '')
              }
            />
          </td>
          <td className="px-6 py-2 text-left block max-w-100">
            <Button
              label="delete"
              variant="text"
              color="primary"
              onClick={() =>
                setSelectedSharedCredentialToDelete(
                  credential.credential_name ?? ''
                )
              }
            />
          </td>
          <td className="px-6 py-2 text-left block max-w-100">
            {/* <Button label='download' variant='text' color='primary'
            onClick={() => downloadSharedCredential(credential.credential_name ?? "")} /> */}
            <a
              href={`/api/credentials/${credential.credential_name}/download`}
              rel="noreferrer"
              target="_blank"
              className="text-teal-500"
            >
              download
            </a>
          </td>
        </tr>
      ))}
      <ConfirmDialog
        open={selectedSharedCredentialToDelete?.length !== 0}
        onClose={() => setSelectedSharedCredentialToDelete('')}
        onConfirm={() =>
          deleteSharedCredential(selectedSharedCredentialToDelete)
        }
        message={`Are you sure you want to delete ${selectedSharedCredentialToDelete} credential?`}
      />
    </tbody>
  );
}
