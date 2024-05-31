import React, { ChangeEvent, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  ProviderSensorModel,
  ProviderSensorModelProviderTypeEnum
} from '../../api';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import Input from '../../components/Input';
import { SensorActionGroup } from '../../components/Sensors/SensorActionGroup';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  closeFirstLevelTab,
  createSensor,
  getSensor,
  refreshSensorsFolderTree,
  setUpdatedSensor
} from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { SensorsApi } from '../../services/apiClient';
import { ROUTES } from '../../shared/routes';
import { urlencodeDecoder } from '../../utils';
import ProvideSensor from './ProvideSensor';
import SensorDefinition from './SensorDefinition';

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
    label: 'Databricks',
    value: ProviderSensorModelProviderTypeEnum.databricks
  },
  {
    label: 'DuckDB',
    value: ProviderSensorModelProviderTypeEnum.duckdb
  },
  {
    label: 'MySQL',
    value: ProviderSensorModelProviderTypeEnum.mysql
  },
  {
    label: 'Oracle',
    value: ProviderSensorModelProviderTypeEnum.oracle
  },
  {
    label: 'Postgresql',
    value: ProviderSensorModelProviderTypeEnum.postgresql
  },
  {
    label: 'Presto',
    value: ProviderSensorModelProviderTypeEnum.presto
  },
  {
    label: 'Redshift',
    value: ProviderSensorModelProviderTypeEnum.redshift
  },
  {
    label: 'Snowflake',
    value: ProviderSensorModelProviderTypeEnum.snowflake
  },
  {
    label: 'Spark',
    value: ProviderSensorModelProviderTypeEnum.spark
  },
  {
    label: 'SQL Server',
    value: ProviderSensorModelProviderTypeEnum.sqlserver
  },
  {
    label: 'Trino',
    value: ProviderSensorModelProviderTypeEnum.trino
  }
];

export const SensorDetail = () => {
  const { full_sensor_name, sensorDetail, path, type, copied } = useSelector(
    getFirstLevelSensorState
  );
  const { refreshSensorsTreeIndicator } = useSelector(
    (state: IRootState) => state.definition
  );
  const dispatch = useActionDispatch();
  const [activeTab, setActiveTab] = useState('definition');
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [sensorName, setSensorName] = useState(
    type === 'create' && copied !== true
      ? ''
      : String(full_sensor_name).split('/')[
          String(full_sensor_name).split('/').length - 1
        ] + '_copy'
  );

  useEffect(() => {
    if (
      full_sensor_name !== undefined &&
      !sensorDetail &&
      (type !== 'create' || copied === true)
    ) {
      dispatch(getSensor(full_sensor_name));
    }
  }, [full_sensor_name, sensorDetail, type]);
  useEffect(() => {
    if (type === 'create' && copied !== true) {
      setSensorName('');
    } else {
      if (full_sensor_name !== undefined) {
        setSensorName(
          String(full_sensor_name).split('/')[
            String(full_sensor_name).split('/').length - 1
          ] + '_copy'
        );
      }
    }
  }, [type, copied]);

  const closeSensorFirstLevelTab = () => {
    dispatch(
      refreshSensorsFolderTree(refreshSensorsTreeIndicator ? false : true)
    );
    dispatch(
      closeFirstLevelTab(
        urlencodeDecoder(
          '/definitions/sensors/' +
            String(full_sensor_name).split('/')[
              String(full_sensor_name).split('/').length - 1
            ]
        )
      )
    );
  };

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
    const fullName = [...(path || []), sensorName].join('/');
    if (type === 'create' && copied !== true) {
      await dispatch(
        createSensor(fullName, {
          ...sensorDetail,
          full_sensor_name: fullName,
          custom: true,
          built_in: false,
          can_edit: true,
          sensor_definition_spec: sensorDetail.sensor_definition_spec ?? {}
        })
      );
    } else if (copied === true) {
      await dispatch(
        createSensor(
          String(full_sensor_name).replace(/\/[^/]*$/, '/') + sensorName,
          {
            ...sensorDetail,
            full_sensor_name: full_sensor_name,
            custom: true,
            built_in: false
          }
        )
      );
    }
    closeSensorFirstLevelTab();
    await dispatch(
      addFirstLevelTab({
        url: ROUTES.SENSOR_DETAIL(
          String(full_sensor_name ?? fullName).split('/')[
            String(full_sensor_name ?? fullName).split('/').length - 1
          ] ?? ''
        ),
        value: ROUTES.SENSOR_DETAIL_VALUE(
          String(full_sensor_name ?? fullName).split('/')[
            String(full_sensor_name ?? fullName).split('/').length - 1
          ] ?? ''
        ),
        state: {
          full_sensor_name: full_sensor_name
            ? String(full_sensor_name).replace(/\/[^/]*$/, '/') + sensorName
            : fullName,
          path: path,
          sensorDetail: {
            ...sensorDetail,
            full_sensor_name: full_sensor_name
              ? String(full_sensor_name).replace(/\/[^/]*$/, '/') + sensorName
              : fullName,
            custom: true,
            built_in: false
          }
        },
        label: sensorName
      })
    );
  };

  const onChangeSensorName = (e: ChangeEvent<HTMLInputElement>) => {
    setSensorName(e.target.value);
    if (path) {
      const fullName = [...(path || []), e.target.value].join('/');
      dispatch(
        setUpdatedSensor({
          ...sensorDetail,
          full_sensor_name: fullName
        })
      );
    } else {
      dispatch(
        setUpdatedSensor({
          ...sensorDetail,
          full_sensor_name:
            String(full_sensor_name).replace(/\/[^/]*$/, '/') + e.target.value
        })
      );
    }
  };
  const onCopy = (): void => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SENSOR_DETAIL(
          String(full_sensor_name).split('/')[
            String(full_sensor_name).split('/').length - 1
          ] + '_copy' ?? ''
        ),
        value: ROUTES.SENSOR_DETAIL_VALUE(
          String(full_sensor_name).split('/')[
            String(full_sensor_name).split('/').length - 1
          ] + '_copy' ?? ''
        ),
        state: {
          full_sensor_name: full_sensor_name,
          copied: true,
          path: path,
          sensorDetail: {
            ...sensorDetail,
            full_sensor_name: full_sensor_name + '_copy',
            custom: true,
            built_in: false,
            can_edit: true
          },
          type: 'create'
        },
        label: `${
          String(full_sensor_name).split('/')[
            String(full_sensor_name).split('/').length - 1
          ]
        }_copy`
      })
    );
  };

  const onDelete = async () => {
    SensorsApi.deleteSensor(full_sensor_name).then(async () =>
      closeSensorFirstLevelTab()
    );
  };

  return (
    <>
      <div className="relative">
        <SensorActionGroup
          onSave={onCreateSensor}
          onCopy={onCopy}
          onDelete={() => setDeleteDialogOpen(true)}
        />
        {type !== 'create' ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="definitionssensors" className="w-5 h-5 shrink-0" />
              <div className="text-lg font-semibold truncate">
                Sensor: {full_sensor_name}
              </div>
            </div>
          </div>
        ) : (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-lg font-semibold truncate">
                Sensor:{' '}
                {path
                  ? [...(path || []), ''].join('/')
                  : String(full_sensor_name).replace(/\/[^/]*$/, '/')}
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
      <ConfirmDialog
        open={deleteDialogOpen}
        onClose={() => setDeleteDialogOpen(false)}
        onConfirm={onDelete}
        message={`Are you sure you want to delete the sensor ${full_sensor_name}`}
      />
    </>
  );
};

export default SensorDetail;
