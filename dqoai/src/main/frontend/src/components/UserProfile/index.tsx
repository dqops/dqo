import { AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { DqoUserProfileModel } from '../../api';
import { EnviromentApiClient } from '../../services/apiClient';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { toggleProfile } from '../../redux/actions/job.actions';
import {
  Popover,
  PopoverHandler,
  PopoverContent,
  IconButton
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import Button from '../Button';

interface UserProfile {
  name?: string;
  email?: string;
}

export default function UserProfile({ name, email }: UserProfile) {
  const { isProfileOpen } = useSelector((state: IRootState) => state.job || {});

  const dispatch = useActionDispatch();

  const toggleOpen = () => {
    dispatch(toggleProfile(!isProfileOpen));
  };

  const [userProfile, setUserProfile] = useState<DqoUserProfileModel>();

  const fetchUserProfile = async () => {
    try {
      const res: AxiosResponse<DqoUserProfileModel> =
        await EnviromentApiClient.getUserProfile();
      setUserProfile(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchUserProfile().then();
  }, [name, email]);

  return (
    <Popover open={isProfileOpen} handler={toggleOpen} placement="top-end">
      <PopoverHandler>
        <IconButton
          className="!mr-3 !bg-transparent"
          ripple={false}
          variant="text"
        >
          <div className="relative">
            <SvgIcon name="userprofile" />
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent className="bg-white h-65 w-70 rounded-md border border-gray-400 flex-col justify-center items-center z-50 text-black">
        <div className="flex justify-between items-center h-12 ">
          <div className="ml-1">
            {' '}
            {userProfile?.user ? userProfile.user : '-'}{' '}
          </div>
          <div className="mr-1 whitespace-normal font-bold"></div>
        </div>
        <div className="font-bold h-8 ml-1">Account limits:</div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Connections:</div>
          <div className="mr-1">
            {userProfile?.connections_limit
              ? userProfile?.connections_limit
              : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Users:</div>
          <div className="mr-1">
            {userProfile?.users_limit ? userProfile.users_limit : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Months:</div>
          <div className="mr-1">
            {userProfile?.months_limit ? userProfile.months_limit : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Connection Tables:</div>
          <div className="mr-1">
            {userProfile?.connection_tables_limit
              ? userProfile.connection_tables_limit
              : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Tables:</div>
          <div className="mr-1">
            {userProfile?.tables_limit ? userProfile.tables_limit : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Jobs:</div>
          <div className="mr-1">
            {userProfile?.jobs_limit ? userProfile.jobs_limit : '-'}
          </div>
        </div>
        <div className="w-full text-center flex justify-center items-center h-20 text-black">
          <a
            href="https://cloud.dqo.ai/account"
            target="_blank"
            rel="noreferrer"
            className="block text-gray-700 mb-3"
          >
            <Button label="Manage account" color="primary" />
          </a>
        </div>
      </PopoverContent>
    </Popover>
  );
}
