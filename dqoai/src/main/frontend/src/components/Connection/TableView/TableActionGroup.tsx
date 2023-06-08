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
import { filter } from 'lodash';
import { getCollectingJobs } from '../../../redux/actions/job.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

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
  const dispatch = useActionDispatch();
  const setCollecting = (bool: boolean) => {
    dispatch(getCollectingJobs(bool));
  };

  const { deleteData } = useTree();
  const [isAddColumnDialogOpen, setIsAddColumnDialogOpen] = useState(false);
  const [loadingJob, setLoadingJob] = useState(false);
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const { jobs, isCollecting } = useSelector(
    (state: IRootState) => state.job || {}
  );
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
    filterJobs();
  }, [connection, schema, table, jobs?.jobs]);
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
      setCollecting(true);
      await JobApiClient.collectStatisticsOnTable(
        statistics?.collect_column_statistics_job_template
      );
    } finally {
      setLoadingJob(false);
    }
  };
  const filterJobs = () => {
    const filteredJobs = jobs?.jobs?.filter(
      (x) =>
        x.jobType === 'collect statistics' &&
        x.parameters?.collectStatisticsParameters
          ?.statisticsCollectorSearchFilters?.schemaTableName ===
          schema + '.' + table &&
        (x.status === DqoJobHistoryEntryModelStatusEnum.running ||
          x.status === DqoJobHistoryEntryModelStatusEnum.queued ||
          x.status === DqoJobHistoryEntryModelStatusEnum.waiting)
    );

    if (filteredJobs && filteredJobs.length > 0) {
      setCollecting(true);
      console.log(isCollecting);
    } else {
      setCollecting(false);
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
          label="Delete Table"
          onClick={() => setIsOpen(true)}
        />
      )}
      {collectStatistic && (
        <Button
          className="flex items-center gap-x-2 justify-center"
          label={isCollecting ? 'Collecting...' : 'Collect Statistic'}
          color={isCollecting ? 'secondary' : 'primary'}
          leftIcon={
            isCollecting ? <SvgIcon name="sync" className="w-4 h-4" /> : ''
          }
          onClick={() => {
            collectStatistics();
          }}
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
