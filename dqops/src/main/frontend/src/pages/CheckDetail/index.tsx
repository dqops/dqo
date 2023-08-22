import React, { ChangeEvent, useEffect, useState } from "react";
import DefinitionLayout from "../../components/DefinitionLayout";
import SvgIcon from "../../components/SvgIcon";
import { useSelector } from "react-redux";
import { getFirstLevelSensorState } from "../../redux/selectors";
import { useActionDispatch } from "../../hooks/useActionDispatch";
// import { createRule, getRule, setUpdatedRule } from "../../redux/actions/sensor.actions";
import { createCheck, updateCheck, deleteCheck } from "../../redux/actions/dataQualityChecks";
import Tabs from "../../components/Tabs";
import Input from "../../components/Input";
import CheckEditor from "./CheckEditor";
import Button from "../../components/Button";

const tabs = [
  {
    label: 'Check Editor',
    value: 'check_editor'
  },
];

export const SensorDetail = () => {
  const { fullCheckName, path, type,
  custom } = useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();
  const [activeTab, setActiveTab] = useState('check_editor');
  const [checkName, setcheckName] = useState("");
  const [selectedSensor, setSelectedSensor] = useState("")
  const [selectedRule, setSelectedRule] = useState("")
  const [isUpdating, setIsUpdating] = useState(false)
  const [isCreating, setIsCreating] = useState(false)
  const [isUpdated, setIsUpdated] = useState(false)

  const onChangeSensor = (value: string)=> {
    setSelectedSensor(value)
  }

  const onChangeRule = (value: string)=> {
    setSelectedRule(value)
  }

  useEffect(() => {
    if(type === "create"){
      setIsCreating(true)
    }else{
      setIsCreating(false)
    }
  }, [fullCheckName, path, type, custom])

  const onCreateUpdateCheck = async () => {
    const fullName = [...path || [], checkName].join('/')
    setIsUpdating(true)
    if(type === "create"){
      await dispatch(createCheck(fullName, { sensor_name: selectedSensor, rule_name: selectedRule }));
      setIsUpdating(false)
      setIsCreating(false)
    }else {
      await dispatch(updateCheck(fullCheckName, { sensor_name: selectedSensor, rule_name: selectedRule }));
      setIsUpdating(false)
    }
    setIsUpdated(false)
  };

  const onDeleteCheck =async () => {
    await dispatch(deleteCheck(fullCheckName))
  }

  const onChangecheckName = (e: ChangeEvent<HTMLInputElement>) => {
    setcheckName(e.target.value);
    // const fullName = [...path || [], e.target.value].join('/')

    // dispatch(setUpdatedRule({
    //   ...ruleDetail,
    //   full_rule_name: fullName
    // }));
  };
  

console.log(isUpdated)
  return (
    <DefinitionLayout>
      <div className="relative">
      <div className="flex space-x-4 items-center absolute right-2 top-2">
      {custom === true && (
        <Button
          color="primary"
          variant="outlined"
          label="Delete check"
          className="w-40 !h-10"
          onClick={onDeleteCheck}
        />
      )}
      <Button
        color="primary"
        variant="contained"
        label="Save"
        className="w-40 !h-10"
         disabled={!isUpdated}
        onClick={onCreateUpdateCheck}
        loading={isUpdating}
      />
    </div>
        {isCreating ===false ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">Check: {fullCheckName}</div>
            </div>
          </div>
        ) : (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">Check: {[...path || [], ""].join('/')}</div>
              <Input
                value={checkName}
                onChange={onChangecheckName}
                error={!checkName}
              />
            </div>
          </div>
        )}
        <div className="border-b border-gray-300 relative">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
        {activeTab === 'check_editor' && (
          <CheckEditor create={type === "create" ? true : false} onChangeRule={onChangeRule} onChangeSensor={onChangeSensor}
           selectedRule={selectedRule} selectedSensor={selectedSensor} setIsUpdated={setIsUpdated}
           />
        )}
      
      </div>
    </DefinitionLayout>
  );
};

export default SensorDetail;
