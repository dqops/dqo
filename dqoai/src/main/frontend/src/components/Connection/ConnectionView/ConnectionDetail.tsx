import React, { useEffect, useState } from 'react';
import {
  ConnectionBasicModel,
  ConnectionSpecProviderTypeEnum
} from '../../../api';
import Input from '../../Input';
import BigqueryConnection from '../../Dashboard/DatabaseConnection/BigqueryConnection';
import SnowflakeConnection from '../../Dashboard/DatabaseConnection/SnowflakeConnection';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionBasic,
  updateConnectionBasic
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ConnectionActionGroup from './ConnectionActionGroup';

interface IConnectionDetailProps {
  connectionName: string;
}

const ConnectionDetail: React.FC<IConnectionDetailProps> = ({
  connectionName
}) => {
  const [updatedConnectionBasic, setUpdatedConnectionBasic] =
    useState<ConnectionBasicModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const { connectionBasic, isUpdating } = useSelector(
    (state: IRootState) => state.connection
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    setUpdatedConnectionBasic(connectionBasic);
  }, [connectionBasic]);

  useEffect(() => {
    dispatch(getConnectionBasic(connectionName));
  }, [connectionName]);

  const onChange = (obj: any) => {
    setUpdatedConnectionBasic({
      ...updatedConnectionBasic,
      ...obj
    });
    setIsUpdated(true);
  };

  const onUpdate = async () => {
    await dispatch(
      updateConnectionBasic(connectionName, updatedConnectionBasic)
    );
    await dispatch(getConnectionBasic(connectionName));
    setIsUpdated(false);
  };

  return (
    <div className="p-4">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdating={isUpdating}
        isUpdated={isUpdated}
      />
      <table className="mb-6">
        <tbody>
          <tr>
            <td className="px-4 py-2">
              <div>Connection name:</div>
            </td>
            <td className="px-4 py-2">
              <div>{updatedConnectionBasic?.connection_name}</div>
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">
              <div>Time zone:</div>
            </td>
            <td className="px-4 py-2">
              <Input
                value={updatedConnectionBasic?.time_zone}
                onChange={(e) => onChange({ time_zone: e.target.value })}
              />
            </td>
          </tr>
        </tbody>
      </table>

      <div className="px-4">
        {updatedConnectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.bigquery && (
          <BigqueryConnection
            bigquery={updatedConnectionBasic?.bigquery}
            onChange={onChange}
          />
        )}
        {updatedConnectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.snowflake && <SnowflakeConnection />}
      </div>
    </div>
  );
};

export default ConnectionDetail;
