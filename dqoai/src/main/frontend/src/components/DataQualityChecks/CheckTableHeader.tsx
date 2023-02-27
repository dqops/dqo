import SvgIcon from "../SvgIcon";
import React from "react";
import { DqoJobHistoryEntryModelStatusEnum, UIAllChecksModel } from "../../api";
import { JobApiClient } from "../../services/apiClient";
import { isEqual } from "lodash";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";

interface TableHeaderProps {
  checksUI: UIAllChecksModel;
}

const TableHeader = ({ checksUI }: TableHeaderProps) => {
  const { jobs } = useSelector((state: IRootState) => state.job);

  const job = jobs?.jobs?.find((item) =>
    isEqual(
      item.parameters?.runChecksParameters?.checkSearchFilters,
      checksUI.run_checks_job_template
    )
  );

  const onRunChecks = async () => {
    await JobApiClient.runChecks({
      checkSearchFilters: checksUI?.run_checks_job_template
    });
  };

  return (
    <thead>
    <tr>
      <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400">
        <div className="flex space-x-1">
          {(!job ||
            job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
            job?.status === DqoJobHistoryEntryModelStatusEnum.failed) && (
            <SvgIcon
              name="play"
              className="text-green-700 h-5 cursor-pointer"
              onClick={onRunChecks}
            />
          )}
          {job?.status === DqoJobHistoryEntryModelStatusEnum.waiting && (
            <SvgIcon
              name="hourglass"
              className="text-gray-700 h-5 cursor-pointer"
            />
          )}
          {(job?.status === DqoJobHistoryEntryModelStatusEnum.running ||
            job?.status === DqoJobHistoryEntryModelStatusEnum.queued) && (
            <SvgIcon
              name="hourglass"
              className="text-gray-700 h-5 cursor-pointer"
            />
          )}
          <SvgIcon
            name="delete"
            className="h-5 cursor-pointer"
          />
        </div>
      </td>
      <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400" />
      <td
        className="text-center whitespace-nowrap text-gray-700 py-3 px-4 border-l border-b border-r font-semibold bg-gray-400"
        colSpan={2}
      >
        Failing check
      </td>
      <td className="w-5 border-b" />
      <td
        className="text-center whitespace-nowrap text-gray-700 py-3 px-4 border-l border-b font-semibold bg-gray-400"
        colSpan={2}
      >
        Passing check
      </td>
    </tr>
    <tr>
      <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400">
        Data quality check
      </td>
      <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400">
        Sensor parameters
      </td>
      <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-orange-100">
        Error threshold
      </td>
      <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-red-100">
        Fatal threshold
      </td>
      <td className="w-5 border-b" />
      <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-yellow-100">
        Warning threshold
      </td>
    </tr>
    </thead>
  );
};

export default TableHeader;