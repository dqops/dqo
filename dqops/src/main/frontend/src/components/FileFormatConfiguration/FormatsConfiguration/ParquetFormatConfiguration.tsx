import React, { useMemo } from 'react';
import { ParquetFileFormatSpec } from '../../../api';
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean';
import FormatConfigurationRenderer from '../FormatConfigurationRenderer';

type TParquetConfigurationProps = {
  configuration: ParquetFileFormatSpec;
  onChangeConfiguration: (params: Partial<ParquetFileFormatSpec>) => void;
};

export default function ParquetFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TParquetConfigurationProps) {
  const parquetConfigurationBooleans: TConfigurationItemRowBoolean[] =
    useMemo(() => {
      return [
        {
          label: 'Binary as string',
          value: configuration?.binary_as_string,
          onChange: (value) =>
            onChangeConfiguration({ binary_as_string: value })
        },
        {
          label: 'Filename',
          value: configuration?.filename,
          onChange: (value) => onChangeConfiguration({ filename: value })
        },
        {
          label: 'File row number',
          value: configuration?.file_row_number,
          onChange: (value) => onChangeConfiguration({ file_row_number: value })
        },
        {
          label: 'Hive partitioning',
          value: configuration?.hive_partitioning,
          onChange: (value) =>
            onChangeConfiguration({ hive_partitioning: value })
        },
        {
          label: 'Union by name',
          value: configuration?.union_by_name,
          onChange: (value) => onChangeConfiguration({ union_by_name: value })
        }
      ];
    }, [configuration]);

  return (
    <FormatConfigurationRenderer
      configurationBooleans={parquetConfigurationBooleans}
      type="Parquet"
    />
  );
}
