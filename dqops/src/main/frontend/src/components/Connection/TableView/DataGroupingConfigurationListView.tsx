import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { DataGroupingConfigurationListModel } from '../../../api';
import { IRootState } from '../../../redux/reducers';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
import Button from '../../Button';
import ConfirmDialog from '../../CustomTree/ConfirmDialog';
import RadioButton from '../../RadioButton';
import SvgIcon from '../../SvgIcon';
import SetDefaultDialog from './SetDefaultDialog';

interface IDataGroupingConfigurationListViewProps {
  dataGroupingConfigurations: DataGroupingConfigurationListModel[];
  getDataGroupingConfigurations: () => void;
  onCreate: () => void;
  onEdit: (groupingConfiguration: DataGroupingConfigurationListModel) => void;
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
    useState<DataGroupingConfigurationListModel>();
  const [messageBox, setMessageBox] = useState<boolean>(false);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const setDefaultGroupingConfiguration = async (
    groupingConfiguration: DataGroupingConfigurationListModel,
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
    groupingConfiguration?: DataGroupingConfigurationListModel
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
    groupingConfiguration: DataGroupingConfigurationListModel
  ) => {
    setOpen(true);
    setSelectedGroupingConfiguration(groupingConfiguration);
  };

  const openConfirmDefaultModal = (
    groupingConfiguration: DataGroupingConfigurationListModel
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

  const elem: DataGroupingConfigurationListModel | undefined =
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
        <thead className="relative">
          <tr className="flex py-2">
            <th>Data grouping configuration name</th>
            {dataGroupingConfigurations.length !== 0 && (
              <th className="absolute right-22.5">Action</th>
            )}
          </tr>
        </thead>
        <tbody>
          <div className="pr-2 py-2 relative flex items-center gap-2  ">
            <div
              className={clsx(
                'w-5 h-5',
                userProfile.can_manage_data_sources !== true
                  ? 'pointer-events-none cursor-not-allowed'
                  : ''
              )}
            >
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
                <div
                  className={clsx(
                    'w-5 h-5',
                    userProfile.can_manage_data_sources !== true
                      ? 'pointer-events-none cursor-not-allowed'
                      : ''
                  )}
                >
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
                <span>
                  {groupingConfiguration.data_grouping_configuration_name}
                </span>
              </td>

              <td className="px-20 py-2 ">
                <Tooltip
                  content={
                    userProfile.can_manage_data_sources !== true
                      ? 'Info'
                      : 'Modify'
                  }
                >
                  <IconButton
                    ripple={false}
                    size="sm"
                    className="group bg-teal-500 ml-3 !shadow-none hover:!shadow-none hover:bg-[#028770]"
                    onClick={() => onEdit(groupingConfiguration)}
                  >
                    <SvgIcon
                      name={
                        userProfile.can_manage_data_sources !== true
                          ? 'info'
                          : 'edit'
                      }
                      className="w-4"
                    />
                  </IconButton>
                </Tooltip>
                <Tooltip content="Delete">
                  <IconButton
                    ripple={false}
                    size="sm"
                    className="group bg-teal-500 ml-3 !shadow-none hover:!shadow-none hover:bg-[#028770]"
                    onClick={() => {
                      openConfirmDeleteModal(groupingConfiguration);
                    }}
                    disabled={userProfile.can_manage_data_sources !== true}
                  >
                    <SvgIcon name="delete" className="w-4" />
                  </IconButton>
                </Tooltip>
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
        disabled={userProfile.can_manage_data_sources !== true}
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
          'Data grouping is an advanced functionality of DQOps that requires planning. DQOps will add a GROUP BY clause to every data quality check query, generating a lot of data quality results. The number of rows returned by a GROUP BY clause in SQL will increase the number of data quality check results tracked by DQOps and will impact data quality KPIs.'
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
