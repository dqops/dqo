import React, { useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { JobApiClient, TableApiClient } from '../../../services/apiClient';
import { useTree } from '../../../contexts/treeContext';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import AddColumnDialog from '../../CustomTree/AddColumnDialog';

import {
  DqoJobHistoryEntryModelJobTypeEnum,
  DqoJobHistoryEntryModelStatusEnum,
  TableColumnsStatisticsModel
} from '../../../api';
import SvgIcon from '../../SvgIcon';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

interface ITableActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
  shouldDelete?: boolean;
  collectStatistic?: boolean;
  addSaveButton?: boolean;
  createDataStream?: boolean;
  maxToCreateDataStream?: boolean;
  createDataStreamFunc?: () => void;
  statistics?: TableColumnsStatisticsModel;
  selectedColumns?: string[]
}

const TableActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate,
  shouldDelete = true,
  collectStatistic,
  addSaveButton = true,
  createDataStream,
  maxToCreateDataStream,
  createDataStreamFunc,
  statistics,
  selectedColumns
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
  const [loadingJob, setLoadingJob] = useState(false);
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const { job_dictionary_state, userProfile } = useSelector(
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

  const collectStatistics = async () => {
      try {
        setLoadingJob(true);
        await JobApiClient.collectStatisticsOnTable(
          undefined,
          false,
          undefined,
          {
            ...statistics?.collect_column_statistics_job_template,
             columnNames: selectedColumns
          });
      } finally {
        setLoadingJob(false);
      }
  };

  const filteredCollectStatisticsJobs = Object.values(job_dictionary_state).filter(
    (x) =>
      x.jobType === DqoJobHistoryEntryModelJobTypeEnum.collect_statistics &&
      x.parameters?.collectStatisticsParameters
        ?.statistics_collector_search_filters?.fullTableName ===
        schema + '.' + table && 
         x.parameters?.collectStatisticsParameters?.statistics_collector_search_filters?.connection === 
        connection &&
      (x.status === DqoJobHistoryEntryModelStatusEnum.running ||
        x.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        x.status === DqoJobHistoryEntryModelStatusEnum.waiting)
  ).length !==0 

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {isSourceScreen && (
        <Button
          className="!h-10"
          color={!(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
          variant={!(userProfile.can_manage_data_sources !== true) ? "outlined" : "contained"}
          label="Add Column"
          onClick={() => setIsAddColumnDialogOpen(true)}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      )}
      {shouldDelete && (
        <Button
          className="!h-10"
          color={!(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
          variant={!(userProfile.can_manage_data_sources !== true) ? "outlined" : "contained"}
          label="Delete Table"
          onClick={() => setIsOpen(true)}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      )}
      {createDataStream && (
        <Button
          label="Create Data Grouping"
          color={!(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
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
      {collectStatistic && (
        <Button
          className="flex items-center gap-x-2 justify-center "
          label={
            filteredCollectStatisticsJobs
              ? 'Collecting...'
              : 
              selectedColumns?.length!== 0 ? 
              'Collect statistics on selected' : 
              'Collect Statistics'
          }
          color={
            filteredCollectStatisticsJobs
              ? 'secondary'
              : 'primary'
          }
          leftIcon={
            filteredCollectStatisticsJobs? (
              <SvgIcon name="sync" className="w-4 h-4 animate-spin" />
            ) : (
              ''
            )
          }
          onClick={collectStatistics}
          loading={loadingJob}
          disabled={userProfile.can_collect_statistics !== true || filteredCollectStatisticsJobs}
        />
      )}
      {addSaveButton && (
        <Button
          color={isUpdated && !isDisabled && !(userProfile.can_manage_data_sources !== true) ? 'primary' : 'secondary'}
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
