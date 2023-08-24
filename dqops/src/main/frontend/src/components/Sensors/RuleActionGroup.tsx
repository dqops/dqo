import Button from '../Button';
import React from 'react';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { updateRule } from '../../redux/actions/definition.actions';

type RuleActionGroupProps = {
  onSave: () => void;
};

export const RuleActionGroup = ({ onSave }: RuleActionGroupProps) => {
  const { full_rule_name, ruleDetail, isUpdating, isUpdatedRuleDetail } =
    useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();

  const handleSave = () => {
    if (onSave) {
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
        />
      )}
      <Button
        color="primary"
        variant="contained"
        label="Save"
        className="w-40 !h-10"
        disabled={!isUpdatedRuleDetail}
        onClick={handleSave}
        loading={isUpdating}
      />
    </div>
  );
};
