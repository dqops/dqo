import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { TableApiClient } from '../../../services/apiClient';
import { useTree } from '../../../contexts/treeContext';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import AddColumnDialog from '../../CustomTree/AddColumnDialog';
import SvgIcon from '../../SvgIcon';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

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
  collectStatisticsSpinner?: boolean
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
  } = useParams();
  const [isOpen, setIsOpen] = useState(false);

  const { deleteData } = useTree();
  const [isAddColumnDialogOpen, setIsAddColumnDialogOpen] = useState(false);
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const { userProfile } = useSelector(
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
          label="Add Column"
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
          label="Delete Table"
          onClick={() => setIsOpen(true)}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      )}
      {createDataStream && (
        <Button
          label="Create Data Grouping"
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
            label="Create Data Grouping"
            color="secondary"
            className="text-black "
            disabled={userProfile.can_manage_data_sources !== true}
          />
        </div>
      )}
      {collectStatistics && (
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
        />
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
