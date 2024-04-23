import React, { useState } from 'react';

import {
  BigQueryParametersSpecJobsCreateProjectEnum,
  ConnectionModel,
  ConnectionModelProviderTypeEnum,
  DuckdbParametersSpecFilesFormatTypeEnum,
  DuckdbParametersSpecStorageTypeEnum,
  MysqlParametersSpecMysqlEngineTypeEnum,
  SingleStoreDbParametersSpecLoadBalancingModeEnum,
  TrinoParametersSpecTrinoEngineTypeEnum,
  DuckdbParametersSpecAwsAuthenticationModeEnum,
  TrinoParametersSpecAwsAuthenticationModeEnum
} from '../../api';
import DatabaseConnection from '../../components/Dashboard/DatabaseConnection';
import SelectDatabase from '../../components/Dashboard/SelectDatabase';
import ImportSchemas from '../../components/ImportSchemas';
import MainLayout from '../../components/MainLayout';
import { BigQueryAuthenticationMode } from '../../shared/enums/bigquery.enum';

const CreateConnection = () => {
  const [step, setStep] = useState(0);
  const [database, setDatabase] = useState<ConnectionModel>({});
  const [nameofDB, setNameofDB] = useState<string>('');

  const onSelect = (
    db: ConnectionModelProviderTypeEnum,
    nameOfDatabase?: string
  ) => {
    addDefaultDatabaseProperties({ provider_type: db }, nameOfDatabase || '');
    setNameofDB(nameOfDatabase ? nameOfDatabase : '');
    setStep(1);
  };

  const addDefaultDatabaseProperties = (
    database: ConnectionModel,
    nameOfDatabase: string
  ) => {
    const copiedDatabase = { ...database };

    switch (database.provider_type) {
      case ConnectionModelProviderTypeEnum.bigquery: {
        copiedDatabase.bigquery = {
          authentication_mode:
            BigQueryAuthenticationMode.google_application_credentials,
          jobs_create_project:
            BigQueryParametersSpecJobsCreateProjectEnum.create_jobs_in_source_project
        };
        break;
      }
      case ConnectionModelProviderTypeEnum.postgresql: {
        copiedDatabase.postgresql = { port: '5432',
        properties: { '': '' } };
        break;
      }
      case ConnectionModelProviderTypeEnum.redshift: {
        copiedDatabase.redshift = { 
          port: '5439',
          redshift_authentication_mode: TrinoParametersSpecAwsAuthenticationModeEnum.default_credentials,
          properties: { '': '' } 
        };
        break;
      }
      case ConnectionModelProviderTypeEnum.sqlserver: {
        copiedDatabase.sqlserver = { port: '1433',
        properties: { '': '' } };
        break;
      }
      case ConnectionModelProviderTypeEnum.presto: {
        copiedDatabase.presto = { port: '8080',
        properties: { '': '' } };
        break;
      }
      case ConnectionModelProviderTypeEnum.trino: {
        copiedDatabase.trino = {
          port: '8080',
          trino_engine_type: nameOfDatabase?.toLowerCase() as TrinoParametersSpecTrinoEngineTypeEnum,
          aws_authentication_mode: TrinoParametersSpecAwsAuthenticationModeEnum.default_credentials,
          catalog:
            (nameOfDatabase?.toLowerCase() as TrinoParametersSpecTrinoEngineTypeEnum) ===
            TrinoParametersSpecTrinoEngineTypeEnum.athena
              ? 'awsdatacatalog'
              : '',
          athena_work_group: 'primary',
          properties: { '': '' }
        };
        break;
      }
      case ConnectionModelProviderTypeEnum.mysql: {
        if (
          nameOfDatabase?.toLowerCase() ===
          MysqlParametersSpecMysqlEngineTypeEnum.singlestoredb
        ) {
          copiedDatabase.mysql = {
            mysql_engine_type:
              MysqlParametersSpecMysqlEngineTypeEnum.singlestoredb,
            single_store_db_parameters_spec: {
              load_balancing_mode:
                SingleStoreDbParametersSpecLoadBalancingModeEnum.none,
              use_ssl: true
            },
            properties: { '': '' }
          };
        } else {
          copiedDatabase.mysql = {
            port: '3306',
            mysql_engine_type: MysqlParametersSpecMysqlEngineTypeEnum.mysql,
            properties: { '': '' }
          };
        }
        break;
      }
      case ConnectionModelProviderTypeEnum.oracle: {
        copiedDatabase.oracle = { port: '1521',
        properties: { '': '' } };
        break;
      }
      case ConnectionModelProviderTypeEnum.spark: {
        copiedDatabase.spark = { port: '10000',
        properties: { '': '' } };
        break;
      }
      case ConnectionModelProviderTypeEnum.databricks: {
        copiedDatabase.databricks = { port: '443',
        properties: { '': '' } };
        break;
      }
      case ConnectionModelProviderTypeEnum.duckdb: {
        let fileFormat: DuckdbParametersSpecFilesFormatTypeEnum =
          DuckdbParametersSpecFilesFormatTypeEnum.csv;
        switch (nameOfDatabase) {
          case 'CSV':
            fileFormat = DuckdbParametersSpecFilesFormatTypeEnum.csv;
            break;
          case 'JSON':
            fileFormat = DuckdbParametersSpecFilesFormatTypeEnum.json;
            break;
          case 'Parquet':
            fileFormat = DuckdbParametersSpecFilesFormatTypeEnum.parquet;
            break;
        }
        copiedDatabase.duckdb = {
          directories: { files: '' },
          files_format_type: fileFormat,
          storage_type: DuckdbParametersSpecStorageTypeEnum.local,
          aws_authentication_mode: DuckdbParametersSpecAwsAuthenticationModeEnum.default_credentials,
          properties: { '': '' }
        };
      }
    }
    setDatabase(copiedDatabase);
  };

  const onPrev = () => {
    if (step > 0) {
      setStep(step - 1);
    }
  };

  const onNext = async () => {
    if (!database?.connection_name) {
      return;
    }
    if (step === 1) {
      setStep(step + 1);
      return;
    }

    if (step < 2) {
      setStep(step + 1);
    }
  };

  return (
    <MainLayout>
      {step === 0 && <SelectDatabase onSelect={onSelect} />}
      {step === 1 && (
        <DatabaseConnection
          onNext={onNext}
          database={database}
          onChange={setDatabase}
          nameOfDatabase={nameofDB.length !== 0 ? nameofDB : ''}
          onBack={() => setStep(0)}
          onNameOfDatabaseChange={setNameofDB}
        />
      )}
      {step === 2 && (
        <ImportSchemas
          connectionName={database?.connection_name ?? ''}
          onPrev={onPrev}
          onNext={onNext}
        />
      )}
    </MainLayout>
  );
};

export default CreateConnection;
