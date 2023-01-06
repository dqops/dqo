import React from 'react';
import NotificationMenu from '../NotificationMenu';
import Logo from '../Logo';
import clsx from 'clsx';
import { useHistory, useLocation } from 'react-router-dom';

interface HeaderProps {
  sidebarWidth: number;
  isHome?: boolean;
}

const Header = ({ sidebarWidth }: HeaderProps) => {
  const history = useHistory();
  const location = useLocation();

  return (
    <div
      className="fixed top-0 right-0 min-h-16 bg-white shadow-header flex items-center justify-between z-10 border-b border-gray-300 px-4"
      style={{ left: sidebarWidth }}
    >
      <div className="flex space-x-2">
        <div onClick={() => history.push('/')}>
          <Logo className="w-30 cursor-pointer" />
        </div>
        <div className="flex items-center">
          <div
            className={clsx("px-4 cursor-pointer", location.pathname === '/checks' ? 'font-bold' : '' )}
            onClick={() => history.push('/checks')}
          >
            Data Quality Checks
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname === '/dashboards' ? 'font-bold' : '' )}
            onClick={() => history.push('/dashboards')}
          >
            Data Quality Dashboards
          </div>
        </div>
      </div>
      <NotificationMenu />
    </div>
  );
};

export default Header;
