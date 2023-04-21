import React, { useEffect, useState } from "react";
import DefinitionLayout from "../../components/DefinitionLayout";
import SvgIcon from "../../components/SvgIcon";
import { useSelector } from "react-redux";
import { getFirstLevelSensorState } from "../../redux/selectors";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { getRule } from "../../redux/actions/sensor.actions";
import Tabs from "../../components/Tabs";
import RuleDefinition from "./RuleDefinition";
import PythonCode from "./PythonCode";

const tabs = [
  {
    label: 'Rule definition',
    value: 'definition'
  },
  {
    label: 'Python code',
    value: 'python_code'
  },
];

export const SensorDetail = () => {
  const { full_rule_name, ruleDetail } = useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();
  const [activeTab, setActiveTab] = useState('definition');

  useEffect(() => {
    dispatch(getRule(full_rule_name))
  }, [full_rule_name]);

  return (
    <DefinitionLayout>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">Rule: {full_rule_name}</div>
          </div>
        </div>

        <div className="border-b border-gray-300 relative">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
        {activeTab === 'definition' && (
          <RuleDefinition rule={ruleDetail} />
        )}
        {activeTab === 'python_code' && (
          <PythonCode rule={ruleDetail} />
        )}
      </div>
    </DefinitionLayout>
  );
};

export default SensorDetail;
