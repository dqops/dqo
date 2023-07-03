import React, { useState } from 'react';
import { DataGroupingConfigurationBasicModel } from '../../../api';
import Button from '../../Button';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
import ConfirmDialog from '../../CustomTree/ConfirmDialog';
import SvgIcon from '../../SvgIcon';

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
  const [selectedGroupingConfiguration, setSelectedGroupingConfiguration] =
    useState<DataGroupingConfigurationBasicModel>();
  const setDefaultGroupingConfiguration = async (
    groupingConfiguration: DataGroupingConfigurationBasicModel
  ) => {
    try {
      await DataGroupingConfigurationsApi.setTableDefaultGroupingConfiguration(
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

  return (
    <div className="px-8 py-4">
      <table className="mb-4">
        <thead>
          <tr className="flex px-2">
            <th className="w-5 h-5"></th>
            <th>Data grouping configuration name</th>
          </tr>
        </thead>
        <tbody>
          {dataGroupingConfigurations.map((groupingConfiguration, index) => (
            <tr key={index}>
              <td className="pr-2 py-2 relative flex items-center gap-2">
                {groupingConfiguration.default_data_grouping_configuration ===
                true ? (
                  <div className="w-5 h-5 bg-primary rounded flex items-center justify-center">
                    <SvgIcon name="check" className="text-white" />
                  </div>
                ) : (
                  <div className="w-5 h-5"></div>
                )}
                <span>
                  {groupingConfiguration.data_grouping_configuration_name}
                </span>
              </td>
              <td className="px-2 py-2">
                <Button
                  label="Edit"
                  color="primary"
                  variant="text"
                  className="!py-0"
                  onClick={() => onEdit(groupingConfiguration)}
                />
              </td>
              <td className="px-2 py-2">
                <Button
                  label="Delete"
                  color="primary"
                  variant="text"
                  className="!py-0"
                  onClick={() => openConfirmDeleteModal(groupingConfiguration)}
                />
              </td>
              <td className="px-2 py-2">
                {!groupingConfiguration.default_data_grouping_configuration ? (
                  <Button
                    label="Make Default"
                    color="primary"
                    variant="text"
                    className="!py-0"
                    onClick={() =>
                      setDefaultGroupingConfiguration(groupingConfiguration)
                    }
                  />
                ) : (
                  ''
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <Button
        label="New data grouping configuration"
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
    </div>
  );
};

export default DataGroupingConfigurationListView;
