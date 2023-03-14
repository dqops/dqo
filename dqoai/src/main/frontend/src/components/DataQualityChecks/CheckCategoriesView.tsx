import React, { Fragment, useState } from "react";
import SvgIcon from "../SvgIcon";
import CheckListItem from "./CheckListItem";
import {
  CheckResultsOverviewDataModel,
  DqoJobHistoryEntryModelStatusEnum,
  UICheckModel,
  UIQualityCategoryModel
} from "../../api";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import { isEqual } from "lodash";
import { JobApiClient } from "../../services/apiClient";
import DeleteOnlyDataDialog from "../CustomTree/DeleteOnlyDataDialog";

interface CheckCategoriesViewProps {
  category: UIQualityCategoryModel;
  checkResultsOverview: CheckResultsOverviewDataModel[];
  handleChangeDataDataStreams: (check: UICheckModel, index: number) => void;
  onUpdate: () => void;
  getCheckOverview: () => void;
}
const CheckCategoriesView = ({ category, checkResultsOverview, handleChangeDataDataStreams, onUpdate, getCheckOverview }: CheckCategoriesViewProps) => {
  const { jobs } = useSelector((state: IRootState) => state.job);
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);

  const job = jobs?.jobs?.find((item) =>
    isEqual(
      item.parameters?.runChecksParameters?.checkSearchFilters,
      category.run_checks_job_template
    )
  );

  const onRunChecks = async () => {
    await JobApiClient.runChecks({
      checkSearchFilters: category?.run_checks_job_template
    });
  };

  return (
    <Fragment>
      <tr>
        <td
          className="py-2 px-4 bg-gray-50 border-b border-t"
          colSpan={2}
        >
          <div className="flex items-center gap-2">
            <div className="font-semibold text-gray-700 capitalize">
              {category.category}
            </div>
            <div className="flex space-x-1">
              {(!job ||
                job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
                job?.status === DqoJobHistoryEntryModelStatusEnum.failed) && (
                <SvgIcon
                  name="play"
                  className="text-primary h-5 cursor-pointer"
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
                onClick={() => setDeleteDataDialogOpened(true)}
              />
            </div>
          </div>
        </td>
        <td className="py-2 px-4 bg-gray-50 border-b border-t" />
        <td className="py-2 px-4 bg-gray-50 border-b border-t" />
        <td className="py-2 px-4 bg-gray-50 border-b border-t" />
      </tr>
      {category.checks &&
        category.checks.map((check, index) => (
          <CheckListItem
            check={check}
            key={index}
            onChange={(item) =>
              handleChangeDataDataStreams(item, index)
            }
            checkResult={checkResultsOverview.find((item) => item.checkName === check.check_name && category.category === item.checkCategory)}
            getCheckOverview={getCheckOverview}
            onUpdate={onUpdate}
          />
        ))}
      <DeleteOnlyDataDialog
        open={deleteDataDialogOpened}
        onClose={() => setDeleteDataDialogOpened(false)}
        onDelete={(params) => {
          setDeleteDataDialogOpened(false);
          JobApiClient.deleteStoredData({
            ...category.data_clean_job_template,
            ...params,
          });
        }}
      />
    </Fragment>
  );
};

export default CheckCategoriesView;
