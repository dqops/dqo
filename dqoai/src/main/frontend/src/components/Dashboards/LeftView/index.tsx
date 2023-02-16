import React, { useState } from "react";
import { useSelector } from "react-redux";
import { IRootState } from "../../../redux/reducers";
import SvgIcon from "../../SvgIcon";
import { useDashboard } from "../../../contexts/dashboardContext";
import { DashboardsFolderSpec } from "../../../api";

interface FolderLevelProps {
  folder: DashboardsFolderSpec;
  defaultOpen?: boolean;
  parents: DashboardsFolderSpec[];
}

const FolderLevel = ({ folder, defaultOpen = false, parents }: FolderLevelProps) => {
  const [isOpened, setIsOpened] = useState(defaultOpen);
  const { changeActiveTab } = useDashboard();
  return (
    <div className="mb-3">
      <div className="flex space-x-1.5 items-center mb-1 cursor-pointer" onClick={() => setIsOpened(prev => !prev)}>
        <SvgIcon name={isOpened ? "folder" : "closed-folder"} className="w-4 h-4 min-w-4" />
        <div className="text-[13px] leading-1.5">{folder.folder_name}</div>
      </div>
      {isOpened && (
        <div className="pl-5">
          {folder.folders?.map((f, index) => (
            <FolderLevel folder={f} key={`${f.folder_name}-${index}`} parents={[...parents, folder]} />
          ))}
          {folder.dashboards?.map((dashboard, jIndex) => (
            <div
              key={jIndex}
              className="cursor-pointer flex space-x-1.5 items-center"
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

  return (
    <div className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white">
      {dashboardFolders.map((folder, index) => (
        <FolderLevel
          folder={folder}
          key={`${folder.folder_name}-${index}`}
          defaultOpen={true}
          parents={[]}
        />
      ))}
    </div>
  );
};

export default LeftView;
