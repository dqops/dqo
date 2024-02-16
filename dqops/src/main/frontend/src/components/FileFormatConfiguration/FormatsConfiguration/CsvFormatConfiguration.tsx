import React, { useMemo } from 'react';
import {
  CsvFileFormatSpec,
  CsvFileFormatSpecCompressionEnum
} from '../../../api';
import { TConfigurationItemRow } from './RowItem/TConfigurationItemRow'
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean'
import FormatConfigurationRenderer from '../FormatConfigurationRenderer'

type TCsvConfigurationProps = {
  configuration: CsvFileFormatSpec;
  onChangeConfiguration: (params: Partial<CsvFileFormatSpec>) => void;
};

const compressionEnumOptions = [
  {
    value: CsvFileFormatSpecCompressionEnum.auto,
    label: CsvFileFormatSpecCompressionEnum.auto
  },
  {
    value: CsvFileFormatSpecCompressionEnum.gzip,
    label: CsvFileFormatSpecCompressionEnum.gzip
  },
  {
    value: CsvFileFormatSpecCompressionEnum.none,
    label: CsvFileFormatSpecCompressionEnum.none
  },
  {
    value: CsvFileFormatSpecCompressionEnum.zstd,
    label: CsvFileFormatSpecCompressionEnum.zstd
  },
]

export default function CsvFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TCsvConfigurationProps) {

  const csvConfiguration: TConfigurationItemRow[] = useMemo(() => {
    return [
      {
        label: 'Compression',
        value: configuration?.compression,
        onChange: (str) => {
          onChangeConfiguration({ compression: str as CsvFileFormatSpecCompressionEnum })
        },
        isEnum: true,
        options: compressionEnumOptions
      },
      {
        label: 'Date format',
        value: configuration?.dateformat,
        onChange: (str) => onChangeConfiguration({ dateformat: str.toString() }),
      },
      {
        label: 'Decimal Separator',
        value: configuration?.decimal_separator,
        onChange: (str) => onChangeConfiguration({ decimal_separator: str.toString() }),
      },
      {
        label: 'Delimiter',
        value: configuration?.delim,
        onChange: (str) => onChangeConfiguration({ delim: str.toString() }),
      },
      {
        label: 'Escape Character/String',
        value: configuration?.escape,
        onChange: (str) => onChangeConfiguration({ escape: str.toString() }),
      },
      {
        label: 'New line',
        value: configuration?.new_line,
        onChange: (str) => onChangeConfiguration({ new_line: str.toString() }),
      },
      {
        label: 'Quote',
        value: configuration?.quote,
        onChange: (str) => onChangeConfiguration({ quote: str.toString() }),
      },
      {
        label: 'Sample size',
        value: configuration?.sample_size,
        onChange: (str) => { !isNaN(Number(str)) ? onChangeConfiguration({ sample_size: Number(str) }) : undefined },
      },
      {
        label: 'Skip',
        value: configuration?.skip,
        onChange: (str) => { !isNaN(Number(str)) ? onChangeConfiguration({ skip: Number(str) }) : undefined },
      },
      {
        label: 'Timestamp Format',
        value: configuration?.timestampformat,
        onChange: (str) => onChangeConfiguration({ timestampformat: str.toString() }),
      }
    ];
  }, [configuration]);

  const csvConfigurationBoolean: TConfigurationItemRowBoolean[] = useMemo(() => {
    return [
      {
        label: 'Treat all columns as varchar',
        value: configuration?.all_varchar,
        onChange: (value) => onChangeConfiguration({ all_varchar: value }),
      },
      {
        label: 'Allow quoted nulls',
        value: configuration?.allow_quoted_nulls,
        onChange: (value) => onChangeConfiguration({ allow_quoted_nulls: value }),
        defaultValue: true
      },
      {
        label: 'Filename',
        value: configuration?.filename,
        onChange: (value) => onChangeConfiguration({ filename: value }),
      },
      {
        label: 'Header',
        value: configuration?.header,
        onChange: (value) => onChangeConfiguration({ header: value }),
      },
      {
        label: 'Hive partitioning',
        value: configuration?.hive_partitioning,
        onChange: (value) => onChangeConfiguration({ hive_partitioning: value }),
      },
      {
        label: 'Ignore errors',
        value: configuration?.ignore_errors,
        onChange: (value) => onChangeConfiguration({ ignore_errors: value }),
      }
    ];
  }, [configuration]);

  return (
    <FormatConfigurationRenderer 
      configuraitonStrings={csvConfiguration}
      configurationBooleans={csvConfigurationBoolean}
    />
  );
}
