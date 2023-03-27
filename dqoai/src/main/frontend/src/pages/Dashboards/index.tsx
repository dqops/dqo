import React, { useEffect } from 'react';
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { getAllDashboards } from "../../redux/actions/dashboard.actions";
import DashboardLayout from "../../components/DashboardLayout";
import PageTabs from "../../components/PageTabs";
import { useDashboard } from "../../contexts/dashboardContext";
import clsx from 'clsx';
import { ITab } from "../../shared/interfaces";
import { AuthenticatedDashboardModel } from "../../api";

const Dashboards = () => {
  const dispatch = useActionDispatch();
  const { tabs, activeTab, setActiveTab, closeTab, openedDashboards } = useDashboard();

  useEffect(() => {
    dispatch(getAllDashboards());
  }, []);

  return (
    <DashboardLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={setActiveTab}
          onRemoveTab={closeTab}
        />
        <div className="flex-1 bg-white border border-gray-300 flex-auto relative">
          {!tabs?.length && (
            <div className={clsx('bg-white absolute top-0 left-0 w-full h-full flex items-center justify-center overflow-auto')}>
              <div>Choose a data quality dashboard from the tree</div>
            </div>
          )}
          {
            tabs.map((tab: ITab) => {
              const dashboard = openedDashboards?.find((item: AuthenticatedDashboardModel) => item.dashboard?.dashboard_name === tab.value);

              return (
                <div
                  key={tab.value}
                  className={clsx('bg-white absolute top-0 left-0 w-full h-full flex items-center justify-center overflow-auto', activeTab === tab.value ? 'z-50' : 'z-0')}
                >
                  {dashboard ? (
                    <iframe
                      className="absolute top-0 left-0"
                      key={tab.value}
                      src={dashboard?.authenticated_dashboard_url}
                      width={dashboard?.dashboard?.width || 0}
                      height={dashboard?.dashboard?.height || 0}
                    />
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
