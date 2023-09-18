import React, { ChangeEvent, useEffect, useState } from 'react';
import DefinitionLayout from '../../components/DefinitionLayout';
import SvgIcon from '../../components/SvgIcon';
import { useSelector } from 'react-redux';
import {  getFirstLevelSensorState } from '../../redux/selectors';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  closeFirstLevelTab,
  createRule,
  getRule,
  setUpdatedRule
} from '../../redux/actions/definition.actions';
import Tabs from '../../components/Tabs';
import RuleDefinition from './RuleDefinition';
import PythonCode from './PythonCode';
import { RuleActionGroup } from '../../components/Sensors/RuleActionGroup';
import Input from '../../components/Input';
import { ROUTES } from '../../shared/routes';

const tabs = [
  {
    label: 'Rule definition',
    value: 'definition'
  },
  {
    label: 'Python code',
    value: 'python_code'
  }
];

export const RuleDetail = () => {
  const { full_rule_name, ruleDetail, path, type, copied } = useSelector(
    getFirstLevelSensorState
  );
  const dispatch = useActionDispatch();
  const [activeTab, setActiveTab] = useState('definition');
  const [ruleName, setRuleName] = useState(type === 'create' && copied !== true ? "" 
  : String(full_rule_name).split("/")[String(full_rule_name).split("/").length - 1] + "_copy"  );

  useEffect(() => {
    if (!ruleDetail && (type !== 'create' || copied === true)) {
      dispatch(getRule(full_rule_name));
    }
  }, [full_rule_name, ruleDetail, type]);

  useEffect(() => {
    if(type === 'create' && copied !== true){
      setRuleName('')
    }else{
      setRuleName(String(full_rule_name).split("/")[String(full_rule_name).split("/").length - 1]+ "_copy")
    }
  },[type, copied])

  const onCreateRule = async () => {
    const fullName = [...(path || []), ruleName].join('/');
    if(type === 'create' && copied !== true){
      await dispatch(createRule(fullName, 
        {...ruleDetail, rule_name: ruleName, custom: true, can_edit: true, built_in: false}));
    }else if(copied === true){
      console.log(full_rule_name, ruleName)

      await dispatch(createRule(String(full_rule_name).replace(/\/[^/]*$/, "/")+ ruleName , {...ruleDetail, full_rule_name: full_rule_name, custom: true, built_in: false}))
      await dispatch(closeFirstLevelTab("definitions/rules/" + String(full_rule_name).split("/")[String(full_rule_name).split("/").length - 1]))
      await  dispatch(
        addFirstLevelTab({
          url: ROUTES.RULE_DETAIL(String(full_rule_name).split("/")[String(full_rule_name).split("/").length - 1]?? ''),
          value: ROUTES.RULE_DETAIL_VALUE(String(full_rule_name).split("/")[String(full_rule_name).split("/").length - 1] ?? ''),
          state: {
            full_rule_name: String(full_rule_name).replace(/\/[^/]*$/, "/")+ ruleName,
            path: path,
            ruleDetail: {...ruleDetail, rule_name: ruleName, full_rule_name: String(full_rule_name).replace(/\/[^/]*$/, "/")+ ruleName , custom: true, built_in: false},
          },
          label: ruleName
        })
      );
    }
  };

  const onChangeRuleName = (e: ChangeEvent<HTMLInputElement>) => {
    setRuleName(e.target.value);
    if(path){
      const fullName = [...(path || []), e.target.value].join('/');
      dispatch(
        setUpdatedRule({
          ...ruleDetail,
          full_rule_name: fullName
        })
        );
      }else{
        dispatch(
          setUpdatedRule({
            ...ruleDetail,
            full_rule_name: String(full_rule_name).replace(/\/[^/]*$/, "/") + e.target.value
          })
      )}
  };
  const onCopy = () : void => { 
    dispatch(
      addFirstLevelTab({
        url: ROUTES.RULE_DETAIL(String(full_rule_name).split("/")[String(full_rule_name).split("/").length - 1]+ "_copy" ?? ''),
        value: ROUTES.RULE_DETAIL_VALUE(String(full_rule_name).split("/")[String(full_rule_name).split("/").length - 1]+ "_copy" ?? ''),
        state: {  
          full_rule_name: full_rule_name,
          copied: true,
          path: path,
          ruleDetail: {...ruleDetail, full_rule_name: full_rule_name + "_copy", custom: true, built_in: false, can_edit: true},
          type: "create"
        },
        label: `${String(full_rule_name).split("/")[String(full_rule_name).split("/").length - 1]}_copy`
      })
    );
}
console.log(path, ruleName, full_rule_name, ruleDetail)
  return (
    <DefinitionLayout>
      <div className="relative">
        <RuleActionGroup onSave={onCreateRule} onCopy = {onCopy}/>

        {type !== 'create' ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">
                Rule: {full_rule_name}
              </div>
            </div>
          </div>
        ) : (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">
                Rule: {path?  [...(path || []), ''].join('/') : String(full_rule_name).replace(/\/[^/]*$/, "/")}
              </div>
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
        {activeTab === 'definition' && <RuleDefinition rule={ruleDetail} />}
        {activeTab === 'python_code' && <PythonCode rule={ruleDetail} />}
      </div>
    </DefinitionLayout>
  );
};

export default RuleDetail;
