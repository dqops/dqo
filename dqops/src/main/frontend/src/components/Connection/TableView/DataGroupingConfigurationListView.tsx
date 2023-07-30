import React, { useState, useEffect } from 'react';
import { DataGroupingConfigurationBasicModel } from '../../../api';
import Button from '../../Button';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
import ConfirmDialog from '../../CustomTree/ConfirmDialog';
import RadioButton from '../../RadioButton';
import SetDefaultDialog from './SetDefaultDialog';
import SvgIcon from '../../SvgIcon';
import { IconButton } from '@material-tailwind/react';

interface IDataGroupingConfigurationListViewProps {
  dataGroupingConfigurations: DataGroupingConfigurationBasicModel[];
  getDataGroupingConfigurations: () => void;
  onCreate: () => void;
  onEdit: (groupingConfiguration: DataGroupingConfigurationBasicModel) => void;
}

const DataGroupingConfigurationListView = ({
  dataGroupingConfigurations,
  getDataGroupingConfigurations,
  onCreate,
  onEdit
}: IDataGroupingConfigurationListViewProps) => {
  const [open, setOpen] = useState(false);
  const [defaultOpen, setDefaultOpen] = useState(false);
  const [selectedGroupingConfiguration, setSelectedGroupingConfiguration] =
    useState<DataGroupingConfigurationBasicModel>();
  const [messageBox, setMessageBox] = useState<boolean>(false);
  const setDefaultGroupingConfiguration = async (
    groupingConfiguration: DataGroupingConfigurationBasicModel,
    nameOfGrouping?: string
  ) => {
    try {
      await DataGroupingConfigurationsApi.setTableDefaultGroupingConfiguration(
        groupingConfiguration.connection_name || '',
        groupingConfiguration.schema_name || '',
        groupingConfiguration.table_name || '',
        nameOfGrouping || ''
      );
      getDataGroupingConfigurations();
    } catch (err) {
      console.error(err);
    }
  };

  const deleteGroupingConfiguration = async (
    groupingConfiguration?: DataGroupingConfigurationBasicModel
  ) => {
    if (!groupingConfiguration) {
      return;
    }

    try {
      await DataGroupingConfigurationsApi.deleteTableGroupingConfiguration(
        groupingConfiguration.connection_name || '',
        groupingConfiguration.schema_name || '',
        groupingConfiguration.table_name || '',
        groupingConfiguration.data_grouping_configuration_name || ''
      );
      getDataGroupingConfigurations();
    } catch (err) {
      console.error(err);
    }
  };

  const openConfirmDeleteModal = (
    groupingConfiguration: DataGroupingConfigurationBasicModel
  ) => {
    setOpen(true);
    setSelectedGroupingConfiguration(groupingConfiguration);
  };

  const openConfirmDefaultModal = (
    groupingConfiguration: DataGroupingConfigurationBasicModel
  ) => {
    setDefaultOpen(true);
    setSelectedGroupingConfiguration(groupingConfiguration);
  };

  useEffect(() => {
    if (isDisableDataGrouping() === true) {
      setMessageBox(true);
    } else {
      setMessageBox(false);
    }
  }, [dataGroupingConfigurations]);

  const elem: DataGroupingConfigurationBasicModel | undefined =
    dataGroupingConfigurations.find(
      (x) => x.default_data_grouping_configuration === true
    );

  const isDisableDataGrouping = () => {
    return dataGroupingConfigurations.find(
      (x) =>
        x.default_data_grouping_configuration === true &&
        x.data_grouping_configuration_name !== ''
    )
      ? false
      : true;
  };

  return (
    <div className="px-8 py-4 text-sm">
      <table className="mb-4">
        <thead>
          <tr className="flex px-2 py-2">
            <th className="w-5 h-5"></th>
            <th>Data grouping configuration name</th>
          </tr>
        </thead>
        <tbody>
          <div className="pr-2 py-2 relative flex items-center gap-2  ">
            <div className="w-5 h-5">
              {' '}
              <RadioButton
                checked={
                  !dataGroupingConfigurations.find(
                    (x) => x.default_data_grouping_configuration === true
                  )
                }
                onClick={() =>
                  elem ? setDefaultGroupingConfiguration(elem) : undefined
                }
              />
            </div>
            <div>Disable data grouping</div>
          </div>
          {dataGroupingConfigurations.map((groupingConfiguration, index) => (
            <tr key={index}>
              <td className="pr-2 py-2 relative flex items-center gap-2">
                {/* {groupingConfiguration.default_data_grouping_configuration ===
                true ? (
                  <div className="w-5 h-5 bg-primary rounded flex items-center justify-center">
                    <SvgIcon name="check" className="text-white" />
                  </div>
                ) : (
                  <div className="w-5 h-5"></div>
                )} */}
                <div className="w-5 h-5">
                  <RadioButton
                    checked={
                      groupingConfiguration.default_data_grouping_configuration
                    }
                    onClick={() =>
                      messageBox
                        ? openConfirmDefaultModal(groupingConfiguration)
                        : setDefaultGroupingConfiguration(
                            groupingConfiguration,
                            groupingConfiguration.data_grouping_configuration_name
                          )
                    }
                  />
                </div>
                <span
                  onClick={() => onEdit(groupingConfiguration)}
                  className="cursor-pointer underline"
                >
                  {groupingConfiguration.data_grouping_configuration_name}
                </span>
              </td>

              <td className="px-2 py-2">
                <IconButton
                  size="sm"
                  className="group bg-teal-500 ml-3"
                  onClick={() => {
                    openConfirmDeleteModal(groupingConfiguration);
                  }}
                >
                  <SvgIcon name="delete" className="w-4" />
                </IconButton>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <Button
        label="New data grouping configuration"
        className="text-sm"
        color="primary"
        onClick={onCreate}
      />
      <ConfirmDialog
        open={open}
        onClose={() => setOpen(false)}
        message={`Are you sure to delete the data grouping configuration ${selectedGroupingConfiguration?.data_grouping_configuration_name}?`}
        onConfirm={() =>
          deleteGroupingConfiguration(selectedGroupingConfiguration)
        }
      />
      <SetDefaultDialog
        open={defaultOpen}
        onClose={() => setDefaultOpen(false)}
        message={
          'Data grouping is an advanced functionality of DQO that requires planning. DQO will add a GROUP BY clause to every data quality check query, generating a lot of data quality results. The number of rows returned by a GROUP BY clause in SQL will increase the number of data quality check results tracked by DQO and will impact data quality KPIs.'
        }
        onConfirm={() =>
          setDefaultGroupingConfiguration(
            selectedGroupingConfiguration ?? {},
            selectedGroupingConfiguration &&
              selectedGroupingConfiguration.data_grouping_configuration_name
          )
        }
      />
    </div>
  );
};

export default DataGroupingConfigurationListView;
