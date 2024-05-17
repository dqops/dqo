import {
  Accordion,
  AccordionBody,
  AccordionHeader,
  IconButton,
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import { AxiosResponse } from 'axios';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { DqoUserProfileModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  setLicenseFree,
  setUserProfile,
  toggleProfile
} from '../../redux/actions/job.actions';
import { IRootState } from '../../redux/reducers';
import { EnviromentApiClient, UsersApi } from '../../services/apiClient';
import Button from '../Button';
import SvgIcon from '../SvgIcon';
import TextArea from '../TextArea';
import ChangePrincipalPasswordDialog from './ChangePrincipalPasswordDialog';

interface UserProfile {
  name?: string;
  email?: string;
}

export default function UserProfile({ name, email }: UserProfile) {
  const { isProfileOpen, userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [apiKey, setApiKey] = useState('');
  const [copied, setCopied] = useState(false);
  const [open, setOpen] = useState(false);
  const [dashboardOpen, setDashboardOpen] = useState(false);
  const [passwordChangedMessage, setPasswordChangedMessage] = useState('');
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
      dispatch(setUserProfile(res.data));
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

  const generateApiKey = async () => {
    await EnviromentApiClient.issueApiKey().then((res) => setApiKey(res.data));
  };

  const copyToClipboard = () => {
    navigator.clipboard.writeText(apiKey);
    setCopied(true);
  };

  const copyWhole = (e: any) => {
    e.target.select();
  };

  const changePrincipalPassword = async (password: string) => {
    await UsersApi.changeCallerPassword(password)
      .then(() =>
        setPasswordChangedMessage('Password has been successfully changed')
      )
      .then(() => setOpen(false))
      .catch((err) => console.error(err));
  };
  const profileItems = [
    {
      label: 'Data quality dashboards:',
      value: userProfile?.data_quality_data_warehouse_enabled
        ? 'enabled'
        : 'disabled'
    },
    {
      label: 'Users:',
      value: userProfile?.users_limit
        ? formatNumberWithSegments(userProfile.users_limit)
        : '-'
    },
    {
      label: 'Monitored connections:',
      value: userProfile?.connections_limit
        ? formatNumberWithSegments(userProfile.connections_limit)
        : '-'
    },
    {
      label: 'Total monitored tables:',
      value: userProfile?.tables_limit
        ? formatNumberWithSegments(userProfile.tables_limit)
        : '-'
    },
    {
      label: 'Tables per connection:',
      value: userProfile?.connection_tables_limit
        ? formatNumberWithSegments(userProfile.connection_tables_limit)
        : '-'
    },
    {
      label: 'Concurrent jobs:',
      value: userProfile?.jobs_limit
        ? formatNumberWithSegments(userProfile.jobs_limit)
        : '-'
    },
    {
      label: 'Months in data quality warehouse:',
      value: userProfile?.months_limit
        ? formatNumberWithSegments(userProfile.months_limit)
        : '-'
    }
  ];
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
      <PopoverContent className="bg-white h-auto w-70 rounded-md border border-gray-400 flex-col justify-center items-center z-50 text-black text-sm">
        <div className="flex justify-between items-center h-12 ">
          <div className="ml-1 flex items-center justify-center gap-x-2">
            {' '}
            <div className="font-bold">User:</div>
            <div className="break-all">
              {userProfile?.user ? userProfile.user : '-'}{' '}
            </div>
          </div>
        </div>

        <Accordion open={dashboardOpen}>
          <AccordionHeader
            onClick={() => setDashboardOpen((open) => !open)}
            className=" !p-1 !m-0 !text-black"
          >
            <div className="text-sm">Data quality dashboards</div>
          </AccordionHeader>
          <AccordionBody>
            <div className="h-15">
              <div className="ml-1 flex items-center gap-x-2 my-2">
                {' '}
                <div className="font-bold">Subscription plan:</div>
                {userProfile?.license_type
                  ? userProfile.license_type
                  : '-'}{' '}
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
            {profileItems.map((item, index) => (
              <div
                className="flex justify-between items-center pb-0.5"
                key={index}
              >
                <div className="ml-1">{item.label}</div>
                <div className="mr-1">{item.value}</div>
              </div>
            ))}
          </AccordionBody>
        </Accordion>

        <div className="my-2 ml-1">
          {apiKey.length !== 0 ? (
            <div className="flex items-center">
              <TextArea
                label="User API Key:"
                value={apiKey}
                className="select-all text-sm"
                onClick={copyWhole}
              />
              <SvgIcon
                name={copied ? 'done' : 'copytext'}
                className="cursor-pointer mt-8 ml-5"
                onClick={() => copyToClipboard()}
              />
            </div>
          ) : (
            <Button
              label="Generate API Key"
              color="primary"
              variant="outlined"
              onClick={generateApiKey}
            />
          )}
        </div>
        <div className="w-full flex flex-col h-auto text-black mt-4">
          {userProfile.can_manage_account === true && (
            <a
              href="https://cloud.dqops.com/account"
              target="_blank"
              rel="noreferrer"
              className="block text-teal-500 text-sm underline mb-3 ml-1"
            >
              Manage account
            </a>
          )}
          {userProfile.can_change_own_password === true && (
            <>
              <div
                className="text-teal-500  text-sm cursor-pointer underline ml-1"
                onClick={() => setOpen(true)}
              >
                Change password
              </div>
              <div className="text-green-500 pt-2 text-sm ml-1">
                {passwordChangedMessage}
              </div>
            </>
          )}
        </div>
        <ChangePrincipalPasswordDialog
          open={open}
          onClose={() => setOpen(false)}
          handleSubmit={changePrincipalPassword}
        />
      </PopoverContent>
    </Popover>
  );
}

function formatNumberWithSegments(num: number): string {
  const numStr = num.toString();
  const segments = [];

  for (let i = numStr.length; i > 0; i -= 3) {
    segments.unshift(numStr.slice(Math.max(0, i - 3), i));
  }

  return segments.join(' ');
}
