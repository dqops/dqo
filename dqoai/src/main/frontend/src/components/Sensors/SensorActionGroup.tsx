import Button from "../Button";
import React from "react";

export const SensorActionGroup = () => {
  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      <Button
        color="primary"
        variant="outlined"
        label="Delete"
        className="w-40 !h-10"
      />
      <Button
        color="primary"
        variant="contained"
        label="Save"
        className="w-40 !h-10"
      />
    </div>
  );
};
