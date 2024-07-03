import React from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { updateRule } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';
import Button from '../Button';

type RuleActionGroupProps = {
  onSave: () => void;
  onCopy?: () => void;
  onDelete?: () => void;
};

export const RuleActionGroup = ({
  onSave,
  onCopy,
  onDelete
}: RuleActionGroupProps) => {
  const {
    full_rule_name,
    ruleDetail,
    isUpdating,
    isUpdatedRuleDetail,
    copied,
    type
  } = useSelector(getFirstLevelSensorState);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const dispatch = useActionDispatch();

  const handleSave = () => {
    if (onSave && (type === 'create' || copied === true)) {
      onSave();
      return;
    }
    if (full_rule_name) {
      dispatch(updateRule(full_rule_name, ruleDetail));
    }
  };

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {ruleDetail?.custom && (
        <Button
          color="primary"
          variant="outlined"
          label="Delete rule"
          className="w-40 !h-10"
          disabled={userProfile.can_manage_definitions !== true}
          onClick={onDelete}
        />
      )}
      <Button
        color="primary"
        variant="outlined"
        label="Copy"
        className="w-40 !h-10"
        disabled={userProfile.can_manage_definitions !== true}
        onClick={onCopy}
      />
      <Button
        color="primary"
        variant="contained"
        label="Save"
        className="w-40 !h-10"
        disabled={
          (!isUpdatedRuleDetail && !copied) ||
          userProfile.can_manage_definitions !== true
        }
        onClick={handleSave}
        loading={isUpdating}
      />
    </div>
  );
};
