import React, { useState } from "react";
import Button from "../Button";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import SvgIcon from "../SvgIcon";
import { JobApiClient } from "../../services/apiClient";
import clsx from "clsx";
import { DqoJobHistoryEntryModelStatusEnum } from "../../api";

export const SynchronizeButton = () => {
  const { folderSynchronizationStatus, job_dictionary_state, userProfile } = useSelector((state: IRootState) => state.job || {});
  const [loading, setLoading] = useState(false);
  const [jobId, setJobId] = useState<number>();

  const job = jobId ? job_dictionary_state[jobId] : undefined;


  const syncAllFolders = async () => {
    try {
      setLoading(true);
      const res = await JobApiClient.synchronizeFolders(undefined, false, undefined, {
        sources: true,
        sensors: true,
        rules: true,
        checks: true,
        dataSensorReadouts: true,
        dataCheckResults: true,
        dataStatistics: true,
        dataErrors: true,
        dataIncidents: true,
        settings: true,
        credentials: true,
        dictionaries: true
      });
      if (res.data) {
        setJobId(res.data.jobId?.jobId);
      }
    } finally {
      setLoading(false);
    }
  };

  const isGreenBorder = !folderSynchronizationStatus || Object.values(folderSynchronizationStatus).every((status) => status === "unchanged");
  const disabled = job && (
    job?.status !== DqoJobHistoryEntryModelStatusEnum.finished &&
    job?.status !== DqoJobHistoryEntryModelStatusEnum.failed
  );

  return (
    <Button
      label="Synchronize"
      color="primary"
      className="px-4"
      leftIcon={(
        <SvgIcon name="sync" className={clsx("w-4 h-4 mr-3", loading ? "animate-spin" : "")} />
      )}
      onClick={syncAllFolders}
      variant={isGreenBorder ? "outlined" : "contained"}
      disabled={disabled || loading || userProfile.can_synchronize === false}
    />
  );
};