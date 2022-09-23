import React, { useEffect, useState } from 'react';
import { ConnectionModel, ConnectionSpecProviderTypeEnum } from '../../../api';
import Input from '../../Input';
import BigqueryConnection from '../../Dashboard/DatabaseConnection/BigqueryConnection';
import SnowflakeConnection from '../../Dashboard/DatabaseConnection/SnowflakeConnection';

interface IConnectionDetailProps {
  connection?: ConnectionModel;
  onChange?: any;
}

const ConnectionDetail: React.FC<IConnectionDetailProps> = ({
  connection,
  onChange
}) => {
  return (
    <div className="p-4">
      <table className="mb-6">
        <tr>
          <td className="px-4 py-2">
            <div>Connection name:</div>
          </td>
          <td className="px-4 py-2">
            <div>{connection?.name}</div>
          </td>
        </tr>
        <tr>
          <td className="px-4 py-2">
            <div>Time zone:</div>
          </td>
          <td className="px-4 py-2">
            <Input
              value={connection?.spec?.time_zone}
              onChange={(e) =>
                onChange({ spec: { time_zone: e.target.value } })
              }
            />
          </td>
        </tr>
      </table>

      <div className="px-4">
        {connection?.spec?.provider_type ===
          ConnectionSpecProviderTypeEnum.bigquery && (
          <BigqueryConnection
            spec={connection?.spec?.bigquery}
            onChange={onChange}
          />
        )}
        {connection?.spec?.provider_type ===
          ConnectionSpecProviderTypeEnum.snowflake && <SnowflakeConnection />}
      </div>
    </div>
  );
};

export default ConnectionDetail;
