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
import { DashboardsFolderSpec, DashboardSpec } from '../../../api';
import { getDashboardTooltipState } from '../../../redux/actions/dashboard.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import Switch from '../../Switch';

interface FolderLevelProps {
  folder: DashboardsFolderSpec;
  parents: DashboardsFolderSpec[];
}

const LeftView = () => {
  const [selected, setSelected] = useState('');
  const sidebarRef = useRef<HTMLDivElement>(null);
  const [isResizing, setIsResizing] = useState(false);
  const [showAdvanced, setShowAdvanced] = useState(localStorage.getItem('show-advanced-dashboards') === 'true');
  const { dashboardFolders } = useSelector(
    (state: IRootState) => state?.dashboard
  );
  const { openDashboardFolder, sidebarWidth, setSidebarWidth } = useDashboard();
  const dispatch = useActionDispatch();
  const startResizing = useCallback((mouseDownEvent: MouseEvent) => {
    setIsResizing(true);
    mouseDownEvent.preventDefault();
    mouseDownEvent.stopPropagation();
  }, []);

  const stopResizing = useCallback(() => {
    setIsResizing(false);
  }, []);

  const showAdvancedChanged = (advanced : boolean) => {
    setShowAdvanced(advanced);
    localStorage.setItem('show-advanced-dashboards', advanced ? 'true' : 'false');
  };

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
        mouseMoveEvent.preventDefault();
        mouseMoveEvent.stopPropagation();
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
    const {
      changeActiveTab,
      dashboardStatus,
      toggleDashboardFolder,
      activeTab
    } = useDashboard();

    const key = useMemo(
      () => [...parents, folder].map((item) => item.folder_name).join('-'),
      [folder, parents]
    );

    useEffect(() => {
      if (selected !== activeTab) {
        setSelected(activeTab);
      }
    }, [activeTab]);

    const [mouseEnterTimeout, setMouseEnterTimeout] = useState<
      NodeJS.Timeout | undefined
    >(undefined);
    const handleMouseEnter = (
      e: React.MouseEvent<HTMLDivElement, MouseEvent>,
      label: string,
      url: string
    ) => {
      setMouseEnterTimeout(
        setTimeout(() => {
          const height = e.clientY;
          dispatch(getDashboardTooltipState({ height, label, url }));
        }, 100)
      );
    };

    const handleMouseLeave = () => {
      if (mouseEnterTimeout) {
        clearTimeout(mouseEnterTimeout);
      }
      dispatch(
        getDashboardTooltipState({
          height: undefined,
          label: undefined,
          url: undefined
        })
      );
    };

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
            {folder.folders
              ?.map((folder, index) => [folder, index] as [folder: DashboardsFolderSpec, index: number])
              ?.filter((folderIndexTuple) => showAdvanced || folderIndexTuple[0].standard)
              ?.map((folderIndexTuple) => (
              <FolderLevel
                folder={folderIndexTuple[0]}
                key={`${folderIndexTuple[0].folder_name}-${folderIndexTuple[1]}`}
                parents={[...parents, folder]}
              />
            ))}
            {folder?.dashboards
               ?.map((dashboard : DashboardSpec, jIndex : number) => [dashboard, jIndex] as [dashboard: DashboardSpec, jIndex: number])
               ?.filter((dashboardIndexTuple) => showAdvanced || dashboardIndexTuple[0].standard)
               ?.map((dashboardIndexTuple) => (
              <div
                key={dashboardIndexTuple[1]}
                className={
                  selected === [key, dashboardIndexTuple[0]?.dashboard_name].join('-')
                    ? 'group cursor-pointer flex space-x-1.5 items-center mb-1 h-5 bg-gray-300 hover:bg-gray-300 relative'
                    : 'group cursor-pointer flex space-x-1.5 items-center mb-1 h-5 hover:bg-gray-300 relative'
                }
                onMouseDown={() => {
                  changeActiveTab(
                    dashboardIndexTuple[0],
                    folder.folder_name,
                    parents,
                    [
                      key,
                      dashboardIndexTuple[0]?.dashboard_name ? dashboardIndexTuple[0]?.dashboard_name : ''
                    ].join('-'),
                    true
                  );
                  setSelected(
                    [
                      key,
                      dashboardIndexTuple[0]?.dashboard_name ? dashboardIndexTuple[0]?.dashboard_name : ''
                    ].join('-')
                  );
                }}
              >
                <SvgIcon name="grid" className="w-4 h-4 min-w-4 shrink-0" />
                <div
                  className="text-[13px] leading-1.5 whitespace-nowrap"
                  onMouseEnter={(e) =>
                    handleMouseEnter(
                      e,
                      dashboardIndexTuple[0]?.dashboard_name ?? '',
                      dashboardIndexTuple[0].url ?? ''
                    )
                  }
                  onMouseLeave={() => handleMouseLeave()}
                >
                  {dashboardIndexTuple[0]?.dashboard_name}
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
      className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 pl-4 pt-6 bg-white overflow-x-hidden"
      ref={sidebarRef}
      style={{ width: sidebarWidth }}
    >
      <div className="w-full h-10 flex items-left gap-x-4 text-sm">
        <Switch
          checked={showAdvanced}
          onChange={showAdvancedChanged}
        />
        Show advanced dashboards
      </div>

      {dashboardFolders
        ?.map((folder, index) => [folder, index] as [folder: DashboardsFolderSpec, index: number])
        ?.filter((folderIndexTuple) => showAdvanced || folderIndexTuple[0].standard)
        ?.map((folderIndexTuple) => (
        <FolderLevel
          folder={folderIndexTuple[0]}
          key={`${folderIndexTuple[0].folder_name}-${folderIndexTuple[1]}`}
          parents={[]}
        />
      ))}

      <div
        className="cursor-ew-resize fixed bottom-0 w-2 transform -translate-x-1/2 z-20 top-16"
        onMouseDown={(event) => startResizing(event as any)}
        style={{ left: sidebarWidth, userSelect: 'none' }}
      />
    </div>
  );
};

export default LeftView;
