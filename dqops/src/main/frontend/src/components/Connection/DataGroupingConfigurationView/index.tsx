import React from 'react';
import {
  DataGroupingConfigurationSpec,
  DataGroupingDimensionSpec
} from '../../../api';
import DataGroupingDimensionItem from '../../DataQualityChecks/DataGroupingDimensionItem';
import { Errors } from '../TableView/DataGroupingConfigurationEditView';
import { useParams } from 'react-router-dom';
import clsx from 'clsx';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

interface IDataGroupingConfigurationViewProps {
  dataGroupingConfiguration?: DataGroupingConfigurationSpec;
  onChange: (value: DataGroupingConfigurationSpec) => void;
  errors?: Errors;
  onClearError?: (idx: number) => void;
}

const DataGroupingConfigurationView = ({
  dataGroupingConfiguration,
  onChange,
  errors,
  onClearError,
}: IDataGroupingConfigurationViewProps) => {
  const { table }: { table: string } = useParams();
  const {  userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const getDataGroupingDimensionLevel = (index: number) => {
    if (index === 0) return dataGroupingConfiguration?.level_1;
    if (index === 1) return dataGroupingConfiguration?.level_2;
    if (index === 2) return dataGroupingConfiguration?.level_3;
    if (index === 3) return dataGroupingConfiguration?.level_4;
    if (index === 4) return dataGroupingConfiguration?.level_5;
    if (index === 5) return dataGroupingConfiguration?.level_6;
    if (index === 6) return dataGroupingConfiguration?.level_7;
    if (index === 7) return dataGroupingConfiguration?.level_8;
    if (index === 8) return dataGroupingConfiguration?.level_9;
  };

  const onChangeDataGroupingDimensionLevel = (
    dataGroupingDimensionLevel: DataGroupingDimensionSpec,
    index: number
  ) => {
    onChange({
      ...(dataGroupingConfiguration || {}),
      [`level_${index + 1}`]: dataGroupingDimensionLevel
    });
  };

  return (
    <div className={clsx("py-4 px-4 text-sm", userProfile.can_manage_data_sources === true ? "" : "pointer-events-none cursor-not-allowed"  )}>
      {Array(9)
        .fill(0)
        .map((item, index) => (
          <DataGroupingDimensionItem
            idx={index}
            key={index}
            dataGroupingLevel={getDataGroupingDimensionLevel(index)}
            onChange={(dataGroupingDimensionLevel) =>
              onChangeDataGroupingDimensionLevel(
                dataGroupingDimensionLevel,
                index
              )
            }
            scope={table ? 'table' : 'connection'}
            error={errors ? errors[`level_${index + 1}`] : ''}
            onClearError={onClearError}
          />
        ))}
    </div>
  );
};

export default DataGroupingConfigurationView;
