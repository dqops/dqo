import React, { useState } from 'react';
import {
  ConnectionBasicModel,
  ConnectionSpecProviderTypeEnum
} from '../../../api';
import Input from '../../Input';
import BigqueryConnection from '../../Dashboard/DatabaseConnection/BigqueryConnection';
import SnowflakeConnection from '../../Dashboard/DatabaseConnection/SnowflakeConnection';
import Button from '../../Button';
import ConfirmDialog from './ConfirmDialog';
import { ConnectionApiClient } from '../../../services/apiClient';

interface IConnectionDetailProps {
  connectionBasic?: ConnectionBasicModel;
  setConnectionBasic: (value: ConnectionBasicModel) => void;
}

const ConnectionDetail: React.FC<IConnectionDetailProps> = ({
  connectionBasic,
  setConnectionBasic
}) => {
  const [isOpen, setIsOpen] = useState(false);

  const onChange = (obj: any) => {
    setConnectionBasic({
      ...connectionBasic,
      ...obj
    });
  };

  const onRemove = async () => {
    if (connectionBasic) {
      await ConnectionApiClient.deleteConnection(
        connectionBasic.connection_name ?? ''
      );
    }
  };

  return (
    <div className="p-4">
      <table className="mb-6">
        <tbody>
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
        </tbody>
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
        <Button color="error" label="Delete" onClick={() => setIsOpen(true)} />
      </div>

      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        connection={connectionBasic}
        onConfirm={onRemove}
      />
    </div>
  );
};

export default ConnectionDetail;
