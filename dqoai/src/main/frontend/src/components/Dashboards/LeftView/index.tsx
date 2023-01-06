import React from "react";
import { useSelector } from "react-redux";
import { IRootState } from "../../../redux/reducers";
import SvgIcon from "../../SvgIcon";
import { useDashboard } from "../../../contexts/dashboardContext";

const LeftView = () => {
  const { dashboardFolders }  = useSelector((state: IRootState) => state.dashboard);
  const { changeActiveTab } = useDashboard();

  return (
    <div className="fixed left-0 top-0 h-full w-70 shadow border-r border-gray-300 p-4 pt-12 bg-white">
      {dashboardFolders.map((folder, index) => (
        <div key={index} className="mb-3">
          <div className="flex space-x-1.5 items-center mb-1">
            <SvgIcon name="folder" className="w-4 h-4" />
            <div className="text-[13px] leading-1.5">{folder.folder_name}</div>
          </div>
          <div className="pl-5">
            {folder.dashboards?.map((dashboard, jIndex) => (
              <div
                key={jIndex}
                className="cursor-pointer flex space-x-1.5 items-center"
                onClick={() => changeActiveTab(dashboard, folder.folder_name)}
              >
                <SvgIcon name="grid" className="w-4 h-4" />
                <div className="text-[13px] leading-1.5">{dashboard.dashboard_name}</div>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default LeftView;
