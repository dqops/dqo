import React, { useMemo } from 'react';
import {
  CsvFileFormatSpec,
  CsvFileFormatSpecCompressionEnum
} from '../../api';
import { TConfigurationItemRow } from './RowItem/TConfigurationItemRow'
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean'
import FormatConfigurationRenderer from './FormatConfigurationRenderer'

type TCsvConfigurationProps = {
  configuration: CsvFileFormatSpec;
  onChangeConfiguration: (params: Partial<CsvFileFormatSpec>) => void;
};

export default function CsvFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TCsvConfigurationProps) {
  
  const csvConfiguration: TConfigurationItemRow[] = useMemo(() => {
    return [
      { // todo: this is an enum, the field should be a combo box
        label: 'Compression',
        value: configuration.compression,
        onChange: (str) => onChangeConfiguration({ compression: str as keyof typeof CsvFileFormatSpecCompressionEnum }),
        defaultValue: CsvFileFormatSpecCompressionEnum.auto
      },
      {
        label: 'Date format',
        value: configuration?.dateformat,
        onChange: (str) => onChangeConfiguration({ dateformat: str.toString() }),
        defaultValue: ''
      },
      {
        label: 'Decimal Separator',
        value: configuration.decimal_separator,
        onChange: (str) => onChangeConfiguration({ decimal_separator: str.toString() }),
        defaultValue: '.'
      },
      {
        label: 'Delimiter',
        value: configuration.delim,
        onChange: (str) => onChangeConfiguration({ delim: str.toString() }),
        defaultValue: ','
      },
      {
        label: 'Escape Character/String',
        value: configuration.escape,
        onChange: (str) => onChangeConfiguration({ escape: str.toString() }),
        defaultValue: '"'
      },
      {
        label: 'New line',
        value: configuration?.new_line,
        onChange: (str) => onChangeConfiguration({ new_line: str.toString() }),
        defaultValue: ''
      },
      {
        label: 'Quote',
        value: configuration.quote,
        onChange: (str) => onChangeConfiguration({ quote: str.toString() }),
        defaultValue: '"'
      },
      {
        label: 'Sample size',
        value: configuration.sample_size,
        onChange: (str) => { !isNaN(Number(str)) ? onChangeConfiguration({ sample_size: Number(str) }) : undefined },
        defaultValue: 20480
      },
      {
        label: 'Skip',
        value: configuration.skip,
        onChange: (str) => { !isNaN(Number(str)) ? onChangeConfiguration({ skip: Number(str) }) : undefined },
        defaultValue: 0
      },
      {
        label: 'Timestamp Format',
        value: configuration.timestampformat,
        onChange: (str) => onChangeConfiguration({ timestampformat: str.toString() }),
        defaultValue: ''
      }
    ];
  }, [configuration]);

  const csvConfigurationBoolean: TConfigurationItemRowBoolean[] = useMemo(() => {
    return [
      {
        label: 'Treat all columns as varchar',
        value: configuration.all_varchar,
        onChange: (value) => onChangeConfiguration({ all_varchar: value }),
        defaultValue: false
      },
      {
        label: 'Allow quoted nulls',
        value: configuration.allow_quoted_nulls,
        onChange: (value) => onChangeConfiguration({ allow_quoted_nulls: value }),
        defaultValue: true
      },
      {
        label: 'Filename',
        value: configuration.filename,
        onChange: (value) => onChangeConfiguration({ filename: value }),
        defaultValue: false
      },
      {
        label: 'Header',
        value: configuration.header,
        onChange: (value) => onChangeConfiguration({ header: value }),
        defaultValue: false
      },
      {
        label: 'Hive partitioning',
        value: configuration.hive_partitioning,
        onChange: (value) => onChangeConfiguration({ hive_partitioning: value }),
        defaultValue: false
      },
      {
        label: 'Ignore errors',
        value: configuration.ignore_errors,
        onChange: (value) => onChangeConfiguration({ ignore_errors: value }),
        defaultValue: false
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
