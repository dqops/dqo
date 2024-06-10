import {
  DataGroupingConfigurationSpec,
  DimensionCurrentDataQualityStatusModel,
  TableColumnsStatisticsModel
} from '../../api';

export const spec: DataGroupingConfigurationSpec = {
  level_1: {
    column: undefined
  },
  level_2: {
    column: undefined
  },
  level_3: {
    column: undefined
  },
  level_4: {
    column: undefined
  },
  level_5: {
    column: undefined
  },
  level_6: {
    column: undefined
  },
  level_7: {
    column: undefined
  },
  level_8: {
    column: undefined
  },
  level_9: {
    column: undefined
  }
};

export interface MyData {
  null_percent: number | undefined;
  unique_value: number | undefined;
  null_count?: number | undefined;
  nameOfCol?: string | undefined;
  minimalValue?: string | undefined;
  maximumValue?: string | undefined;
  detectedDatatypeVar: number | undefined;
  length?: number | undefined;
  scale?: number | undefined;
  importedDatatype?: string | undefined;
  columnHash: number;
  isColumnSelected: boolean;
  dimentions?: (
    | {
        [key: string]: DimensionCurrentDataQualityStatusModel;
      }
    | undefined
  )[];
  labels?: string;
}

export interface ITableColumnsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  checkedColumns?: Array<string>;
  setCheckedColumns?: (columns: Array<string>) => void;
  statistics?: TableColumnsStatisticsModel;
  refreshListFunc: () => void;
}

export const labels = [
  'Dimensions',
  'Column name',
  'Labels',
  'Detected data type',
  'Imported data type',
  'Length',
  'Scale',
  'Min value',
  'Max value',
  'Nulls count'
];
