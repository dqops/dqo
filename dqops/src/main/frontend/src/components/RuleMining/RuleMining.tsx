import { Tabs } from '@material-tailwind/react';
import React from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';

export default function RuleMining({
  timePartitioned,
  setTimePartitioned
}: {
  timePartitioned?: 'daily' | 'monthly';
  setTimePartitioned?: (value: 'daily' | 'monthly') => void;
}) {
  const {
    checkTypes,
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const tabs = [
    {
      label:
        checkTypes === CheckTypes.MONITORING
          ? 'Daily checkpoints'
          : 'Daily partitioned',
      value: 'daily'
    },
    {
      label:
        checkTypes === CheckTypes.MONITORING
          ? 'Monthly checkpoints'
          : 'Monthly partitioned',
      value: 'monthly'
    }
  ];
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  return (
    <div>
      {' '}
      {timePartitioned &&
        userProfile &&
        userProfile.license_type &&
        userProfile.license_type?.toLowerCase() !== 'free' &&
        !userProfile.trial_period_expires_at && (
          <div className="border-b border-gray-300">
            <Tabs
              tabs={tabs}
              activeTab={timePartitioned}
              onChange={setTimePartitioned}
              className="pt-2"
            />
          </div>
        )}
      <div></div>
    </div>
  );
}
