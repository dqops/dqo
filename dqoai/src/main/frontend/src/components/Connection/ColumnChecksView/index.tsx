import React, { useEffect, useMemo, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import Button from '../../Button';
import {
  getColumnChecksUi,
  updateColumnCheckUI
} from '../../../redux/actions/column.actions';
import { isEqual } from 'lodash';
import { CheckResultOverviewApi } from '../../../services/apiClient';

interface IColumnChecksViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const ColumnChecksView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IColumnChecksViewProps) => {
  const { checksUI, isUpdating } = useSelector(
    (state: IRootState) => state.column
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  
  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnAdHocChecksOverview(connectionName, schemaName, tableName, columnName).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };
  
  useEffect(() => {
    setUpdatedChecksUI(checksUI);
  }, [checksUI]);

  useEffect(() => {
    dispatch(
      getColumnChecksUi(connectionName, schemaName, tableName, columnName)
    );
  }, [connectionName, schemaName, tableName, columnName]);

  const onUpdate = async () => {
    if (!updatedChecksUI) {
      return;
    }
    await dispatch(
      updateColumnCheckUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedChecksUI
      )
    );
    await dispatch(
      getColumnChecksUi(connectionName, schemaName, tableName, columnName)
    );
  };

  const isUpdated = useMemo(
    () => !isEqual(updatedChecksUI, checksUI),
    [checksUI, updatedChecksUI]
  );
  
  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`Data quality checks for ${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
        </div>
        <Button
          color={isUpdated ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          loading={isUpdating}
        />
      </div>
      <div>
        <DataQualityChecks
          checksUI={updatedChecksUI}
          onChange={setUpdatedChecksUI}
          className="max-h-checks-1"
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
        />
      </div>
    </div>
  );
};

export default ColumnChecksView;
