import {
  CheckCurrentDataQualityStatusModel,
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum,
  ColumnCurrentDataQualityStatusModel,
  DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
} from '../../../../api';
import { TFirstLevelCheck, severityMap } from './TableQualityStatusConstans';

export const getColor = (
  status:
    | CheckCurrentDataQualityStatusModelCurrentSeverityEnum
    | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
    | null
    | undefined
) => {
  // console.log(status)
  switch (status) {
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error:
      return 'bg-gray-150';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal:
      return 'bg-red-300';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error:
      return 'bg-orange-300';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning:
      return 'bg-yellow-300';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid:
      return 'bg-green-300';
    default:
      return '';
  }
};
export const getColumnStatus = (
  severityType: 'current' | 'highest',
  categoryDimension: 'category' | 'dimension',
  column: ColumnCurrentDataQualityStatusModel,
  firstLevelCheck: string
) => {
  const checks: CheckCurrentDataQualityStatusModel[] = [];
  Object.values(column?.checks ?? {}).forEach((check) => {
    if (
      categoryDimension === 'category'
        ? check.category === firstLevelCheck
        : check.quality_dimension === firstLevelCheck
    ) {
      checks.push(check);
    }
  });
  for (const severity of severityMap) {
    const foundCheck = checks.find(
      (x) =>
        (severityType === 'highest'
          ? x.highest_historical_severity
          : x.current_severity) === severity
    );

    if (foundCheck) {
      return {
        status: severity,
        lastExecutedAt: foundCheck.last_executed_at
      };
    }
  }
  return { status: null, lastExecutedAt: null };
};

export const getColumnCircleStatus = (
  severityType: 'current' | 'highest',
  categoryDimension: 'category' | 'dimension',
  column: ColumnCurrentDataQualityStatusModel,
  firstLevelCheck: string
) => {
  const checks: CheckCurrentDataQualityStatusModel[] = [];
  Object.values(column.checks ?? {}).forEach((check) => {
    if (
      categoryDimension === 'category'
        ? check.category === firstLevelCheck
        : check.quality_dimension === firstLevelCheck
    ) {
      checks.push(check);
    }
  });
  for (const severity of severityMap) {
    const foundCheck = checks.find(
      (x) =>
        (severityType === 'highest'
          ? x.current_severity
          : x.highest_historical_severity) === severity
    );

    if (foundCheck) {
      return {
        status: severity,
        lastExecutedAt: foundCheck.last_executed_at
      };
    }
  }
  return { status: null, lastExecutedAt: null };
};

export const getTableCircleStatus = (
  severityType: 'current' | 'highest',
  checks: TFirstLevelCheck[]
) => {
  const checkType = 'table';

  for (const severity of severityMap) {
    const foundCheck = checks.find(
      (x) =>
        (severityType === 'highest' ? x.currentSeverity : x.highestSeverity) ===
          severity && x.checkType === checkType
    );

    if (foundCheck) {
      return { status: severity, lastExecutedAt: foundCheck.lastExecutedAt };
    }
  }

  return { status: null, lastExecutedAt: null };
};

export const getTableStatus = (
  severityType: 'current' | 'highest',
  checks: TFirstLevelCheck[]
) => {
  const checkType = 'table';
  for (const severity of severityMap) {
    const foundCheck = checks.find(
      (x) =>
        (severityType === 'highest' ? x.highestSeverity : x.currentSeverity) ===
          severity && x.checkType === checkType
    );

    if (foundCheck) {
      return { status: severity, lastExecutedAt: foundCheck.lastExecutedAt };
    }
  }

  return { status: null, lastExecutedAt: null };
};
