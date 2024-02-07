import React, { ReactNode } from 'react';
import LeftView from '../Dashboards/LeftView';
import Header from '../Header';
import { useDashboard } from '../../contexts/dashboardContext';
import { ROUTES } from '../../shared/routes';
import Dashboards from '../../pages/Dashboards';

interface LayoutProps {
  route: string;
}

const DashboardLayout = ({ route }: LayoutProps) => {
  const getComponent = () => {
    switch (route) {
      case ROUTES.PATTERNS.DASHBOARDS:
        return <Dashboards />;
      default:
        return null;
    }
  };

  const renderComponent: ReactNode = getComponent();

  const { sidebarWidth } = useDashboard();
  return (
    <div className="flex min-h-screen overflow-hidden z-30">
      <Header />
      <LeftView />
      <div className="flex flex-1">
        <div
          className="mt-16 p-5 flex-1 overflow-auto"
          style={{
            marginLeft: sidebarWidth,
            maxWidth: `calc(100vw - ${sidebarWidth}px)`
          }}
        >
          {renderComponent}
        </div>
      </div>
    </div>
  );
};

export default DashboardLayout;
