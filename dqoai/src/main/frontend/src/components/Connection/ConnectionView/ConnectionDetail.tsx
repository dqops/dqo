import React, { useEffect, useState } from 'react';
import {
  ConnectionBasicModel,
  ConnectionSpecProviderTypeEnum
} from '../../../api';
import Input from '../../Input';
import BigqueryConnection from '../../Dashboard/DatabaseConnection/BigqueryConnection';
import SnowflakeConnection from '../../Dashboard/DatabaseConnection/SnowflakeConnection';
import { AxiosResponse } from 'axios';
import { ConnectionApiClient } from '../../../services/apiClient';

interface IConnectionDetailProps {
  connectionName: string;
}

const ConnectionDetail: React.FC<IConnectionDetailProps> = ({
  connectionName
}) => {
  const [connectionBasic, setConnectionBasic] =
    useState<ConnectionBasicModel>();

  const fetchConnectionBasic = async () => {
    try {
      const res: AxiosResponse<ConnectionBasicModel> =
        await ConnectionApiClient.getConnectionBasic(connectionName);

      setConnectionBasic(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchConnectionBasic().then();
  }, []);

  const onChange = (obj: any) => {
    setConnectionBasic({
      ...connectionBasic,
      ...obj
    });
  };

  return (
    <div className="p-4">
      <table className="mb-6">
        <tr>
          <td className="px-4 py-2">
            <div>Connection name:</div>
          </td>
          <td className="px-4 py-2">
            <div>{connectionBasic?.connection_name}</div>
          </td>
        </tr>
        <tr>
          <td className="px-4 py-2">
            <div>Time zone:</div>
          </td>
          <td className="px-4 py-2">
            <Input
              value={connectionBasic?.time_zone}
              onChange={(e) => onChange({ time_zone: e.target.value })}
            />
          </td>
        </tr>
      </table>

      <div className="px-4">
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.bigquery && (
          <BigqueryConnection
            spec={connectionBasic?.bigquery}
            onChange={onChange}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.snowflake && <SnowflakeConnection />}
      </div>
    </div>
  );
};

export default ConnectionDetail;
