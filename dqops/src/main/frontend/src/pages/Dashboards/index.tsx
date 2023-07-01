import React, { useEffect } from 'react';
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

const Dashboards = () => {
  const dispatch = useActionDispatch();
  const { isLicenseFree } = useSelector((state: IRootState) => state.job || {});
  const { tabs, activeTab, setActiveTab, closeTab, openedDashboards, error } =
    useDashboard();

  useEffect(() => {
    dispatch(getAllDashboards());
  }, []);

  return (
    <DashboardLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={(tab: TabOption) => setActiveTab(tab.value)}
          onRemoveTab={closeTab}
          limit={8}
        />
        <div className="flex-1 bg-white border border-gray-300 flex-auto relative">
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
                (item.folder_path || '')
                  .split('/')
                  .concat(item.dashboard?.dashboard_name || '')
                  .join('-') === tab.value
            );

            return (
              <div
                key={tab.value}
                className={clsx(
                  'bg-white absolute top-0 left-0 w-full h-full flex items-center justify-center overflow-auto',
                  activeTab === tab.value ? 'z-50' : 'z-0'
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
                      ? 'DQO Cloud API Key expired, please run "cloud login" from the DQO shell to get a new key'
                      : 'Choose a data quality dashboard from the tree'}
                  </div>
                )}
              </div>
            );
          })}
        </div>
        {isLicenseFree && (
          <div
            className="z-50 text-red-500 bg-white bg-opacity-50"
            style={{ position: 'fixed', top: '94%', right: '2%' }}
          >
            This DQO instance is not licensed. You are using a complimentary
            data quality warehousing service that is limited <br />
            to showing the results of 5 tables from the first connected data
            source. Please contact DQO sales for upgrade.
          </div>
        )}
      </div>
    </DashboardLayout>
  );
};

export default Dashboards;
