import React from 'react';

import { DATABASE_TYPE } from '../../../shared/enums';
import SvgIcon from '../../SvgIcon';
import { ConnectionModelProviderTypeEnum } from '../../../api';

interface ISelectDatabaseProps {
  onSelect: (
    db: ConnectionModelProviderTypeEnum,
    nameOfdatabase?: string
  ) => void;
}

const SelectDatabase = ({ onSelect }: ISelectDatabaseProps) => {
  return (
    <div className="w-full">
      <div className="text-2xl font-semibold text-gray-900 mb-4">
        Select a database
      </div>
      <div className="flex justify-center content-center">
        <div className="flex flex-wrap gap-6 ">
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.postgresql,
                'AlloyDB'
              )
            }
          >
            <SvgIcon name="alloydb" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              AlloyDB for PostgreSQL
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.postgresql,
                'Amazon Aurora'
              )
            }
          >
            <SvgIcon name="amazonrds" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Amazon Aurora
            </div>
          </div>
          <div
              className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
              onClick={() =>
                  onSelect(ConnectionModelProviderTypeEnum.redshift)
              }
          >
            <SvgIcon name="redshift" className="mb-3 w-16 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">Amazon Redshift</div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.mysql,
                'Amazon RDS for mySQL'
              )
            }
          >
            <SvgIcon name="amazonrds" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Amazon RDS for mySQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.postgresql,
                'Amazon RDS for PostgreSQL'
              )
            }
          >
            <SvgIcon name="amazonrds" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Amazon RDS for PostgreSQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.sqlserver,
                'Amazon RDS for SQL Server'
              )
            }
          >
            <SvgIcon name="amazonrds" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Amazon RDS for SQL Server
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.mysql,
                'Azure Database for MySQL'
              )
            }
          >
            <SvgIcon
              name="azuredatabaseformysql"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Azure Database for MySQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.postgresql,
                'Azure Database for PostgreSQL'
              )
            }
          >
            <SvgIcon
              name="azuredatabaseforpostgresql"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Azure Database for PostgreSQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.sqlserver,
                'Azure SQL Database'
              )
            }
          >
            <SvgIcon
              name="azuresqldatabase"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Azure SQL Database
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.sqlserver,
                'Azure SQL Managed Instance'
              )
            }
          >
            <SvgIcon
              name="azuresqlmanagedinstance"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Azure SQL Managed Instance
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.sqlserver,
                'Azure Synapse Analytics'
              )
            }
          >
            <SvgIcon
              name="azuresynapseanalytics"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Azure Synapse Analytics
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionModelProviderTypeEnum.bigquery)
            }
          >
            <SvgIcon name="big-query" className="mb-3 w-16" />
            <div className="text-xl font-semibold text-gray-700">Bigquery</div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.mysql,
                'Cloud SQL for MySQL'
              )
            }
          >
            <SvgIcon
              name="cloudsqlformysql"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Cloud SQL for MySQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.postgresql,
                'Cloud SQL for PostgreSQL'
              )
            }
          >
            <SvgIcon
              name="cloudsqlforpostgresql"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Cloud SQL for PostgreSQL
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.sqlserver,
                'Cloud SQL for SQL Server'
              )
            }
          >
            <SvgIcon
              name="cloudsqlforsqlserver"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Cloud SQL for SQL Server
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.postgresql,
                'CockroachDB'
              )
            }
          >
            <SvgIcon name="cockroachdb" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              CockroachDB
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionModelProviderTypeEnum.mysql, 'MariaDB')
            }
          >
            <SvgIcon name="mariadb" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">MariaDB</div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionModelProviderTypeEnum.sqlserver)
            }
          >
            <SvgIcon name="sqlserver" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Microsoft SQL Server
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() => onSelect(ConnectionModelProviderTypeEnum.mysql)}
          >
            <SvgIcon name="mysql" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">MySQL</div>
          </div>

          <div
              className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
              onClick={() =>
                  onSelect(ConnectionModelProviderTypeEnum.oracle)
              }
          >
            <SvgIcon name="oracle" className="mb-3 w-16 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">Oracle Database</div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.mysql,
                'Percona Server for MySQL'
              )
            }
          >
            <SvgIcon
              name="perconaserverformysql"
              className="mb-3 w-20 text-blue-500"
            />
            <div className="text-xl font-semibold text-gray-700">
              Percona Server for MySQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionModelProviderTypeEnum.postgresql)
            }
          >
            <SvgIcon name="postgresql" className="mb-3 w-16 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              PostgreSQL
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionModelProviderTypeEnum.snowflake)
            }
          >
            <SvgIcon name="snowflake" className="mb-3 w-16 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">Snowflake</div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionModelProviderTypeEnum.postgresql,
                'YugabyteDB'
              )
            }
          >
            <SvgIcon name="yugabytedb" className="mb-3 w-16 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              YugabyteDB
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SelectDatabase;
