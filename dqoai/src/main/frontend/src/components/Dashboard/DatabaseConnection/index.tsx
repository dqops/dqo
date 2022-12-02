import React from 'react';

import { DATABASE_TYPE } from '../../../shared/enums';
import Button from '../../Button';
import Input from '../../Input';
import BigqueryConnection from './BigqueryConnection';
import SnowflakeConnection from './SnowflakeConnection';
import { IDatabase } from '../../../shared/interfaces';

interface IDatabaseConnectionProps {
  type?: DATABASE_TYPE;
  onPrev: () => void;
  onNext: () => void;
  database: IDatabase;
  onChange: (db: IDatabase) => void;
}

const DatabaseConnection = ({
  type,
  onPrev,
  onNext,
  database,
  onChange,
}: IDatabaseConnectionProps) => {
  return (
    <div>
      <div className="flex justify-between mb-4">
        <div>
          <div className="text-2xl font-semibold mb-3">Connect a database</div>
          <div>
            {type === DATABASE_TYPE.BIGQUERY ? 'Google Bigquery' : 'Snowflake'}{' '}
            Connection Settings
          </div>
        </div>
        <img
          src={
            type === DATABASE_TYPE.BIGQUERY ? '/bigQuery.png' : '/snowflake.png'
          }
          className="h-16"
          alt="db logo"
        />
      </div>

      <div className="bg-white rounded-lg px-4 py-6 border border-gray-100">
        <Input
          label="Database Name"
          className="mb-4"
          value={database.connectionName}
          onChange={(e) =>
            onChange({ ...database, connectionName: e.target.value })
          }
        />
        <Input label="JDBC driver url" className="mb-4" />
        <Input label="Username" className="mb-4" />
        <Input label="Password" className="mb-6" />

        {type === DATABASE_TYPE.BIGQUERY ? (
          <BigqueryConnection />
        ) : (
          <SnowflakeConnection />
        )}
        <Input label="Timezone" className="mb-4" />

        <div className="flex space-x-4 justify-end mt-6">
          <Button
            color="primary"
            variant="outlined"
            label="Prev"
            className="w-40"
            onClick={onPrev}
          />
          <Button
            color="primary"
            variant="contained"
            label="Next"
            className="w-40"
            onClick={onNext}
          />
        </div>
      </div>
    </div>
  );
};

export default DatabaseConnection;
