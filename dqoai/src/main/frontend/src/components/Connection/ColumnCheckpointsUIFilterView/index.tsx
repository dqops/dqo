import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../../api';
import { getColumnCheckpointsUIFilter } from '../../../redux/actions/column.actions';
import { CheckResultOverviewApi } from "../../../services/apiClient";

interface ColumnCheckpointsUIFilterViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
  category: string;
  checkName: string;
  timePartitioned: 'daily' | 'monthly'
}

const ColumnCheckpointsUIFilterView = ({
  connectionName,
  schemaName,
  tableName,
  columnName,
  category,
  checkName,
  timePartitioned
}: ColumnCheckpointsUIFilterViewProps) => {
  const { checkpointsUIFilter } = useSelector(
    (state: IRootState) => state.column
  );
  const dispatch = useActionDispatch();
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnCheckpointsOverview(connectionName, schemaName, tableName, columnName, timePartitioned).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(checkpointsUIFilter);
  }, [checkpointsUIFilter]);
  
  useEffect(() => {
    dispatch(
      getColumnCheckpointsUIFilter(connectionName, schemaName, tableName, columnName, timePartitioned, category, checkName)
    );
  }, [connectionName, schemaName, tableName, columnName, category, checkName]);
  
  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName}.${columnName}.checks.${category} - ${checkName}`}</div>
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

export default ColumnCheckpointsUIFilterView;
