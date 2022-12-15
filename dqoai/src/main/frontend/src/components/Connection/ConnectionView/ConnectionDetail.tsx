import React, { useEffect } from 'react';
import { ConnectionSpecProviderTypeEnum } from '../../../api';
import Input from '../../Input';
import BigqueryConnection from '../../Dashboard/DatabaseConnection/BigqueryConnection';
import SnowflakeConnection from '../../Dashboard/DatabaseConnection/SnowflakeConnection';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionBasic,
  setIsUpdatedConnectionBasic,
  setUpdatedConnectionBasic,
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
  const { updatedConnectionBasic, isUpdatedConnectionBasic } = useSelector(
    (state: IRootState) => state.connection
  );

  const { isUpdating } = useSelector((state: IRootState) => state.connection);
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (!updatedConnectionBasic) {
      dispatch(getConnectionBasic(connectionName));
    }
  }, [connectionName]);

  const onChange = (obj: any) => {
    dispatch(
      setUpdatedConnectionBasic({
        ...updatedConnectionBasic,
        ...obj
      })
    );
    dispatch(setIsUpdatedConnectionBasic(true));
  };

  const onUpdate = async () => {
    if (!updatedConnectionBasic) {
      return;
    }
    await dispatch(
      updateConnectionBasic(connectionName, updatedConnectionBasic)
    );
    await dispatch(getConnectionBasic(connectionName));
    dispatch(setIsUpdatedConnectionBasic(false));
  };

  return (
    <div className="p-4">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdating={isUpdating}
        isUpdated={isUpdatedConnectionBasic}
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
