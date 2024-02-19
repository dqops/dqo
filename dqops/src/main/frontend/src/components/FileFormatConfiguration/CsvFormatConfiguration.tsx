import React, { useMemo } from 'react';
import { CsvFileFormatSpec } from '../../api';
import ConfigurationItemRow from './ConfigurationItemRow';

type TCsvConfigurationProps = {
  configuration: CsvFileFormatSpec;
  onChangeConfiguration: (params: Partial<CsvFileFormatSpec>) => void;
};
type TConfigurationItemRow = {
  label: string;
  value?: string | number;
  onChange: (str: string) => void;
};

// todo: set input fields as in duckdb docs defaults to csv

export default function CsvFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TCsvConfigurationProps) {
  const csvConfiguration: TConfigurationItemRow[] = useMemo(() => {
    return [
      // {
      //   label: 'Treat all columns as varchar',
      //   value: configuration.all_varchar,
      //   onChange: (str) => onChangeConfiguration({ all_varchar: str })
      // },
      // {
      //   label: 'Allow quoted nulls',
      //   value: configuration.allow_quoted_nulls,
      //   onChange: (str) => onChangeConfiguration({ allow_quoted_nulls: str })
      // },
      // {
      //   label: 'Compression',
      //   value: configuration.compression,
      //   onChange: (str) => onChangeConfiguration({ compression: str })
      // },
      // {
      //   label: 'Date format',
      //   value: configuration.dateformat,
      //   onChange: (str) => onChangeConfiguration({ dateformat: str })
      // },
      // {
      //   label: 'Decimal Separator',
      //   value: configuration.decimal_separator,
      //   onChange: (str) => onChangeConfiguration({ decimal_separator: str })
      // },
      // {
      //   label: 'Delimiter',
      //   value: configuration.delim,
      //   onChange: (str) => onChangeConfiguration({ delim: str })
      // },
      // {
      //   label: 'Escape Character/String',
      //   value: configuration.escape,
      //   onChange: (str) => onChangeConfiguration({ escape: str })
      // },
      // filename
      // header
      // hivePartitioning
      // ignoreErrors
      // {
      //   label: 'New Line',
      //   value: configuration.new_line,
      //   onChange: (str) => onChangeConfiguration({ new_line: str })
      // },
      // {
      //   label: 'Quote',
      //   value: configuration.quote,
      //   onChange: (str) => onChangeConfiguration({ quote: str })
      // },
      // {
      //   label: 'Skip',
      //   value: configuration.skip,
      //   onChange: (str) => {
      //     !isNaN(Number(str))
      //       ? onChangeConfiguration({ skip: Number(str) })
      //       : undefined;
      //   }
      // },
      // {
      //   label: 'Timestamp Format',
      //   value: configuration.timestampformat,
      //   onChange: (str) => onChangeConfiguration({ timestampformat: str })
      // }
    ];
  }, [configuration]);

  return (
    <div
      style={{
        display: 'grid',
        gridTemplateColumns: '1fr 1fr 1fr',
        gap: '16px'
      }}
    >
      {csvConfiguration.map((x, index) => (
        <ConfigurationItemRow
          key={index}
          label={x.label}
          value={x.value}
          onChange={x.onChange}
        />
      ))}
    </div>
  );
}
