import {
  CheckContainerModel,
  ComparisonCheckResultModel,
  TableComparisonGroupingColumnPairModel,
  TableComparisonModel,
  TableComparisonResultsModel
} from '../../../../api';
import { TableComparisonsApi } from '../../../../services/apiClient';
import { TParameters } from '../../../../shared/constants';
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
    Object.keys(tableComparisonResults.table_comparison_results).map(
      (key: string) => {
        if (key.includes(type)) {
          colorVar = tableComparisonResults.table_comparison_results[key];
        }
      }
    );
  }

  if (colorVar?.fatals && Number(colorVar.fatals) !== 0) {
    return 'bg-[#EF8079]';
  } else if (colorVar?.errors && Number(colorVar.errors) !== 0) {
    return 'bg-[#EFB87E]';
  } else if (colorVar?.warnings && Number(colorVar.warnings) !== 0) {
    return 'bg-[#EFEC82]';
  } else if (colorVar?.valid_results && Number(colorVar.valid_results) !== 0) {
    return 'bg-[#5CBCAB]';
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

export const getRequiredColumnsIndexes = (
  dataGrouping: TableComparisonGroupingColumnPairModel[]
) => {
  const referenceGrouping = dataGrouping.map(
    (x) => x?.reference_table_column_name
  );
  const comparedGrouping = dataGrouping.map(
    (x) => x?.compared_table_column_name
  );

  const maxLeghtToCheck = Math.max(
    referenceGrouping.length,
    comparedGrouping.length
  );

  const referenceMissingIndexes = [];
  const comparedMissingIndexes = [];

  let check = false;
  for (let i = maxLeghtToCheck - 1; i >= 0; i--) {
    if (check === false) {
      if (referenceGrouping?.[i] && comparedGrouping?.[i]) {
        check = true;
      } else if (
        referenceGrouping?.[i] &&
        (comparedGrouping?.[i] === undefined ||
          comparedGrouping?.[i]?.length === 0)
      ) {
        check = true;
        comparedMissingIndexes.push(i);
      } else if (
        comparedGrouping?.[i] &&
        (referenceGrouping?.[i] === undefined ||
          referenceGrouping?.[i]?.length === 0)
      ) {
        check = true;
        referenceMissingIndexes.push(i);
      }
    } else {
      if (
        comparedGrouping?.[i] === undefined ||
        comparedGrouping?.[i]?.length === 0
      ) {
        comparedMissingIndexes.push(i);
      }
      if (
        referenceGrouping?.[i] === undefined ||
        referenceGrouping?.[i]?.length === 0
      ) {
        referenceMissingIndexes.push(i);
      }
    }
  }

  const isConfigurationCorrect =
    referenceMissingIndexes.length === 0 && comparedMissingIndexes.length === 0;
  return {
    referenceMissingIndexes,
    comparedMissingIndexes,
    isConfigurationCorrect
  };
};

export const getIsButtonEnabled = (parameters: TParameters): boolean => {
  return !!(
    parameters.refConnection &&
    parameters.refSchema &&
    parameters.refTable &&
    parameters.name &&
    getRequiredColumnsIndexes(parameters.dataGroupingArray ?? [])
      .isConfigurationCorrect
  );
};

export const validate404Status = (
  status: number,
  callBack?: () => void
): boolean => {
  if (callBack && status === 404) {
    callBack();
  }
  return status === 200 || status === 404;
};
