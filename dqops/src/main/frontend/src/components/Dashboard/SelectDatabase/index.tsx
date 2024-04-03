import clsx from 'clsx';
import React from 'react';
import { ConnectionModelProviderTypeEnum } from '../../../api';
import { databaseOptions } from '../../../shared/constants';
import SvgIcon from '../../SvgIcon';

interface ISelectDatabaseProps {
  onSelect: (
    db: ConnectionModelProviderTypeEnum,
    nameOfDatabase?: string
  ) => void;
}

const SelectDatabase = ({ onSelect }: ISelectDatabaseProps) => {
  return (
    <div className="w-full">
      <div className="text-2xl font-semibold text-gray-900 mb-4">
        Select a database
      </div>
      <div className="flex justify-center content-center">
        <div className="flex flex-wrap gap-6">
          {databaseOptions.map((option, index) => (
            <div
              key={index}
              className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
              onClick={() => onSelect(option.type, option.name)}
            >
              <SvgIcon
                name={option.iconName}
                className={clsx(
                  'mb-3 text-blue-500',
                  option.name === 'Spark' && 'w-30'
                )}
              />
              <div className="text-lg font-semibold text-gray-700">
                {option.displayName}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SelectDatabase;
