import React, { useMemo } from 'react';
import { ParquetFileFormatSpec, ParquetFileFormatSpecCompressionEnum } from '../../../api';
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean';
import FormatConfigurationRenderer from '../FormatConfigurationRenderer';
import { TConfigurationItemRow } from './RowItem/TConfigurationItemRow';

type TParquetConfigurationProps = {
  configuration: ParquetFileFormatSpec;
  onChangeConfiguration: (params: Partial<ParquetFileFormatSpec>) => void;
};

const compressionEnumOptions = [
  // {
  //   value: JsonFileFormatSpecCompressionEnum.auto,
  //   label: JsonFileFormatSpecCompressionEnum.auto
  // },
  {
    value: ParquetFileFormatSpecCompressionEnum.none,
    label: ParquetFileFormatSpecCompressionEnum.none + " (*.parquet)",
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.gzip,
    label: ParquetFileFormatSpecCompressionEnum.gzip + " (*.parquet.gz)",
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.zstd,
    label: ParquetFileFormatSpecCompressionEnum.zstd + " (*.parquet.zst)",
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.snappy,
    label: ParquetFileFormatSpecCompressionEnum.snappy + " (*.parquet.snappy)",
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.lz4,
    label: ParquetFileFormatSpecCompressionEnum.lz4 + " (*.parquet.lz4)",
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.gzip,
    label: ParquetFileFormatSpecCompressionEnum.gzip + "_no_ext (*.parquet)",
    noCompressionExtension: true
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.zstd,
    label: ParquetFileFormatSpecCompressionEnum.zstd + "_no_ext (*.parquet)",
    noCompressionExtension: true
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.snappy,
    label: ParquetFileFormatSpecCompressionEnum.snappy + "_no_ext (*.parquet)",
    noCompressionExtension: true
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.lz4,
    label: ParquetFileFormatSpecCompressionEnum.lz4 + "_no_ext  (*.parquet)",
    noCompressionExtension: true
  },
];

export default function ParquetFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TParquetConfigurationProps) {

  const parquetConfiguration: TConfigurationItemRow[] = useMemo(() => {
    return [
      {
        label: 'Compression',
        value: configuration?.compression,
        onChange: (str) => {
          onChangeConfiguration({
            compression:
              String(str).length > 0
                ? (str as ParquetFileFormatSpecCompressionEnum)
                : undefined
          });
        },
        isEnum: true,
        options: [{ label: '', value: '' }, ...compressionEnumOptions],
        defaultValue: ''
      }
    ];
  }, [configuration]);

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
      configuraitonStrings={parquetConfiguration}
      configurationBooleans={parquetConfigurationBooleans}
      type="Parquet"
    />
  );
}
