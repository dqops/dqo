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
import { useTree } from '../../../contexts/treeContext';
import { useHistory } from 'react-router-dom';
import TimezoneSelect from "../../TimezoneSelect";
import { ROUTES } from "../../../shared/routes";

interface IDatabaseConnectionProps {
  onNext: () => void;
  database: ConnectionBasicModel;
  onChange: (db: ConnectionBasicModel) => void;
}

const DatabaseConnection = ({
  database,
  onChange
}: IDatabaseConnectionProps) => {
  const { addConnection } = useTree();
  const history = useHistory();

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
    addConnection(res.data);
    history.push(`${ROUTES.CONNECTION_DETAIL(database.connection_name, 'schemas')}?import_schema=true`);
  };

  const onTestConnection = async () => {
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
        <TimezoneSelect
          label="Timezone"
          className="mb-4"
          onChange={(value) => onChange({ ...database, time_zone: value })}
          value={database.time_zone}
        />

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
            variant="outlined"
            label="Test Connection"
            onClick={onTestConnection}
          />

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
