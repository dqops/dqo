import React, { useEffect, useMemo, useState } from 'react';
import Button from '../../Button';
import Input from '../../Input';
import {
  ConnectionBasicModel,
  ConnectionBasicModelProviderTypeEnum,
  ConnectionTestModel,
  ConnectionTestModelConnectionTestResultEnum
} from '../../../api';
import {
  ConnectionApiClient,
  DataSourcesApi
} from '../../../services/apiClient';
import { useTree } from '../../../contexts/treeContext';
import Loader from '../../Loader';
import ErrorModal from './ErrorModal';
import ConfirmErrorModal from './ConfirmErrorModal';
import BigqueryConnection from './BigqueryConnection';
import BigqueryLogo from '../../SvgIcon/svg/bigquery.svg';
import SnowflakeConnection from './SnowflakeConnection';
import SnowflakeLogo from '../../SvgIcon/svg/snowflake.svg';
import PostgreSQLConnection from './PostgreSQLConnection';
import PostgreSQLLogo from '../../SvgIcon/svg/postgresql.svg';
import RedshiftConnection from './RedshiftConnection';
import RedshiftLogo from '../../SvgIcon/svg/redshift.svg';
import SqlServerConnection from './SqlServerConnection';
import SqlServerLogo from '../../SvgIcon/svg/mssql-server.svg';
import MySQLConnection from './MySQLConnection';
import MySQLLogo from '../../SvgIcon/svg/mysql.svg';
import OracleConnection from './OracleConnection';
import OracleLogo from '../../SvgIcon/svg/oracle.svg';
import SvgIcon from '../../SvgIcon';

interface IDatabaseConnectionProps {
  onNext: () => void;
  database: ConnectionBasicModel;
  onChange: (db: ConnectionBasicModel) => void;
  nameOfdatabase?: string;
}

const DatabaseConnection = ({
  database,
  onChange,
  nameOfdatabase
}: IDatabaseConnectionProps) => {
  const { addConnection } = useTree();
  const [isTesting, setIsTesting] = useState(false);
  const [testResult, setTestResult] = useState<ConnectionTestModel>();
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
    } else if (
      database.connection_name?.length &&
      database.connection_name.length > 36
    ) {
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
    let testRes : ConnectionTestModel | null = null;
    try {
      testRes = (await DataSourcesApi.testConnection(true, database)).data;
      setIsTesting(false);
    } catch (err) {
      setIsTesting(false);
    } finally {
      if (
        testRes?.connectionTestResult ===
        ConnectionTestModelConnectionTestResultEnum.SUCCESS
      ) {
        await onConfirmSave();
      } else if (testRes?.connectionTestResult ===
        ConnectionTestModelConnectionTestResultEnum.CONNECTION_ALREADY_EXISTS) {
        setMessage(testRes?.errorMessage);
      }
    }
  };

  const onTestConnection = async () => {
    try {
      setIsTesting(true);
      const res = await DataSourcesApi.testConnection(true, database);
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
        return 'Google BigQuery Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.snowflake:
        return 'Snowflake Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.postgresql:
        return 'PostgreSQL Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.redshift:
        return 'Amazon Redshift Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.sqlserver:
        return 'Microsoft SQL Server Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.mysql:
        return 'MySQL Connection Settings';
      case ConnectionBasicModelProviderTypeEnum.oracle:
        return 'Oracle Database Connection Settings';
      default:
        return 'Database Connection Settings';
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
    ),
    [ConnectionBasicModelProviderTypeEnum.oracle]: (
      <OracleConnection
          oracle={database.oracle}
          onChange={(oracle) => onChange({ ...database, oracle })}
      />
    )
  };

  const dbImage = useMemo(() => {
    switch (database.provider_type) {
      case ConnectionBasicModelProviderTypeEnum.bigquery:
        return BigqueryLogo;
      case ConnectionBasicModelProviderTypeEnum.snowflake:
        return SnowflakeLogo;
      case ConnectionBasicModelProviderTypeEnum.postgresql:
        return PostgreSQLLogo;
      case ConnectionBasicModelProviderTypeEnum.redshift:
        return RedshiftLogo;
      case ConnectionBasicModelProviderTypeEnum.sqlserver:
        return SqlServerLogo;
      case ConnectionBasicModelProviderTypeEnum.mysql:
        return MySQLLogo;
      case ConnectionBasicModelProviderTypeEnum.oracle:
        return OracleLogo;
      default:
        return '';
    }
  }, [database.provider_type]);

  return (
    <div>
      <div className="flex justify-between mb-4">
        <div>
          <div className="text-2xl font-semibold mb-3">Connect a database</div>
          <div>
            {nameOfdatabase
              ? nameOfdatabase + ' Connection Settings'
              : getTitle(database.provider_type)}
          </div>
        </div>
        {nameOfdatabase ? (
          <SvgIcon
            name={nameOfdatabase.toLowerCase().replace(/\s/g, '')}
            className="mb-3 w-20 text-blue-500"
          />
        ) : (
          <img src={dbImage} className="h-16" alt="db logo" />
        )}
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
          {testResult?.connectionTestResult ===
            ConnectionTestModelConnectionTestResultEnum.SUCCESS && (
            <div className="text-primary text-sm">Connection successful</div>
          )}
          {testResult?.connectionTestResult ===
            ConnectionTestModelConnectionTestResultEnum.CONNECTION_ALREADY_EXISTS && (
            <div className="text-red-700 text-sm">
              <span>Connection already exists</span>
            </div>
          )}
          {testResult?.connectionTestResult ===
            ConnectionTestModelConnectionTestResultEnum.FAILURE && (
            <div className="text-red-700 text-sm">
              <span>Connection failed</span>
              <span
                className="ml-2 underline cursor-pointer"
                onClick={openErrorModal}
              >
                Show more
              </span>
            </div>
          )}
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
