import React, { useState } from 'react';
import Button from '../../components/Button';
import ConfirmDialog from './ConfirmDialog';
import { ColumnApiClient } from '../../services/apiClient';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../shared/routes';
import clsx from 'clsx';
import Loader from '../../components/Loader';
import AddColumnDialog from '../../components/CustomTree/AddColumnDialog';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

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
  const { checkTypes, connection, schema, table, column }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string } = useParams();
  const [isOpen, setIsOpen] = useState(false);
  const [isAddColumnDialogOpen, setIsAddColumnDialogOpen] = useState(false);
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const removeColumn = async () => {
    await ColumnApiClient.deleteColumn(
      connection ?? '',
      schema ?? '',
      table ?? '',
      column ?? ''
    );
  };

  const columnPath = `${connection}.${schema}.${table}.${column}`
  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {isSourceScreen && (
        <Button
          className="!h-10"
          color="primary"
          variant="outlined"
          label="Add Column"
          onClick={() => setIsAddColumnDialogOpen(true)}
          disabled={userProfile.can_manage_data_sources === false}
        />
      )}
      {shouldDelete && (
        <Button
          className="!h-10"
          color="primary"
          variant="outlined"
          label="Delete Column"
          onClick={() => setIsOpen(true)}
          disabled={userProfile.can_manage_data_sources === false}
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
            disabled={runningStatistics || userProfile.can_collect_statistics === false}
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
          disabled={isDisabled || userProfile.can_manage_data_sources === false}
          
        />
      )}
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        columnPath={columnPath}
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
