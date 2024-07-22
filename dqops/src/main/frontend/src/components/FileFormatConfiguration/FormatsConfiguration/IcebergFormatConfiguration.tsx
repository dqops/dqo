import React, { useMemo } from 'react';
import { IcebergFileFormatSpec } from '../../../api';
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean';
import FormatConfigurationRenderer from '../FormatConfigurationRenderer';
import ConfigurationItemRowCompression from './RowItem/ConfigurationItemRowCompression';

type TIcebergConfigurationProps = {
  configuration: IcebergFileFormatSpec;
  onChangeConfiguration: (params: Partial<IcebergFileFormatSpec>) => void;
};

export default function IcebergFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TIcebergConfigurationProps) {

  const icebergConfigurationBooleans: TConfigurationItemRowBoolean[] =
    useMemo(() => {
      return [
        {
          label: 'Allow moved paths',
          value: configuration?.allow_moved_paths,
          onChange: (value) =>
            onChangeConfiguration({ allow_moved_paths: value })
        }
      ];
    }, [configuration]);

  return (
    <FormatConfigurationRenderer
      configurationStrings={[]}
      configurationBooleans={icebergConfigurationBooleans}
      type="Iceberg"
    />
  );
}
