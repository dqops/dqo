import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState
} from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import SvgIcon from '../../SvgIcon';
import { useDashboard } from '../../../contexts/dashboardContext';
import { DashboardsFolderSpec } from '../../../api';
import clsx from 'clsx';

interface FolderLevelProps {
  folder: DashboardsFolderSpec;
  parents: DashboardsFolderSpec[];
}

const LeftView = () => {
  const [selected, setSelected] = useState('');
  const sidebarRef = useRef<HTMLDivElement>(null);
  const [isResizing, setIsResizing] = useState(false);
  const { dashboardFolders } = useSelector(
    (state: IRootState) => state.dashboard
  );
  const { openDashboardFolder, sidebarWidth, setSidebarWidth } = useDashboard();

  const startResizing = useCallback(() => {
    setIsResizing(true);
  }, []);

  const stopResizing = useCallback(() => {
    setIsResizing(false);
  }, []);

  useEffect(() => {
    openDashboardFolder(dashboardFolders.map((item) => item.folder_name));
  }, [dashboardFolders]);

  const resize = useCallback(
    (mouseMoveEvent: MouseEvent) => {
      if (isResizing) {
        const newWidth =
          mouseMoveEvent.clientX -
          (sidebarRef.current as HTMLDivElement).getBoundingClientRect().left;
        if (newWidth < 240 || newWidth > 700) return;

        setSidebarWidth(newWidth);
      }
    },
    [isResizing]
  );

  useEffect(() => {
    window.addEventListener('mousemove', resize);
    window.addEventListener('mouseup', stopResizing);
    return () => {
      window.removeEventListener('mousemove', resize);
      window.removeEventListener('mouseup', stopResizing);
    };
  }, [resize, stopResizing]);

  const FolderLevel = ({ folder, parents }: FolderLevelProps) => {
    const { changeActiveTab, dashboardStatus, toggleDashboardFolder, activeTab } =
      useDashboard();

    const key = useMemo(
      () => [...parents, folder].map((item) => item.folder_name).join('-'),
      [folder, parents]
    );

    const [activeTooltip, setActiveTooltip] = useState<number | null>(null);
    const [isImageLoaded, setIsImageLoaded] = useState(false);
    let timeout: NodeJS.Timeout | null = null;
    
    const handleMouseEnter = (index: number) => {
      timeout = setTimeout(() => {
        setActiveTooltip(index);
      }, 300);
    };

    const handleMouseLeave = () => {
      if (timeout) {
        clearTimeout(timeout);
        timeout = null;
      }
      setActiveTooltip(null);
    };

    const handleImageLoad = () => {
      setIsImageLoaded(true);
    };

    useEffect(() => {
      if(selected !== activeTab){
        setSelected(activeTab)
      }
    },[activeTab])

    return (
      <div>
        <div
          className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer hover:bg-gray-300"
          onClick={() => toggleDashboardFolder(key)}
        >
          <SvgIcon
            name={dashboardStatus[key] ? 'folder' : 'closed-folder'}
            className="w-4 h-4 min-w-4"
          />
          <div className="text-[13px] leading-1.5 truncate">
            {folder.folder_name}
          </div>
        </div>
        {!!dashboardStatus[key] && (
          <div className="pl-5">
            {folder.folders?.map((f, index) => (
              <FolderLevel
                folder={f}
                key={`${f.folder_name}-${index}`}
                parents={[...parents, folder]}
              />
            ))}
            {folder.dashboards?.map((dashboard, jIndex) => (
              <div
                key={jIndex}
                className={
                  selected === [key, dashboard.dashboard_name].join('-')
                    ? 'group cursor-pointer flex space-x-1.5 items-center mb-1 h-5 bg-gray-300 hover:bg-gray-300 relative'
                    : 'group cursor-pointer flex space-x-1.5 items-center mb-1 h-5 hover:bg-gray-300 relative'
                }
                onClick={() => {
                  changeActiveTab(
                    dashboard,
                    folder.folder_name,
                    parents,
                    [
                      key,
                      dashboard.dashboard_name ? dashboard.dashboard_name : ''
                    ].join('-'),
                    true
                  );
                  setSelected(
                    [
                      key,
                      dashboard.dashboard_name ? dashboard.dashboard_name : ''
                    ].join('-')
                  );
                }}
              >
                <SvgIcon name="grid" className="w-4 h-4 min-w-4 shrink-0" />
                <div className="text-[13px] leading-1.5 whitespace-nowrap" 
                      onMouseEnter={() => handleMouseEnter(jIndex)}
                      onMouseLeave={handleMouseLeave}>
                   {activeTooltip === jIndex && (
              <div className={clsx("py-2 px-2 bg-gray-800 text-white absolute z-1000 text-xs text-left rounded-1 whitespace-normal", dashboard.dashboard_name?.includes("profiling status") ? "top-5" : "bottom-5")} style={{ left: "0" }}>
                {dashboard.dashboard_name}
                <img
                src={`${dashboard.url}/thumbnail`}
                alt=""
                style={{ display: isImageLoaded ? "block" : "none" }}
                onLoad={handleImageLoad}
                className='pt-2'
                loading='eager'
                />
              {!isImageLoaded && <p className='pt-5'>Loading...</p>}
            </div>
            )}
                  {dashboard.dashboard_name}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    );
  };

  return (
    <div
      className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white overflow-x-hidden"
      ref={sidebarRef}
      style={{ width: sidebarWidth }}
    >
      {dashboardFolders.map((folder, index) => (
        <FolderLevel
          folder={folder}
          key={`${folder.folder_name}-${index}`}
          parents={[]}
        />
      ))}

      <div
        className="cursor-ew-resize fixed bottom-0 w-2 transform -translate-x-1/2 z-20 top-16"
        onMouseDown={startResizing}
        style={{ left: sidebarWidth, userSelect: "none" }}
      />
    </div>
  );
};

export default LeftView;
