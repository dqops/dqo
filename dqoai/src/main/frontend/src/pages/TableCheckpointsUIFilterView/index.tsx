import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getTableCheckpointsUIFilter,
} from '../../redux/actions/table.actions';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../api';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";

const TableCheckpointsUIFilterView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName, timePartitioned, category, checkName }: { connection: string, schema: string, table: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string } = useParams();
  const { checkpointsUIFilter } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTableCheckpointsOverview(connectionName, schemaName, tableName, timePartitioned).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(checkpointsUIFilter);
  }, [checkpointsUIFilter]);
  
  useEffect(() => {
    dispatch(
      getTableCheckpointsUIFilter(connectionName, schemaName, tableName, timePartitioned, category, checkName)
    );
  }, [connectionName, schemaName, tableName, category, checkName]);
  
  return (
    <ConnectionLayout>
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
    </ConnectionLayout>
  );
};

export default TableCheckpointsUIFilterView;
