import {
  TableComparisonResultsModel,
  ComparisonCheckResultModel,
  TableComparisonModel,
  CheckContainerModel
} from '../../../../api';
import { TableComparisonsApi } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';

export const getComparisonResults = (
  nameOfColumn: string,
  tableComparisonResults: TableComparisonResultsModel | undefined
): { [key: string]: ComparisonCheckResultModel } => {
  const columnComparisonResults =
    tableComparisonResults?.column_comparison_results ?? {};

  if (Object.keys(columnComparisonResults).find((x) => x === nameOfColumn)) {
    return (
      (columnComparisonResults[nameOfColumn].column_comparison_results as {
        [key: string]: ComparisonCheckResultModel;
      }) ?? {}
    );
  } else {
    return {};
  }
};

export const calculateColor = (
  nameOfCol: string,
  nameOfCheck: string,
  type?: 'row_count' | 'column_count',
  checkTypes?: CheckTypes,
  tableComparisonResults?: any
): string => {
  let newNameOfCheck = '';
  if (checkTypes === CheckTypes.PROFILING) {
    newNameOfCheck = 'profile_' + nameOfCheck;
  }
  if (
    checkTypes === CheckTypes.MONITORING ||
    checkTypes === CheckTypes.PARTITIONED
  ) {
    newNameOfCheck = nameOfCheck;
  }

  let colorVar = getComparisonResults(nameOfCol, tableComparisonResults)[
    newNameOfCheck
  ];
  if (
    type &&
    tableComparisonResults &&
    tableComparisonResults.table_comparison_results
  ) {
    if (type === 'row_count') {
      colorVar =
        Object.values(tableComparisonResults.table_comparison_results)?.at(0) ??
        {};
    } else if (type === 'column_count') {
      colorVar =
        Object.values(tableComparisonResults.table_comparison_results)?.at(1) ??
        {};
    }
  }

  if (colorVar?.fatals && Number(colorVar.fatals) !== 0) {
    return 'bg-red-200';
  } else if (colorVar?.errors && Number(colorVar.errors) !== 0) {
    return 'bg-orange-200';
  } else if (colorVar?.warnings && Number(colorVar.warnings) !== 0) {
    return 'bg-yellow-200';
  } else if (colorVar?.valid_results && Number(colorVar.valid_results) !== 0) {
    return 'bg-green-200';
  } else {
    return '';
  }
};

export const onUpdate = (
  connection: string,
  schema: string,
  table: string,
  checkTypes: CheckTypes,
  timePartitioned: 'daily' | 'monthly' | undefined,
  reference: TableComparisonModel | undefined,
  handleChange: (value: CheckContainerModel) => Promise<void>,
  tableChecksToUpdate: any
) => {
  if (checkTypes === CheckTypes.PROFILING) {
    TableComparisonsApi.updateTableComparisonProfiling(
      connection,
      schema,
      table,
      reference?.table_comparison_configuration_name ?? '',
      reference
    ).catch((err) => {
      console.error(err);
    });
  } else if (checkTypes === CheckTypes.MONITORING) {
    if (timePartitioned === 'daily') {
      TableComparisonsApi.updateTableComparisonMonitoringDaily(
        connection,
        schema,
        table,
        reference?.table_comparison_configuration_name ?? '',
        reference
      ).catch((err) => {
        console.error(err);
      });
    } else if (timePartitioned === 'monthly') {
      TableComparisonsApi.updateTableComparisonMonitoringMonthly(
        connection,
        schema,
        table,
        reference?.table_comparison_configuration_name ?? '',
        reference
      ).catch((err) => {
        console.error(err);
      });
    }
  } else if (checkTypes === CheckTypes.PARTITIONED) {
    if (timePartitioned === 'daily') {
      TableComparisonsApi.updateTableComparisonPartitionedDaily(
        connection,
        schema,
        table,
        reference?.table_comparison_configuration_name ?? '',
        reference
      ).catch((err) => {
        console.error(err);
      });
    } else if (timePartitioned === 'monthly') {
      TableComparisonsApi.updateTableComparisonPartitionedMonthly(
        connection,
        schema,
        table,
        reference?.table_comparison_configuration_name ?? '',
        reference
      ).catch((err) => {
        console.error(err);
      });
    }
  }

  handleChange(tableChecksToUpdate as CheckContainerModel);
};
