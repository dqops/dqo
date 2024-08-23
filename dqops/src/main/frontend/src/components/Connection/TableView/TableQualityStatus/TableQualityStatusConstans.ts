import {
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum,
  TableCurrentDataQualityStatusModel
} from '../../../../api';

export type TFirstLevelCheck = {
  checkName: string;
  currentSeverity?: CheckCurrentDataQualityStatusModelCurrentSeverityEnum;
  highestSeverity?: CheckCurrentDataQualityStatusModelCurrentSeverityEnum;
  lastExecutedAt?: number | string;
  checkType: string;
  category?: string;
  qualityDimension?: string;
  execution_errors?: number;
};

export interface ITableParameters {
  firstLevelChecks: Record<string, TFirstLevelCheck[]>;
  categoryDimension: 'category' | 'dimension';
  severityType: 'current' | 'highest';
  tableDataQualityStatus: TableCurrentDataQualityStatusModel;
  timeScale: 'daily' | 'monthly' | undefined;
  extendedChecks: Array<{ checkType: string; categoryDimension: string }>;
  setExtendedChecks: React.Dispatch<
    React.SetStateAction<
      Array<{ checkType: string; categoryDimension: string }>
    >
  >;
}

export const severityMap = [
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error,
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal,
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error,
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning,
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid
];

export const backgroundStyle: React.CSSProperties = {
  background: `
      repeating-linear-gradient(
        45deg,
        #ffffff,
        #ffffff 5px,
        #cccccc 5px,
        #cccccc 10px
      ),
      repeating-linear-gradient(
        45deg,
        #cccccc,
        #cccccc 5px,
        #ffffff 5px,
        #ffffff 10px
     )`
};

export const secondBackgroundStyle: React.CSSProperties = {
  background: `
  repeating-linear-gradient(
    45deg,
    #ffffff,
    #ffffff 5px,
    #eeeeee 5px, /* Slightly brighter gray */
    #eeeeee 10px
  ),
  repeating-linear-gradient(
    45deg,
    #eeeeee, /* Slightly brighter gray */
    #eeeeee 5px,
    #ffffff 5px,
    #ffffff 10px
  )`
};
