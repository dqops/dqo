import React, { useEffect, useState } from "react";
import DefinitionLayout from "../../components/DefinitionLayout";
import SvgIcon from "../../components/SvgIcon";
import { useSelector } from "react-redux";
import { getFirstLevelSensorState } from "../../redux/selectors";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { getSensor } from "../../redux/actions/sensor.actions";
import Tabs from "../../components/Tabs";
import SensorDefinition from "./SensorDefinition";
import { ITab } from "../../shared/interfaces";
import { ProviderSensorModel } from "../../api";
import ProvideSensor from "./ProvideSensor";

const initTabs = [
  {
    label: 'Sensor definition',
    value: 'definition'
  },
  {
    label: 'BigQuery',
    value: 'bigquery'
  },
  {
    label: 'Snowflake',
    value: 'snowflake'
  },
  {
    label: 'Postgresql',
    value: 'postgresql'
  },
  {
    label: 'Redshift',
    value: 'redshift'
  },
  {
    label: 'SQL Server',
    value: 'sqlServer'
  }
];

export const SensorDetail = () => {
  const [tabs, setTabs] = useState<ITab[]>(initTabs);
  const { full_sensor_name, sensorDetail } = useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();
  const [activeTab, setActiveTab] = useState('definition');

  useEffect(() => {
    dispatch(getSensor(full_sensor_name))
  }, [full_sensor_name]);

  return (
    <DefinitionLayout>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">Sensor: {full_sensor_name}</div>
          </div>
        </div>

        <div className="border-b border-gray-300 relative">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
        {activeTab === 'definition' && (
          <SensorDefinition sensor={sensorDetail} />
        )}
        {tabs.slice(1).map((tab, index: number) => tab.value === activeTab && (
          <ProvideSensor
            key={index}
            providerSensor={sensorDetail.provider_sensor_list?.find((item: ProviderSensorModel) => item.providerType === tab.value)}
            onChange={() => {}}
          />
        ))}
      </div>
    </DefinitionLayout>
  );
};

export default SensorDetail;
