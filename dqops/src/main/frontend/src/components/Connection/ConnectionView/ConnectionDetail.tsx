import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  ConnectionModel,
  ConnectionSpecProviderTypeEnum,
  ConnectionTestModel,
  ConnectionTestModelConnectionTestResultEnum,
  SharedCredentialListModel
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionBasic,
  setConnectionBasic,
  setIsUpdatedConnectionBasic,
  updateConnectionBasic
} from '../../../redux/actions/connection.actions';
import { IRootState } from '../../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import {
  DataSourcesApi,
  SharedCredentialsApi
} from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import Button from '../../Button';
import BigqueryConnection from '../../Dashboard/DatabaseConnection/BigqueryConnection';
import ConfirmErrorModal from '../../Dashboard/DatabaseConnection/ConfirmErrorModal';
import DatabricksConnection from '../../Dashboard/DatabaseConnection/DatabricksConnection';
import Db2Connection from '../../Dashboard/DatabaseConnection/Db2Connection';
import DuckdbConnection from '../../Dashboard/DatabaseConnection/DuckDBConnection';
import ErrorModal from '../../Dashboard/DatabaseConnection/ErrorModal';
import HanaConnection from '../../Dashboard/DatabaseConnection/HanaConnection';
import JdbcPropertiesView from '../../Dashboard/DatabaseConnection/JdbcProperties';
import MySQLConnection from '../../Dashboard/DatabaseConnection/MySQLConnection';
import OracleConnection from '../../Dashboard/DatabaseConnection/OracleConnection';
import PostgreSQLConnection from '../../Dashboard/DatabaseConnection/PostgreSQLConnection';
import PrestoConnection from '../../Dashboard/DatabaseConnection/PrestoConnection';
import RedshiftConnection from '../../Dashboard/DatabaseConnection/RedshiftConnection';
import SnowflakeConnection from '../../Dashboard/DatabaseConnection/SnowflakeConnection';
import SparkConnection from '../../Dashboard/DatabaseConnection/SparkConnection';
import SqlServerConnection from '../../Dashboard/DatabaseConnection/SqlServerConnection';
import TrinoConnection from '../../Dashboard/DatabaseConnection/TrinoConnection';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import Input from '../../Input';
import Loader from '../../Loader';
import SvgIcon from '../../SvgIcon';
import ConnectionActionGroup from './ConnectionActionGroup';

const ConnectionDetail = () => {
  const {
    connection,
    checkTypes
  }: { connection: string; checkTypes: CheckTypes } = useDecodedParams();

  const activeTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const {
    connectionBasic,
    isUpdatedConnectionBasic,
    isUpdating
  }: {
    connectionBasic?: ConnectionModel;
    isUpdatedConnectionBasic?: boolean;
    isUpdating?: boolean;
  } = useSelector(getFirstLevelState(checkTypes));
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const dispatch = useActionDispatch();
  const [isTesting, setIsTesting] = useState(false);
  const [testResult, setTestResult] = useState<ConnectionTestModel>();
  const [showError, setShowError] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [message, setMessage] = useState<string>();
  const [showAdvancedProperties, setShowAdvancedProperties] = useState(false);
  const [sharedCredentials, setSharedCredentials] = useState<
    SharedCredentialListModel[]
  >([]);

  useEffect(() => {
    dispatch(getConnectionBasic(checkTypes, activeTab, connection));
  }, [checkTypes, connection]);

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

    if (
      testRes.data?.connectionTestResult ===
      ConnectionTestModelConnectionTestResultEnum.SUCCESS
    ) {
      await onConfirmSave();
    } else if (
      testRes.data?.connectionTestResult ===
      ConnectionTestModelConnectionTestResultEnum.FAILURE
    ) {
      setShowConfirm(true);
      setMessage(testRes.data?.errorMessage);
    } else {
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

  const getSharedCredentials = async () => {
    await SharedCredentialsApi.getAllSharedCredentials().then((res) =>
      setSharedCredentials(res.data)
    );
  };

  useEffect(() => {
    getSharedCredentials();
  }, []);

  return (
    <div
      className={clsx(
        'p-4 text-sm',
        userProfile.can_manage_scheduler !== true
          ? 'pointer-events-none cursor-not-allowed'
          : ''
      )}
    >
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdating={isUpdating}
        isUpdated={isUpdatedConnectionBasic}
      />
      <div className="mb-1">
        <div className="flex px-4 py-2 pb-4">
          <div>Connection name</div>
          <div className="ml-4">{connectionBasic?.connection_name}</div>
        </div>
      </div>
      <div className="px-4 !mt-6">
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.bigquery && (
          <BigqueryConnection
            bigquery={connectionBasic?.bigquery}
            onChange={(bigquery) => onChange({ bigquery })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.snowflake && (
          <SnowflakeConnection
            snowflake={connectionBasic?.snowflake}
            onChange={(snowflake) => onChange({ snowflake })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.redshift && (
          <RedshiftConnection
            redshift={connectionBasic?.redshift}
            onChange={(redshift) => onChange({ redshift })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.sqlserver && (
          <SqlServerConnection
            sqlserver={connectionBasic?.sqlserver}
            onChange={(sqlserver) => onChange({ sqlserver })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.postgresql && (
          <PostgreSQLConnection
            postgresql={connectionBasic?.postgresql}
            onChange={(postgresql) => onChange({ postgresql })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.mysql && (
          <MySQLConnection
            mysql={connectionBasic?.mysql}
            onChange={(mysql) => onChange({ mysql })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.oracle && (
          <OracleConnection
            oracle={connectionBasic?.oracle}
            onChange={(oracle) => onChange({ oracle })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.databricks && (
          <DatabricksConnection
            databricks={connectionBasic?.databricks}
            onChange={(databricks) => onChange({ databricks })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.presto && (
          <PrestoConnection
            presto={connectionBasic?.presto}
            onChange={(presto) => onChange({ presto })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.spark && (
          <SparkConnection
            spark={connectionBasic?.spark}
            onChange={(spark) => onChange({ spark })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.trino && (
          <TrinoConnection
            trino={connectionBasic?.trino}
            onChange={(trino) => onChange({ trino })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.duckdb && (
          <DuckdbConnection
            duckdb={connectionBasic?.duckdb}
            onChange={(duckdb) => onChange({ duckdb })}
            sharedCredentials={sharedCredentials}
            freezeFileType
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.hana && (
          <HanaConnection
            hana={connectionBasic?.hana}
            onChange={(hana) => onChange({ hana })}
            sharedCredentials={sharedCredentials}
          />
        )}
        {connectionBasic?.provider_type ===
          ConnectionSpecProviderTypeEnum.db2 && (
          <Db2Connection
            db2={connectionBasic?.db2}
            onChange={(db2) => onChange({ db2 })}
            sharedCredentials={sharedCredentials}
          />
        )}
      </div>
      {showAdvancedProperties ? (
        <SectionWrapper
          title="Advanced properties"
          svgIcon
          onClick={() => setShowAdvancedProperties(false)}
          className="ml-4 !pb-1 !pt-1 !mt-4 !mb-4"
        >
          <div className="flex px-4 py-2 mt-4 items-center">
            <div className="!w-60">Parallel jobs limit</div>
            <div>
              <Input
                value={connectionBasic?.parallel_jobs_limit}
                onChange={(e) => {
                  if (!isNaN(Number(e.target.value))) {
                    onChange({
                      ...connectionBasic,
                      parallel_jobs_limit:
                        String(e.target.value).length === 0
                          ? undefined
                          : Number(e.target.value)
                    });
                  }
                }}
              />
            </div>
          </div>

          <div className="flex px-4 py-2 items-center">
            <div className="!w-60">Schedule only on DQOps instance</div>
            <div>
              <Input
                value={connectionBasic?.schedule_on_instance}
                placeholder="Enter the name of a DQOps named instance which will run scheduled data quality checks"
                onChange={(e) => {
                  onChange({
                    ...connectionBasic,
                    schedule_on_instance: String(e.target.value)
                  });
                }}
                className="!min-w-145"
              />
            </div>
          </div>
          <div className="ml-4">
            <JdbcPropertiesView
              properties={connectionBasic?.advanced_properties}
              onChange={(properties) =>
                onChange({ advanced_properties: properties })
              }
              title="Advanced property name"
              sharedCredentials={sharedCredentials}
            />
          </div>
        </SectionWrapper>
      ) : (
        <div
          className="flex items-center ml-4 mb-4 text-sm font-bold cursor-pointer"
          onClick={() => setShowAdvancedProperties(true)}
        >
          <SvgIcon name="chevron-right" className="w-5 h-5" />
          Advanced properties
        </div>
      )}

      <div className="flex space-x-4 justify-end items-center mt-6 px-4 mb-5">
        {isTesting && (
          <Loader isFull={false} className="w-8 h-8 !text-primary" />
        )}
        {testResult?.connectionTestResult ===
          ConnectionTestModelConnectionTestResultEnum.SUCCESS && (
          <div className="text-primary text-sm">Connection successful</div>
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
        {testResult?.connectionTestResult ===
          ConnectionTestModelConnectionTestResultEnum.CONNECTION_ALREADY_EXISTS && (
          <div className="text-red-700 text-sm">
            <span>Connection already exists</span>
          </div>
        )}
        <Button
          color="primary"
          variant="outlined"
          label="Test connection"
          onClick={onTestConnection}
          disabled={isTesting || userProfile.can_manage_data_sources !== true}
        />
        <Button
          color={isUpdatedConnectionBasic ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          disabled={isTesting || isUpdating}
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
