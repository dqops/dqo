import React, { ChangeEvent, useEffect, useState } from 'react';
import DefinitionLayout from '../../components/DefinitionLayout';
import SvgIcon from '../../components/SvgIcon';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  createSensor,
  getSensor,
  setUpdatedSensor
} from '../../redux/actions/definition.actions';
import Tabs from '../../components/Tabs';
import SensorDefinition from './SensorDefinition';
import {
  ProviderSensorModel,
  ProviderSensorModelProviderTypeEnum
} from '../../api';
import ProvideSensor from './ProvideSensor';
import Input from '../../components/Input';
import { SensorActionGroup } from '../../components/Sensors/SensorActionGroup';

const tabs = [
  {
    label: 'Sensor definition',
    value: 'definition'
  },
  {
    label: 'BigQuery',
    value: ProviderSensorModelProviderTypeEnum.bigquery
  },
  {
    label: 'Snowflake',
    value: ProviderSensorModelProviderTypeEnum.snowflake
  },
  {
    label: 'Postgresql',
    value: ProviderSensorModelProviderTypeEnum.postgresql
  },
  {
    label: 'Redshift',
    value: ProviderSensorModelProviderTypeEnum.redshift
  },
  {
    label: 'SQL Server',
    value: ProviderSensorModelProviderTypeEnum.sqlserver
  },
  {
    label: 'MySQL',
    value: ProviderSensorModelProviderTypeEnum.mysql
  },
  {
    label: "Oracle",
    value: ProviderSensorModelProviderTypeEnum.oracle
  }
];

export const SensorDetail = () => {
  const { full_sensor_name, sensorDetail, path, type } = useSelector(
    getFirstLevelSensorState
  );
  const dispatch = useActionDispatch();
  const [activeTab, setActiveTab] = useState('definition');
  const [sensorName, setSensorName] = useState('');

  useEffect(() => {
    if (!sensorDetail && type !== 'create') {
      dispatch(getSensor(full_sensor_name));
    }
  }, [full_sensor_name, sensorDetail, type]);

  const handleChangeProvideSensor = (
    tab: string,
    providerSensor: ProviderSensorModel
  ) => {
    const exist = sensorDetail?.provider_sensor_list?.find(
      (item: ProviderSensorModel) => item.providerType === tab
    );

    const newProviderSensorList = exist
      ? sensorDetail?.provider_sensor_list
          ?.filter((item: ProviderSensorModel) => !!item.providerType)
          .map((item: ProviderSensorModel) =>
            item.providerType === tab ? providerSensor : item
          )
      : [
          ...(sensorDetail?.provider_sensor_list?.filter(
            (item: ProviderSensorModel) => !!item.providerType
          ) || []),
          providerSensor
        ];

    dispatch(
      setUpdatedSensor({
        ...sensorDetail,
        ...{
          provider_sensor_list: newProviderSensorList
        }
      })
    );
  };

  const onCreateSensor = async () => {
    if (!sensorName) return;
    const fullName = [...(path || []), sensorName].join('/');

    await dispatch(createSensor(fullName, sensorDetail));
  };

  const onChangeSensorName = (e: ChangeEvent<HTMLInputElement>) => {
    setSensorName(e.target.value);
    const fullName = [...(path || []), e.target.value].join('/');

    dispatch(
      setUpdatedSensor({
        ...sensorDetail,
        full_sensor_name: fullName
      })
    );
  };

  return (
    <DefinitionLayout>
      <div className="relative">
        <SensorActionGroup onSave={onCreateSensor} />
        {type !== 'create' ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">
                Sensor: {full_sensor_name}
              </div>
            </div>
          </div>
        ) : (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">
                Sensor: {[...(path || []), ''].join('/')}
              </div>

              <Input
                value={sensorName}
                onChange={onChangeSensorName}
                error={!sensorName}
              />
            </div>
          </div>
        )}

        <div className="border-b border-gray-300 relative">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
        {activeTab === 'definition' && (
          <SensorDefinition sensor={sensorDetail} />
        )}
        {tabs
          .slice(1)
          .map(
            (tab, index: number) =>
              tab.value === activeTab && (
                <ProvideSensor
                  key={index}
                  providerType={
                    tab.value as ProviderSensorModelProviderTypeEnum
                  }
                  providerSensor={sensorDetail?.provider_sensor_list
                    ?.filter((item: ProviderSensorModel) => !!item.providerType)
                    ?.find(
                      (item: ProviderSensorModel) =>
                        item.providerType === tab.value
                    )}
                  onChange={(value) =>
                    handleChangeProvideSensor(tab.value, value)
                  }
                />
              )
          )}
      </div>
    </DefinitionLayout>
  );
};

export default SensorDetail;
