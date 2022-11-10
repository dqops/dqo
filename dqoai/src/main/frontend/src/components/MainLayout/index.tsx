import React from 'react';

import PropTypes from 'prop-types';

import Header from '../Header';
import Sidebar from '../Sidebar';

interface LayoutProps {
  children?: any;
}

const MainLayout = ({ children }: LayoutProps) => {
  return (
    <div className="flex min-h-screen">
      <Sidebar />
      <div className="flex flex-1">
        <Header />
        <div className="ml-70 mt-16 p-5 flex-1 max-h-container max-w-container overflow-auto">{children}</div>
      </div>
    </div>
  );
};

MainLayout.propTypes = {
  children: PropTypes.any.isRequired
};

export default MainLayout;
