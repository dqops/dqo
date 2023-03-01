import React, { useEffect, useState } from 'react';
import DataQualityChecks from '../../components/DataQualityChecks';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnChecksUi,
  setUpdatedChecksUi,
  updateColumnCheckUI
} from '../../redux/actions/column.actions';
import { CheckResultOverviewApi } from "../../services/apiClient";

interface IAdhocViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const AdhocView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IAdhocViewProps) => {
  const { columnBasic, checksUI, isUpdating, isUpdatedChecksUi, loading } = useSelector(
    (state: IRootState) => state.column
  );
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnAdHocChecksOverview(connectionName, schemaName, tableName, columnName).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    if (
      !checksUI ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnChecksUi(connectionName, schemaName, tableName, columnName)
      );
    }
  }, [connectionName, schemaName, columnName, tableName, columnBasic]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateColumnCheckUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        checksUI
      )
    );
    await dispatch(
      getColumnChecksUi(connectionName, schemaName, tableName, columnName)
    );
  };

  const handleChange = (value: UIAllChecksModel) => {
    dispatch(setUpdatedChecksUi(value));
  };

  return (
    <div>
      <ColumnActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedChecksUi}
        isUpdating={isUpdating}
      />
      <DataQualityChecks
        onUpdate={onUpdate}
        checksUI={checksUI}
        onChange={handleChange}
        checkResultsOverview={checkResultsOverview}
        getCheckOverview={getCheckOverview}
        loading={loading}
      />
    </div>
  );
};

export default AdhocView;
