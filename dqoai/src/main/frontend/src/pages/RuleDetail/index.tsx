import React, { ChangeEvent, useEffect, useState } from "react";
import DefinitionLayout from "../../components/DefinitionLayout";
import SvgIcon from "../../components/SvgIcon";
import { useSelector } from "react-redux";
import { getFirstLevelSensorState } from "../../redux/selectors";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { createRule, getRule, setUpdatedRule } from "../../redux/actions/sensor.actions";
import Tabs from "../../components/Tabs";
import RuleDefinition from "./RuleDefinition";
import PythonCode from "./PythonCode";
import { RuleActionGroup } from "../../components/Sensors/RuleActionGroup";
import Input from "../../components/Input";

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
  const { full_rule_name, ruleDetail, path, type } = useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();
  const [activeTab, setActiveTab] = useState('definition');
  const [ruleName, setRuleName] = useState("");

  useEffect(() => {
    if (!ruleDetail && type !== 'create') {
      dispatch(getRule(full_rule_name))
    }
  }, [full_rule_name, ruleDetail, type]);

  const onCreateRule = async () => {
    if (!ruleName) return;

    const fullName = [...path || [], ruleName].join('/')

    await dispatch(createRule(fullName, ruleDetail));
  };

  const onChangeRuleName = (e: ChangeEvent<HTMLInputElement>) => {
    setRuleName(e.target.value);
    const fullName = [...path || [], e.target.value].join('/')

    dispatch(setUpdatedRule({
      ...ruleDetail,
      full_rule_name: fullName
    }));
  };

  return (
    <DefinitionLayout>
      <div className="relative">
        <RuleActionGroup onSave={onCreateRule} />

        {type !== 'create' ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">Rule: {full_rule_name}</div>
            </div>
          </div>
        ) : (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">Rule: {[...path || [], ""].join('/')}</div>

              <Input
                value={ruleName}
                onChange={onChangeRuleName}
                error={!ruleName}
              />
            </div>
          </div>
        )}

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
