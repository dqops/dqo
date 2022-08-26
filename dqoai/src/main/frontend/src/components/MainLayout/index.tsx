import React from 'react';
import PropTypes from 'prop-types';

import Header from '../Header';
import Sidebar from '../Sidebar';

interface LayoutProps {
  children?: any;
}

const MainLayout: React.FC<LayoutProps> = ({
children,
}) => {
  return (
    <div className="flex flex-col min-h-screen">
      <Sidebar />
      <div className="">
        <Header />
        <div>{children}</div>
      </div>
    </div>
  );
};

MainLayout.propTypes = {
  children: PropTypes.any.isRequired,
};

export default MainLayout;
