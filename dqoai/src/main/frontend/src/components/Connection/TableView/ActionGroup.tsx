import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { TableApiClient } from '../../../services/apiClient';

interface IActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
}

const ActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate
}: IActionGroupProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const { tableBasic } = useSelector((state: IRootState) => state.table);

  const removeTable = async () => {
    if (tableBasic) {
      await TableApiClient.deleteTable(
        tableBasic.connection_name ?? '',
        tableBasic.target?.schema_name ?? '',
        tableBasic.target?.table_name ?? ''
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
        table={tableBasic}
        onConfirm={removeTable}
      />
    </div>
  );
};

export default ActionGroup;
