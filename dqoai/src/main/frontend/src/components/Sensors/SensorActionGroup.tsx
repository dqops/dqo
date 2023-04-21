import Button from "../Button";
import React from "react";
import { useSelector } from "react-redux";
import { getFirstLevelSensorState } from "../../redux/selectors";

export const SensorActionGroup = () => {
  const { ruleDetail } = useSelector(getFirstLevelSensorState);

  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {ruleDetail?.custom && (
        <Button
          color="primary"
          variant="outlined"
          label="Delete"
          className="w-40 !h-10"
        />
      )}
      <Button
        color="primary"
        variant="contained"
        label="Save"
        className="w-40 !h-10"
      />
    </div>
  );
};
