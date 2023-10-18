import React, { useState } from 'react';
import DefinitionLayout from '../../components/DefinitionLayout';
import Tabs from '../../components/Tabs';
import SvgIcon from '../../components/SvgIcon';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { SettingsApi } from '../../services/apiClient';
import { CheckContainerModel } from '../../api';
import DataQualityChecks from '../../components/DataQualityChecks';
import Button from '../../components/Button';
import { useParams } from 'react-router-dom';

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
  const { defaultCheck } : { defaultCheck : string} = useParams()
  const { type } = useSelector(getFirstLevelSensorState);
  const [activeTab, setActiveTab] = useState('table');
  const [checkContainer, setCheckContainer] = useState<CheckContainerModel>();

  const [isUpdated, setIsUpdated] = useState(false);

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
        await SettingsApi.getDefaultDataObservabilityMonthlyMonitoringTableChecks().then(
          (res) => setCheckContainer(res.data)
        );
      }
    }
  };

  const updateDataSetType = async (value: CheckContainerModel) => {
    if (type === 'Profiling checks') {
      if (activeTab === 'column') {
        await SettingsApi.updateDefaultProfilingColumnChecks(value);
      } else {
        await SettingsApi.updateDefaultProfilingTableChecks(value);
      }
    } else if (type === 'Monitoring daily') {
      if (activeTab === 'column') {
        await SettingsApi.updateDefaultDataObservabilityDailyMonitoringColumnChecks(
          value
        );
      } else {
        await SettingsApi.updateDefaultDataObservabilityDailyMonitoringTableChecks(
          value
        );
      }
    } else if (type === 'Monitoring monthly') {
      if (activeTab === 'column') {
        await SettingsApi.updateDefaultDataObservabilityMonthlyMonitoringColumnChecks(
          value
        );
      } else {
        await SettingsApi.updateDefaultDataObservabilityMonthlyMonitoringTableChecks(
          value
        );
      }
    }
  };

  React.useEffect(() => {
    fetchDataSetType();
  }, [type, activeTab, defaultCheck]);

  // console.log(type, activeTab);
  // console.log(defaultCheck)

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
    setCheckContainer(value);
    setIsUpdated(true);
  };

  const handleSubmit = () => {
    updateDataSetType(checkContainer ?? {});
    setIsUpdated(false);
  };

  const getCheckOverview = () => {};

  return (
    <DefinitionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">
            Default check editor for {type} ({activeTab})
          </div>
        </div>
        <Button
          label="Save"
          color="primary"
          className="pl-14 pr-14"
          onClick={handleSubmit}
          disabled={!isUpdated}
        />
      </div>
      <div className="border-b border-gray-300 relative">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      <div>
        <DataQualityChecks
          checksUI={checkContainer}
          onUpdate={onUpdate}
          onChange={handleChange}
          checkResultsOverview={[]}
          getCheckOverview={getCheckOverview}
          isDefaultEditing={true}
        />
      </div>
    </DefinitionLayout>
  );
};
export default checkDefaultDetail;
