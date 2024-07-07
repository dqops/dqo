import clsx from 'clsx';
import { values } from 'lodash';
import React, { ChangeEvent, useEffect, useState } from 'react';
import {
  DataGroupingConfigurationListModel,
  DataGroupingConfigurationSpec,
  DataGroupingDimensionSpecSourceEnum
} from '../../../api';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
import Button from '../../Button';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';
import DataGroupingConfigurationView from '../DataGroupingConfigurationView';
import ActionGroup from './TableActionGroup';

interface IDataGroupingConfigurationEditViewProps {
  onBack: () => void;
  selectedGroupingConfiguration?: DataGroupingConfigurationListModel;
  connection: string;
  schema: string;
  table: string;
  getGroupingConfigurations: () => void;
}

export interface Errors {
  [key: string]: string;
}

const DataGroupingConfigurationEditView = ({
  onBack,
  selectedGroupingConfiguration,
  connection,
  schema,
  table,
  getGroupingConfigurations
}: IDataGroupingConfigurationEditViewProps) => {
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);
  const [dataGroupingConfiguration, setDataGroupingConfiguration] =
    useState<DataGroupingConfigurationSpec>();
  const [name, setName] = useState('');
  const [error, setError] = useState('');
  const [levelErrors, setLevelErrors] = useState<Errors>({});
  const [isValid, setIsValid] = useState(false);
  useEffect(() => {
    if (
      selectedGroupingConfiguration &&
      selectedGroupingConfiguration.data_grouping_configuration_name
    ) {
      DataGroupingConfigurationsApi.getTableGroupingConfiguration(
        connection,
        schema,
        table,
        selectedGroupingConfiguration.data_grouping_configuration_name || ''
      ).then((res) => {
        setDataGroupingConfiguration(res.data.spec);
      });
    } else {
      setDataGroupingConfiguration({});
    }
  }, [selectedGroupingConfiguration]);

  const onUpdate = async () => {
    try {
      setIsUpdating(true);
      if (selectedGroupingConfiguration) {
        await DataGroupingConfigurationsApi.updateTableGroupingConfiguration(
          connection,
          schema,
          table,
          selectedGroupingConfiguration.data_grouping_configuration_name || '',
          {
            data_grouping_configuration_name: name,
            spec: dataGroupingConfiguration
          }
        );
      } else {
        if (name === '') {
          return;
        }

        if (!dataGroupingConfiguration) {
          setError('Grouping Configuration is Required');
          return;
        }
        const errors: Errors = {};

        Object.entries(dataGroupingConfiguration).forEach(([level, item]) => {
          if (
            item.source === DataGroupingDimensionSpecSourceEnum.tag &&
            !item.tag
          ) {
            errors[level] = 'Tag is Required';
          }
        });

        if (Object.values(errors).length) {
          setLevelErrors(errors);
          return;
        }

        await DataGroupingConfigurationsApi.createTableGroupingConfiguration(
          connection,
          schema,
          table,
          {
            data_grouping_configuration_name: name,
            spec: dataGroupingConfiguration
          }
        );
      }
      setIsUpdated(false);
      getGroupingConfigurations();
      onBack();
    } finally {
      setIsUpdating(false);
    }
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
    setIsUpdated(true);
    if (checkValidity(dataGroupingConfiguration) && e.target.value.length > 0) {
      setIsValid(true);
    } else {
      setIsValid(false);
    }
  };

  const onChangeDataGroupingConfiguration = (
    spec: DataGroupingConfigurationSpec
  ) => {
    if (name || selectedGroupingConfiguration) {
      setIsUpdated(true);
    }
    if (checkValidity(spec) && name.length > 0) {
      setIsValid(true);
    } else {
      setIsValid(false);
    }
    setDataGroupingConfiguration(spec);
  };

  const onClearError = (idx: number) => {
    setLevelErrors({
      ...levelErrors,
      [`level_${idx}`]: ''
    });
  };

  const checkValidity = (spec: DataGroupingConfigurationSpec | undefined) => {
    let isValid = true;

    values(spec).forEach((dataGroupingLevel) => {
      const isTagEmpty =
        dataGroupingLevel?.source === DataGroupingDimensionSpecSourceEnum.tag &&
        (!dataGroupingLevel.tag || dataGroupingLevel.tag.length === 0);

      const isColumnEmpty =
        dataGroupingLevel?.source ===
          DataGroupingDimensionSpecSourceEnum.column_value &&
        (!dataGroupingLevel.column || dataGroupingLevel.column.length === 0);

      if (isTagEmpty || isColumnEmpty) {
        isValid = false;
      }
    });
    return isValid;
  };

  return (
    <div className="px-4 text-sm">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
        isDisabled={isValid ? false : true}
      />
      <div className="flex py-4 border-b border-gray-300 px-8 -mx-4 justify-between items-center">
        {selectedGroupingConfiguration ? (
          <div>
            {selectedGroupingConfiguration?.data_grouping_configuration_name}
          </div>
        ) : (
          <div className="flex space-x-4 items-center">
            <div>Data grouping configuration name</div>
            <Input
              className={clsx(
                'w-80',
                name.length > 0 ? '' : 'border border-red-500'
              )}
              value={name}
              onChange={onChangeName}
            />
          </div>
        )}
        <Button
          label="Back"
          color="primary"
          variant="text"
          className="px-0"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
      </div>
      {error && <div className="text-red-700 text-xs pt-4 px-6">{error}</div>}
      <DataGroupingConfigurationView
        dataGroupingConfiguration={dataGroupingConfiguration}
        onChange={onChangeDataGroupingConfiguration}
        errors={levelErrors}
        onClearError={onClearError}
      />
    </div>
  );
};

export default DataGroupingConfigurationEditView;
