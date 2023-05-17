import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { TableApiClient } from '../../../services/apiClient';
import { useTree } from "../../../contexts/treeContext";
import { useParams } from "react-router-dom";
import { CheckTypes } from "../../../shared/routes";
import AddColumnDialog from '../../CustomTree/AddColumnDialog';

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
  const { checkTypes, connection, schema, table }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useParams()
  const [isOpen, setIsOpen] = useState(false);
  const { deleteData } = useTree();
  const [isAddColumnDialogOpen, setIsAddColumnDialogOpen] = useState(false);
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;

  const fullPath = `${connection}.${schema}.${table}`;

  const removeTable = async () => {
    await TableApiClient.deleteTable(
      connection ?? '',
      schema ?? '',
      table ?? ''
    );

    deleteData(fullPath);
  };

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {isSourceScreen && (
        <Button
          className="!h-10"
          color="primary"
          variant="outlined"
          label="Add Column"
          onClick={() => setIsAddColumnDialogOpen(true)}
        />
      )}
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
        tablePath={fullPath}
        onConfirm={removeTable}
      />
      <AddColumnDialog
        open={isAddColumnDialogOpen}
        onClose={() => setIsAddColumnDialogOpen(false)}
      />
    </div>
  );
};

export default TableActionGroup;
