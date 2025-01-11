import React, { useMemo } from 'react';
import { AvroFileFormatSpec } from '../../../api';
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean';
import FormatConfigurationRenderer from '../FormatConfigurationRenderer';

type TIcebergConfigurationProps = {
  configuration: AvroFileFormatSpec;
  onChangeConfiguration: (params: Partial<AvroFileFormatSpec>) => void;
};

export default function IcebergFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TIcebergConfigurationProps) {

  const avroConfigurationBooleans: TConfigurationItemRowBoolean[] =
    useMemo(() => {
      return [
        {
          label: 'Filename',
          value: configuration?.filename,
          onChange: (value) =>
            onChangeConfiguration({ filename: value })
        }
      ];
    }, [configuration]);

  return (
    <FormatConfigurationRenderer
      configurationStrings={[]}
      configurationBooleans={avroConfigurationBooleans}
      type="Avro"
    />
  );
}
