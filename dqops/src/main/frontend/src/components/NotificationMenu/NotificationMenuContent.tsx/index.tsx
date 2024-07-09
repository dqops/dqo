import clsx from 'clsx';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setCronScheduler } from '../../../redux/actions/job.actions';
import { IRootState } from '../../../redux/reducers';
import { JobApiClient } from '../../../services/apiClient';
import Switch from '../../Switch';
import ErrorItem from '../ErrorItem';
import JobItem from '../JobItem';

export default function NotificationMenuContent() {
  const { jobList, isCronScheduled, userProfile, notificationCount } =
    useSelector((state: IRootState) => state.job || {});
  const dispatch = useActionDispatch();

  const scheduleCron = (bool: boolean) => {
    dispatch(setCronScheduler(bool));
  };

  useEffect(() => {
    const getIsCronSchedulerRunning = async () => {
      const res = await JobApiClient.isCronSchedulerRunning();
      scheduleCron(res.data);
    };
    getIsCronSchedulerRunning();
  }, []);

  const startCroner = async () => {
    await JobApiClient.startCronScheduler();
  };
  const stopCroner = async () => {
    await JobApiClient.stopCronScheduler();
  };

  const changeStatus = () => {
    if (isCronScheduled === true) {
      stopCroner();
      scheduleCron(false);
    }
    if (isCronScheduled === false) {
      startCroner();
      scheduleCron(true);
    }
  };

  return (
    <>
      <div className="border-b border-gray-300 text-gray-700 font-semibold pb-2 text-lg flex flex-col gap-y-2 px-4 relative">
        <div>Notifications: {notificationCount}</div>
        <div className="flex items-center gap-x-3 text-sm">
          <div className="whitespace-no-wrap">Jobs scheduler </div>
          <div
            className={clsx(
              userProfile.can_manage_scheduler !== true
                ? 'pointer-events-none cursor-not-allowed'
                : ''
            )}
          >
            <Switch
              checked={isCronScheduled ? isCronScheduled : false}
              onChange={() => changeStatus()}
            />
          </div>
          {isCronScheduled === false && (
            <div className="font-light text-xs text-red-500 text-center">
              (Warning: scheduled jobs will not be executed)
            </div>
          )}
        </div>
      </div>
      <div className="overflow-x-hidden max-h-100 py-4 px-4 relative">
        {/* {error_dictionary_state.map((error, index) => <ErrorItem error={error} key={index}/>)} */}
        {Object.keys(jobList)
          .reverse()
          .map((jobId: string, index) =>
            jobId[0] === '-' ? (
              <ErrorItem errorId={jobId} key={index} />
            ) : (
              <JobItem
                jobId={jobId}
                key={index}
                canUserCancelJobs={userProfile.can_cancel_jobs}
              />
            )
          )}
      </div>
    </>
  );
}
