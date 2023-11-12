import React, { useEffect, useRef, useState } from 'react';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { getAllDashboards } from '../../redux/actions/dashboard.actions';
import DashboardLayout from '../../components/DashboardLayout';
import PageTabs from '../../components/PageTabs';
import { useDashboard } from '../../contexts/dashboardContext';
import clsx from 'clsx';
import { ITab } from '../../shared/interfaces';
import { AuthenticatedDashboardModel } from '../../api';
import { TabOption } from '../../components/PageTabs/tab';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import { useTree } from '../../contexts/treeContext';

const Dashboards = () => {
  const dispatch = useActionDispatch();
  const { dashboardTooltipState } = useSelector(
    (state: IRootState) => state.dashboard
  );

  const { objectNotFound, setObjectNotFound } = useTree();

  const { isLicenseFree } = useSelector((state: IRootState) => state.job || {});
  const {
    tabs,
    activeTab,
    setActiveTab,
    closeTab,
    openedDashboards,
    error,
    sidebarWidth
  } = useDashboard();

  useEffect(() => {
    dispatch(getAllDashboards());
  }, []);

  const [isTooltipVisible, setIsTooltipVisible] = useState(false);
  const [imageWidth, setImageWidth] = useState(1);
  const timerRef = useRef<NodeJS.Timeout | null>(null);

  const showTooltip = () => {
    setIsTooltipVisible(true);

    if (timerRef.current) {
      clearTimeout(timerRef.current);
    }

    timerRef.current = setTimeout(() => {
      setIsTooltipVisible(false);
    }, 3000);
  };

  useEffect(() => {
    if (dashboardTooltipState.label) {
      showTooltip();
    } else {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
        timerRef.current = null;
      }
    }
    setImageWidth(1);
    return () => {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
        timerRef.current = null;
      }
    };
  }, [dashboardTooltipState.label]);

  return (
    <DashboardLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={(tab: TabOption) => setActiveTab(tab.value)}
          onRemoveTab={closeTab}
          limit={7}
        />
        <div className=" bg-white border border-gray-300 flex-auto relative">
          {!tabs?.length && (
            <div
              className={clsx(
                'bg-white absolute top-0 left-0 w-full h-full flex items-center justify-center overflow-auto'
              )}
            >
              <div>Choose a data quality dashboard from the tree</div>
            </div>
          )}
          {tabs.map((tab: ITab) => {
            const dashboard = openedDashboards?.find(
              (item: AuthenticatedDashboardModel) =>
                (item?.folder_path || '')
                  .split('/')
                  .concat(item?.dashboard?.dashboard_name || '')
                  .join('-') === tab.value
            );

            return (
              <div
                key={tab.value}
                className={clsx(
                  'bg-white absolute top-0 left-0 w-full h-full flex items-center justify-center overflow-auto',
                  activeTab === tab.value ? 'z-20' : 'z-0'
                )}
              >
                {dashboard?.authenticated_dashboard_url ? (
                  <iframe
                    className="absolute top-0 left-0"
                    key={tab.value}
                    src={dashboard?.authenticated_dashboard_url}
                    width={dashboard?.dashboard?.width || 0}
                    height={dashboard?.dashboard?.height || 0}
                  />
                ) : (
                  <div>
                    {error[tab.value]
                      ? 'DQOps Cloud API Key is invalid. Your trial period has expired or a new DQOps version was released. Please run "cloud login" from the DQOps shell to get a new key'
                      : 'Choose a data quality dashboard from the tree'}
                  </div>
                )}
              </div>
            );
          })}
        </div>
        {dashboardTooltipState.height && isTooltipVisible ? (
          <div
            className={clsx(
              'py-2 px-2 bg-gray-800 text-white absolute z-1000 text-xs text-left rounded-1 whitespace-normal'
            )}
            style={{
              left: `${sidebarWidth}px`,
              top: `${dashboardTooltipState.height}px`
            }}
          >
            {dashboardTooltipState.label}
            {imageWidth < 400 && (
              <img
                alt=""
                src={`${dashboardTooltipState.url}/thumbnail`}
                style={{ display: 'block' }}
                className="pt-2 max-h-100 max-w-100"
                loading="eager"
                onLoad={(e) =>
                  setImageWidth((e.target as HTMLImageElement).width)
                }
              />
            )}
          </div>
        ) : null}
        {isLicenseFree && (
          <div
            className="z-40 text-red-500 bg-white bg-opacity-50"
            style={{ position: 'fixed', top: '94%', right: '2%' }}
          >
            This DQOps instance is not licensed. You are using a complimentary
            data quality warehousing service that is limited <br />
            to showing the results of 5 tables from the first connected data
            source. Please contact DQOps sales for an upgrade.
          </div>
        )}
      </div>
      <ConfirmDialog
        open={objectNotFound}
        onConfirm={() =>
          new Promise(() => {
            closeTab(activeTab), setObjectNotFound(false);
          })
        }
        isCancelExcluded={true}
        onClose={() => {
          closeTab(activeTab), setObjectNotFound(false);
        }}
        message="The definition of this object was deleted in DQOps user home. The tab will be closed."
      />
    </DashboardLayout>
  );
};

export default Dashboards;
