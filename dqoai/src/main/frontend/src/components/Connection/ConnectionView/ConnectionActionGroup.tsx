import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { ConnectionApiClient } from '../../../services/apiClient';

interface IConnectionActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
}

const ConnectionActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate
}: IConnectionActionGroupProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const { connectionBasic } = useSelector(
    (state: IRootState) => state.connection
  );

  const removeConnection = async () => {
    if (connectionBasic) {
      await ConnectionApiClient.deleteConnection(
        connectionBasic.connection_name ?? ''
      );
    }
  };

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      <Button
        variant="text"
        color="info"
        label="Delete"
        onClick={() => setIsOpen(true)}
      />
      <Button
        color={isUpdated && !isDisabled ? 'primary' : 'secondary'}
        variant="contained"
        label="Save"
        className="w-40"
        onClick={onUpdate}
        loading={isUpdating}
        disabled={isDisabled}
      />
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        connection={connectionBasic}
        onConfirm={removeConnection}
      />
    </div>
  );
};

export default ConnectionActionGroup;
