import React, { useState } from 'react';
import {
  ColumnQualityPolicyListModel,
  TableQualityPolicyListModel,
  TargetColumnPatternSpec,
  TargetTablePatternSpec
} from '../../api';
import Button from '../../components/Button';
import { useDefinition } from '../../contexts/definitionContext';
import {
  ColumnQualityPoliciesApiClient,
  TableQualityPoliciesApiClient
} from '../../services/apiClient';
import DefaultCheckTargetConfiguration from './DefaultCheckTargetConfiguration';

type TCreateCheckPatternProps = {
  type: 'table' | 'column';
};

type TTarget = ColumnQualityPolicyListModel | TableQualityPolicyListModel;

type TTargetSpec = TargetColumnPatternSpec | TargetTablePatternSpec;

export default function CreateCheckPattern({ type }: TCreateCheckPatternProps) {
  const { openDefaultCheckPatternFirstLevelTab } = useDefinition();
  const [target, setTarget] = useState<TTarget>({});
  const targetSpecKey = type === 'column' ? 'target_column' : 'target_table';

  const onChangeTarget = (
    updatedTarget: Partial<TTarget> | Partial<TTargetSpec>
  ) => {
    if (
      'policy_name' in updatedTarget ||
      'priority' in updatedTarget ||
      'description' in updatedTarget ||
      'disabled' in updatedTarget
    ) {
      setTarget((prev) => ({
        ...prev,
        ...updatedTarget
      }));
    } else {
      setTarget((prev) => ({
        ...prev,
        [targetSpecKey]: {
          ...(target?.[targetSpecKey as keyof TTarget] as TTargetSpec),
          ...updatedTarget
        }
      }));
    }
  };

  const onSave = () => {
    if (!target.policy_name) return;
    if (type === 'column') {
      ColumnQualityPoliciesApiClient.createColumnQualityPolicyTarget(
        target.policy_name,
        target
      );
    } else {
      TableQualityPoliciesApiClient.createTableQualityPolicyTarget(
        target.policy_name,
        target
      );
    }
    openDefaultCheckPatternFirstLevelTab(type, target.policy_name);
    // onChangeCreating();
  };

  return (
    <>
      <div>
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full text-lg font-semibold truncate">
            Create {type} check pattern
          </div>
          <Button
            label="Save"
            color="primary"
            className="pl-14 pr-14"
            onClick={onSave}
            disabled={!target.policy_name}
          />
        </div>
        <div className="px-4 pt-2">
          <DefaultCheckTargetConfiguration
            type={type}
            target={target}
            onChangeTarget={onChangeTarget}
            create={true}
          />
        </div>
      </div>
    </>
  );
}
