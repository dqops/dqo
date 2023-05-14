import React from "react";
import Select, { SelectProps } from "../Select";

interface EditSelectProps extends SelectProps {
  isReadOnly?: boolean;
}

export const EditSelect = ({ isReadOnly, value, ...props }: EditSelectProps) => {
  if (isReadOnly) {
    return (
      <div>
        {value}
      </div>
    );
  }

  return (
    <Select
      value={value}
      {...props}
    />
  );
}

export default EditSelect;