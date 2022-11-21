import React, { useEffect, useMemo, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Tabs from '../../Tabs';
import TableDetails from './TableDetails';
import ScheduleDetail from './ScheduleDetail';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  CommentSpec,
  DataStreamMappingSpec,
  RecurringScheduleSpec,
  TableBasicModel,
  TimeSeriesConfigurationSpec,
  UIAllChecksModel
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableBasic,
  getTableAdHocChecksUI,
  getTableComments,
  getTableDataStreamMapping,
  getTableLabels,
  getTableSchedule,
  getTableTime,
  updateTableBasic,
  updateTableAdHocChecksUI,
  updateTableComments,
  updateTableDataStreamMapping,
  updateTableLabels,
  updateTableSchedule,
  updateTableTime
} from '../../../redux/actions/table.actions';
import CommentsView from '../CommentsView';
import LabelsView from '../LabelsView';
import DataQualityChecks from '../../DataQualityChecks';
import { useHistory } from 'react-router-dom';
import qs from 'query-string';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useTree } from '../../../contexts/treeContext';
import TimestampsView from './TimestampsView';

interface ITableViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
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
    label: 'Data Streams',
    value: 'data-streams'
  },
  {
    label: 'Timestamps',
    value: 'timestamps'
  }
];

const TableView = ({
  connectionName,
  schemaName,
  tableName
}: ITableViewProps) => {
  const [activeTab, setActiveTab] = useState('table');

  const {
    tableBasic,
    schedule,
    timeSeries,
    comments,
    labels,
    checksUI,
    isUpdating,
    dataStreamsMapping
  } = useSelector((state: IRootState) => state.table);
  const { activeTab: pageTab, tabMap, setTabMap } = useTree();

  const [updatedTableBasic, setUpdatedTableBasic] = useState<TableBasicModel>();
  const [updatedSchedule, setUpdatedSchedule] =
    useState<RecurringScheduleSpec>();
  const [updatedTimeSeries, setUpdatedTimeSeries] =
    useState<TimeSeriesConfigurationSpec>();
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [updatedDataStreamMapping, setUpdatedDataStreamMapping] =
    useState<DataStreamMappingSpec>();
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
    setUpdatedDataStreamMapping(dataStreamsMapping);
  }, [dataStreamsMapping]);

  useEffect(() => {
    setUpdatedTableBasic(undefined);
    setUpdatedSchedule(undefined);
    setUpdatedTimeSeries(undefined);
    setUpdatedComments([]);
    setUpdatedLabels([]);
    setUpdatedDataStreamMapping(undefined);

    dispatch(getTableBasic(connectionName, schemaName, tableName));
    dispatch(getTableSchedule(connectionName, schemaName, tableName));
    dispatch(getTableTime(connectionName, schemaName, tableName));
    dispatch(getTableComments(connectionName, schemaName, tableName));
    dispatch(getTableLabels(connectionName, schemaName, tableName));
    dispatch(getTableAdHocChecksUI(connectionName, schemaName, tableName));
    dispatch(getTableDataStreamMapping(connectionName, schemaName, tableName));

    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName,
      table: tableName
    });

    history.replace(`/?${searchQuery}`);
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (activeTab === 'table' || activeTab === 'timestamps') {
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
        updateTableAdHocChecksUI(
          connectionName,
          schemaName,
          tableName,
          updatedChecksUI
        )
      );
      await dispatch(getTableAdHocChecksUI(connectionName, schemaName, tableName));
    }
    if (activeTab === 'data-streams') {
      await dispatch(
        updateTableDataStreamMapping(
          connectionName,
          schemaName,
          tableName,
          updatedDataStreamMapping
        )
      );
      await dispatch(
        getTableDataStreamMapping(connectionName, schemaName, tableName)
      );
    }
  };

  const onChangeTab = (tab: string) => {
    setActiveTab(tab);
    setTabMap({
      ...tabMap,
      [pageTab]: tab
    });
  };

  useEffect(() => {
    if (tabMap[pageTab]) {
      setActiveTab(tabMap[pageTab]);
    } else {
      setActiveTab('table');
    }
  }, [pageTab, tabMap]);

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
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName}`}</div>
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
        {activeTab === 'data-streams' && (
          <DataStreamsMappingView
            dataStreamsMapping={updatedDataStreamMapping}
            onChange={setUpdatedDataStreamMapping}
          />
        )}
      </div>
      <div>
        {activeTab === 'timestamps' && (
          <TimestampsView
            columnsSpec={updatedTableBasic?.timestamp_columns}
            onChange={(columns) =>
              setUpdatedTableBasic({
                ...updatedTableBasic,
                timestamp_columns: columns
              })
            }
          />
        )}
      </div>
    </div>
  );
};

export default TableView;
