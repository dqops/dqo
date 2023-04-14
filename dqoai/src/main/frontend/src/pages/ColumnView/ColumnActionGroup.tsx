import React, { useState } from 'react';
import Button from '../../components/Button';
import ConfirmDialog from './ConfirmDialog';
import { useSelector } from 'react-redux';
import { ColumnApiClient } from '../../services/apiClient';
import { getFirstLevelState } from "../../redux/selectors";
import { useParams } from "react-router-dom";
import { CheckTypes } from "../../shared/routes";

interface IActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
  shouldDelete?: boolean;
}

const ColumnActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate,
  shouldDelete = true
}: IActionGroupProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const [isOpen, setIsOpen] = useState(false);
  const { columnBasic } = useSelector(getFirstLevelState(checkTypes));

  const removeColumn = async () => {
    if (columnBasic) {
      await ColumnApiClient.deleteColumn(
        columnBasic?.connection_name ?? '',
        columnBasic?.table?.schema_name ?? '',
        columnBasic.table?.table_name ?? '',
        columnBasic.column_name ?? ''
      );
    }
  };

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {shouldDelete && (
        <Button
          className="!h-10"
          color="primary"
          variant="outlined"
          label="Delete Column"
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
        column={columnBasic}
        onConfirm={removeColumn}
      />
    </div>
  );
};

export default ColumnActionGroup;
