import {
  IconButton,
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import React, { useState } from 'react';
import Button from '../Button';
import Checkbox from '../Checkbox';
import { JobApiClient } from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useHistory, useParams } from 'react-router-dom';
import AdvisorConfirmDialog from './AdvisorConfirmDialog';
import SvgIcon from '../SvgIcon';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

type HeaderBannerProps = {
  onClose: () => void;
};

export const HeaderBanner = ({ onClose }: HeaderBannerProps) => {
  const [openPopover, setOpenPopover] = React.useState(false);
  const triggers = {
    onMouseEnter: () => setOpenPopover(true),
    onMouseLeave: () => setOpenPopover(false)
  };
  const {
    connection
  }: { checkTypes: CheckTypes; connection: string; schema: string } =
    useParams();
  const [confirmOpen, setConfirmOpen] = useState(false);
  const history = useHistory();
  const [scheduleConfigured, setScheduleConfigured] = useState(false);
  const [isCollected, setIsCollected] = useState(false);
  const [isProfilingChecked, setIsProfilingChecked] = useState(false);
  const { advisorObject, userProfile } = useSelector((state: IRootState) => state.job);

  const collectStatistics = () => {
    setIsCollected(true);

    JobApiClient.collectStatisticsOnDataGroups({
      connectionName: advisorObject.connectionName
    });
  };

  const runProfilingChecks = () => {
    JobApiClient.runChecks(false, undefined, {
      checkSearchFilters: {
        connectionName: advisorObject.connectionName,
        checkType: CheckTypes.PROFILING
      }
    });
    setIsProfilingChecked(true);
  };

  const configureScheduling = () => {
    setScheduleConfigured(true);
    history.push(
      ROUTES.CONNECTION_DETAIL(
        CheckTypes.SOURCES,
        advisorObject.connectionName ?? connection,
        'schedule'
      )
    );
  };

  const openAdvisor = () => {
    if (scheduleConfigured && isCollected && isProfilingChecked) {
      onClose();
      return;
    }
    setConfirmOpen(true);
  };

  return (
    <div className="absolute z-10 top-0 left-0 right-0">
      <Popover open={openPopover} handler={setOpenPopover}>
        <PopoverHandler {...triggers}>
          <div className="h-12 bg-orange-500 flex justify-between items-center px-4 bg-opacity-90">
            <div className="flex items-center gap-2">
              <SvgIcon name="info" className="text-white" />
              <h4 className="text-white font-bold">
                Data source needs configuration
              </h4>
            </div>

            <IconButton
              color="red"
              size="sm"
              className="!shadow-none"
              onClick={onClose}
            >
              <SvgIcon name="close" />
            </IconButton>
          </div>
        </PopoverHandler>

        <PopoverContent
          {...triggers}
          className="max-w-150 z-50 !top-12 !left-0 rounded-none text-gray-700"
        >
          <div className="flex mb-2 items-center justify-between">
            <div className="grow">Collect basic statistics</div>

            <div className="w-50 justify-between flex gap-2 items-center">
              <Button
                variant={'contained'}
                color={isCollected ? 'secondary' : 'primary'}
                label="Collect statistics"
                className="text-sm px-2 w-40"
                onClick={isCollected ? undefined : collectStatistics}
                disabled={userProfile.can_collect_statistics  !== true}
              />

              <div className="pr-4 flex items-center">
                <Checkbox
                  checked={isCollected}
                  onChange={() => {
                    setIsCollected(!isCollected);
                  }}
                />
              </div>
            </div>
          </div>
          <div className="flex mb-2 items-center justify-between">
            <div className="grow">Run profiling checks</div>

            <div className="w-50 justify-between flex gap-2 items-center">
              <Button
                variant="contained"
                color={isProfilingChecked ? 'secondary' : 'primary'}
                label="Run profiling checks"
                className="text-sm px-2 w-40"
                onClick={isProfilingChecked ? undefined : runProfilingChecks}
                disabled={userProfile.can_run_checks !== true}
              />

              <div className="pr-4 flex items-center">
                <Checkbox
                  checked={isProfilingChecked}
                  onChange={() => {
                    setIsProfilingChecked(!isProfilingChecked);
                  }}
                />
              </div>
            </div>
          </div>
          <div className="flex mb-2 items-center justify-between">
            <div className="grow">
              Configure scheduling for profiling and daily monitoring checks
            </div>

            <div className="w-50 justify-between flex gap-2 items-center">
              <Button
                variant={scheduleConfigured ? 'contained' : 'outlined'}
                color={scheduleConfigured ? 'secondary' : 'primary'}
                label="Configure scheduling"
                className="text-sm px-2 w-40"
                onClick={scheduleConfigured ? undefined : configureScheduling}
              />

              <div className="pr-4 flex items-center">
                <Checkbox
                  checked={scheduleConfigured}
                  onChange={() => {
                    setScheduleConfigured(!scheduleConfigured);
                  }}
                />
              </div>
            </div>
          </div>

          <div className="flex justify-end">
            <Button
              variant={
                scheduleConfigured && isCollected && isProfilingChecked
                  ? 'contained'
                  : 'outlined'
              }
              color="primary"
              label="Close advisor"
              className="px-2 my-4"
              onClick={openAdvisor}
            />
          </div>
        </PopoverContent>
      </Popover>

      <AdvisorConfirmDialog
        open={confirmOpen}
        onClose={() => setConfirmOpen(false)}
      />
    </div>
  );
};
