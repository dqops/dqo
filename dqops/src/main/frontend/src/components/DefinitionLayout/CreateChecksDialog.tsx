import React, { ChangeEvent, useEffect, useMemo, useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import { CheckDefinitionModel, ColumnListModel, RuleListModel, SensorListModel } from '../../api';
import Button from '../Button';
import Input from '../Input';
import Select from '../Select';
import { RulesApi, SensorsApi } from '../../services/apiClient';

interface CreateCheckProps {
  open: boolean;
  onClose: () => void;
  onConfirm: (fullCheckName: string, body?: CheckDefinitionModel) => Promise<void>
}

const CreateCheckDialog = ({
  open,
  onClose,
  onConfirm
}: CreateCheckProps) => {
  const handleSubmit = () => {
    onConfirm("custom/" + nameOfCheck,
    {check_name: nameOfCheck, rule_name: selectedRule, sensor_name: selectedSensor, custom: true});
    onClose();
  };

  const [allSensors, setAllSensors] = useState<SensorListModel[]>()
  const [allRules, setAllRules] = useState<RuleListModel[]>()
  const [selectedSensor, setSelectedSensor] = useState("")
  const [selectedRule, setSelectedRule] = useState("")
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

  const onChangeSensor = (value: string)=> {
    setSelectedSensor(value)
  }

  const onChangeRule = (value: string)=> {
    setSelectedRule(value)
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
      {open === true ? 
      <div className='z-1 fixed w-300 border border-gray-300 bg-white' style={{left: "20%", top: "40%"}}>
        <div className="pt-10 pb-2 px-8 z-1">
          <div className="text-2xl text-gray-700 text-center whitespace-normal break-all">
         Create a custom check
          </div>
          Check name:
          <Input value={nameOfCheck} onChange={onChangeNameOfCheck}/>
          Sensor name:
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
        <div className="flex items-center pt-6 justify-center space-x-6 pb-8">
          <Button
            color="primary"
            variant="outlined"
            className="px-8"
            onClick={onClose}
            label="Cancel"
          />
          <Button
            color="primary"
            className="px-8"
            onClick={handleSubmit}
            label="Confirm"
          />
        </div>
      </div>
      : <></>}
    </div>
  );
};

export default CreateCheckDialog;
