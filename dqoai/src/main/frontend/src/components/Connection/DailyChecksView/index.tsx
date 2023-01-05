import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDailyCheckpoints,
  updateTableDailyCheckpoints
} from '../../../redux/actions/table.actions';
import Button from '../../Button';
import { CheckResultOverviewApi } from "../../../services/apiClient";

interface IDailyChecksViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const DailyChecksView = ({
  connectionName,
  schemaName,
  tableName
}: IDailyChecksViewProps) => {
  const { dailyCheckpoints, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTableCheckpointsOverview(connectionName, schemaName, tableName, 'daily').then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(dailyCheckpoints);
  }, [dailyCheckpoints]);

  useEffect(() => {
    dispatch(getTableDailyCheckpoints(connectionName, schemaName, tableName));
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (!updatedChecksUI) return;

    await dispatch(
      updateTableDailyCheckpoints(
        connectionName,
        schemaName,
        tableName,
        updatedChecksUI
      )
    );
    await dispatch(
      getTableDailyCheckpoints(connectionName, schemaName, tableName)
    );
    setIsUpdated(false);
  };

  const onChangeUI = (ui: UIAllChecksModel) => {
    setUpdatedChecksUI(ui);
    setIsUpdated(true);
  };

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`Daily Checkpoints for ${connectionName}.${schemaName}.${tableName}`}</div>
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
          onUpdate={onUpdate}
          className="max-h-checks-1"
          checksUI={updatedChecksUI}
          onChange={onChangeUI}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
        />
      </div>
    </div>
  );
};

export default DailyChecksView;
