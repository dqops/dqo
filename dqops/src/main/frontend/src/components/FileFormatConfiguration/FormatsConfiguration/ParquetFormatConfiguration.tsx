import React, { useMemo } from 'react';
import { ParquetFileFormatSpec, ParquetFileFormatSpecCompressionEnum } from '../../../api';
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean';
import FormatConfigurationRenderer from '../FormatConfigurationRenderer';
import ConfigurationItemRowCompression from './RowItem/ConfigurationItemRowCompression';

type TParquetConfigurationProps = {
  configuration: ParquetFileFormatSpec;
  onChangeConfiguration: (params: Partial<ParquetFileFormatSpec>) => void;
};

const compressionEnumOptions = [
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
    value: ParquetFileFormatSpecCompressionEnum.gzip + "_no_ext",
    label: ParquetFileFormatSpecCompressionEnum.gzip + "_no_ext (*.parquet)",
    noCompressionExtension: true
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.zstd + "_no_ext",
    label: ParquetFileFormatSpecCompressionEnum.zstd + "_no_ext (*.parquet)",
    noCompressionExtension: true
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.snappy + "_no_ext",
    label: ParquetFileFormatSpecCompressionEnum.snappy + "_no_ext (*.parquet)",
    noCompressionExtension: true
  },
  {
    value: ParquetFileFormatSpecCompressionEnum.lz4 + "_no_ext",
    label: ParquetFileFormatSpecCompressionEnum.lz4 + "_no_ext  (*.parquet)",
    noCompressionExtension: true
  },
];

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
      configurationStrings={[]}
      configurationBooleans={parquetConfigurationBooleans}
      type="Parquet"
    >
      <ConfigurationItemRowCompression
          label='Compression'
          value={configuration?.compression + (configuration?.no_compression_extension===true ? "_no_ext" : "")}
          onChange={(str, no_compression_extension) => {
            onChangeConfiguration({
              compression:
                String(str).length > 0
                  ? (str as ParquetFileFormatSpecCompressionEnum)
                  : undefined, 
              no_compression_extension: no_compression_extension
            });
          }}
          options={[{ label: '', value: '' }, ...compressionEnumOptions]}
        />
    </FormatConfigurationRenderer>
  );
}
