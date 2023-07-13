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

  const collectStatistics = () => {
    setIsCollected(true);

    JobApiClient.collectStatisticsOnDataGroups({
      connectionName: connection
    });
  };

  const runProfilingChecks = () => {
    JobApiClient.runChecks(false, undefined, {
      checkSearchFilters: {
        connectionName: connection,
        checkType: CheckTypes.PROFILING
      }
    });
    setIsProfilingChecked(true);
  };

  const configureScheduling = () => {
    setScheduleConfigured(true);
    history.push(
      ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connection, 'schedule')
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
            <div className="grow">Run advanced profiling checks</div>

            <div className="w-50 justify-between flex gap-2 items-center">
              <Button
                variant="contained"
                color={isProfilingChecked ? 'secondary' : 'primary'}
                label="Run profiling checks"
                className="text-sm px-2 w-40"
                onClick={isProfilingChecked ? undefined : runProfilingChecks}
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
              Configure scheduling for profiling and daily recurring checks
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
