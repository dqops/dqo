import React, { useEffect, useState } from 'react';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import {
  ColumnApiClient,
  JobApiClient,
  TableApiClient
} from '../../../services/apiClient';
import { useTree } from '../../../contexts/treeContext';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import AddColumnDialog from '../../CustomTree/AddColumnDialog';
import { AxiosResponse } from 'axios';
import {
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
}

const TableActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate,
  shouldDelete = true,
  collectStatistic,
  addSaveButton = true
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
  const { job_dictionary_state } = useSelector((state: IRootState) => state.job || {});
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<TableColumnsStatisticsModel> =
        await ColumnApiClient.getColumnsStatistics(connection, schema, table);
      setStatistics(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchColumns();
  }, [connection, schema, table]);
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
        statistics?.collect_column_statistics_job_template
      );
    } finally {
      setLoadingJob(false);
    }
  };

  const filteredJobs = Object.values(job_dictionary_state).filter(
    (x) =>
      x.jobType === 'collect statistics' &&
      x.parameters?.collectStatisticsParameters
        ?.statisticsCollectorSearchFilters?.schemaTableName ===
        schema + '.' + table &&
      (x.status === DqoJobHistoryEntryModelStatusEnum.running ||
        x.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        x.status === DqoJobHistoryEntryModelStatusEnum.waiting)
  );

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
      {collectStatistic && (
        <Button
          className="flex items-center gap-x-2 justify-center"
          label={
            filteredJobs?.find(
              (x) =>
                x.parameters?.collectStatisticsParameters
                  ?.statisticsCollectorSearchFilters?.schemaTableName ===
                schema + '.' + table
            )
              ? 'Collecting...'
              : 'Collect Statistic'
          }
          color={
            filteredJobs?.find(
              (x) =>
                x.parameters?.collectStatisticsParameters
                  ?.statisticsCollectorSearchFilters?.schemaTableName ===
                schema + '.' + table
            )
              ? 'secondary'
              : 'primary'
          }
          leftIcon={
            filteredJobs?.find(
              (x) =>
                x.parameters?.collectStatisticsParameters
                  ?.statisticsCollectorSearchFilters?.schemaTableName ===
                schema + '.' + table
            ) ? (
              <SvgIcon name="sync" className="w-4 h-4" />
            ) : (
              ''
            )
          }
          onClick={collectStatistics}
          loading={loadingJob}
        />
      )}
      {addSaveButton && (
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
