import React, { useEffect } from 'react';
import { ConnectionSpecProviderTypeEnum } from '../../../api';
import BigqueryConnection from '../../Dashboard/DatabaseConnection/BigqueryConnection';
import SnowflakeConnection from '../../Dashboard/DatabaseConnection/SnowflakeConnection';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionBasic,
  setIsUpdatedConnectionBasic,
  setConnectionBasic,
  updateConnectionBasic
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";
import TimezoneSelect from "../../TimezoneSelect";

const ConnectionDetail = () => {
  const { connection }: { connection: string } = useParams();

  const { connectionBasic, isUpdatedConnectionBasic } = useSelector(
    (state: IRootState) => state.connection
  );

  const { isUpdating } = useSelector((state: IRootState) => state.connection);
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (connectionBasic?.connection_name !== connection) {
      dispatch(getConnectionBasic(connection));
    }
  }, [connection]);

  const onChange = (obj: any) => {
    dispatch(
      setConnectionBasic({
        ...connectionBasic,
        ...obj
      })
    );
    dispatch(setIsUpdatedConnectionBasic(true));
  };

  const onUpdate = async () => {
    if (!connectionBasic) {
      return;
    }
    await dispatch(
      updateConnectionBasic(connection, connectionBasic)
    );
    await dispatch(getConnectionBasic(connection));
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
              <div>{connectionBasic?.connection_name}</div>
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">
              <div>Time zone:</div>
            </td>
            <td className="px-4 py-2">
              <TimezoneSelect
                value={connectionBasic?.time_zone}
                onChange={(value) => onChange({ time_zone: value })}
              />
            </td>
          </tr>
        </tbody>
      </table>

      <div className="px-4">
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.bigquery && (
          <BigqueryConnection
            bigquery={connectionBasic?.bigquery}
            onChange={(bigquery) => onChange({ bigquery })}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.snowflake && <SnowflakeConnection />}
      </div>
    </div>
  );
};

export default ConnectionDetail;
