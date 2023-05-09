import React from 'react';

import { DATABASE_TYPE } from '../../../shared/enums';
import SvgIcon from '../../SvgIcon';
import { ConnectionBasicModelProviderTypeEnum } from '../../../api';
import YugabyteDbJpg from '../../SvgIcon/missingSvg/YugabyteDB.jpg';

interface ISelectDatabaseProps {
  onSelect: (
    db: ConnectionBasicModelProviderTypeEnum,
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
                ConnectionBasicModelProviderTypeEnum.postgresql,
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
                ConnectionBasicModelProviderTypeEnum.postgresql,
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
              onSelect(
                ConnectionBasicModelProviderTypeEnum.mysql,
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
                ConnectionBasicModelProviderTypeEnum.postgresql,
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
              onSelect(ConnectionBasicModelProviderTypeEnum.sqlserver)
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
                ConnectionBasicModelProviderTypeEnum.mysql,
                'Amazon RDS for SQL Server'
              )
            }
          >
            <SvgIcon name="azuremysql" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Azure Database for MySQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(
                ConnectionBasicModelProviderTypeEnum.postgresql,
                'Azure Database for MySQL'
              )
            }
          >
            <SvgIcon
              name="azurepostgresql"
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
                ConnectionBasicModelProviderTypeEnum.sqlserver,
                'Azure Database for PostgreSQL'
              )
            }
          >
            <SvgIcon name="azuresql" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Azure SQL Database
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionBasicModelProviderTypeEnum.sqlserver)
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
              onSelect(ConnectionBasicModelProviderTypeEnum.sqlserver)
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
              onSelect(ConnectionBasicModelProviderTypeEnum.bigquery)
            }
          >
            <SvgIcon name="big-query" className="mb-3 w-16" />
            <div className="text-xl font-semibold text-gray-700">Bigquery</div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() => onSelect(ConnectionBasicModelProviderTypeEnum.mysql)}
          >
            <SvgIcon name="cloudsql" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Cloud SQL for MySQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionBasicModelProviderTypeEnum.postgresql)
            }
          >
            <SvgIcon name="cloudsql" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Cloud SQL for PostgreSQL
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionBasicModelProviderTypeEnum.sqlserver)
            }
          >
            <SvgIcon name="cloudsql" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Cloud SQL for SQL Server
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionBasicModelProviderTypeEnum.postgresql)
            }
          >
            <SvgIcon name="cockroachbd" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              CockroachDB
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() => onSelect(ConnectionBasicModelProviderTypeEnum.mysql)}
          >
            <SvgIcon name="mariadb" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">MariaDB</div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionBasicModelProviderTypeEnum.sqlserver)
            }
          >
            <SvgIcon name="sqlserver" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Microsoft SQL Server
            </div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() => onSelect(ConnectionBasicModelProviderTypeEnum.mysql)}
          >
            <SvgIcon name="mysql" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">MySQL</div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() => onSelect(ConnectionBasicModelProviderTypeEnum.mysql)}
          >
            <SvgIcon name="perconaserver" className="mb-3 w-20 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">
              Percona Server for MySQL
            </div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionBasicModelProviderTypeEnum.postgresql)
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
              onSelect(ConnectionBasicModelProviderTypeEnum.redshift)
            }
          >
            <SvgIcon name="redshift" className="mb-3 w-16 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">Redshift</div>
          </div>
          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionBasicModelProviderTypeEnum.snowflake)
            }
          >
            <SvgIcon name="snowflake" className="mb-3 w-16 text-blue-500" />
            <div className="text-xl font-semibold text-gray-700">Snowflake</div>
          </div>

          <div
            className="min-w-100 max-w-100 bg-white rounded-lg shadow-lg p-4 flex-1 cursor-pointer border border-gray-100 flex flex-col items-center justify-center h-40"
            onClick={() =>
              onSelect(ConnectionBasicModelProviderTypeEnum.postgresql)
            }
          >
            <img
              src={YugabyteDbJpg}
              alt=""
              className="mb-3 w-40 h-40 text-blue-500"
            ></img>
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
