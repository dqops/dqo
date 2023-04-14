import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { useSelector } from 'react-redux';
import { TableApiClient } from '../../../services/apiClient';
import { useTree } from "../../../contexts/treeContext";
import { getFirstLevelState } from "../../../redux/selectors";
import { useParams } from "react-router-dom";
import { CheckTypes } from "../../../shared/routes";

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
  const { checkTypes }: { checkTypes: CheckTypes } = useParams()
  const [isOpen, setIsOpen] = useState(false);
  const { tableBasic } = useSelector(getFirstLevelState(checkTypes));
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
          className="!h-10"
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
        className="w-40 !h-10"
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
