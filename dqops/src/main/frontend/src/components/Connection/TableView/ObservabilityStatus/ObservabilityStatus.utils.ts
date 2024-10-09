import moment from 'moment';
import {
  CheckResultEntryModel,
  CheckResultsListModel,
  CheckResultsOverviewDataModelStatusesEnum
} from '../../../../api';
import { checkNameDictionary } from './ObservabilityStatus.constans';

export const calculateDateRange = (month: string) => {
  if (!month) return { startDate: '', endDate: '' };

  if (month === 'Last 3 months') {
    return {
      startDate: moment().add(-3, 'month').format('YYYY-MM-DD'),
      endDate: moment().format('YYYY-MM-DD')
    };
  }

  return {
    startDate: moment(month, 'MMMM YYYY').format('YYYY-MM-DD'),
    endDate: moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD')
  };
};

export const getColor = (
  status?: CheckResultsOverviewDataModelStatusesEnum
) => {
  switch (status) {
    case 'valid':
      return 'bg-teal-500';
    case 'warning':
      return 'bg-yellow-900';
    case 'error':
      return 'bg-orange-900';
    case 'fatal':
      return 'bg-red-900';
    case 'execution_error':
      return 'bg-black';
    default:
      return '  ';
  }
};

export const getDisplayCheckNameFromDictionary = (checkName: string) => {
  if (checkName in checkNameDictionary) {
    return checkNameDictionary[checkName as keyof typeof checkNameDictionary];
  }
  return checkName;
};
type ChartType = {
  results: CheckResultEntryModel[];
  month: string;
  checkName: string;
  dataGroup?: string;
};
export const getResultsForCharts = (
  results: Array<CheckResultsListModel>,
  column?: string,
  month?: string,
  dataGroup?: string
) => {
  const newResults: Array<ChartType> = [];
  results.forEach((result) => {
    if (!column) {
      // Row Count Checks
      if (result.checkName === 'daily_row_count_anomaly') {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (result.checkName === 'daily_partition_row_count_anomaly') {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (
        !results.find((x) => x.checkName === 'daily_row_count_anomaly') &&
        result.checkName === 'daily_row_count'
      ) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (
        !results.find(
          (x) => x.checkName === 'daily_partition_row_count_anomaly'
        ) &&
        result.checkName === 'daily_partition_row_count'
      ) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }

      // Data Freshness Checks
      if (result.checkName?.includes('daily_data_freshness_anomaly')) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (
        !results.find((x) => x.checkName === 'daily_data_freshness_anomaly') &&
        result.checkName?.includes('daily_data_freshness')
      ) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
    } else {
      // Nulls Percent Checks
      if (result.checkName?.includes('daily_nulls_percent_anomaly')) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (result.checkName?.includes('daily_partition_nulls_percent_anomaly')) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (
        !results.find((x) => x.checkName === 'daily_nulls_percent_anomaly') &&
        result.checkName?.includes('daily_nulls_percent')
      ) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (
        !results.find(
          (x) => x.checkName === 'daily_partition_nulls_percent_anomaly'
        ) &&
        result.checkName?.includes('daily_partition_nulls_percent')
      ) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }

      // Distinct Count Checks
      if (result.checkName?.includes('daily_distinct_count_anomaly')) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (
        result.checkName?.includes('daily_partition_distinct_count_anomaly')
      ) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (
        !results.find((x) => x.checkName === 'daily_distinct_count_anomaly') &&
        result.checkName?.includes('daily_distinct_count')
      ) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
      if (
        !results.find(
          (x) => x.checkName === 'daily_partition_distinct_count_anomaly'
        ) &&
        result.checkName?.includes('daily_partition_distinct_count')
      ) {
        newResults.push({
          results: result.checkResultEntries ?? [],
          month: month ?? 'Last 3 months',
          dataGroup,
          checkName: result?.checkName ?? ''
        });
      }
    }
  });

  return newResults;
};
