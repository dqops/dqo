import React, { useState } from 'react';
import Button from '../../../../Button';
import clsx from 'clsx';
import SvgIcon from '../../../../SvgIcon';
import Input from '../../../../Input';

type TFirstLevelConfiguretion = {
  name?: string;
  onChangeName: (name: string) => void;
  onSave: () => void;
  isButtonEnabled: boolean;
  comparisonAlreadyExist?: boolean;
  onBack: () => void;
};

export default function FirstLineNameConfiguration({
  name,
  onChangeName,
  onSave,
  isButtonEnabled,
  comparisonAlreadyExist,
  onBack
}: TFirstLevelConfiguretion) {
  return (
    <div className="flex items-center justify-between border-b border-gray-300 mb-4 py-4 px-8 w-full">
      <div className="flex items-center justify-center gap-x-5">
        <div className="font-bold text-center">
          Table comparison configuration name:{' '}
        </div>
        <Input
          className={
            name?.length === 0 ? 'min-w-80 border border-red-500' : 'min-w-80'
          }
          value={name}
          onChange={(e) => onChangeName(e.target.value)}
          placeholder="Table comparison configuration name"
        />
      </div>
      {comparisonAlreadyExist ? (
        <div className="bg-red-300 p-4 rounded-lg text-white border-2 border-red-500">
          A table comparison with this name already exists
        </div>
      ) : null}
      <div className="flex justify-center items-center gap-x-2">
        <Button
          onClick={onSave}
          label="Save"
          color="primary"
          className="w-40"
          disabled={!isButtonEnabled}
        />
        <Button
          label="Back"
          color="primary"
          variant="text"
          className="px-0"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
      </div>
    </div>
  );
}
