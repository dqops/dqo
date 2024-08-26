import clsx from 'clsx';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import Button from '../../components/Button';
import AddColumnDialog from '../../components/CustomTree/AddColumnDialog';
import SvgIcon from '../../components/SvgIcon';
import { IRootState } from '../../redux/reducers';
import { ColumnApiClient } from '../../services/apiClient';
import { useDecodedParams } from '../../utils';
import ConfirmDialog from './ConfirmDialog';

interface IActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
  shouldDelete?: boolean;
  isStatistics?: boolean;
  onCollectStatistics?: () => void;
  collectStatisticsSpinner?: boolean;
}

const ColumnActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate,
  shouldDelete = true,
  isStatistics,
  onCollectStatistics,
  collectStatisticsSpinner
}: IActionGroupProps) => {
  const {
    connection,
    schema,
    table,
    column
  }: {
    connection: string;
    schema: string;
    table: string;
    column: string;
  } = useDecodedParams();
  const [isOpen, setIsOpen] = useState(false);
  const [isAddColumnDialogOpen, setIsAddColumnDialogOpen] = useState(false);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const removeColumn = async () => {
    await ColumnApiClient.deleteColumn(
      connection ?? '',
      schema ?? '',
      table ?? '',
      column ?? ''
    );
  };

  const columnPath = `${connection}.${schema}.${table}.${column}`;

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {shouldDelete && (
        <Button
          className="!h-10"
          color={
            !(userProfile.can_manage_data_sources !== true)
              ? 'primary'
              : 'secondary'
          }
          variant={
            !(userProfile.can_manage_data_sources !== true)
              ? 'outlined'
              : 'contained'
          }
          label="Delete column"
          onClick={() => setIsOpen(true)}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      )}

      {isStatistics ? (
        <div className="flex items-center space-x-4">
          <Button
            color={collectStatisticsSpinner ? 'secondary' : 'primary'}
            label={
              collectStatisticsSpinner ? 'Collecting...' : 'Collect statistics'
            }
            className={clsx('!h-10 whitespace-nowrap gap-x-2 ')}
            leftIcon={
              collectStatisticsSpinner ? (
                <SvgIcon name="sync" className="w-4 h-4 animate-spin" />
              ) : (
                ''
              )
            }
            onClick={onCollectStatistics}
            disabled={
              userProfile.can_collect_statistics !== true ||
              collectStatisticsSpinner
            }
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
          disabled={isDisabled || userProfile.can_manage_data_sources !== true}
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
