import moment from 'moment/moment';
import { useParams } from 'react-router-dom';
import {
  CheckContainerModel,
  CheckModel,
  CheckResultsOverviewDataModel,
  CheckSearchFiltersCheckTypeEnum,
  ConnectionModel,
  ConnectionModelProviderTypeEnum,
  TableListModel
} from '../api';
import {
  CheckResultApi,
  ErrorSamplesApiClient,
  ErrorsApi,
  SensorReadoutsApi
} from '../services/apiClient';
import { CheckTypes } from '../shared/routes';

export const getDaysString = (value: string | number) => {
  const daysDiff = moment().diff(moment(value), 'day');
  if (daysDiff === 0) return 'Today';
  if (daysDiff === 1) return '1 day ago';

  return `${daysDiff} days ago`;
};

export const wait = (time: number) =>
  new Promise((resolve) => setTimeout(resolve, time));

export const getLocalDateInUserTimeZone = (date: Date): string => {
  const userTimeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false,
    timeZone: userTimeZone
  };

  let strDate = String(date);

  if (strDate.includes(' 24:')) {
    strDate = strDate.replace(' 24:', ' 00:');
  }

  return new Date(strDate).toLocaleString('en-US', options);
};

export const urlencodeEncoder = (url: string | undefined) => {
  if (!url) return '';

  let decodedValue = '';
  for (let i = 0; i < url.length; i++) {
    if (url[i] === '%' && i + 2 < url.length) {
      const encodedChar = url.slice(i, i + 3);
      switch (encodedChar) {
        case '%20':
          decodedValue += ' ';
          break;
        case '%2E':
          decodedValue += '.';
          break;
        case '%2F':
          decodedValue += '/';
          break;
        case '%5C':
          decodedValue += '\\';
          break;
        // case '%25':
        //   decodedValue += '%';
        //   break;
        default:
          decodedValue += encodedChar;
          break;
      }
      i += 2;
    } else {
      decodedValue += url[i];
    }
  }

  return decodedValue;
};

export const urlencodeDecoder = (url: string | undefined) => {
  if (!url) return '';

  let encodedValue = '';
  for (let i = 0; i < url.length; i++) {
    const char = url[i];
    switch (char) {
      // case '%': encodedValue += '%25'; break;
      case ' ':
        encodedValue += '%20';
        break;
      case '.':
        encodedValue += '%2E';
        break;
      case '/':
        encodedValue += '%2F';
        break;
      case '\\':
        encodedValue += '%5C';
        break;
      default:
        encodedValue += char;
    }
  }
  return encodedValue;
};

export const getDetectedDatatype = (numberForFile: any) => {
  if (Number(numberForFile) === 1) {
    return 'INTEGER';
  }
  if (Number(numberForFile) === 2) {
    return 'FLOAT';
  }
  if (Number(numberForFile) === 3) {
    return 'DATE';
  }
  if (Number(numberForFile) === 4) {
    return 'DATETIME';
  }
  if (Number(numberForFile) === 5) {
    return 'TIMESTAMP';
  }
  if (Number(numberForFile) === 6) {
    return 'BOOLEAN';
  }
  if (Number(numberForFile) === 7) {
    return 'STRING';
  }
  if (Number(numberForFile) === 8) {
    return 'Mixed data type';
  }
};

export const sortPatterns = <T>(
  patterns: T[],
  key: keyof T,
  order: 'asc' | 'desc'
) => {
  const copiedPatterns = [...patterns];

  copiedPatterns.sort((a, b) => {
    const valueA = a[key];
    const valueB = b[key];

    if (valueA === null && valueB === null) {
      return 0;
    } else if (valueA === null) {
      return order === 'asc' ? -1 : 1;
    } else if (valueB === null) {
      return order === 'asc' ? 1 : -1;
    }

    if (valueA === undefined && valueB === undefined) {
      return 0;
    } else if (valueA === undefined) {
      return order === 'asc' ? -1 : 1;
    } else if (valueB === undefined) {
      return order === 'asc' ? 1 : -1;
    }

    const comparison = order === 'asc' ? 1 : -1;

    if (valueA < valueB) {
      return -1 * comparison;
    } else if (valueA > valueB) {
      return 1 * comparison;
    } else {
      return 0;
    }
  });
  return copiedPatterns;
};
export function sortByKey(key: string) {
  return function (a: any, b: any): number {
    const aProp = key.split('.').reduce((obj, prop) => obj && obj[prop], a);
    const bProp = key.split('.').reduce((obj, prop) => obj && obj[prop], b);

    if (typeof aProp === 'string' && typeof bProp === 'string') {
      return aProp.localeCompare(bProp);
    } else if (typeof aProp === 'number' && typeof bProp === 'number') {
      return aProp - bProp;
    } else {
      return 0;
    }
  };
}

export function useDecodedParams(): any {
  const parameters: { [key: string]: any } = useParams();
  const decodedParams: { [key: string]: any } = {};

  Object.entries(parameters).forEach(([key, value]) => {
    decodedParams[key] = urlencodeEncoder(String(value));
  });

  return decodedParams;
}
export const filterPathsDuckDbTable = (table: TableListModel) => {
  return {
    ...table,
    file_format: {
      ...table.file_format,
      file_paths: table.file_format?.file_paths?.filter((x) => x)
    }
  };
};
export const filterPropertiesDirectories = (db: ConnectionModel) => {
  const directories = {
    ...(db?.[db.provider_type as keyof ConnectionModel] as any)?.directories
  };
  const properties = {
    ...(db?.[db.provider_type as keyof ConnectionModel] as any)?.properties
  };
  Object.keys(properties).forEach((key) => {
    if (!properties[key]) {
      delete properties[key];
    }
  });

  if (db.provider_type === ConnectionModelProviderTypeEnum.duckdb) {
    Object.keys(directories).forEach((key) => {
      if (!directories[key]) {
        delete directories[key];
      }
    });
    return {
      ...db,
      duckdb: {
        ...db.duckdb,
        directories,
        properties
      }
    };
  }
  return {
    ...db,
    [db.provider_type as keyof ConnectionModel]: {
      ...(db?.[db.provider_type as keyof ConnectionModel] as any),
      properties
    }
  };
};

export const getFirstLevelConnectionTab = (checkType: CheckTypes) => {
  switch (checkType) {
    case CheckTypes.SOURCES:
      return 'detail';
    case CheckTypes.PROFILING:
      return 'schemas';
    case CheckTypes.PARTITIONED:
      return 'schemas';
    case CheckTypes.MONITORING:
      return 'schemas';
  }
};

export const getFirstLevelTableTab = (checkType: CheckTypes) => {
  switch (checkType) {
    case CheckTypes.SOURCES:
      return 'detail';
    case CheckTypes.PROFILING:
      return 'statistics';
    case CheckTypes.PARTITIONED:
      return 'observability-status';
    case CheckTypes.MONITORING:
      return 'observability-status';
  }
};

export const getFirstLevelColumnTab = (checkType: CheckTypes) => {
  switch (checkType) {
    case CheckTypes.SOURCES:
      return 'detail';
    case CheckTypes.PROFILING:
      return 'statistics';
    case CheckTypes.PARTITIONED:
      return 'observability-status';
    case CheckTypes.MONITORING:
      return 'observability-status';
  }
};
export const limitTextLength = (str: string | undefined, maxSize: number) => {
  if (!str) return;
  if (str.length > maxSize) {
    return str.slice(0, maxSize) + '...';
  }
  return str;
};

export const getLocalIncidentCheckResults = async ({
  checkType,
  connection,
  schema,
  table,
  column,
  dataGrouping,
  checkName,
  runCheckType,
  startDate,
  endDate,
  timeScale,
  category,
  comparisonName
}: {
  checkType: CheckTypes;
  connection: string;
  schema: string;
  table: string;
  column?: string;
  dataGrouping?: string;
  startDate: string;
  endDate: string;
  timeScale?: 'daily' | 'monthly';
  checkName: string;
  runCheckType?: string;
  category?: string;
  comparisonName?: string;
}) => {
  if (JSON.stringify(startDate) !== JSON.stringify(endDate)) {
    try {
      if (column) {
        if (checkType === CheckSearchFiltersCheckTypeEnum.profiling) {
          const res = await CheckResultApi.getColumnProfilingChecksResults(
            connection,
            schema,
            table,
            column,
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          );
          return res.data;
        } else if (
          runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring
        ) {
          const res = await CheckResultApi.getColumnMonitoringChecksResults(
            connection,
            schema,
            table,
            column,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          );
          return res.data;
        } else if (
          runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned
        ) {
          const res = await CheckResultApi.getColumnPartitionedChecksResults(
            connection,
            schema,
            table,
            column,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          );
          return res.data;
        }
      } else {
        if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
          const res = await CheckResultApi.getTableProfilingChecksResults(
            connection,
            schema,
            table,
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          );
          return res.data;
        } else if (
          runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring
        ) {
          const res = await CheckResultApi.getTableMonitoringChecksResults(
            connection,
            schema,
            table,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          );
          return res.data;
        } else if (
          runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned
        ) {
          const res = await CheckResultApi.getTablePartitionedChecksResults(
            connection,
            schema,
            table,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          );
          return res.data;
        }
      }
    } catch (error) {
      console.error('Error fetching check results:', error);
    }
  }
};

export const getIncidentsSensorReadouts = async ({
  checkType,
  connection,
  schema,
  table,
  column,
  dataGrouping,
  startDate,
  endDate,
  checkName,
  runCheckType,
  timeScale,
  category,
  comparisonName
}: {
  checkType: CheckTypes;
  connection: string;
  schema: string;
  table: string;
  column?: string;
  dataGrouping?: string;
  check?: CheckModel;
  startDate: string;
  endDate: string;
  timeScale?: 'daily' | 'monthly';
  checkName: string;
  runCheckType?: string;
  category?: string;
  comparisonName?: string;
}) => {
  try {
    if (column) {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        const res = await SensorReadoutsApi.getColumnProfilingSensorReadouts(
          connection,
          schema,
          table,
          column,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        const res = await SensorReadoutsApi.getColumnMonitoringSensorReadouts(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        const res = await SensorReadoutsApi.getColumnPartitionedSensorReadouts(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      }
    } else {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        const res = await SensorReadoutsApi.getTableProfilingSensorReadouts(
          connection,
          schema,
          table,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        const res = await SensorReadoutsApi.getTableMonitoringSensorReadouts(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        const res = await SensorReadoutsApi.getTablePartitionedSensorReadouts(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      }
    }
  } catch (error) {
    console.error('Error fetching sensor readouts:', error);
  }
};

export const getIncidentsErrors = async ({
  checkType,
  connection,
  schema,
  table,
  column,
  dataGrouping,
  startDate,
  endDate,
  checkName,
  runCheckType,
  timeScale,
  category,
  comparisonName
}: {
  checkType: CheckTypes;
  connection: string;
  schema: string;
  table: string;
  column?: string;
  dataGrouping?: string;
  check?: CheckModel;
  startDate: string;
  endDate: string;
  timeScale?: 'daily' | 'monthly';
  checkName: string;
  runCheckType?: string;
  category?: string;
  comparisonName?: string;
}) => {
  try {
    if (column) {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        const res = await ErrorsApi.getColumnProfilingErrors(
          connection,
          schema,
          table,
          column,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        const res = await ErrorsApi.getColumnMonitoringErrors(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        const res = await ErrorsApi.getColumnPartitionedErrors(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      }
    } else {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        const res = await ErrorsApi.getTableProfilingErrors(
          connection,
          schema,
          table,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        const res = await ErrorsApi.getTableMonitoringErrors(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        const res = await ErrorsApi.getTablePartitionedErrors(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      }
    }
  } catch (error) {
    console.error('Error fetching errors:', error);
  }
};

export const getIncidentsErrorSamples = async ({
  checkType,
  connection,
  schema,
  table,
  column,
  dataGrouping,
  startDate,
  endDate,
  checkName,
  runCheckType,
  timeScale,
  category,
  comparisonName
}: {
  checkType: CheckTypes;
  connection: string;
  schema: string;
  table: string;
  column?: string;
  dataGrouping?: string;
  check?: CheckModel;
  startDate: string;
  endDate: string;
  timeScale?: 'daily' | 'monthly';
  checkName: string;
  runCheckType?: string;
  category?: string;
  comparisonName?: string;
}) => {
  try {
    if (column) {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        const res = await ErrorSamplesApiClient.getColumnProfilingErrorSamples(
          connection,
          schema,
          table,
          column,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        const res = await ErrorSamplesApiClient.getColumnMonitoringErrorSamples(
          connection,
          schema,
          table,
          column,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        const res =
          await ErrorSamplesApiClient.getColumnPartitionedErrorSamples(
            connection,
            schema,
            table,
            column,
            timeScale || 'daily',
            dataGrouping,
            startDate,
            endDate,
            checkName,
            category,
            comparisonName
          );
        return res.data;
      }
    } else {
      if (runCheckType === CheckSearchFiltersCheckTypeEnum.profiling) {
        const res = await ErrorSamplesApiClient.getTableProfilingErrorSamples(
          connection,
          schema,
          table,
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.monitoring) {
        const res = await ErrorSamplesApiClient.getTableMonitoringErrorSamples(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      } else if (runCheckType === CheckSearchFiltersCheckTypeEnum.partitioned) {
        const res = await ErrorSamplesApiClient.getTablePartitionedErrorSamples(
          connection,
          schema,
          table,
          timeScale || 'daily',
          dataGrouping,
          startDate,
          endDate,
          checkName,
          category,
          comparisonName
        );
        return res.data;
      }
    }
  } catch (error) {
    console.error('Error fetching errors:', error);
  }
};

export function getParamsFromURL(
  url: string
): Record<string, string | boolean | undefined> {
  const params: Record<string, string | boolean | undefined> = {};
  const queryString = url.split('?')[1];

  if (queryString) {
    const pairs = queryString.split('&');

    for (const pair of pairs) {
      const [key, value] = pair.split('=');

      if (key) {
        if (key === 'severity') {
          switch (value) {
            case 'warning':
              params[key] = '1';
              break;
            case 'error':
              params[key] = '2';
              break;
            case 'fatal':
              params[key] = '3';
              break;
            default:
              params[key] = value;
          }
        } else if (value === 'true') {
          params[key] = true;
        } else if (value === 'false') {
          params[key] = false;
        } else {
          params[key] = value;
        }
      }
    }
  }

  return params;
}

export const getSeverity = (severity: string | undefined) => {
  switch (severity) {
    case '1':
      return 'warning';
    case '2':
      return 'error';
    case '3':
      return 'fatal';
    default:
      return severity;
  }
};

export const getRuleParametersConfigured = (checksUI?: CheckContainerModel) => {
  const param = !!checksUI?.categories
    ?.flatMap((category) => category.checks || [])
    .flatMap((check) => check || [])
    .flatMap((check) => check.rule || [])
    .find((x) => {
      let count = 0;
      if (x.warning?.configured) count++;
      if (x.error?.configured) count++;
      if (x.fatal?.configured) count++;
      return count > 1;
    });
  return param;
};
export const getIsAnyChecksEnabled = (checksUI?: CheckContainerModel) => {
  const checks: CheckModel[] = [];
  checksUI?.categories?.forEach((category) => {
    checks.push(...(category.checks || []));
  });
  const param = checks?.find((x) => {
    if (x.configured === true) {
      return true;
    }
  });
  return !!param;
};

export const getIsAnyChecksEnabledOrDefault = (
  checksUI?: CheckContainerModel
) => {
  const checks: CheckModel[] = [];
  checksUI?.categories?.forEach((category) => {
    checks.push(...(category.checks || []));
  });
  const param = checks?.find((x) => {
    if (x.configured === true || x.default_check === true) {
      return true;
    }
  });
  return !!param;
};

export const getIsAnyCheckResults = (
  checksOverview?: CheckResultsOverviewDataModel[]
) => {
  const param = checksOverview?.find((x) => {
    if (x.results?.length) {
      return true;
    }
  });
  return !!param;
};

export const getProviderTypeTitle = (
  providerType: ConnectionModelProviderTypeEnum | undefined
) => {
  switch (providerType) {
    case ConnectionModelProviderTypeEnum.bigquery:
      return 'BigQuery';
    case ConnectionModelProviderTypeEnum.snowflake:
      return 'Snowflake';
    case ConnectionModelProviderTypeEnum.postgresql:
      return 'PostgreSQL';
    case ConnectionModelProviderTypeEnum.redshift:
      return 'Redshift';
    case ConnectionModelProviderTypeEnum.sqlserver:
      return 'Microsoft SQL Server/SQL Server connection parameters';
    case ConnectionModelProviderTypeEnum.presto:
      return 'Presto';
    case ConnectionModelProviderTypeEnum.trino:
      return 'Trino';
    case ConnectionModelProviderTypeEnum.mysql:
      return 'MySQL';
    case ConnectionModelProviderTypeEnum.oracle:
      return 'Oracle';
    case ConnectionModelProviderTypeEnum.spark:
      return 'Spark';
    case ConnectionModelProviderTypeEnum.databricks:
      return 'Databricks';
    case ConnectionModelProviderTypeEnum.hana:
      return 'HANA';
    case ConnectionModelProviderTypeEnum.db2:
      return 'DB2';
    case ConnectionModelProviderTypeEnum.duckdb:
      return 'DuckDB';
    case ConnectionModelProviderTypeEnum.clickhouse:
      return 'ClickHouse';
    case ConnectionModelProviderTypeEnum.questdb:
      return 'QuestDB';
    default:
      return '';
  }
};

export const getRouteValidLabel = (route: string) => {
  if (route.includes('/columns/all')) {
    return 'Columns';
  }
  if (route.endsWith('checks/advanced-profiling')) {
    return 'Profiling checks';
  }
  if (route.endsWith('monitoring/daily')) {
    return 'Daily monitoring';
  }
  if (route.endsWith('monitoring/monthly')) {
    return 'Monthly monitoring';
  }
  if (route.endsWith('partitioned/daily')) {
    return 'Daily period checks';
  }
  if (route.endsWith('partitioned/monthly')) {
    return 'Monthly period checks';
  }
  return route.split('/')[route.split('/').length - 1];
};

export const isValidRouteWithoutTab = (route: string) => {
  return (
    route.endsWith('/columns/all') ||
    route.endsWith('monitoring/daily') ||
    route.endsWith('monitoring/monthly') ||
    route.endsWith('partitioned/daily') ||
    route.endsWith('partitioned/monthly') ||
    route.endsWith('checks/advanced-profiling') ||
    route.endsWith('/:category/:checkName')
  );
};
