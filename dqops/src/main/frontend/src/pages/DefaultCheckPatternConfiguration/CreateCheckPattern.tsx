import React, { useState } from 'react';
import {
  DefaultColumnChecksPatternListModel,
  DefaultTableChecksPatternListModel,
  TargetColumnPatternSpec,
  TargetTablePatternSpec
} from '../../api';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import DefaultCheckTargetConfiguration from './DefaultCheckTargetConfiguration';
import {
  DefaultColumnCheckPatternsApiClient,
  DefaultTableCheckPatternsApiClient
} from '../../services/apiClient';
import { useDefinition } from '../../contexts/definitionContext';

type TCreateCheckPatternProps = {
  type: 'table' | 'column';
};

type TTarget =
  | DefaultColumnChecksPatternListModel
  | DefaultTableChecksPatternListModel;

type TTargetSpec = TargetColumnPatternSpec | TargetTablePatternSpec;

export default function CreateCheckPattern({ type }: TCreateCheckPatternProps) {
  const { openDefaultCheckPatternFirstLevelTab } = useDefinition();
  const [target, setTarget] = useState<TTarget>({});
  const targetSpecKey = type === 'column' ? 'target_column' : 'target_table';

  const onChangeTarget = (
    updatedTarget: Partial<TTarget> | Partial<TTargetSpec>
  ) => {
    if ('pattern_name' in updatedTarget || 'priority' in updatedTarget) {
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
    if (!target.pattern_name) return;
    if (type === 'column') {
      DefaultColumnCheckPatternsApiClient.createDefaultColumnChecksPatternTarget(
        target.pattern_name,
        target
      );
    } else {
      DefaultTableCheckPatternsApiClient.createDefaultTableChecksPatternTarget(
        target.pattern_name,
        target
      );
    }
    openDefaultCheckPatternFirstLevelTab(type, target.pattern_name);
    // onChangeCreating();
  };

  return (
    <>
      <div className="px-4">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">
              Create {type} check pattern
            </div>
          </div>
          <Button
            label="Save"
            color="primary"
            className="pl-14 pr-14"
            onClick={onSave}
            disabled={!target.pattern_name}
          />
        </div>
        <DefaultCheckTargetConfiguration
          type={type}
          target={target}
          onChangeTarget={onChangeTarget}
          create={true}
        />
      </div>
    </>
  );
}
