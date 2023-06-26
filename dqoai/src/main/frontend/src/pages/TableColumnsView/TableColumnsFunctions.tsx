import { DataGroupingConfigurationSpec } from '../../api';

export const renderValue = (value: any) => {
  if (typeof value === 'boolean') {
    return value ? 'Yes' : 'No';
  }
  if (typeof value === 'object') {
    return value.toString();
  }
  return value;
};

export const cutString = (text: string) => {
  if (text.length > 22 && isNaN(Number(text))) {
    return text.slice(0, 22) + '...';
  } else {
    return text;
  }
};
export interface LocationState {
  bool: boolean;
  data_stream_name: string;
  spec: DataGroupingConfigurationSpec;
}

export interface MyData {
  null_percent: number | undefined;
  unique_value: number | undefined;
  null_count?: number | undefined;
  nameOfCol?: string | undefined;
  minimalValue?: string | undefined;
  detectedDatatypeVar: number | undefined;
  length?: number | undefined;
  scale?: number | undefined;
  importedDatatype: string | undefined;
  columnHash: number;
  isColumnSelected: boolean;
}

export const labels = [
  'Column name',
  'Detected data type',
  'Imported data type',
  'Length',
  'Scale',
  'Minimal value',
  'Null count'
];
export const calculate_color = (
  uniqueCount: number,
  maxUniqueCount: number
) => {
  if (uniqueCount === 0) {
    return 'rgba(255, 255, 255, 1)';
  }

  if (uniqueCount === maxUniqueCount) {
    return 'rgba(2, 154, 128, 0.1)';
  }

  if (uniqueCount === 1) {
    return 'rgba(2, 154, 128, 0.8)';
  }

  const logarithm = Math.log2(uniqueCount);
  const alpha = (1 - (logarithm / Math.log2(maxUniqueCount)) * 0.9) / 1.3;
  const color = `rgba(2, 154, 128, ${alpha})`;

  return color;
};
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
