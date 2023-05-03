import React, { useState } from 'react';

import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import { SensorBasicFolderModel } from "../../api";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { addFirstLevelTab } from "../../redux/actions/sensor.actions";
import { ROUTES } from "../../shared/routes";

interface SensorContextMenuProps {
  sensor?: SensorBasicFolderModel;
  path?: string[];
}

const SensorContextMenu = ({ sensor, path }: SensorContextMenuProps) => {
  const [open, setOpen] = useState(false);
  const dispatch = useActionDispatch();

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  const openAddNewSensor = () => {
    console.log('path', path);
    dispatch(addFirstLevelTab({
      url: ROUTES.SENSOR_DETAIL([...path || [], "new_sensor"].join("-")),
      value: ROUTES.SENSOR_DETAIL_VALUE([...path || [], "new_sensor"].join("-")),
      state: {
        type: "create",
        path
      },
      label: "New sensor"
    }));
  };

  return (
    <Popover placement="bottom-end" open={open} handler={setOpen}>
      <PopoverHandler onClick={openPopover}>
        <div className="text-gray-700 !absolute right-0 w-7 h-7 rounded-full flex items-center justify-center bg-white">
          <SvgIcon name="options" className="w-5 h-5 text-gray-500" />
        </div>
      </PopoverHandler>
      <PopoverContent className="z-50 min-w-50 max-w-50 border-gray-500 p-2">
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
          >
            Add new folder
          </div>
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default SensorContextMenu;
