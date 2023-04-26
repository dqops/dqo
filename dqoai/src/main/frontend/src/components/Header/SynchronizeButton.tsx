import React, { useState } from "react";
import Button from "../Button";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import SvgIcon from "../SvgIcon";
import { JobApiClient } from "../../services/apiClient";
import clsx from "clsx";

export const SynchronizeButton = () => {
  const { folderSynchronizationStatus } = useSelector((state: IRootState) => state.job);
  const [loading, setLoading] = useState(false);

  const syncAllFolders = async () => {
    try {
      setLoading(true);
      await JobApiClient.synchronizeFolders({
        sources: true,
        sensors: true,
        rules: true,
        checks: true,
        dataSensorReadouts: true,
        dataCheckResults: true,
        dataStatistics: true,
        dataErrors: true,
      });
    } finally {
      setLoading(false);
    }
  };

  const isGreenBorder = !folderSynchronizationStatus || Object.values(folderSynchronizationStatus).every((status) => status === "unchanged");
  const disabled = folderSynchronizationStatus && Object.values(folderSynchronizationStatus).some((status) => status === "synchronizing");

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
      disabled={disabled || loading}
    />
  );
};