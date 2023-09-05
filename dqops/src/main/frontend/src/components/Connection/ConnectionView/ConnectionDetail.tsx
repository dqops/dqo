import React, { useEffect, useState } from 'react';
import {
  ConnectionBasicModel,
  ConnectionRemoteModel,
  ConnectionRemoteModelConnectionStatusEnum,
  ConnectionSpecProviderTypeEnum
} from '../../../api';
import BigqueryConnection from '../../Dashboard/DatabaseConnection/BigqueryConnection';
import SnowflakeConnection from '../../Dashboard/DatabaseConnection/SnowflakeConnection';
import { useSelector } from 'react-redux';
import {
  getConnectionBasic,
  setConnectionBasic,
  setIsUpdatedConnectionBasic,
  updateConnectionBasic
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";
import ErrorModal from "../../Dashboard/DatabaseConnection/ErrorModal";
import Loader from "../../Loader";
import Button from "../../Button";
import { DataSourcesApi } from "../../../services/apiClient";
import ConfirmErrorModal from "../../Dashboard/DatabaseConnection/ConfirmErrorModal";
import PostgreSQLConnection from "../../Dashboard/DatabaseConnection/PostgreSQLConnection";
import RedshiftConnection from "../../Dashboard/DatabaseConnection/RedshiftConnection";
import SqlServerConnection from "../../Dashboard/DatabaseConnection/SqlServerConnection";
import OracleConnection from "../../Dashboard/DatabaseConnection/OracleConnection";
import MySQLConnection from "../../Dashboard/DatabaseConnection/MySQLConnection";
import { CheckTypes } from "../../../shared/routes";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { IRootState } from '../../../redux/reducers';
import clsx from 'clsx';

const ConnectionDetail = () => {
  const { connection, checkTypes }: { connection: string, checkTypes: CheckTypes } = useParams();

  const activeTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const { connectionBasic, isUpdatedConnectionBasic, isUpdating }: {
    connectionBasic?: ConnectionBasicModel
    isUpdatedConnectionBasic?: boolean;
    isUpdating?: boolean;
  } = useSelector(getFirstLevelState(checkTypes));
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const dispatch = useActionDispatch();
  const [isTesting, setIsTesting] = useState(false);
  const [testResult, setTestResult] = useState<ConnectionRemoteModel>();
  const [showError, setShowError] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [message, setMessage] = useState<string>();

  useEffect(() => {
    dispatch(getConnectionBasic(checkTypes, activeTab, connection));
  }, [checkTypes, activeTab, connection]);

  const onChange = (obj: any) => {
    dispatch(
      setConnectionBasic(checkTypes, activeTab, {
        ...connectionBasic,
        ...obj
      })
    );
    dispatch(setIsUpdatedConnectionBasic(checkTypes, activeTab, true));
  };

  const onConfirmSave = async () => {
    if (!connectionBasic) {
      return;
    }

    await dispatch(
      updateConnectionBasic(checkTypes, activeTab, connection, connectionBasic)
    );

    dispatch(getConnectionBasic(checkTypes, activeTab, connection));

    dispatch(setIsUpdatedConnectionBasic(checkTypes, activeTab, false));
    setShowConfirm(false);
  };

  const onUpdate = async () => {
    if (!connectionBasic) {
      return;
    }


    setIsTesting(true);
    const testRes = await DataSourcesApi.testConnection(false, connectionBasic);
    setIsTesting(false);

    if (testRes.data?.connectionStatus === ConnectionRemoteModelConnectionStatusEnum.SUCCESS) {
      await onConfirmSave();
    } else {
      setShowConfirm(true);
      setMessage(testRes.data?.errorMessage);
    }
  };

  const onTestConnection = async () => {
    try {
      setIsTesting(true);
      const res = await DataSourcesApi.testConnection(false, connectionBasic);
      setTestResult(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setIsTesting(false);
    }
  };

  const openErrorModal = () => {
    setShowError(true);
  };

  return (
    <div className={clsx("p-4",userProfile.can_manage_scheduler === false ? "pointer-events-none cursor-not-allowed" : "")}>
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
          ConnectionSpecProviderTypeEnum.snowflake && (
            <SnowflakeConnection
              snowflake={connectionBasic?.snowflake}
              onChange={(snowflake) => onChange({ snowflake })}
            />
          )
        }
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.redshift && (
            <RedshiftConnection
              redshift={connectionBasic?.redshift}
              onChange={(redshift) => onChange({ redshift })}
            />
          )
        }
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.sqlserver && (
            <SqlServerConnection
              sqlserver={connectionBasic?.sqlserver}
              onChange={(sqlserver) => onChange({ sqlserver })}
            />
          )
        }
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.postgresql && (
            <PostgreSQLConnection
              postgresql={connectionBasic?.postgresql}
              onChange={(postgresql) => onChange({ postgresql })}
            />
          )
        }
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.mysql && (
            <MySQLConnection
              mysql={connectionBasic?.mysql}
              onChange={(mysql) => onChange({ mysql })}
            />
          )
        }
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.oracle && (
            <OracleConnection
              oracle={connectionBasic?.oracle}
              onChange={(oracle) => onChange({ oracle })}
            />
          )
        }
      </div>

      <div className="flex space-x-4 justify-end items-center mt-6 px-4 mb-5">
        {isTesting && (
          <Loader isFull={false} className="w-8 h-8 !text-primary" />
        )}
        {
          testResult?.connectionStatus === ConnectionRemoteModelConnectionStatusEnum.SUCCESS && (
            <div className="text-primary text-sm">
              Connection successful
            </div>
          )
        }
        {
          testResult?.connectionStatus === ConnectionRemoteModelConnectionStatusEnum.FAILURE && (
            <div className="text-red-700 text-sm">
              <span>Connection failed</span>
              <span
                className="ml-2 underline cursor-pointer"
                onClick={openErrorModal}
              >
                Show more
              </span>
            </div>
          )
        }
        <Button
          color="primary"
          variant="outlined"
          label="Test Connection"
          onClick={onTestConnection}
          disabled={isTesting || userProfile.can_manage_data_sources===false}
        />
      </div>
      <ErrorModal
        open={showError}
        onClose={() => setShowError(false)}
        message={testResult?.errorMessage}
      />
      <ConfirmErrorModal
        open={showConfirm}
        onClose={() => setShowConfirm(false)}
        message={message}
        onConfirm={onConfirmSave}
      />
    </div>
  );
};

export default ConnectionDetail;
