import Input, { IInputProps } from "../Input";
import React from "react";

interface EditInputProps extends IInputProps {
  isReadOnly?: boolean;
}

export const EditInput = ({ isReadOnly, value, ...props }: EditInputProps) => {
  if (isReadOnly) {
    return (
      <div>
        {value}
      </div>
    );
  }

  return (
    <Input
      value={value}
      {...props}
    />
  );
}

export default EditInput;