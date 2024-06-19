import { AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { DqoSettingsModel } from '../../api';
import { EnviromentApiClient } from '../../services/apiClient';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { toggleSettings } from '../../redux/actions/job.actions';
import {
  Popover,
  PopoverHandler,
  PopoverContent
} from '@material-tailwind/react';

function SettingsPopUp() {
  const { areSettingsOpen } = useSelector((state: IRootState) => state.job || {});

  const dispatch = useActionDispatch();

  const toggleOpen = () => {
    dispatch(toggleSettings(!areSettingsOpen));
  };

  const [profileSettings, setProfileSettings] = useState<DqoSettingsModel>();

  const fetchProfileSettings = async () => {
    try {
      const res: AxiosResponse<DqoSettingsModel> =
        await EnviromentApiClient.getDqoSettings();
      setProfileSettings(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchProfileSettings().then();
  }, []);

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  const objectElements = Object.entries(
    profileSettings?.properties ? profileSettings.properties : {}
  ).map(([key, value]) => (
    <div key={key} className="flex">
      <div className=" w-2/5 mr-5 whitespace-normal">{key} </div>
      <div className=" w-3/5 whitespace-normal">{renderValue(value)}</div>
    </div>
  ));

  return (
    <Popover open={areSettingsOpen} handler={toggleOpen} placement="top-start">
      <PopoverHandler>
        <div className="text-black h-6 cursor-pointer">Settings</div>
      </PopoverHandler>
      <PopoverContent className="bg-white min-w-1/3 h-100 rounded-md border border-gray-400 flex-col justify-center items-center z-50 text-black overflow-y-auto">
        <div>{objectElements}</div>
      </PopoverContent>
    </Popover>
  );
}
export default SettingsPopUp;
