import React from 'react';
import SvgIcon from '../../SvgIcon';

interface ISelectDatabaseProps {
  onSelect: (db: string) => void;
}

const SelectDatabase: React.FC<ISelectDatabaseProps> = ({ onSelect }) => {
  return (
    <div>
      <div className="text-2xl font-semibold mb-4">Select a database</div>
      <div className="flex space-x-6">
        <div
          className="bg-white rounded-lg shadow-lg px-4 py-8 flex-1 cursor-pointer border border-gray-100 min-h-40 flex flex-col items-center justify-center"
          onClick={() => onSelect('big-query')}
        >
          <SvgIcon name="big-query" className="mb-3 w-16" />
          <div className="text-3xl font-semibold">
            Bigquery
          </div>
        </div>
        <div
          className="bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 min-h-40 flex flex-col items-center justify-center"
          onClick={() => onSelect('snowflake')}
        >
          <SvgIcon name="snowflake" className="mb-3 w-16 text-blue-500" />
          <div className="text-3xl font-semibold">
            Snowflake
          </div>
        </div>
      </div>
    </div>
  );
};

export default SelectDatabase;
