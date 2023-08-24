import React, { useState } from 'react';
import DefinitionLayout from '../../components/DefinitionLayout';
import Tabs from '../../components/Tabs';
import SvgIcon from '../../components/SvgIcon';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { SettingsApi } from '../../services/apiClient';
import { CheckContainerModel, CheckResultsOverviewDataModel } from '../../api';
import DataQualityChecks from '../../components/DataQualityChecks';
import { setUpdatedChecksModel } from '../../redux/actions/table.actions';
import { CheckTypes } from '../../shared/routes';
import { useActionDispatch } from '../../hooks/useActionDispatch';

const tabs = [
  {
    label: 'Table',
    value: 'table'
  },
  {
    label: 'Column',
    value: 'column'
  }
];

const checkDefaultDetail = () => {
  const { type } = useSelector(getFirstLevelSensorState);
  const [activeTab, setActiveTab] = useState('table');
  const [checkContainer, setCheckContainer] = useState<CheckContainerModel>();
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const firstLevelActiveTab = useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();

  const fetchDataSetType = async () => {
    if (type === 'Profiling checks') {
      if (activeTab === 'column') {
        await SettingsApi.getDefaultProfilingColumnChecks().then((res) =>
          setCheckContainer(res.data)
        );
      } else {
        await SettingsApi.getDefaultProfilingTableChecks().then((res) =>
          setCheckContainer(res.data)
        );
      }
    } else if (type === 'Monitoring daily') {
      if (activeTab === 'column') {
        await SettingsApi.getDefaultDataObservabilityDailyMonitoringColumnChecks().then(
          (res) => setCheckContainer(res.data)
        );
      } else {
        await SettingsApi.getDefaultDataObservabilityDailyMonitoringTableChecks().then(
          (res) => setCheckContainer(res.data)
        );
      }
    } else if (type === 'Monitoring monthly') {
      if (activeTab === 'column') {
        await SettingsApi.getDefaultDataObservabilityMonthlyMonitoringColumnChecks().then(
          (res) => setCheckContainer(res.data)
        );
      } else {
        await SettingsApi.getDefaultDataObservabilityDailyMonitoringTableChecks().then(
          (res) => setCheckContainer(res.data)
        );
      }
    }
  };

  React.useEffect(() => {
    fetchDataSetType();
  }, [type, activeTab]);

  console.log(checkContainer);

  const onUpdate = async () => {
    // await dispatch(
    //   updateTableProfilingChecksModel(
    //     checkTypes,
    //     firstLevelActiveTab,
    //     connectionName,
    //     schemaName,
    //     tableName,
    //     checksUI
    //   )
    // );
    // await dispatch(
    //   getTableProfilingChecksModel(
    //     checkTypes,
    //     firstLevelActiveTab,
    //     connectionName,
    //     schemaName,
    //     tableName,
    //     false
    //   )
    // );
  };

  const handleChange = (value: CheckContainerModel) => {
    dispatch(
      setUpdatedChecksModel(CheckTypes.PROFILING, firstLevelActiveTab, value)
    );
  };

  const getCheckOverview = () => {
    //     CheckResultOverviewApi.getTableProfilingChecksOverview(
    //       connectionName,
    //       schemaName,
    //       tableName
    //     ).then((res) => {
    //       setCheckResultsOverview(res.data);
    //     });
  };

  return (
    <DefinitionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">
            Default check editor for {type} ({activeTab})
          </div>
        </div>
      </div>
      <div className="border-b border-gray-300 relative">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      <div>
        <DataQualityChecks
          checksUI={checkContainer}
          //   columntable={activeTab}
          onUpdate={onUpdate}
          onChange={handleChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          isDefaultEditing={true}
        />
      </div>
    </DefinitionLayout>
  );
};
export default checkDefaultDetail;
