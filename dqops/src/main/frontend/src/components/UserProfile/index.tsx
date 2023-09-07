import { AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { DqoUserProfileModel } from '../../api';
import { EnviromentApiClient } from '../../services/apiClient';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { toggleProfile, setLicenseFree, setUserProfile } from '../../redux/actions/job.actions';
import {
  Popover,
  PopoverHandler,
  PopoverContent,
  IconButton
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import Button from '../Button';
import moment from 'moment';
import TextArea from '../TextArea';

interface UserProfile {
  name?: string;
  email?: string;
}

export default function UserProfile({ name, email }: UserProfile) {
  const { isProfileOpen, userProfile } = useSelector((state: IRootState) => state.job || {});
  const [apiKey, setApiKey] = useState("");
  const dispatch = useActionDispatch();

  const toggleOpen = () => {
    dispatch(toggleProfile(!isProfileOpen));
  };

  const setLicenseFreeFunc = () => {
    dispatch(setLicenseFree(true));
  };


  const fetchUserProfile = async () => {
    try {
      const res: AxiosResponse<DqoUserProfileModel> =
        await EnviromentApiClient.getUserProfile();
      dispatch(setUserProfile(res.data))
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchUserProfile().then(
      () => userProfile?.license_type === 'FREE' && setLicenseFreeFunc()
    );
  }, [name, email]);
  const today = moment();
  const sampleData = moment(userProfile?.trial_period_expires_at);
  const dayDiff = sampleData.diff(today, 'days');

  const generateApiKey =async () => {
    await EnviromentApiClient.issueApiKey().then((res) => setApiKey(res.data))
  }

  console.log(apiKey)

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
      <PopoverContent className="bg-white h-108 w-70 rounded-md border border-gray-400 flex-col justify-center items-center z-50 text-black">
        <div className="flex justify-between items-center h-12 ">
          <div className="ml-1 flex items-center justify-center gap-x-2">
            {' '}
            <div className="font-bold">User:</div>
            {userProfile?.user ? userProfile.user : '-'}{' '}
          </div>
        </div>
        <div className="h-15">
          <div className="ml-1 flex items-center gap-x-2 my-2">
            {' '}
            <div className="font-bold">Subscription plan:</div>
            {userProfile?.license_type ? userProfile.license_type : '-'}{' '}
          </div>
          {userProfile?.trial_period_expires_at && (
            <div className="ml-1 flex items-center gap-x-2 my-2">
              {' '}
              <div className="font-bold" style={{ whiteSpace: 'nowrap' }}>
                {dayDiff} days left
              </div>
              <div
                className="h-1 bg-gray-100"
                style={{ width: '100%', position: 'relative' }}
              >
                <div
                  className="h-1 absolute bg-teal-500"
                  style={{
                    width: `${(dayDiff / 14) * 100}%`
                  }}
                ></div>
              </div>
            </div>
          )}
        </div>

        <div className="font-bold h-8 ml-1">Account limits:</div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Users:</div>
          <div className="mr-1">
            {userProfile?.users_limit ? userProfile.users_limit : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Monitored connections:</div>
          <div className="mr-1">
            {userProfile?.connections_limit
              ? userProfile?.connections_limit
              : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Total monitored tables:</div>
          <div className="mr-1">
            {userProfile?.tables_limit ? userProfile.tables_limit : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Tables per connection:</div>
          <div className="mr-1">
            {userProfile?.connection_tables_limit
              ? userProfile.connection_tables_limit
              : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Concurrent jobs:</div>
          <div className="mr-1">
            {userProfile?.jobs_limit ? userProfile.jobs_limit : '-'}
          </div>
        </div>
        <div className="flex justify-between items-center">
          <div className="ml-1">Months in warehouse:</div>
          <div className="mr-1">
            {userProfile?.months_limit ? userProfile.months_limit : '-'}
          </div>
        </div>
        <div className='my-2'>{apiKey.length!==0 ? <div><TextArea label='User API Key:' value={apiKey}/></div> : <Button label='Generate API Key' color='primary' variant='outlined' onClick={generateApiKey}/>}</div>
        <div className="w-full text-center flex justify-center items-center h-20 text-black">
          <a
            href="https://cloud.dqo.ai/account"
            target="_blank"
            rel="noreferrer"
            className="block text-gray-700 mb-3"
          >
            <Button label="Manage account" color="primary" disabled={userProfile.can_manage_account === false}/>
          </a>
        </div>
      </PopoverContent>
    </Popover>
  );
}
