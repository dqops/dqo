import React, { useEffect, useMemo, useState } from 'react';
import { RuleBasicModel, SensorBasicModel } from '../../api';
import { RulesApi, SensorsApi } from '../../services/apiClient';
import Select from '../../components/Select';
import Button from '../../components/Button';
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
  custom: any;
}

const CheckEditor = ({
  onChangeRule,
  onChangeSensor,
  selectedRule,
  selectedSensor,
  setIsUpdated,
  custom
}: CreateCheckProps) => {
  const [allSensors, setAllSensors] = useState<SensorBasicModel[]>();
  const [allRules, setAllRules] = useState<RuleBasicModel[]>();
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
  };

  const openRuleFirstLevelTab = (ruleName: string) => {
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
  };

  // console.log(selectedRule);
  // console.log(selectedSensor);

  return (
    <div>
      <div>
        {custom !== false ? (
          <div className="pt-10 pb-2 px-8 z-1">
            <div className="pb-4 gap-2">
              Sensor Name:
              <div className="flex items-center gap-x-4 pt-2">
                <Select
                  placeholder="Sensor"
                  options={
                    (memoizedData.sensors &&
                      memoizedData.sensors.map((x) => ({
                        label: x.sensor_name ?? '',
                        value: x.sensor_name ?? ''
                      }))) ||
                    []
                  }
                  value={selectedSensor}
                  onChange={(selected) => {
                    onChangeSensor(selected), setIsUpdated(true);
                  }}
                  className="w-1/2"
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
                  placeholder="Rule"
                  options={
                    (memoizedData.rules &&
                      memoizedData.rules.map((x) => ({
                        label: x.rule_name ?? '',
                        value: x.rule_name ?? ''
                      }))) ||
                    []
                  }
                  value={selectedRule}
                  onChange={(selected) => {
                    onChangeRule(selected), setIsUpdated(true);
                  }}
                  className="w-1/2"
                />
                <Button
                  label="Show definition"
                  color="primary"
                  disabled={!selectedRule}
                  onClick={() => openRuleFirstLevelTab(selectedRule)}
                />
              </div>
            </div>
          </div>
        ) : (
          <div>Default check editor screen</div>
        )}
      </div>
    </div>
  );
};

export default CheckEditor;
