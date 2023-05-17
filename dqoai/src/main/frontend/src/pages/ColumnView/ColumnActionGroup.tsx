import React, { useState } from 'react';
import Button from '../../components/Button';
import ConfirmDialog from './ConfirmDialog';
import { useSelector } from 'react-redux';
import { ColumnApiClient } from '../../services/apiClient';
import { getFirstLevelState } from '../../redux/selectors';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../shared/routes';
import clsx from 'clsx';
import Loader from '../../components/Loader';
import AddColumnDialog from '../../components/CustomTree/AddColumnDialog';

interface IActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
  shouldDelete?: boolean;

  isStatistics?: boolean;
  onCollectStatistics?: () => void;
  runningStatistics?: boolean;
}

const ColumnActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate,
  shouldDelete = true,
  isStatistics,
  onCollectStatistics,
  runningStatistics
}: IActionGroupProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const [isOpen, setIsOpen] = useState(false);
  const { columnBasic } = useSelector(getFirstLevelState(checkTypes));
  const [isAddColumnDialogOpen, setIsAddColumnDialogOpen] = useState(false);
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
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
          label="Delete Column"
          onClick={() => setIsOpen(true)}
        />
      )}

      {isStatistics ? (
        <div className="flex items-center space-x-4">
          {runningStatistics && (
            <Loader isFull={false} className="w-8 h-8 !text-primary" />
          )}
          <Button
            color="primary"
            variant="outlined"
            label="Collect Statistics"
            className={clsx(
              '!h-10 disabled:bg-gray-500 disabled:border-none disabled:text-white whitespace-nowrap'
            )}
            onClick={onCollectStatistics}
            disabled={runningStatistics}
          />
        </div>
      ) : (
        <Button
          color={isUpdated && !isDisabled ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40 !h-10"
          onClick={onUpdate}
          loading={isUpdating}
          disabled={isDisabled}
        />
      )}
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        column={columnBasic}
        onConfirm={removeColumn}
      />
      <AddColumnDialog
        open={isAddColumnDialogOpen}
        onClose={() => setIsAddColumnDialogOpen(false)}
      />
    </div>
  );
};

export default ColumnActionGroup;
