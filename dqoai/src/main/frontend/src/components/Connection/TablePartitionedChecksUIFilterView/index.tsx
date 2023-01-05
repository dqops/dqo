import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTablePartitionedChecksUIFilter,
} from '../../../redux/actions/table.actions';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../../api';
import { CheckResultOverviewApi } from "../../../services/apiClient";

interface TablePartitionedChecksUIFilterViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  category: string;
  checkName: string;
  timePartitioned: 'daily' | 'monthly'
}

const TablePartitionedChecksUIFilterView = ({
  connectionName,
  schemaName,
  tableName,
  category,
  checkName,
  timePartitioned
}: TablePartitionedChecksUIFilterViewProps) => {
  const { partitionedChecksUIFilter } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTablePartitionedChecksOverview(connectionName, schemaName, tableName, timePartitioned).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(partitionedChecksUIFilter);
  }, [partitionedChecksUIFilter]);
  
  useEffect(() => {
    dispatch(
      getTablePartitionedChecksUIFilter(connectionName, schemaName, tableName, timePartitioned, category, checkName)
    );
  }, [connectionName, schemaName, tableName, category, checkName]);

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName}.checks.${category} - ${checkName}`}</div>
        </div>
      </div>
      <div>
        <DataQualityChecks
          onUpdate={() => {}}
          className="max-h-checks-1"
          checksUI={updatedChecksUI}
          onChange={setUpdatedChecksUI}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
        />
      </div>
    </div>
  );
};

export default TablePartitionedChecksUIFilterView;
