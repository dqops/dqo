import React, { useEffect, useMemo, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import qs from 'query-string';
import { ITreeNode } from '../../../shared/interfaces';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Tabs from '../../Tabs';
import { IRootState } from '../../../redux/reducers';
import { ColumnBasicModel, CommentSpec, UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getColumnBasic,
  getColumnChecksUi,
  getColumnComments,
  getColumnLabels,
  updateColumnBasic,
  updateColumnCheckUI,
  updateColumnComments,
  updateColumnLabels
} from '../../../redux/actions/column.actions';
import CommentsView from '../CommentsView';
import LabelsView from '../LabelsView';
import ColumnDetails from './ColumnDetails';
import DataQualityChecks from '../../DataQualityChecks';
import { useTabs } from '../../../contexts/tabContext';

interface IColumnViewProps {
  node: ITreeNode;
}

const tabs = [
  {
    label: 'Column',
    value: 'column'
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
  }
];

const ColumnView = ({ node }: IColumnViewProps) => {
  const [activeTab, setActiveTab] = useState('column');

  const { columnBasic, comments, labels, isUpdating, checksUI } = useSelector(
    (state: IRootState) => state.column
  );
  const history = useHistory();
  const { tabMap, setTabMap } = useTabs();

  const { connectionName, schemaName, tableName, columnName } = useMemo(() => {
    const connectionName = node.key.split('.')[1] || '';
    const schemaName = node.key.split('.')[2] || '';
    const tableName = node.key.split('.')[3] || '';
    const columnName = node.module;

    return { connectionName, schemaName, tableName, columnName };
  }, [node]);

  const [updatedColumnBasic, setUpdatedColumnBasic] =
    useState<ColumnBasicModel>();
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const dispatch = useActionDispatch();

  useEffect(() => {
    setUpdatedComments(comments);
  }, [comments]);
  useEffect(() => {
    setUpdatedLabels(labels);
  }, [labels]);
  useEffect(() => {
    setUpdatedColumnBasic(columnBasic);
  }, [columnBasic]);

  useEffect(() => {
    setUpdatedChecksUI(checksUI);
  }, [checksUI]);

  useEffect(() => {
    setUpdatedComments([]);
    setUpdatedLabels([]);

    dispatch(getColumnBasic(connectionName, schemaName, tableName, columnName));
    dispatch(
      getColumnComments(connectionName, schemaName, tableName, columnName)
    );
    dispatch(
      getColumnLabels(connectionName, schemaName, tableName, columnName)
    );
    dispatch(
      getColumnChecksUi(connectionName, schemaName, tableName, columnName)
    );
    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName,
      table: tableName,
      column: columnName
    });

    history.replace(`/connection?${searchQuery}`);
  }, [connectionName, schemaName, tableName, columnName]);

  const onUpdate = async () => {
    if (activeTab === 'column') {
      await dispatch(
        updateColumnBasic(
          connectionName,
          schemaName,
          tableName,
          columnName,
          updatedColumnBasic
        )
      );
    }
    if (activeTab === 'comments') {
      await dispatch(
        updateColumnComments(
          connectionName,
          schemaName,
          tableName,
          columnName,
          updatedComments
        )
      );
      await dispatch(
        getColumnComments(connectionName, schemaName, tableName, columnName)
      );
    }
    if (activeTab === 'labels') {
      await dispatch(
        updateColumnLabels(
          connectionName,
          schemaName,
          tableName,
          columnName,
          updatedLabels
        )
      );
      await dispatch(
        getColumnLabels(connectionName, schemaName, tableName, columnName)
      );
    }
    if (activeTab === 'data-quality-checks') {
      await dispatch(
        updateColumnCheckUI(
          connectionName,
          schemaName,
          tableName,
          columnName,
          updatedComments
        )
      );
      await dispatch(
        getColumnChecksUi(connectionName, schemaName, tableName, columnName)
      );
    }
  };

  useEffect(() => {
    if (tabMap[node.module]) {
      setActiveTab(tabMap[node.module]);
    }
  }, [node, tabMap]);

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2">
        <div className="flex items-center space-x-2">
          <SvgIcon name="column" className="w-5 h-5" />
          <div className="text-xl font-semibold">{node.module}</div>
        </div>
        <Button
          color="primary"
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          loading={isUpdating}
        />
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      <div>
        {activeTab === 'column' && (
          <ColumnDetails
            columnBasic={updatedColumnBasic}
            setColumnBasic={setUpdatedColumnBasic}
          />
        )}
        <div>
          {activeTab === 'data-quality-checks' && (
            <DataQualityChecks
              checksUI={updatedChecksUI}
              onChange={setUpdatedChecksUI}
            />
          )}
        </div>
        {activeTab === 'comments' && (
          <CommentsView
            comments={updatedComments}
            onChange={setUpdatedComments}
          />
        )}
        {activeTab === 'labels' && (
          <LabelsView labels={updatedLabels} onChange={setUpdatedLabels} />
        )}
      </div>
    </div>
  );
};

export default ColumnView;
