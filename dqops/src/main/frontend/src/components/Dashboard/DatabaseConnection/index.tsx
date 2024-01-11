import React, { useEffect, useMemo, useState } from 'react';
import Button from '../../Button';
import Input from '../../Input';
import {
  ConnectionModel,
  ConnectionModelProviderTypeEnum,
  ConnectionTestModel,
  ConnectionTestModelConnectionTestResultEnum,
  SharedCredentialListModel
} from '../../../api';
import {
  ConnectionApiClient,
  DataSourcesApi,
  SharedCredentialsApi
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
import PrestoConnection from './PrestoConnection';
import PrestoLogo from '../../SvgIcon/svg/presto.svg';
import TrinoConnection from './TrinoConnection';
import TrinoLogo from '../../SvgIcon/svg/trino.svg';
import RedshiftConnection from './RedshiftConnection';
import RedshiftLogo from '../../SvgIcon/svg/redshift.svg';
import SqlServerConnection from './SqlServerConnection';
import SqlServerLogo from '../../SvgIcon/svg/mssql-server.svg';
import MySQLConnection from './MySQLConnection';
import MySQLLogo from '../../SvgIcon/svg/mysql.svg';
import OracleConnection from './OracleConnection';
import OracleLogo from '../../SvgIcon/svg/oracle.svg';
import SvgIcon from '../../SvgIcon';
import SparkConnection from './SparkConnection';
import SparkLogo from '../../SvgIcon/svg/spark.svg';
import DatabricksConnection from './DatabricksConnection';
import DatabricksLogo from '../../SvgIcon/svg/databricks.svg';
import clsx from 'clsx';

interface IDatabaseConnectionProps {
  onNext: () => void;
  database: ConnectionModel;
  onChange: (db: ConnectionModel) => void;
  nameOfDatabase?: string;
  onBack: () => void;
}

const DatabaseConnection = ({
  database,
  onChange,
  nameOfDatabase,
  onBack
}: IDatabaseConnectionProps) => {
  const { addConnection } = useTree();
  const [isTesting, setIsTesting] = useState(false);
  const [testResult, setTestResult] = useState<ConnectionTestModel>();
  const [showError, setShowError] = useState(false);
  const [isSaving, setIsSaving] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [message, setMessage] = useState<string>();
  const [nameError, setNameError] = useState('');
  const [sharedCredentials, setSharedCredentials] = useState<
    SharedCredentialListModel[]
  >([]);

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
    let testRes: ConnectionTestModel | null = null;
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
      } else if (
        testRes?.connectionTestResult ===
        ConnectionTestModelConnectionTestResultEnum.CONNECTION_ALREADY_EXISTS
      ) {
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

  const getTitle = (provider?: ConnectionModelProviderTypeEnum) => {
    switch (provider) {
      case ConnectionModelProviderTypeEnum.bigquery:
        return 'Google BigQuery Connection Settings';
      case ConnectionModelProviderTypeEnum.snowflake:
        return 'Snowflake Connection Settings';
      case ConnectionModelProviderTypeEnum.postgresql:
        return 'PostgreSQL Connection Settings';
      case ConnectionModelProviderTypeEnum.redshift:
        return 'Amazon Redshift Connection Settings';
      case ConnectionModelProviderTypeEnum.sqlserver:
        return 'Microsoft SQL Server Connection Settings';
      case ConnectionModelProviderTypeEnum.presto:
        return 'PrestoDB Connection Settings';
      case ConnectionModelProviderTypeEnum.trino:
        return 'Trino Connection Settings';
      case ConnectionModelProviderTypeEnum.mysql:
        return 'MySQL Connection Settings';
      case ConnectionModelProviderTypeEnum.oracle:
        return 'Oracle Database Connection Settings';
      case ConnectionModelProviderTypeEnum.spark:
        return 'Spark Connection Settings';
      case ConnectionModelProviderTypeEnum.databricks:
        return 'Databricks Connection Settings';
      default:
        return 'Database Connection Settings';
    }
  };

  const getSharedCredentials = async () => {
    await SharedCredentialsApi.getAllSharedCredentials().then((res) =>
      setSharedCredentials(res.data)
    );
  };

  useEffect(() => {
    getSharedCredentials();
  }, []);

  const components = {
    [ConnectionModelProviderTypeEnum.bigquery]: (
      <BigqueryConnection
        bigquery={database.bigquery}
        onChange={(bigquery) => onChange({ ...database, bigquery })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.snowflake]: (
      <SnowflakeConnection
        snowflake={database.snowflake}
        onChange={(snowflake) => onChange({ ...database, snowflake })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.postgresql]: (
      <PostgreSQLConnection
        postgresql={database.postgresql}
        onChange={(postgresql) => onChange({ ...database, postgresql })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.redshift]: (
      <RedshiftConnection
        redshift={database.redshift}
        onChange={(redshift) => onChange({ ...database, redshift })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.sqlserver]: (
      <SqlServerConnection
        sqlserver={database.sqlserver}
        onChange={(sqlserver) => onChange({ ...database, sqlserver })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.presto]: (
      <PrestoConnection
        presto={database.presto}
        onChange={(presto) => onChange({ ...database, presto })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.trino]: (
      <TrinoConnection
        trino={database.trino}
        onChange={(trino) => onChange({ ...database, trino })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.mysql]: (
      <MySQLConnection
        mysql={database.mysql}
        onChange={(mysql) => onChange({ ...database, mysql })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.oracle]: (
      <OracleConnection
        oracle={database.oracle}
        onChange={(oracle) => onChange({ ...database, oracle })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.spark]: (
      <SparkConnection
        spark={database.spark}
        onChange={(spark) => onChange({ ...database, spark })}
        sharedCredentials={sharedCredentials}
      />
    ),
    [ConnectionModelProviderTypeEnum.databricks]: (
      <DatabricksConnection
        databricks={database.databricks}
        onChange={(databricks) => onChange({ ...database, databricks })}
        sharedCredentials={sharedCredentials}
      />
    )
  };

  const dbImage = useMemo(() => {
    switch (database.provider_type) {
      case ConnectionModelProviderTypeEnum.bigquery:
        return BigqueryLogo;
      case ConnectionModelProviderTypeEnum.snowflake:
        return SnowflakeLogo;
      case ConnectionModelProviderTypeEnum.postgresql:
        return PostgreSQLLogo;
      case ConnectionModelProviderTypeEnum.redshift:
        return RedshiftLogo;
      case ConnectionModelProviderTypeEnum.sqlserver:
        return SqlServerLogo;
      case ConnectionModelProviderTypeEnum.presto:
        return PrestoLogo;
      case ConnectionModelProviderTypeEnum.trino:
        return TrinoLogo;
      case ConnectionModelProviderTypeEnum.mysql:
        return MySQLLogo;
      case ConnectionModelProviderTypeEnum.oracle:
        return OracleLogo;
      case ConnectionModelProviderTypeEnum.spark:
        return SparkLogo;
      case ConnectionModelProviderTypeEnum.databricks:
        return DatabricksLogo;
      default:
        return '';
    }
  }, [database.provider_type]);
  console.log(database)

  return (
    <div>
      <div
        className="mb-4 flex items-center text-teal-500 cursor-pointer"
        onClick={onBack}
      >
        {' '}
        <SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />
        Back
      </div>
      <div className="flex justify-between mb-4">
        <div>
          <div className="text-2xl font-semibold mb-3">Connect a database</div>
          <div>
            {nameOfDatabase
              ? nameOfDatabase + ' Connection Settings'
              : getTitle(database.provider_type)}
          </div>
        </div>
        {nameOfDatabase ? (
          <SvgIcon
            name={nameOfDatabase.toLowerCase().replace(/\s/g, '')}
            className={clsx(
              'mb-3 w-20 text-blue-500',
              nameOfDatabase === 'Spark' && 'w-35'
            )}
          />
        ) : (
          <img src={dbImage} className="h-16" alt="db logo" />
        )}
      </div>

      <div className="bg-white rounded-lg px-4 py-6 border border-gray-100">
        <Input
          label="Connection Name"
          className="mb-4"
          value={database.connection_name}
          onChange={(e) =>
            onChange({ ...database, connection_name: e.target.value })
          }
          error={!!nameError}
          helperText={nameError}
        />
        <Input
          label="Parallel jobs limit"
          value={database.parallel_jobs_limit}
          onChange={(e) => {
            if (!isNaN(Number(e.target.value))) {
              onChange({
                ...database,
                parallel_jobs_limit:
                  String(e.target.value).length === 0
                    ? undefined
                    : Number(e.target.value)
              });
            }
          }}
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
