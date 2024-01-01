import { IFilterTemplate } from '../../../../shared/constants';
import { CheckTypes, ROUTES } from '../../../../shared/routes';

export const getCommonParams = (
  filterParameters: IFilterTemplate,
  table: string
): [string, string, string, string] => [
  filterParameters.checkTypes,
  filterParameters.connection,
  filterParameters.schema,
  table
];

export const getAdditionalParams = (
  filterParameters: IFilterTemplate,
  column?: string
): [string, string, string, string] => [
  column ?? '',
  filterParameters.activeTab ?? 'daily',
  filterParameters.checkCategory!,
  filterParameters.checkName!
];

export const getValue = (
  checkTypes: string,
  commonParams: [string, string, string, string],
  column?: string
): string => {
  switch (checkTypes) {
    case CheckTypes.PROFILING:
      return column
        ? ROUTES.COLUMN_PROFILING_VALUE(...commonParams, column)
        : ROUTES.TABLE_PROFILING_VALUE(...commonParams);
    case CheckTypes.PARTITIONED:
      return column
        ? ROUTES.COLUMN_PARTITIONED_VALUE(...commonParams, column)
        : ROUTES.TABLE_PARTITIONED_VALUE(...commonParams);
    case CheckTypes.MONITORING:
      return column
        ? ROUTES.COLUMN_MONITORING_VALUE(...commonParams, column)
        : ROUTES.TABLE_MONITORING_VALUE(...commonParams);
    default:
      return '';
  }
};

export const getUrl = (
  filterParameters: IFilterTemplate,
  commonParams: [string, string, string, string],
  additionalParams: [string, string, string, string],
  column?: string
): string => {
  return filterParameters.checkTypes === CheckTypes.PROFILING
    ? column
      ? ROUTES.COLUMN_PROFILING_UI_FILTER(
          ...commonParams,
          additionalParams[0],
          ...(additionalParams.slice(2) as [string, string])
        )
      : ROUTES.TABLE_PROFILING_UI_FILTER(
          ...commonParams,
          ...(additionalParams.slice(2) as [string, string])
        )
    : filterParameters.checkTypes === CheckTypes.PARTITIONED
    ? column
      ? ROUTES.COLUMN_PARTITIONED_UI_FILTER(
          ...commonParams,
          ...additionalParams
        )
      : ROUTES.TABLE_PARTITIONED_UI_FILTER(
          ...commonParams,
          ...(additionalParams.slice(1) as [string, string, string])
        )
    : column
    ? ROUTES.COLUMN_MONITORING_UI_FILTER(...commonParams, ...additionalParams)
    : ROUTES.TABLE_MONITORING_UI_FILTER(
        ...commonParams,
        ...(additionalParams.slice(1) as [string, string, string])
      );
};
