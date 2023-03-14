import React, { useEffect, useMemo } from "react";
import { useSelector } from "react-redux";
import { IRootState } from "../../../redux/reducers";
import SvgIcon from "../../SvgIcon";
import { useDashboard } from "../../../contexts/dashboardContext";
import { DashboardsFolderSpec } from "../../../api";

interface FolderLevelProps {
  folder: DashboardsFolderSpec;
  parents: DashboardsFolderSpec[];
}

const FolderLevel = ({ folder, parents }: FolderLevelProps) => {
  const { changeActiveTab, dashboardStatus, toggleDashboardFolder } = useDashboard();
  const key = useMemo(() => [...parents, folder].map((item) => item.folder_name).join('-'), [folder, parents]);

  return (
    <div>
      <div className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer" onClick={() => toggleDashboardFolder(key)}>
        <SvgIcon name={dashboardStatus[key] ? "folder" : "closed-folder"} className="w-4 h-4 min-w-4" />
        <div className="text-[13px] leading-1.5 truncate">{folder.folder_name}</div>
      </div>
      {!!dashboardStatus[key] && (
        <div className="pl-5">
          {folder.folders?.map((f, index) => (
            <FolderLevel folder={f} key={`${f.folder_name}-${index}`} parents={[...parents, folder]} />
          ))}
          {folder.dashboards?.map((dashboard, jIndex) => (
            <div
              key={jIndex}
              className="cursor-pointer flex space-x-1.5 items-center mb-1 h-5"
              onClick={() => changeActiveTab(dashboard, folder.folder_name, parents)}
            >
              <SvgIcon name="grid" className="w-4 h-4 min-w-4 shrink-0" />
              <div className="text-[13px] leading-1.5 whitespace-nowrap">{dashboard.dashboard_name}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

const LeftView = () => {
  const { dashboardFolders }  = useSelector((state: IRootState) => state.dashboard);
  const { openDashboardFolder } = useDashboard();

  useEffect(() => {
    openDashboardFolder(dashboardFolders.map((item) => item.folder_name));
  }, [dashboardFolders]);

  return (
    <div className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white">
      {dashboardFolders.map((folder, index) => (
        <FolderLevel
          folder={folder}
          key={`${folder.folder_name}-${index}`}
          parents={[]}
        />
      ))}
    </div>
  );
};

export default LeftView;
