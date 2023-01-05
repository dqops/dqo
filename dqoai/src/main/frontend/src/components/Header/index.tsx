import React from 'react';
import NotificationMenu from '../NotificationMenu';
import Logo from '../Logo';
import SvgIcon from '../SvgIcon';
import clsx from 'clsx';
import { useHistory } from 'react-router-dom';

interface HeaderProps {
  sidebarWidth: number;
  isHome?: boolean;
}

const Header = ({ sidebarWidth, isHome }: HeaderProps) => {
  const history = useHistory();
  return (
    <div
      className={clsx(
        "fixed top-0 right-0 min-h-16 bg-white shadow-header flex items-center justify-between z-10 border-b border-gray-300",
        isHome ? 'px-2' : 'px-4'
      )}
      style={{ left: sidebarWidth }}
    >
      <div className="flex space-x-2">
        {isHome && (
          <SvgIcon name="chevron-right" className="w-6 cursor-pointer" />
        )}
        <div onClick={() => history.push('/')}>
          <Logo className="w-30 cursor-pointer" />
        </div>
      </div>
      <NotificationMenu />
    </div>
  );
};

export default Header;
