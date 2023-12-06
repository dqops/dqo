import { CheckCurrentDataQualityStatusModelCurrentSeverityEnum } from '../../../../api';

export const getColor = (
  status:
    | CheckCurrentDataQualityStatusModelCurrentSeverityEnum
    | null
    | undefined
) => {
  switch (status) {
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error:
      return 'bg-gray-150';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal:
      return 'bg-red-200';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error:
      return 'bg-orange-200';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning:
      return 'bg-yellow-200';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid:
      return 'bg-green-200';
    default:
      return '';
  }
};
