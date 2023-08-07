import React from 'react';

import PropTypes from 'prop-types';

import LeftView from '../Dashboards/LeftView';
import Header from '../Header';
import { useDashboard } from '../../contexts/dashboardContext';

interface LayoutProps {
  children?: any;
}

const DashboardLayout = ({ children }: LayoutProps) => {
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
          {children}
        </div>
      </div>
    </div>
  );
};

DashboardLayout.propTypes = {
  children: PropTypes.any.isRequired
};

export default DashboardLayout;
