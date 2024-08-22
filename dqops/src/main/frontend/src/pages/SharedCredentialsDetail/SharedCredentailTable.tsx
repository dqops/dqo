import { IconButton } from '@material-tailwind/react';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { SharedCredentialListModel } from '../../api';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { IRootState } from '../../redux/reducers';

type TSharedCredentialTableProps = {
  sharedCredentialList: SharedCredentialListModel[];
  deleteSharedCredential: (credential: string) => Promise<void>;
  setCredentialToEdit: (credential: string) => void;
  filter?: boolean;
};

export default function SharedCredentailTable({
  sharedCredentialList,
  deleteSharedCredential,
  setCredentialToEdit
}: TSharedCredentialTableProps) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const [
    selectedSharedCredentialToDelete,
    setSelectedSharedCredentialToDelete
  ] = useState<string>('');
  const dispatch = useActionDispatch();
  const updateSharedCredential = async (credential_name: string) => {
    setCredentialToEdit(credential_name);
  };

  return (
    <tbody>
      {sharedCredentialList?.map((credential, index) => (
        <tr key={index} className="flex items-center text-sm">
          <td
            className="px-6 py-2 text-left block w-100 text-teal-500 cursor-pointer"
            onClick={() =>
              updateSharedCredential(credential.credential_name ?? '')
            }
          >
            {credential.credential_name}
          </td>
          <td className="px-6 py-2 text-left block w-100">{credential.type}</td>
          <td className="px-2 py-2 text-left block max-w-100">
            <IconButton
              size="sm"
              onClick={() =>
                updateSharedCredential(credential.credential_name ?? '')
              }
              color="teal"
              className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
              disabled={userProfile.can_manage_definitions !== true}
            >
              <SvgIcon name="edit" className="w-4" />
            </IconButton>
          </td>
          <td className="px-2 py-2 text-left block max-w-100">
            <a
              href={`/api/credentials/${credential.credential_name}/download`}
              rel="noreferrer"
              target="_blank"
              className="text-teal-500"
            >
              <IconButton
                size="sm"
                disabled={userProfile.can_manage_definitions !== true}
                color="teal"
                className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
              >
                <SvgIcon name="download" className="w-4" />
              </IconButton>
            </a>
          </td>
          <td className="px-2 py-2 text-left block max-w-100">
            <IconButton
              size="sm"
              onClick={() =>
                setSelectedSharedCredentialToDelete(
                  credential.credential_name ?? ''
                )
              }
              disabled={userProfile.can_manage_definitions !== true}
              color="teal"
              className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
            >
              <SvgIcon name="delete" className="w-4" />
            </IconButton>
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
