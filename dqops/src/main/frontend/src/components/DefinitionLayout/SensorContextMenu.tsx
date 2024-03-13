import React, { useState } from 'react';

import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import { useSelector } from 'react-redux';
import { SensorFolderModel, SensorListModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab, refreshSensorsFolderTree } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { SensorsApi } from '../../services/apiClient';
import { ROUTES } from '../../shared/routes';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import SvgIcon from '../SvgIcon';
import AddFolderDialog from './AddFolderDialog';

interface SensorContextMenuProps {
  folder?: SensorFolderModel;
  path?: string[];
  singleSensor?: boolean;
  sensor?: SensorListModel;
}

const SensorContextMenu = ({
  folder,
  path,
  singleSensor,
  sensor,
}: SensorContextMenuProps) => {
  const [open, setOpen] = useState(false);
  const dispatch = useActionDispatch();
  const [isOpen, setIsOpen] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const {
    refreshSensorsTreeIndicator 
  } = useSelector((state: IRootState) => state.definition);

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  const openAddNewSensor = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SENSOR_DETAIL([...(path || []), 'new_sensor'].join('-')),
        value: ROUTES.SENSOR_DETAIL_VALUE(
          [...(path || []), 'new_sensor'].join('-')
        ),
        state: {
          type: 'create',
          path
        },
        label: 'New sensor'
      })
    );
  };

  const openAddNewFolder = () => {
    setIsOpen(true);
  };

  const closeModal = () => {
    setIsOpen(false);
    setOpen(false);
  };

  const onCopy = (): void => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SENSOR_DETAIL(sensor?.sensor_name + '_copy' ?? ''),
        value: ROUTES.SENSOR_DETAIL_VALUE(sensor?.sensor_name + '_copy' ?? ''),
        state: {
          full_sensor_name: sensor?.full_sensor_name,
          copied: true,
          path: path,
          type: 'create'
        },
        label: `${
          String(sensor?.full_sensor_name).split('/')[
            String(sensor?.full_sensor_name).split('/').length - 1
          ]
        }_copy`
      })
    );
  };

  const deleteSensorFromTree = async () => {
    await SensorsApi.deleteSensor(sensor?.full_sensor_name ?? '').then(
      () => dispatch(refreshSensorsFolderTree(refreshSensorsTreeIndicator ? false : true))
    );
  };

  return (
    <Popover placement="bottom-end" open={open} handler={setOpen}>
      <PopoverHandler onClick={openPopover}>
        <div className="text-gray-700 !absolute right-0 w-7 h-7 rounded-full flex items-center justify-center bg-white">
          <SvgIcon name="options" className="w-5 h-5 text-gray-500" />
        </div>
      </PopoverHandler>
      <PopoverContent className="z-50 min-w-50 max-w-50 border-gray-500 p-2">
        {singleSensor === true ? (
          <div onClick={(e) => e.stopPropagation()}>
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={onCopy}
            >
              Copy sensor
            </div>
            {sensor?.built_in === false ? (
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() => setDeleteDialogOpen(true)}
              >
                Delete sensor
              </div>
            ) : null}
            <ConfirmDialog
              open={deleteDialogOpen}
              onClose={() => setDeleteDialogOpen(false)}
              onConfirm={deleteSensorFromTree}
              message={`Are you sure you want to delete the sensor ${sensor?.full_sensor_name}`}
            />
          </div>
        ) : (
          <div>
            <div onClick={(e) => e.stopPropagation()}>
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={openAddNewSensor}
              >
                Add new sensor
              </div>
            </div>
            <div onClick={(e) => e.stopPropagation()}>
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={openAddNewFolder}
              >
                Add new folder
              </div>
              <AddFolderDialog
                open={isOpen}
                onClose={closeModal}
                path={path}
                folder={folder}
              />
            </div>
          </div>
        )}
      </PopoverContent>
    </Popover>
  );
};

export default SensorContextMenu;
