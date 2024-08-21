import { Tooltip } from '@material-tailwind/react';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { useTree } from '../../../contexts/treeContext';
import { IRootState } from '../../../redux/reducers';
import { getFirstLevelActiveTab } from '../../../redux/selectors';
import { TableApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import Button from '../../Button';
import AddColumnDialog from '../../CustomTree/AddColumnDialog';
import SvgIcon from '../../SvgIcon';
import ConfirmDialog from './ConfirmDialog';

interface ITableActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
  shouldDelete?: boolean;
  addSaveButton?: boolean;
  createDataStream?: boolean;
  maxToCreateDataStream?: boolean;
  createDataStreamFunc?: () => void;
  collectStatistics?: () => Promise<void>;
  selectedColumns?: boolean;
  collectStatisticsSpinner?: boolean;
}

const TableActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate,
  shouldDelete = true,
  addSaveButton = true,
  createDataStream,
  maxToCreateDataStream,
  createDataStreamFunc,
  collectStatistics,
  selectedColumns,
  collectStatisticsSpinner
}: ITableActionGroupProps) => {
  const {
    checkTypes,
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const [isOpen, setIsOpen] = useState(false);

  const { deleteData } = useTree();
  const [isAddColumnDialogOpen, setIsAddColumnDialogOpen] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const { userProfile, job_allert } = useSelector(
    (state: IRootState) => state.job || {}
  );

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
          label="Add column"
          onClick={() => setIsAddColumnDialogOpen(true)}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      )}
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
          label="Delete table"
          onClick={() => setIsOpen(true)}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      )}
      {createDataStream && (
        <Button
          label="Create data grouping"
          color={
            !(userProfile.can_manage_data_sources !== true)
              ? 'primary'
              : 'secondary'
          }
          onClick={createDataStreamFunc}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      )}
      {maxToCreateDataStream && (
        <div className="flex items-center justify-center text-red-500 gap-x-2">
          {' '}
          (You can choose max 9 columns)
          <Button
            label="Create data grouping"
            color="secondary"
            className="text-black "
            disabled={userProfile.can_manage_data_sources !== true}
          />
        </div>
      )}
      {collectStatistics && (
        <Tooltip
          content={job_allert.tooltipMessage}
          className={
            job_allert.tooltipMessage
              ? 'max-w-60 py-2 px-2 bg-gray-800'
              : 'hidden'
          }
        >
          <div>
            <Button
              className="flex items-center gap-x-2 justify-center "
              label={
                collectStatisticsSpinner
                  ? 'Collecting...'
                  : selectedColumns
                  ? 'Collect statistics on selected'
                  : 'Collect statistics'
              }
              color={collectStatisticsSpinner ? 'secondary' : 'primary'}
              leftIcon={
                collectStatisticsSpinner ? (
                  <SvgIcon name="sync" className="w-4 h-4 animate-spin" />
                ) : (
                  ''
                )
              }
              onClick={collectStatistics}
              disabled={
                userProfile.can_collect_statistics !== true ||
                collectStatisticsSpinner
              }
              flashRedBorder={
                firstLevelActiveTab === job_allert.activeTab &&
                job_allert.action === 'collect_statistics'
                  ? true
                  : false
              }
            />
          </div>
        </Tooltip>
      )}
      {addSaveButton && (
        <Button
          color={
            isUpdated &&
            !isDisabled &&
            !(userProfile.can_manage_data_sources !== true)
              ? 'primary'
              : 'secondary'
          }
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
