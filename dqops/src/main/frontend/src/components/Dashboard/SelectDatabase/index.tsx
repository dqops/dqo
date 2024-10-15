import clsx from 'clsx';
import React from 'react';
import { ConnectionModelProviderTypeEnum } from '../../../api';
import { databaseOptions } from '../../../shared/constants';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';

interface ISelectDatabaseProps {
  onSelect: (
    db: ConnectionModelProviderTypeEnum,
    nameOfDatabase?: string
  ) => void;
}

const SelectDatabase = ({ onSelect }: ISelectDatabaseProps) => {
  const [filter, setFilter] = React.useState<string>('');
  const [filteredDatabases, setFilteredDatabases] =
    React.useState<any[]>(databaseOptions);

  const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilter(e.target.value);
    setFilteredDatabases(
      databaseOptions.filter((option) =>
        option.displayName.toLowerCase().includes(e.target.value.toLowerCase())
      )
    );
  };

  return (
    <div className="w-full">
      <div className="text-2xl font-semibold text-gray-900 mb-4">
        Select a database
      </div>
      <div className="mb-4">
        <Input
          placeholder="Search"
          onChange={handleFilterChange}
          value={filter}
          className="!min-w-100 !max-w-100"
        />
      </div>
      <div className="flex content-center">
        <div className="flex flex-wrap gap-6">
          {filteredDatabases.map((option, index) => (
            <div
              key={index}
              className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
              onClick={() => onSelect(option.type, option.name)}
            >
              {option.type === ConnectionModelProviderTypeEnum.hana ? (
                <p className="p-8 font-bold text-xl">
                  <span style={{ color: '#008FD3' }}>SAP </span>
                  <span style={{ color: '#debb00' }}>HANA</span>
                </p>
              ) : (
                <SvgIcon
                  name={option.iconName}
                  className={clsx(
                    'mb-3 text-blue-500',
                    option.name === 'Spark' && 'w-30',
                    option.iconName === 'perconaserverformysql' && 'w-20'
                  )}
                />
              )}
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
