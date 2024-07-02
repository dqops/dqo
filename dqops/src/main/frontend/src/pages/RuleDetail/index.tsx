import React, { ChangeEvent, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import Input from '../../components/Input';
import { RuleActionGroup } from '../../components/Sensors/RuleActionGroup';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  closeFirstLevelTab,
  createRule,
  getRule,
  refreshRuleFolderTree,
  setUpdatedRule
} from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { RulesApi } from '../../services/apiClient';
import { ROUTES } from '../../shared/routes';
import PythonCode from './PythonCode';
import RuleDefinition from './RuleDefinition';

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
  const { refreshRulesTreeIndicator, activeTab: firstLevelActiveTab } =
    useSelector((state: IRootState) => state.definition);
  const dispatch = useActionDispatch();
  const [activeTab, setActiveTab] = useState('definition');
  const [ruleName, setRuleName] = useState(
    type === 'create' && copied !== true
      ? ''
      : String(full_rule_name).split('/')[
          String(full_rule_name).split('/').length - 1
        ] + '_copy'
  );
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);

  useEffect(() => {
    if (
      full_rule_name !== undefined &&
      !ruleDetail &&
      (type !== 'create' || copied === true)
    ) {
      dispatch(getRule(String(full_rule_name)));
    }
  }, [full_rule_name, ruleDetail, type]);

  useEffect(() => {
    if (type === 'create' && copied !== true) {
      setRuleName('');
    } else {
      if (full_rule_name !== undefined) {
        setRuleName(
          String(full_rule_name).split('/')[
            String(full_rule_name).split('/').length - 1
          ] + '_copy'
        );
      }
    }
  }, [type, copied]);

  const onCreateRule = async () => {
    const fullName = [...(path || []), ruleName].join('/');
    if (!ruleName.length) return;
    if (type === 'create' && copied !== true) {
      await dispatch(
        createRule(fullName, {
          ...ruleDetail,
          full_rule_name: fullName,
          rule_name: ruleName,
          custom: true,
          can_edit: true,
          built_in: false
        })
      );
    } else if (copied === true) {
      await dispatch(
        createRule(String(full_rule_name).replace(/\/[^/]*$/, '/') + ruleName, {
          ...ruleDetail,
          rule_name: ruleName,
          full_rule_name:
            String(full_rule_name).replace(/\/[^/]*$/, '/') + ruleName,
          custom: true,
          built_in: false
        })
      );
    }
    closeRuleFirstLevelTab();
    await dispatch(
      addFirstLevelTab({
        url: ROUTES.RULE_DETAIL(
          String(full_rule_name ?? fullName).split('/')[
            String(full_rule_name ?? fullName).split('/').length - 1
          ] ?? ''
        ),
        value: ROUTES.RULE_DETAIL_VALUE(
          String(full_rule_name ?? fullName).split('/')[
            String(full_rule_name ?? fullName).split('/').length - 1
          ] ?? ''
        ),
        state: {
          full_rule_name: full_rule_name
            ? String(full_rule_name).replace(/\/[^/]*$/, '/') + ruleName
            : fullName,
          path: path,
          ruleDetail: {
            ...ruleDetail,
            rule_name: ruleName,
            full_rule_name: full_rule_name
              ? String(full_rule_name).replace(/\/[^/]*$/, '/') + ruleName
              : fullName,
            custom: true,
            built_in: false
          }
        },
        label: ruleName
      })
    );
  };

  const closeRuleFirstLevelTab = () => {
    dispatch(refreshRuleFolderTree(refreshRulesTreeIndicator ? false : true));
    dispatch(closeFirstLevelTab(firstLevelActiveTab));
  };

  const onChangeRuleName = (e: ChangeEvent<HTMLInputElement>) => {
    setRuleName(e.target.value);
    if (path) {
      const fullName = [...(path || []), e.target.value].join('/');
      dispatch(
        setUpdatedRule({
          ...ruleDetail,
          full_rule_name: fullName
        })
      );
    } else {
      dispatch(
        setUpdatedRule({
          ...ruleDetail,
          full_rule_name:
            String(full_rule_name).replace(/\/[^/]*$/, '/') + e.target.value
        })
      );
    }
  };
  const onCopy = (): void => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.RULE_DETAIL(
          String(full_rule_name).split('/')[
            String(full_rule_name).split('/').length - 1
          ] + '_copy' ?? ''
        ),
        value: ROUTES.RULE_DETAIL_VALUE(
          String(full_rule_name).split('/')[
            String(full_rule_name).split('/').length - 1
          ] + '_copy' ?? ''
        ),
        state: {
          full_rule_name: full_rule_name,
          copied: true,
          path: path,
          ruleDetail: {
            ...ruleDetail,
            full_rule_name: full_rule_name + '_copy',
            custom: true,
            built_in: false,
            can_edit: true
          },
          type: 'create'
        },
        label: `${
          String(full_rule_name).split('/')[
            String(full_rule_name).split('/').length - 1
          ]
        }_copy`
      })
    );
  };

  const onDelete = async () => {
    RulesApi.deleteRule(full_rule_name).then(async () =>
      closeRuleFirstLevelTab()
    );
  };

  return (
    <>
      <div className="relative">
        <RuleActionGroup
          onSave={onCreateRule}
          onCopy={onCopy}
          onDelete={() => setDeleteDialogOpen(true)}
        />

        {type !== 'create' ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="definitionsrules" className="w-5 h-5 shrink-0" />
              <div className="text-lg font-semibold truncate">
                Rule: {full_rule_name}
              </div>
            </div>
          </div>
        ) : (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-lg font-semibold truncate">
                Rule:{' '}
                {path
                  ? [...(path || []), ''].join('/')
                  : String(full_rule_name).replace(/\/[^/]*$/, '/')}
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
        {activeTab === 'definition' && (
          <RuleDefinition rule={ruleDetail ?? {}} />
        )}
        {activeTab === 'python_code' && <PythonCode rule={ruleDetail ?? {}} />}
      </div>
      <ConfirmDialog
        open={deleteDialogOpen}
        onClose={() => setDeleteDialogOpen(false)}
        onConfirm={onDelete}
        message={`Are you sure you want to delete the rule ${full_rule_name}`}
      />
    </>
  );
};

export default RuleDetail;
