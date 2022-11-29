import React from 'react';
import Button from '../../Button';

interface IActionGroupProps {
  isDisabled?: boolean;
  onUpdate: () => void;
  isUpdating?: boolean;
  isUpdated?: boolean;
}

const ColumnActionGroup = ({
  isUpdated,
  isUpdating,
  isDisabled,
  onUpdate
}: IActionGroupProps) => {
  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      <Button
        color={isUpdated && !isDisabled ? 'primary' : 'secondary'}
        variant="contained"
        label="Save"
        className="w-40"
        onClick={onUpdate}
        loading={isUpdating}
        disabled={isDisabled}
      />
    </div>
  );
};

export default ColumnActionGroup;
