import React, { useMemo } from 'react';
import { ParquetFileFormatSpec } from '../../api';
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean'
import FormatConfigurationRenderer from './FormatConfigurationRenderer'

type TParquetConfigurationProps = {
  configuration: ParquetFileFormatSpec;
  onChangeConfiguration: (params: Partial<ParquetFileFormatSpec>) => void;
};

export default function ParquetFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TParquetConfigurationProps) {
  const parquetConfigurationBooleans: TConfigurationItemRowBoolean[] = useMemo(() => {
    return [
      {
        label: 'Binary as string',
        value: configuration?.binary_as_string,
        onChange: (value) => onChangeConfiguration({ binary_as_string: value }),
        defaultValue: false
      },
      {
        label: 'Filename',
        value: configuration?.filename,
        onChange: (value) => onChangeConfiguration({ filename: value }),
        defaultValue: false
      },
      {
        label: 'File row number',
        value: configuration?.file_row_number,
        onChange: (value) => onChangeConfiguration({ file_row_number: value }),
        defaultValue: false
      },
      {
        label: 'Hive partitioning',
        value: configuration?.hive_partitioning,
        onChange: (value) => onChangeConfiguration({ hive_partitioning: value }),
        defaultValue: false
      },
      {
        label: 'Union by name',
        value: configuration?.union_by_name,
        onChange: (value) => onChangeConfiguration({ union_by_name: value }),
        defaultValue: false
      },
    ];
  }, [configuration]);

  return (
    <FormatConfigurationRenderer 
      configurationBooleans={parquetConfigurationBooleans}
    />
  );
}
