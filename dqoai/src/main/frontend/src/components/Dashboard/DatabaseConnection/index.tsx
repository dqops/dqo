import React, { useEffect, useMemo, useState } from 'react';

import Button from '../../Button';
import Input from '../../Input';
import BigqueryConnection from './BigqueryConnection';
import SnowflakeConnection from './SnowflakeConnection';
import {
  ConnectionBasicModel,
  ConnectionBasicModelProviderTypeEnum, ConnectionRemoteModel, ConnectionRemoteModelConnectionStatusEnum
} from '../../../api';
import { ConnectionApiClient, SourceConnectionApi } from '../../../services/apiClient';
import { useTree } from '../../../contexts/treeContext';
import Loader from "../../Loader";
import ErrorModal from "./ErrorModal";
import ConfirmErrorModal from "./ConfirmErrorModal";
import PostgreSQLConnection from "./PostgreSQLConnection";
import PostgreSQLLogo from '../../SvgIcon/svg/postgresql.svg';
import RedshiftConnection from "./RedshiftConnection";
import RedshiftLogo from '../../SvgIcon/svg/redshift.svg';
import SqlServerConnection from "./SqlServerConnection";
import SqlServerLogo from '../../SvgIcon/svg/mssql-server.svg';
import MySQLConnection from "./MySQLConnection";
import MySQLLogo from '../../SvgIcon/svg/mysql.svg';

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
  const [isTesting, setIsTesting] = useState(false);
  const [testResult, setTestResult] = useState<ConnectionRemoteModel>();
  const [showError, setShowError] = useState(false);
  const [isSaving, setIsSaving] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [message, setMessage] = useState<string>();
  const [nameError, setNameError] = useState('');

  const onConfirmSave = async () => {
    if (!database.connection_name) {
      return;
    }

    setIsSaving(true);
    await ConnectionApiClient.createConnectionBasic(
      database?.connection_name ?? '',
      database
    );
    const res = await ConnectionApiClient.getConnectionBasic(
      database.connection_name
    );
    addConnection(res.data);
    setIsSaving(false);
    setShowConfirm(false);
  };

  useEffect(() => {
    if (!/^([A-Za-z0-9-_])*$/.test(database.connection_name as string)) {
      setNameError('Database name should be alphanumeric characters');
    }  else if (database.connection_name?.length && database.connection_name.length > 36) {
          setNameError('Database name cannot be longer than 36 characters');
    } else {
      setNameError('');
    }
  }, [database?.connection_name]);

  const onSave = async () => {
    if (!database.connection_name) {
      setNameError('Connection Name is required');
      return;
    }
    if (nameError) {
      return;
    }

    setIsTesting(true);
    let testRes;
    try {
      testRes = await SourceConnectionApi.testConnection(true, database);
      setIsTesting(false);
    } catch (err) {
      setIsTesting(false);
    } finally {
      if (testRes?.data?.connectionStatus === ConnectionRemoteModelConnectionStatusEnum.SUCCESS) {
        await onConfirmSave();
      } else {
        setShowConfirm(true);
        setMessage(testRes?.data?.errorMessage);
      }
    }
  };

  const onTestConnection = async () => {
    try {
      setIsTesting(true);
      const res = await SourceConnectionApi.testConnection(true, database);
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

  const getTitle = (provider?: ConnectionBasicModelProviderTypeEnum) => {
    switch (provider) {
      case ConnectionBasicModelProviderTypeEnum.bigquery:
        return 'Google Bigquery Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.snowflake:
        return 'Snowflake Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.postgresql:
        return 'PostgreSQL Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.redshift:
        return 'Redshift Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.sqlserver:
        return 'SQL Server Connection Settings';
        case ConnectionBasicModelProviderTypeEnum.mysql:
          return 'MYSQL Connection Settings';
      default:
        return 'Database Connection Settings'
    }
  };

  const components = {
    [ConnectionBasicModelProviderTypeEnum.bigquery]: (
      <BigqueryConnection
        bigquery={database.bigquery}
        onChange={(bigquery) => onChange({ ...database, bigquery })}
      />
    ),
    [ConnectionBasicModelProviderTypeEnum.snowflake]: (
      <SnowflakeConnection
        snowflake={database.snowflake}
        onChange={(snowflake) => onChange({ ...database, snowflake })}
      />
    ),
    [ConnectionBasicModelProviderTypeEnum.postgresql]: (
      <PostgreSQLConnection
        postgresql={database.postgresql}
        onChange={(postgresql) => onChange({ ...database, postgresql })}
      />
      ),
    [ConnectionBasicModelProviderTypeEnum.redshift]: (
      <RedshiftConnection
        redshift={database.redshift}
        onChange={(redshift) => onChange({ ...database, redshift })}
      />
    ),
    [ConnectionBasicModelProviderTypeEnum.sqlserver]: (
      <SqlServerConnection
        sqlserver={database.sqlserver}
        onChange={(sqlserver) => onChange({ ...database, sqlserver })}
      />
    ),
    [ConnectionBasicModelProviderTypeEnum.mysql]: (
      <MySQLConnection
        mysql={database.mysql}
        onChange={(mysql) => onChange({ ...database, mysql })}
      />
    )
  };

  const dbImage = useMemo(() => {
    switch (database.provider_type) {
      case ConnectionBasicModelProviderTypeEnum.bigquery:
        return '/bigQuery.png';
      case ConnectionBasicModelProviderTypeEnum.snowflake:
        return '/snowflake.png';
      case ConnectionBasicModelProviderTypeEnum.postgresql:
        return PostgreSQLLogo;
      case ConnectionBasicModelProviderTypeEnum.redshift:
        return RedshiftLogo;
      case ConnectionBasicModelProviderTypeEnum.sqlserver:
        return SqlServerLogo;
      case ConnectionBasicModelProviderTypeEnum.mysql:
        return MySQLLogo;
      default:
        return '';
    }
  }, [database.provider_type])

  return (
    <div>
      <div className="flex justify-between mb-4">
        <div>
          <div className="text-2xl font-semibold mb-3">Connect a database</div>
          <div>{getTitle(database.provider_type)}</div>
        </div>
        <img
          src={dbImage}
          className="h-16"
          alt="db logo"
        />
      </div>

      <div className="bg-white rounded-lg px-4 py-6 border border-gray-100">
        <Input
          label="Connection Name"
          value={database.connection_name}
          onChange={(e) =>
            onChange({ ...database, connection_name: e.target.value })
          }
          error={!!nameError}
          helperText={nameError}
        />

        <div className="mt-6">
          {database.provider_type ? components[database.provider_type] : ''}
        </div>

        <div className="flex space-x-4 justify-end items-center mt-6">
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
            disabled={isTesting}
          />

          <Button
            color="primary"
            variant="contained"
            label="Save"
            className="w-40"
            onClick={onSave}
            disabled={isTesting || isSaving}
          />
        </div>
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

export default DatabaseConnection;
