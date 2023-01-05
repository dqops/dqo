import React, { useEffect } from 'react';
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { getAllDashboards } from "../../redux/actions/dashboard.actions";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import DashboardLayout from "../../components/DashboardLayout";
import PageTabs from "../../components/PageTabs";
import { useDashboard } from "../../contexts/dashboardContext";
import clsx from 'clsx';
import { ITab } from "../../shared/interfaces";

const Dashboards = () => {
  const dispatch = useActionDispatch();
  const { dashboardFolders }  = useSelector((state: IRootState) => state.dashboard);
  const { tabs, activeTab, setActiveTab, closeTab, onAddTab } = useDashboard();

  useEffect(() => {
    dispatch(getAllDashboards());
  }, []);

  const currentDashboard = dashboardFolders[0]?.dashboards ? dashboardFolders[0]?.dashboards[0] : undefined;

  return (
    <DashboardLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={setActiveTab}
          onRemoveTab={closeTab}
          onAddTab={onAddTab}
        />
        <div className="flex-1 bg-white border border-gray-300 flex-auto relative">
          {!tabs?.length && (
            <div className={clsx('bg-white absolute top-0 left-0 w-full h-full flex items-center justify-center overflow-auto')}>
              <div>Choose a data quality dashboard from the tree</div>
            </div>
          )}
          {
            tabs.map((tab: ITab) => {
              const folder = dashboardFolders.find((folder) => !!folder.dashboards?.find((item) => item.dashboard_name === tab.value));
              const dashboard = folder?.dashboards?.find((item) => item.dashboard_name === tab.value);
              return (
                <div
                  key={tab.value}
                  className={clsx('bg-white absolute top-0 left-0 w-full h-full flex items-center justify-center overflow-auto', activeTab === tab.value ? 'z-50' : 'z-0')}
                >
                  {dashboard ? (
                    <div>
                      <iframe
                        key={tab.value}
                        src={dashboard?.url}
                        width={dashboard?.width || 0}
                        height={dashboard?.height || 0}
                      />
                    </div>
                  ) : (
                    <div>
                      Choose a data quality dashboard from the tree
                    </div>
                  )}
                </div>
              )
            })
          }
        </div>
      </div>
    </DashboardLayout>
  );
};

export default Dashboards;
