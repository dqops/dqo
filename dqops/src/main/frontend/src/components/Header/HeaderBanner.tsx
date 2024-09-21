import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { toggleMenu } from '../../redux/actions/job.actions';
import { IRootState } from '../../redux/reducers';
import { JobApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import Button from '../Button';
import Checkbox from '../Checkbox';

type HeaderBannerProps = {
  onClose: () => void;
};

export const HeaderBanner = ({ onClose }: HeaderBannerProps) => {
  const dispatch = useActionDispatch();
  const [isCollected, setIsCollected] = useState(true);
  const [isProfilingChecked, setIsProfilingChecked] = useState(true);
  const { advisorObject, isAdvisorOpen } = useSelector(
    (state: IRootState) => state.job
  );

  const collectStatistics = async () => {
    setIsCollected(true);

    if (advisorObject.tableNames && advisorObject.tableNames.length > 0) {
      advisorObject.tableNames.forEach((tableName) => {
        JobApiClient.collectStatisticsOnTable(undefined, true, undefined, false, undefined, {
          connection: advisorObject.connectionName,
          fullTableName: advisorObject.schemaName + "." + tableName
        });
      });
    } else {
      JobApiClient.collectStatisticsOnTable(undefined, true, undefined, false, undefined, {
        connection: advisorObject.connectionName,
        fullTableName: advisorObject.schemaName + ".*"
      });
    }
  };

  const runProfilingChecks = async () => {
    if (advisorObject.tableNames && advisorObject.tableNames.length > 0) {
      advisorObject.tableNames.forEach((tableName) => {
        JobApiClient.runChecks(undefined, false, undefined, {
          check_search_filters: {
            connection: advisorObject.connectionName,
            fullTableName: advisorObject.schemaName + "." + tableName,
            checkType: CheckTypes.PROFILING
          }
        });
      });
    } else {
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: {
          connection: advisorObject.connectionName,
          fullTableName: advisorObject.schemaName + ".*",
          checkType: CheckTypes.PROFILING
        }
      });
    }
  };

  const handleSubmit = async () => {
    if (isCollected) {
      await collectStatistics();
    }
    if (isProfilingChecked) {
      await runProfilingChecks();
    }
    if (isCollected || isProfilingChecked) {
      dispatch(toggleMenu(true));
    }
    onClose();
  };

  return (
    <Dialog open={isAdvisorOpen} handler={onClose}>
      <DialogBody className="pt-10 pb-2 px-8">
        <div className="text-2xl text-gray-700 text-center whitespace-normal mb-5">
          New tables have been imported into DQOps. It is recommended to collect statistics to
          enable data quality rule mining based on data samples.
        </div>
        <div>
          <div className="text-black flex flex-col gap-y-2 mb-4">
            <div className="px-4 flex items-center">
              <Checkbox
                checked={isCollected}
                onChange={() => {
                  setIsCollected(!isCollected);
                }}
                label="Collect basic statistics"
              />
            </div>
            <div className="px-4 flex items-center">
              <Checkbox
                checked={isProfilingChecked}
                onChange={() => {
                  setIsProfilingChecked(!isProfilingChecked);
                }}
                label="Profile data with default profiling checks"
              />
            </div>
          </div>
          <div className="text-md text-orange-500 text-center whitespace-normal">
            Warning: If you have imported many tables, avoid profiling all tables simultaneously
            Instead, click the {'"'}Cancel{'"'} button and collect statistics for
            each table individually.
          </div>
        </div>
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={onClose}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={handleSubmit}
          label="Start profiling"
        />
      </DialogFooter>
    </Dialog>
  );
};
