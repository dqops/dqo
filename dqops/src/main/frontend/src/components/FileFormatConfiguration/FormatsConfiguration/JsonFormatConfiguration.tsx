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

type TJsonConfigurationProps = {
  configuration: JsonFileFormatSpec;
  onChangeConfiguration: (params: Partial<JsonFileFormatSpec>) => void;
};

const compressionEnumOptions = [
  {
    value: JsonFileFormatSpecCompressionEnum.auto,
    label: JsonFileFormatSpecCompressionEnum.auto
  },
  {
    value: JsonFileFormatSpecCompressionEnum.gzip,
    label: JsonFileFormatSpecCompressionEnum.gzip
  },
  {
    value: JsonFileFormatSpecCompressionEnum.none,
    label: JsonFileFormatSpecCompressionEnum.none
  },
  {
    value: JsonFileFormatSpecCompressionEnum.zstd,
    label: JsonFileFormatSpecCompressionEnum.zstd
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
        label: 'Compression',
        value: configuration?.compression,
        onChange: (str) => {
          onChangeConfiguration({
            compression:
              String(str).length > 0
                ? (str as JsonFileFormatSpecCompressionEnum)
                : undefined
          });
        },
        isEnum: true,
        options: [{ label: '', value: '' }, ...compressionEnumOptions],
        defaultValue: ''
      },
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
        label: 'Timestamp Format',
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
            onChangeConfiguration({ convert_strings_to_integers: value }),
          defaultValue: false
        },
        {
          label: 'Filename',
          value: configuration?.filename,
          onChange: (value) => onChangeConfiguration({ filename: value }),
          defaultValue: false
        },
        {
          label: 'Hive partitioning',
          value: configuration?.hive_partitioning,
          onChange: (value) =>
            onChangeConfiguration({ hive_partitioning: value }),
          defaultValue: false
        },
        {
          label: 'Ignore errors',
          value: configuration?.ignore_errors,
          onChange: (value) => onChangeConfiguration({ ignore_errors: value }),
          defaultValue: false
        }
      ];
    }, [configuration]);
  //wrapper file format options
  return (
    <FormatConfigurationRenderer
      configuraitonStrings={jsonConfiguration}
      configurationBooleans={jsonConfigurationBoolean}
      type="Json"
    />
  );
}
