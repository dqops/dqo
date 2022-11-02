import React, { useEffect, useMemo, useState } from 'react';
import { ITreeNode } from '../../../shared/interfaces';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Tabs from '../../Tabs';
import TableDetails from './TableDetails';
import ScheduleDetail from './ScheduleDetail';
import TableColumns from './TableColumns';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  CommentSpec,
  DimensionsConfigurationSpec,
  RecurringScheduleSpec,
  TableBasicModel,
  TimeSeriesConfigurationSpec,
  UIAllChecksModel
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableBasic,
  getTableChecksUI,
  getTableComments,
  getTableDimensions,
  getTableLabels,
  getTableSchedule,
  getTableTime,
  updateTableBasic,
  updateTableChecksUI,
  updateTableComments,
  updateTableDimensions,
  updateTableLabels,
  updateTableSchedule,
  updateTableTime
} from '../../../redux/actions/table.actions';
import CommentsView from '../CommentsView';
import LabelsView from '../LabelsView';
import DataQualityChecks from '../../DataQualityChecks';
import { useHistory } from 'react-router-dom';
import qs from 'query-string';
import { useTabs } from '../../../contexts/tabContext';
import DimensionsView from '../DimensionsView';

interface ITableViewProps {
  node: ITreeNode;
}

const tabs = [
  {
    label: 'Table',
    value: 'table'
  },
  {
    label: 'Schedule',
    value: 'schedule'
  },
  {
    label: 'Data Quality Checks',
    value: 'data-quality-checks'
  },
  {
    label: 'Comments',
    value: 'comments'
  },
  {
    label: 'Labels',
    value: 'labels'
  },
  {
    label: 'Columns',
    value: 'columns'
  },
  {
    label: 'Dimensions',
    value: 'dimensions'
  }
];

const TableView = ({ node }: ITableViewProps) => {
  const [activeTab, setActiveTab] = useState('table');

  const {
    tableBasic,
    schedule,
    timeSeries,
    comments,
    labels,
    checksUI,
    isUpdating,
    dimensions
  } = useSelector((state: IRootState) => state.table);
  const { tabMap, setTabMap } = useTabs();

  const connectionName = node.key.split('.')[1] || '';
  const schemaName = node.key.split('.')[2] || '';
  const tableName = node.module;

  const [updatedTableBasic, setUpdatedTableBasic] = useState<TableBasicModel>();
  const [updatedSchedule, setUpdatedSchedule] =
    useState<RecurringScheduleSpec>();
  const [updatedTimeSeries, setUpdatedTimeSeries] =
    useState<TimeSeriesConfigurationSpec>();
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [updatedDimensions, setUpdatedDimensions] =
    useState<DimensionsConfigurationSpec>();
  const dispatch = useActionDispatch();
  const history = useHistory();

  useEffect(() => {
    setUpdatedTableBasic(tableBasic);
  }, [tableBasic]);

  useEffect(() => {
    setUpdatedSchedule(schedule);
  }, [schedule]);

  useEffect(() => {
    setUpdatedTimeSeries(timeSeries);
  }, [timeSeries]);

  useEffect(() => {
    setUpdatedComments(comments);
  }, [comments]);
  useEffect(() => {
    setUpdatedLabels(labels);
  }, [labels]);

  useEffect(() => {
    setUpdatedChecksUI(checksUI);
  }, [checksUI]);

  useEffect(() => {
    setUpdatedDimensions(dimensions);
  }, [dimensions]);

  useEffect(() => {
    setUpdatedTableBasic(undefined);
    setUpdatedSchedule(undefined);
    setUpdatedTimeSeries(undefined);
    setUpdatedComments([]);
    setUpdatedLabels([]);
    setUpdatedDimensions(undefined);

    dispatch(getTableBasic(connectionName, schemaName, tableName));
    dispatch(getTableSchedule(connectionName, schemaName, tableName));
    dispatch(getTableTime(connectionName, schemaName, tableName));
    dispatch(getTableComments(connectionName, schemaName, tableName));
    dispatch(getTableLabels(connectionName, schemaName, tableName));
    dispatch(getTableChecksUI(connectionName, schemaName, tableName));
    dispatch(getTableDimensions(connectionName, schemaName, tableName));

    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName,
      table: tableName
    });

    history.replace(`/connection?${searchQuery}`);
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (activeTab === 'table') {
      await dispatch(
        updateTableBasic(
          connectionName,
          schemaName,
          tableName,
          updatedTableBasic
        )
      );
      await dispatch(getTableBasic(connectionName, schemaName, tableName));
    }
    if (activeTab === 'schedule') {
      await dispatch(
        updateTableSchedule(
          connectionName,
          schemaName,
          tableName,
          updatedSchedule
        )
      );
      await dispatch(getTableSchedule(connectionName, schemaName, tableName));
    }
    if (activeTab === 'time') {
      await dispatch(
        updateTableTime(
          connectionName,
          schemaName,
          tableName,
          updatedTimeSeries
        )
      );
      await dispatch(getTableTime(connectionName, schemaName, tableName));
    }
    if (activeTab === 'comments') {
      await dispatch(
        updateTableComments(
          connectionName,
          schemaName,
          tableName,
          updatedComments
        )
      );
      await dispatch(getTableComments(connectionName, schemaName, tableName));
    }
    if (activeTab === 'labels') {
      await dispatch(
        updateTableLabels(connectionName, schemaName, tableName, updatedLabels)
      );
      await dispatch(getTableLabels(connectionName, schemaName, tableName));
    }
    if (activeTab === 'data-quality-checks') {
      await dispatch(
        updateTableChecksUI(
          connectionName,
          schemaName,
          tableName,
          updatedChecksUI
        )
      );
      await dispatch(getTableChecksUI(connectionName, schemaName, tableName));
    }
    if (activeTab === 'dimensions') {
      await dispatch(
        updateTableDimensions(
          connectionName,
          schemaName,
          tableName,
          updatedDimensions
        )
      );
      await dispatch(getTableDimensions(connectionName, schemaName, tableName));
    }
  };

  const onChangeTab = (tab: string) => {
    setActiveTab(tab);
    setTabMap({
      ...tabMap,
      [node.module]: tab
    });
  };

  useEffect(() => {
    if (tabMap[node.module]) {
      setActiveTab(tabMap[node.module]);
    }
  }, [node, tabMap]);

  const isDisabled = useMemo(() => {
    if (activeTab === 'labels') {
      return updatedLabels.some((label) => !label);
    }

    return false;
  }, [updatedLabels]);

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{node.module}</div>
        </div>
        <Button
          color="primary"
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          loading={isUpdating}
          disabled={isDisabled}
        />
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      <div>
        {activeTab === 'table' && (
          <TableDetails
            tableBasic={updatedTableBasic}
            setTableBasic={setUpdatedTableBasic}
          />
        )}
      </div>
      <div>
        {activeTab === 'schedule' && (
          <ScheduleDetail
            schedule={updatedSchedule}
            setSchedule={setUpdatedSchedule}
          />
        )}
      </div>
      <div>
        {activeTab === 'data-quality-checks' && (
          <DataQualityChecks
            checksUI={updatedChecksUI}
            onChange={setUpdatedChecksUI}
          />
        )}
      </div>
      <div>
        {activeTab === 'comments' && (
          <CommentsView
            comments={updatedComments}
            onChange={setUpdatedComments}
          />
        )}
      </div>
      <div>
        {activeTab === 'labels' && (
          <LabelsView labels={updatedLabels} onChange={setUpdatedLabels} />
        )}
      </div>
      <div>
        {activeTab === 'columns' && (
          <TableColumns
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={node.module}
          />
        )}
      </div>
      <div>
        {activeTab === 'dimensions' && (
          <DimensionsView
            dimensions={updatedDimensions}
            onChange={setUpdatedDimensions}
          />
        )}
      </div>
    </div>
  );
};

export default TableView;
