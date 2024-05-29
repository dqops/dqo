import React, { useMemo } from 'react';
import {
  JsonFileFormatSpec,
  JsonFileFormatSpecFormatEnum,
  JsonFileFormatSpecCompressionEnum,
  JsonFileFormatSpecRecordsEnum
} from '../../../api';
import { TConfigurationItemRow } from './RowItem/TConfigurationItemRow';
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean';
import FormatConfigurationRenderer from '../FormatConfigurationRenderer';
import ConfigurationItemRowCompression from './RowItem/ConfigurationItemRowCompression';

type TJsonConfigurationProps = {
  configuration: JsonFileFormatSpec;
  onChangeConfiguration: (params: Partial<JsonFileFormatSpec>) => void;
};

const compressionEnumOptions = [
  {
    value: JsonFileFormatSpecCompressionEnum.none,
    label: JsonFileFormatSpecCompressionEnum.none + " (*.json)",
  },
  {
    value: JsonFileFormatSpecCompressionEnum.gzip,
    label: JsonFileFormatSpecCompressionEnum.gzip + " (*.json.gz)",
  },
  {
    value: JsonFileFormatSpecCompressionEnum.zstd,
    label: JsonFileFormatSpecCompressionEnum.zstd + " (*.json.zst)",
  },
  {
    value: JsonFileFormatSpecCompressionEnum.gzip + "_no_ext",
    label: JsonFileFormatSpecCompressionEnum.gzip + "_no_ext (*.json)",
    noCompressionExtension: true
  },
  {
    value: JsonFileFormatSpecCompressionEnum.zstd + "_no_ext",
    label: JsonFileFormatSpecCompressionEnum.zstd + "_no_ext (*.json)",
    noCompressionExtension: true
  }
];

const jsonFormatOptions = [
  {
    value: JsonFileFormatSpecFormatEnum.array,
    label: JsonFileFormatSpecFormatEnum.array
  },
  {
    value: JsonFileFormatSpecFormatEnum.auto,
    label: JsonFileFormatSpecFormatEnum.auto
  },
  {
    value: JsonFileFormatSpecFormatEnum.newline_delimited,
    label: JsonFileFormatSpecFormatEnum.newline_delimited
  },
  {
    value: JsonFileFormatSpecFormatEnum.unstructured,
    label: JsonFileFormatSpecFormatEnum.unstructured
  }
];

const jsonRecordsOptions = [
  {
    value: JsonFileFormatSpecRecordsEnum.auto,
    label: JsonFileFormatSpecRecordsEnum.auto
  },
  {
    value: JsonFileFormatSpecRecordsEnum.false,
    label: JsonFileFormatSpecRecordsEnum.false
  },
  {
    value: JsonFileFormatSpecRecordsEnum.true,
    label: JsonFileFormatSpecRecordsEnum.true
  }
];

export default function JsonFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TJsonConfigurationProps) {
  const jsonConfiguration: TConfigurationItemRow[] = useMemo(() => {
    return [
      {
        label: 'Date format',
        value: configuration?.dateformat,
        onChange: (str) => onChangeConfiguration({ dateformat: str.toString() })
      },
      {
        label: 'Json format',
        value: configuration?.format,
        onChange: (str) =>
          onChangeConfiguration({
            format:
              String(str).length > 0
                ? (str as JsonFileFormatSpecFormatEnum)
                : undefined
          }),
        isEnum: true,
        options: [{ label: '', value: '' }, ...jsonFormatOptions],
        defaultValue: ''
      },
      {
        label: 'Maximum depth',
        value: configuration?.maximum_depth,
        onChange: (str) => {
          !isNaN(Number(str))
            ? onChangeConfiguration({ maximum_depth: Number(str) })
            : undefined;
        }
      },
      {
        label: 'Maximum object size (in bytes)',
        value: configuration?.maximum_object_size,
        onChange: (str) => {
          !isNaN(Number(str))
            ? onChangeConfiguration({ maximum_object_size: Number(str) })
            : undefined;
        }
      },
      {
        label: 'Records',
        value: configuration?.records,
        onChange: (str) =>
          onChangeConfiguration({
            records:
              String(str).length > 0
                ? (str as JsonFileFormatSpecRecordsEnum)
                : undefined
          }),
        isEnum: true,
        options: [{ label: '', value: '' }, ...jsonRecordsOptions],
        defaultValue: ''
      },
      {
        label: 'Sample size',
        value: configuration?.sample_size,
        onChange: (str) => {
          !isNaN(Number(str))
            ? onChangeConfiguration({ sample_size: Number(str) })
            : undefined;
        }
      },
      {
        label: 'Timestamp format',
        value: configuration?.timestampformat,
        onChange: (str) =>
          onChangeConfiguration({ timestampformat: str.toString() })
      }
    ];
  }, [configuration]);

  const jsonConfigurationBoolean: TConfigurationItemRowBoolean[] =
    useMemo(() => {
      return [
        {
          label: 'Convert strings to integers',
          value: configuration?.convert_strings_to_integers,
          onChange: (value) =>
            onChangeConfiguration({ convert_strings_to_integers: value })
        },
        {
          label: 'Filename',
          value: configuration?.filename,
          onChange: (value) => onChangeConfiguration({ filename: value })
        },
        {
          label: 'Hive partitioning',
          value: configuration?.hive_partitioning,
          onChange: (value) =>
            onChangeConfiguration({ hive_partitioning: value })
        },
        {
          label: 'Ignore errors',
          value: configuration?.ignore_errors,
          onChange: (value) => onChangeConfiguration({ ignore_errors: value })
        }
      ];
    }, [configuration]);
  //wrapper file format options
  return (
    <FormatConfigurationRenderer
      configurationStrings={jsonConfiguration}
      configurationBooleans={jsonConfigurationBoolean}
      type="JSON"
    >
      <ConfigurationItemRowCompression
          label='Compression'
          value={configuration?.compression + (configuration?.no_compression_extension===true ? "_no_ext" : "")}
          onChange={(str, no_compression_extension) => {
            onChangeConfiguration({
              compression:
                String(str).length > 0
                  ? (str as JsonFileFormatSpecCompressionEnum)
                  : undefined, 
              no_compression_extension: no_compression_extension
            });
          }}
          options={[{ label: '', value: '' }, ...compressionEnumOptions]}
        />
    </FormatConfigurationRenderer>
  );
}
