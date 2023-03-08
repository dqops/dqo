import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { TableApiClient } from '../../../services/apiClient';
import { useTree } from "../../../contexts/treeContext";

interface ITableActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
  shouldDelete?: boolean;
}

const TableActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate,
  shouldDelete = true
}: ITableActionGroupProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const { tableBasic } = useSelector((state: IRootState) => state.table);
  const { deleteData } = useTree();

  const removeTable = async () => {
    if (tableBasic) {
      await TableApiClient.deleteTable(
        tableBasic.connection_name ?? '',
        tableBasic.target?.schema_name ?? '',
        tableBasic.target?.table_name ?? ''
      );

      const identify = `${tableBasic?.connection_name}.${tableBasic?.target?.schema_name}.${tableBasic?.target?.table_name}`;
      deleteData(identify);
    }
  };

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {shouldDelete && (
        <Button
          color="primary"
          variant="outlined"
          label="Delete Table"
          onClick={() => setIsOpen(true)}
        />
      )}
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

export default TableActionGroup;
