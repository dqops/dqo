import Button from "../Button";
import React from "react";
import { useSelector } from "react-redux";
import { getFirstLevelSensorState } from "../../redux/selectors";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { updateRule } from "../../redux/actions/sensor.actions";

export const RuleActionGroup = () => {
  const { full_rule_name, ruleDetail, isUpdating, isUpdatedRuleDetail } = useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();

  const onSave = () => {
    dispatch(updateRule(full_rule_name, ruleDetail))
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
        onClick={onSave}
        loading={isUpdating}
      />
    </div>
  );
};
