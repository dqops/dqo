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
      check_search_filters: {
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
        <PopoverHandler>
          <div className="h-12 bg-orange-500 flex justify-between items-center px-4 bg-opacity-90 cursor-pointer"
          onClick={() => setOpenPopover(!openPopover)}>
            <div className="flex items-center gap-2">
              <SvgIcon name="info" className="text-white" />
              <h4 className="text-white font-bold">
                New tables have been imported and the automatic monitoring has been scheduled. Click here for more actions.
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
          className="max-w-250 z-50 !top-12 !left-0 rounded-none text-gray-700"
        >
          <div className="flex mb-4  items-center justify-between">
            <div className="grow">Collect basic statistics</div>

            <div className="w-50 justify-between flex gap-2 items-center">
              <Button
                variant={'contained'}
                color={isCollected ? 'secondary' : 'primary'}
                label={isCollected ? "The job has started" :"Collect statistics"}
                className="text-sm px-2 w-45"
                onClick={isCollected ? undefined : collectStatistics}
                disabled={userProfile.can_collect_statistics  !== true}
                
              />

              <div className="px-4 flex items-center">
                <Checkbox
                  checked={isCollected}
                  onChange={() => {
                    setIsCollected(!isCollected);
                  }}
                />
              </div>
            </div>
          </div>
          <div className="flex mb-4 items-center justify-between">
            <div className="grow">Run profiling checks</div>

            <div className="w-50 justify-between flex gap-2 items-center">
              <Button
                variant="contained"
                color={isProfilingChecked ? 'secondary' : 'primary'}
                label={isProfilingChecked ? "The job has started": "Run profiling checks"}
                className="text-sm px-2 w-45"
                onClick={isProfilingChecked ? undefined : runProfilingChecks}
                disabled={userProfile.can_run_checks !== true}
              />

              <div className="px-4 flex items-center">
                <Checkbox
                  checked={isProfilingChecked}
                  onChange={() => {
                    setIsProfilingChecked(!isProfilingChecked);
                  }}
                />
              </div>
            </div>
          </div>
          <div className="flex mb-4 w-150 items-center justify-between">
            <div className="grow">
              Review scheduling for profiling and
              <br></br> daily monitoring checks
            </div>

            <div className="w-50 justify-between flex gap-2 items-center">
              <Button
                variant={scheduleConfigured ? 'contained' : 'outlined'}
                color={scheduleConfigured ? 'secondary' : 'primary'}
                label="Review scheduling"
                className="text-sm px-2 w-45"
                onClick={scheduleConfigured ? undefined : configureScheduling}
              />

              <div className="px-4 flex items-center">
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
