import React, { ChangeEvent, useEffect, useMemo, useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import { CheckSpecModel, ColumnBasicModel, RuleBasicModel, SensorBasicModel } from '../../api';
import { RulesApi, SensorsApi } from '../../services/apiClient';
import Select from '../../components/Select';
import Input from '../../components/Input';
import Button from '../../components/Button';

interface CreateCheckProps {
  create: boolean;
  onChangeSensor: (arg: string ) => void
  onChangeRule: (arg: string ) => void
  selectedRule: string,
  selectedSensor: string
}

const CheckEditor = ({
  create,
  onChangeRule,
  onChangeSensor,
  selectedRule,
  selectedSensor,
}: CreateCheckProps) => {
  const handleSubmit = () => {
    //onConfirm("custom/" + nameOfCheck,
    //{check_name: nameOfCheck, rule_name: selectedRule, sensor_name: selectedSensor, custom: true});
  };

  const [allSensors, setAllSensors] = useState<SensorBasicModel[]>()
  const [allRules, setAllRules] = useState<RuleBasicModel[]>()
  const [nameOfCheck, setNameOfCheck] = useState("")

  
  const getAllSensors =async () => {
    await SensorsApi.getAllSensors().then((res) => setAllSensors(res.data))
  }

  const getAllRules =async () => {
    await RulesApi.getAllRules().then((res) => setAllRules(res.data))
  }
  const onChangeNameOfCheck = (e: ChangeEvent<HTMLInputElement>)=> {
    setNameOfCheck(e.target.value)
  }

  useEffect(() => {
    getAllSensors()
    getAllRules()
  }, []);

  const memoizedData = useMemo(() => {
    return {
      sensors: allSensors,
      rules: allRules,
    };
  }, [allSensors, allRules]);

  return (
    <div>
      <div>
        <div className="pt-10 pb-2 px-8 z-1">
          <div className="text-2xl text-gray-700 text-center whitespace-normal break-all">
          </div>
          {/* {create=== false && <div>
          Check Name:
          <Input value={nameOfCheck} onChange={onChangeNameOfCheck}/>
          </div>} */}
          Sensor Name: 
          <Select
            placeholder='Sensor'
            options={memoizedData.rules && memoizedData.rules.map((x) => ({
              label: x.rule_name ?? "", 
              value: x.rule_name ?? ""
            })) || []}
            value={selectedSensor}
            onChange={onChangeSensor}
            />
            Rule name: 
            <Select placeholder='Rule'  
            options={memoizedData.sensors && memoizedData.sensors.map((x) => ({
            label: x.sensor_name ?? "", 
            value: x.sensor_name ?? ""
            })) || []}
            value={selectedRule}
            onChange={onChangeRule}
            />
        </div>
      </div>
    </div>
  );
};

export default CheckEditor;
