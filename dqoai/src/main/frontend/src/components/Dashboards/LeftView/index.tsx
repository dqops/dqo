import React, { useCallback, useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { IRootState } from "../../../redux/reducers";
import SvgIcon from "../../SvgIcon";
import { useDashboard } from "../../../contexts/dashboardContext";
import { DashboardsFolderSpec } from "../../../api";

const LeftView = () => {
  const { dashboardFolders }  = useSelector((state: IRootState) => state.dashboard);
  const { changeActiveTab } = useDashboard();

  const [topFolders, setTopFolders] = useState<(DashboardsFolderSpec & { opened?: boolean })[]>(dashboardFolders.map(f => ({ ...f, opened: true })));

  const onToggle = useCallback((folderName: string) => () => {
    setTopFolders(prev => prev.map(f => f.folder_name === folderName ? ({ ...f, opened: !f.opened }) : f));
  }, []);

  useEffect(() => {
    setTopFolders(dashboardFolders.map(f => ({ ...f, opened: true })));
  }, [dashboardFolders]);

  return (
    <div className="fixed left-0 top-16 h-full w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white">
      {topFolders.map((folder, index) => (
        <div key={index} className="mb-3">
          <div className="flex space-x-1.5 items-center mb-1 cursor-pointer" onClick={onToggle(folder.folder_name ?? "")}>
            <SvgIcon name={folder.opened ? "folder" : "closed-folder"} className="w-4 h-4" />
            <div className="text-[13px] leading-1.5">{folder.folder_name}</div>
          </div>
          {folder.opened && (
            <div className="pl-5">
              {folder.dashboards?.map((dashboard, jIndex) => (
                <div
                  key={jIndex}
                  className="cursor-pointer flex space-x-1.5 items-center"
                  onClick={() => changeActiveTab(dashboard, folder.folder_name)}
                >
                  <SvgIcon name="grid" className="w-4 h-4 shrink-0" />
                  <div className="text-[13px] leading-1.5 whitespace-nowrap">{dashboard.dashboard_name}</div>
                </div>
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default LeftView;
