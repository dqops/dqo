import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../api';
import { getColumnAdHockChecksUIFilter } from '../../redux/actions/column.actions';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";

const ColumnAdHockChecksUIFilterView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName, column: columnName, category, checkName }: { connection: string, schema: string, table: string, column: string, category: string, checkName: string } = useParams();
  const { checksUIFilter } = useSelector(
    (state: IRootState) => state.column
  );
  const dispatch = useActionDispatch();
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnAdHocChecksOverview(connectionName, schemaName, tableName, columnName).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(checksUIFilter);
  }, [checksUIFilter]);
  
  useEffect(() => {
    dispatch(
      getColumnAdHockChecksUIFilter(connectionName, schemaName, tableName, columnName, category, checkName)
    );
  }, [connectionName, schemaName, tableName, category, checkName]);
  
  return (
    <ConnectionLayout>
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
    </ConnectionLayout>
  );
};

export default ColumnAdHockChecksUIFilterView;
