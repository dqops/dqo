import React from 'react';

import Button from '../../Button';
import Input from '../../Input';
import BigqueryConnection from './BigqueryConnection';
import SnowflakeConnection from './SnowflakeConnection';
import {
  ConnectionBasicModel,
  ConnectionBasicModelProviderTypeEnum
} from '../../../api';
import { ConnectionApiClient } from '../../../services/apiClient';
import { data } from 'autoprefixer';
import { useTree } from '../../../contexts/treeContext';

interface IDatabaseConnectionProps {
  onPrev: () => void;
  onNext: () => void;
  database: ConnectionBasicModel;
  onChange: (db: ConnectionBasicModel) => void;
}

const DatabaseConnection = ({
  onPrev,
  onNext,
  database,
  onChange
}: IDatabaseConnectionProps) => {
  const { addConnection } = useTree();

  const onSave = async () => {
    if (!database.connection_name) {
      return;
    }

    await ConnectionApiClient.createConnectionBasic(
      database?.connection_name ?? '',
      database
    );
    const res = await ConnectionApiClient.getConnectionBasic(
      database.connection_name
    );
    console.log('0-----------', res.data);
    addConnection(res.data);
    onNext();
  };

  return (
    <div>
      <div className="flex justify-between mb-4">
        <div>
          <div className="text-2xl font-semibold mb-3">Connect a database</div>
          <div>
            {database.provider_type ===
            ConnectionBasicModelProviderTypeEnum.bigquery
              ? 'Google Bigquery'
              : 'Snowflake'}{' '}
            Connection Settings
          </div>
        </div>
        <img
          src={
            database.provider_type ===
            ConnectionBasicModelProviderTypeEnum.bigquery
              ? '/bigQuery.png'
              : '/snowflake.png'
          }
          className="h-16"
          alt="db logo"
        />
      </div>

      <div className="bg-white rounded-lg px-4 py-6 border border-gray-100">
        <Input
          label="Connection Name"
          className="mb-4"
          value={database.connection_name}
          onChange={(e) =>
            onChange({ ...database, connection_name: e.target.value })
          }
        />
        <Input
          label="Timezone"
          className="mb-4"
          value={database.time_zone}
          onChange={(e) => onChange({ ...database, time_zone: e.target.value })}
        />

        {database.provider_type !==
          ConnectionBasicModelProviderTypeEnum.bigquery && (
          <>
            <Input
              label="Database Name"
              className="mb-4"
              value={database.database_name}
              onChange={(e) =>
                onChange({ ...database, database_name: e.target.value })
              }
            />
            <Input
              label="JDBC driver url"
              className="mb-4"
              value={database.url}
              onChange={(e) => onChange({ ...database, url: e.target.value })}
            />
            <Input
              label="Username"
              className="mb-4"
              value={database.user}
              onChange={(e) => onChange({ ...database, user: e.target.value })}
            />
            <Input
              label="Password"
              className="mb-6"
              value={database.password}
              onChange={(e) =>
                onChange({ ...database, password: e.target.value })
              }
            />
          </>
        )}

        <div className="mt-6">
          {database.provider_type ===
          ConnectionBasicModelProviderTypeEnum.bigquery ? (
            <BigqueryConnection
              bigquery={database.bigquery}
              onChange={(bigquery) => onChange({ ...database, bigquery })}
            />
          ) : (
            <SnowflakeConnection />
          )}
        </div>

        <div className="flex space-x-4 justify-end mt-6">
          <Button
            color="primary"
            variant="contained"
            label="Save"
            className="w-40"
            onClick={onSave}
          />
        </div>
      </div>
    </div>
  );
};

export default DatabaseConnection;
