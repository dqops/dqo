import React, { ChangeEvent, useEffect, useMemo, useState } from 'react';
import { RuleListModel, SensorListModel } from '../../api';
import { RulesApi, SensorsApi } from '../../services/apiClient';
import Select from '../../components/Select';
import Button from '../../components/Button';
import Checkbox from '../../components/Checkbox';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { ROUTES } from '../../shared/routes';

interface CreateCheckProps {
  create: boolean;
  onChangeSensor: (arg: string) => void;
  onChangeRule: (arg: string) => void;
  selectedRule: string;
  selectedSensor: string;
  setIsUpdated: React.Dispatch<React.SetStateAction<boolean>>;
  onChangeHelpText: (e: ChangeEvent<HTMLTextAreaElement>) => void;
  custom: any;
  helpText?: string;
  standard?: boolean;
  onChangeStandard: (value: boolean) => void;
  canEditDefinitions?: boolean;
}

const CheckEditor = ({
  onChangeRule,
  onChangeSensor,
  selectedRule,
  selectedSensor,
  setIsUpdated,
  custom,
  helpText,
  onChangeHelpText,
  standard,
  onChangeStandard,
  canEditDefinitions
}: CreateCheckProps) => {
  const [allSensors, setAllSensors] = useState<SensorListModel[]>();
  const [allRules, setAllRules] = useState<RuleListModel[]>();
  const dispatch = useActionDispatch();

  const getAllSensors = async () => {
    await SensorsApi.getAllSensors().then((res) => setAllSensors(res.data));
  };

  const getAllRules = async () => {
    await RulesApi.getAllRules().then((res) => setAllRules(res.data));
  };

  useEffect(() => {
    getAllSensors();
    getAllRules();
  }, []);

  const memoizedData = useMemo(() => {
    return {
      sensors: allSensors,
      rules: allRules
    };
  }, [allSensors, allRules]);

  const openSensorFirstLevelTab = (sensorName: string) => {
    if (sensorName.includes('/')) {
      const array = sensorName.split('/');
      const newSensorName = array[array.length - 1];
      dispatch(
        addFirstLevelTab({
          url: ROUTES.SENSOR_DETAIL(newSensorName ?? ''),
          value: ROUTES.SENSOR_DETAIL_VALUE(newSensorName ?? ''),
          state: {
            full_sensor_name:
              memoizedData.sensors &&
              memoizedData.sensors.find((x) => x.sensor_name === newSensorName)
                ?.full_sensor_name
          },
          label: newSensorName
        })
      );
    } else {
      dispatch(
        addFirstLevelTab({
          url: ROUTES.SENSOR_DETAIL(sensorName ?? ''),
          value: ROUTES.SENSOR_DETAIL_VALUE(sensorName ?? ''),
          state: {
            full_sensor_name:
              memoizedData.sensors &&
              memoizedData.sensors.find((x) => x.sensor_name === sensorName)
                ?.full_sensor_name
          },
          label: sensorName
        })
      );
    }
  };

  const openRuleFirstLevelTab = (ruleName: string) => {
    if (ruleName.includes('/')) {
      const array = ruleName.split('/');
      const newRuleName = array[array.length - 1];
      dispatch(
        addFirstLevelTab({
          url: ROUTES.RULE_DETAIL(newRuleName ?? ''),
          value: ROUTES.RULE_DETAIL_VALUE(newRuleName ?? ''),
          state: {
            full_rule_name:
              memoizedData.rules &&
              memoizedData.rules.find((x) => x.rule_name === newRuleName)
                ?.full_rule_name
          },
          label: newRuleName
        })
      );
    } else {
      dispatch(
        addFirstLevelTab({
          url: ROUTES.RULE_DETAIL(ruleName ?? ''),
          value: ROUTES.RULE_DETAIL_VALUE(ruleName ?? ''),
          state: {
            full_rule_name:
              memoizedData.rules &&
              memoizedData.rules.find((x) => x.rule_name === ruleName)
                ?.full_rule_name
          },
          label: ruleName
        })
      );
    }
  };
      return (
        <div className="pt-10 pb-2 px-8 z-1">
          <div className="pb-4 gap-2">
            <Checkbox 
              checked={standard}
              onChange={(value: boolean) => { 
                onChangeStandard(value); setIsUpdated(true);
              }}
              disabled={custom === false ? true : false}
              className={custom === false ? 'cursor-default' : ''}
              label="Standard data quality check, always shown in the editor"
            />
          </div>
          <div className="pb-4 gap-2">
            Sensor name:
            <div className="flex items-center gap-x-4 pt-2">
              <Select
                placeholder={selectedSensor}
                options={
                  (memoizedData.sensors &&
                    memoizedData.sensors.map((x) => ({
                      label: x.full_sensor_name ?? '',
                      value: x.full_sensor_name ?? ''
                    }))) ||
                  []
                }
                disabled={(custom === false || canEditDefinitions === false) ? true : false}
                value={selectedSensor}
                onChange={(selected) => {
                  onChangeSensor(selected), setIsUpdated(true);
                }}
                disableIcon={custom === false ? true : false}
                className="w-1/2"
                triggerClassName={(custom === false || canEditDefinitions === false) ? "cursor-default" : ""}
              />
              <Button
                label="Show definition"
                color="primary"
                disabled={!selectedSensor}
                onClick={() => openSensorFirstLevelTab(selectedSensor)}
              />
            </div>
          </div>
          <div>
            Rule name:
            <div className="flex items-center gap-x-4 pt-2">
              <Select
                placeholder={selectedRule}
                options={
                  (memoizedData.rules &&
                    memoizedData.rules.map((x) => ({
                      label: x.full_rule_name ?? '',
                      value: x.full_rule_name ?? ''
                    }))) ||
                  []
                }
                value={selectedRule}
                disabled={(custom === false || canEditDefinitions===false) ? true : false}
                onChange={(selected) => {
                  onChangeRule(selected), setIsUpdated(true);
                }}
                disableIcon={custom === false ? true : false}
                className="w-1/2"
                triggerClassName={(custom === false || canEditDefinitions === false) ? "cursor-default" : ""}
              />
              <Button
                label="Show definition"
                color="primary"
                disabled={!selectedRule}
                onClick={() => openRuleFirstLevelTab(selectedRule)}
              />
            </div>
          </div>
          <div className="mt-6">
            Help text:
            <div className="flex items-center gap-x-4 pt-2 w-1/2">
              <textarea
                value={helpText}
                className="font-regular text-sm focus:ring-1 focus:ring-teal-500 focus:ring-opacity-80 focus:border-0 border-gray-300 h-26 placeholder-gray-500 py-0.5 px-3 border text-gray-900 focus:text-gray-900 focus:outline-none min-w-40 w-full leading-1.5 rounded"
                onChange={(e) => {
                  onChangeHelpText(e), setIsUpdated(true);
                }}
                disabled={(custom === false || canEditDefinitions === false) ? true : false}
              ></textarea>
            </div>
          </div>
        </div>
      );
    };

export default CheckEditor;
