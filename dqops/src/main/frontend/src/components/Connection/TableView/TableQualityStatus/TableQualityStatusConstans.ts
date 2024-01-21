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
};

export interface ITableParameters {
  firstLevelChecks: Record<string, TFirstLevelCheck[]>;
  categoryDimension: 'category' | 'dimension';
  severityType: 'current' | 'highest';
  tableDataQualityStatus: TableCurrentDataQualityStatusModel;
  timeScale: 'daily' | 'monthly' | undefined;
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
