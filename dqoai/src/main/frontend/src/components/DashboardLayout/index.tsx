import React from 'react';

import PropTypes from 'prop-types';

import TopView from "../Dashboards/TopView";
import LeftView from "../Dashboards/LeftView";
import Header from "../Header";

interface LayoutProps {
  children?: any;
}

const DashboardLayout = ({ children }: LayoutProps) => {
  return (
    <div className="flex min-h-screen overflow-hidden">
      <Header sidebarWidth={280} />
      <LeftView />
      <div className="flex flex-1">
        <div
          className="mt-16 p-5 flex-1 overflow-auto"
          style={{
            marginLeft: 280,
            maxWidth: `calc(100vw - 280px)`
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
