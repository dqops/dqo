import {
  CheckTemplate,
  ConnectionModelProviderTypeEnum,
  DqoJobChangeModelStatusEnum,
  DqoJobEntryParametersModel,
  DqoJobHistoryEntryModel,
  TableComparisonGroupingColumnPairModel,
  TimeWindowFilterParameters
} from '../api';

export type PageTab = {
  label: string;
  value: string;
};

interface IDatabaseOption {
  type: ConnectionModelProviderTypeEnum;
  name: string;
  iconName: string;
  displayName: string;
}

export type TParameters = {
  name?: string;
  refConnection?: string;
  refSchema?: string;
  refTable?: string;
  dataGroupingArray?: TableComparisonGroupingColumnPairModel[];
};

export interface IFilterTemplate {
  tableNamePattern?: string | undefined;
  columnNamePattern?: string | undefined;
  columnDataType?: string | undefined;
  checkTarget?: 'table' | 'column' | undefined;
  checkCategory?: string | undefined;
  checkName?: string | undefined;
  activeOffCheck?: boolean;
  selectedCheck?: CheckTemplate
}
export interface IJob {
  errorMessage?: string | undefined;
  jobId: {
    parentJobId: any;
    jobId: number | undefined;
    createdAt: number | undefined;
  };
  jobType: string;
  parameters: DqoJobEntryParametersModel | undefined;
  status: DqoJobChangeModelStatusEnum | undefined;
  statusChangedAt?: number | undefined;
  childs: DqoJobHistoryEntryModel[];
}

enum CheckTypes {
  MONITORING = 'monitoring',
  SOURCES = 'sources',
  PROFILING = 'profiling',
  PARTITIONED = 'partitioned'
}

export const CONNECTION_LEVEL_TABS: {
  [key in CheckTypes]: PageTab[];
} = {
  [CheckTypes.SOURCES]: [
    {
      label: 'Connection',
      value: 'detail'
    },
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Comments',
      value: 'comments'
    },
    {
      label: 'Labels',
      value: 'labels'
    },
    {
      label: 'Schemas',
      value: 'schemas'
    },
    {
      label: 'Default grouping template',
      value: 'data-streams'
    }
  ],
  [CheckTypes.PROFILING]: [
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Schemas',
      value: 'schemas'
    }
  ],
  [CheckTypes.PARTITIONED]: [
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Schemas',
      value: 'schemas'
    }
  ],
  [CheckTypes.MONITORING]: [
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Schemas',
      value: 'schemas'
    }
  ]
};

export const TABLE_LEVEL_TABS: {
  [key in CheckTypes]: PageTab[];
} = {
  [CheckTypes.SOURCES]: [
    {
      label: 'Table',
      value: 'detail'
    },
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Comments',
      value: 'comments'
    },
    {
      label: 'Labels',
      value: 'labels'
    },
    {
      label: 'Data Groupings',
      value: 'data-groupings'
    },
    {
      label: 'Date and time columns',
      value: 'timestamps'
    },
    {
      label: 'Incident Configuration',
      value: 'incident_configuration'
    }
  ],
  [CheckTypes.PROFILING]: [
    {
      label: 'Basic data statistics',
      value: 'statistics'
    },
    {
      label: 'Table preview',
      value: 'preview'
    },
    {
      label: 'Profiling checks',
      value: 'advanced'
    },
    {
      label: 'Table quality status',
      value: 'table-quality-status'
    },
    {
      label: 'Table comparisons',
      value: 'table-comparisons'
    }
  ],
  [CheckTypes.PARTITIONED]: [
    {
      label: 'Daily checks',
      value: 'daily'
    },
    {
      label: 'Monthly checks',
      value: 'monthly'
    },
    {
      label: 'Table quality status daily',
      value: 'table-quality-status-daily'
    },
    {
      label: 'Table quality status monthly',
      value: 'table-quality-status-monthly'
    },
    {
      label: 'Daily comparisons',
      value: 'daily_comparisons'
    },
    {
      label: 'Monthly comparisons',
      value: 'monthly_comparisons'
    }
  ],
  [CheckTypes.MONITORING]: [
    {
      label: 'Daily checks',
      value: 'daily'
    },
    {
      label: 'Monthly checks',
      value: 'monthly'
    },
    {
      label: 'Table quality status daily',
      value: 'table-quality-status-daily'
    },
    {
      label: 'Table quality status monthly',
      value: 'table-quality-status-monthly'
    },
    {
      label: 'Daily comparisons',
      value: 'daily_comparisons'
    },
    {
      label: 'Monthly comparisons',
      value: 'monthly_comparisons'
    }
  ]
};

export const COLUMN_LEVEL_TABS: {
  [key in CheckTypes]: PageTab[];
} = {
  [CheckTypes.SOURCES]: [
    {
      label: 'Column',
      value: 'detail'
    },
    {
      label: 'Comments',
      value: 'comments'
    },
    {
      label: 'Labels',
      value: 'labels'
    }
  ],
  [CheckTypes.PROFILING]: [
    {
      label: 'statistics',
      value: 'statistics'
    },
    {
      label: 'advanced',
      value: 'advanced'
    }
  ],
  [CheckTypes.PARTITIONED]: [
    {
      label: 'Daily checks',
      value: 'daily'
    },
    {
      label: 'Monthly checks',
      value: 'monthly'
    }
  ],
  [CheckTypes.MONITORING]: [
    {
      label: 'Daily checks',
      value: 'daily'
    },
    {
      label: 'Monthly checks',
      value: 'monthly'
    }
  ]
};

export const databaseOptions: IDatabaseOption[] = [
  {
    type: ConnectionModelProviderTypeEnum.postgresql,
    name: 'AlloyDB',
    iconName: 'alloydb',
    displayName: 'AlloyDB for PostgreSQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.trino,
    name: 'Athena',
    iconName: 'athena',
    displayName: 'Amazon Athena'
  },
  {
    type: ConnectionModelProviderTypeEnum.postgresql,
    name: 'Amazon Aurora',
    iconName: 'amazonrds',
    displayName: 'Amazon Aurora'
  },
  {
    type: ConnectionModelProviderTypeEnum.mysql,
    name: 'Amazon RDS for mySQL',
    iconName: 'amazonrds',
    displayName: 'Amazon RDS for mySQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.postgresql,
    name: 'Amazon RDS for PostgreSQL',
    iconName: 'amazonrds',
    displayName: 'Amazon RDS for PostgreSQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.sqlserver,
    name: 'Amazon RDS for SQL Server',
    iconName: 'amazonrds',
    displayName: 'Amazon RDS for SQL Server'
  },
  {
    type: ConnectionModelProviderTypeEnum.mysql,
    name: 'Azure Database for MySQL',
    iconName: 'azuredatabaseformysql',
    displayName: 'Azure Database for MySQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.postgresql,
    name: 'Azure Database for PostgreSQL',
    iconName: 'azuredatabaseforpostgresql',
    displayName: 'Azure Database for PostgreSQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.sqlserver,
    name: 'Azure SQL Database',
    iconName: 'azuresqldatabase',
    displayName: 'Azure SQL Database'
  },
  {
    type: ConnectionModelProviderTypeEnum.sqlserver,
    name: 'Azure SQL Managed Instance',
    iconName: 'azuresqlmanagedinstance',
    displayName: 'Azure SQL Managed Instance'
  },
  {
    type: ConnectionModelProviderTypeEnum.sqlserver,
    name: 'Azure Synapse Analytics',
    iconName: 'azuresynapseanalytics',
    displayName: 'Azure Synapse Analytics'
  },
  {
    type: ConnectionModelProviderTypeEnum.bigquery,
    name: 'BigQuery',
    iconName: 'bigquery',
    displayName: 'Bigquery'
  },
  {
    type: ConnectionModelProviderTypeEnum.mysql,
    name: 'Cloud SQL for MySQL',
    iconName: 'cloudsqlformysql',
    displayName: 'Cloud SQL for MySQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.postgresql,
    name: 'Cloud SQL for PostgreSQL',
    iconName: 'cloudsqlforpostgresql',
    displayName: 'Cloud SQL for PostgreSQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.sqlserver,
    name: 'Cloud SQL for SQL Server',
    iconName: 'cloudsqlforsqlserver',
    displayName: 'Cloud SQL for SQL Server'
  },
  {
    type: ConnectionModelProviderTypeEnum.postgresql,
    name: 'CockroachDB',
    iconName: 'cockroachdb',
    displayName: 'CockroachDB'
  },
  {
    type: ConnectionModelProviderTypeEnum.databricks,
    name: 'Databricks',
    iconName: 'databricks',
    displayName: 'Databricks'
  },
  {
    type: ConnectionModelProviderTypeEnum.mysql,
    name: 'MariaDB',
    iconName: 'mariadb',
    displayName: 'MariaDB'
  },
  {
    type: ConnectionModelProviderTypeEnum.sqlserver,
    name: 'SQL Server',
    iconName: 'sqlserver',
    displayName: 'Microsoft SQL Server'
  },
  {
    type: ConnectionModelProviderTypeEnum.mysql,
    name: 'MySQL',
    iconName: 'mysql',
    displayName: 'MySQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.oracle,
    name: 'Oracle',
    iconName: 'oracle',
    displayName: 'Oracle Database'
  },
  {
    type: ConnectionModelProviderTypeEnum.mysql,
    name: 'Percona Server for MySQL',
    iconName: 'perconaserverformysql',
    displayName: 'Percona Server for MySQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.postgresql,
    name: 'Postgresql',
    iconName: 'postgresql',
    displayName: 'PostgreSQL'
  },
  {
    type: ConnectionModelProviderTypeEnum.presto,
    name: 'Presto',
    iconName: 'presto',
    displayName: 'Presto'
  },
  {
    type: ConnectionModelProviderTypeEnum.redshift,
    name: 'Redshift',
    iconName: 'redshift',
    displayName: 'Amazon Redshift'
  },
  {
    type: ConnectionModelProviderTypeEnum.spark,
    name: 'Spark',
    iconName: 'spark',
    displayName: 'Spark'
  },
  {
    type: ConnectionModelProviderTypeEnum.snowflake,
    name: 'Snowflake',
    iconName: 'snowflake',
    displayName: 'Snowflake'
  },
  {
    type: ConnectionModelProviderTypeEnum.trino,
    name: 'Trino',
    iconName: 'trino',
    displayName: 'Trino'
  },
  {
    type: ConnectionModelProviderTypeEnum.postgresql,
    name: 'YugabyteDB',
    iconName: 'yugabytedb',
    displayName: 'YugabyteDB'
  }
];

export const RUN_CHECK_TIME_WINDOW_FILTERS: {
  [key in string]: TimeWindowFilterParameters | null;
} = {
  'Default incremental time window': null,
  'Today only': {
    daily_partitioning_include_today: true,
    daily_partitioning_recent_days: 0
  },
  'Yesterday only': {
    daily_partitioning_include_today: false,
    daily_partitioning_recent_days: 1
  },
  'Last 3 days, excluding today': {
    daily_partitioning_include_today: false,
    daily_partitioning_recent_days: 3
  },
  'Last 7 days, excluding today': {
    daily_partitioning_include_today: false,
    daily_partitioning_recent_days: 7
  },
  'Last 30 days, excluding today': {
    daily_partitioning_include_today: false,
    daily_partitioning_recent_days: 30
  },
  'Current month only': {
    monthly_partitioning_include_current_month: true,
    monthly_partitioning_recent_months: 0
  },
  'Last month only': {
    monthly_partitioning_include_current_month: false,
    monthly_partitioning_recent_months: 1
  },
  'Last 3 months, excluding current month': {
    monthly_partitioning_include_current_month: false,
    monthly_partitioning_recent_months: 3
  },
  'Last 12 months, excluding current month': {
    monthly_partitioning_include_current_month: false,
    monthly_partitioning_recent_months: 12
  }
};

export const formatNumber = (t: number) => {
  const k = Math.abs(t);

  if (k > 1000 && k < 1000000) {
    if (k > Math.pow(10, 3) && k < Math.pow(10, 4)) {
      return (k / Math.pow(10, 3)).toFixed(3) + 'k';
    } else if (k > Math.pow(10, 4) && k < Math.pow(10, 5)) {
      return (k / Math.pow(10, 3)).toFixed(2) + 'k';
    } else {
      return (k / Math.pow(10, 3)).toFixed(1) + 'k';
    }
  } else if (k > Math.pow(10, 6) && k < Math.pow(10, 9)) {
    if (k > Math.pow(10, 6) && k < Math.pow(10, 7)) {
      return (k / Math.pow(10, 6)).toFixed(3) + 'M';
    } else if (k > Math.pow(10, 7) && k < Math.pow(10, 8)) {
      return (k / Math.pow(10, 6)).toFixed(2) + 'M';
    } else {
      return (k / Math.pow(10, 6)).toFixed(1) + 'M';
    }
  } else if (k > Math.pow(10, 9) && k < Math.pow(10, 12)) {
    if (k > Math.pow(10, 9) && k < Math.pow(10, 10)) {
      return (k / Math.pow(10, 9)).toFixed(3) + 'G';
    } else if (k > Math.pow(10, 10) && k < Math.pow(10, 11)) {
      return (k / Math.pow(10, 9)).toFixed(2) + 'G';
    } else {
      return (k / Math.pow(10, 9)).toFixed(1) + 'G';
    }
  } else if (k > Math.pow(10, 12) && k < Math.pow(10, 15)) {
    if (k > Math.pow(10, 12) && k < Math.pow(10, 13)) {
      return (k / Math.pow(10, 12)).toFixed(3) + 'T';
    } else if (k > Math.pow(10, 13) && k < Math.pow(10, 14)) {
      return (k / Math.pow(10, 12)).toFixed(2) + 'T';
    } else {
      return (k / Math.pow(10, 12)).toFixed(1) + 'T';
    }
  } else {
    return k;
  }
};

export const dateToString = (k: string) => {
  if (k === '') {
    return false;
  }

  if (isNaN(Date.parse(k))) {
    return false;
  }
  const a = k.replace(/T/g, ' ');
  return a;
};
