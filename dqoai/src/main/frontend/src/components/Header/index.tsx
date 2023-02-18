import React from 'react';
import NotificationMenu from '../NotificationMenu';
import Logo from '../Logo';
import clsx from 'clsx';
import { useHistory, useLocation } from 'react-router-dom';
import { CheckTypes } from "../../shared/routes";
import HelpMenu from "../HelpMenu";

const Header = () => {
  const history = useHistory();
  const location = useLocation();

  const onClick = (route: string) => () => {
    history.push(route)
  }

  return (
    <div
      className="fixed top-0 left-0 right-0 min-h-16 max-h-16 bg-white shadow-header flex items-center justify-between z-10 border-b border-gray-300 px-4"
    >
      <div className="flex space-x-2">
        <div onClick={() => history.push('/')}>
          <Logo className="w-30 cursor-pointer" />
        </div>
        <div className="flex items-center">
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith(`/${CheckTypes.SOURCES}`) ? 'font-bold' : '' )}
            onClick={onClick(`/${CheckTypes.SOURCES}`)}
          >
            Source
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith(`/${CheckTypes.PROFILING}`) ? 'font-bold' : '' )}
            onClick={onClick(`/${CheckTypes.PROFILING}`)}
          >
            Profiling
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith(`/${CheckTypes.CHECKS}`) ? 'font-bold' : '' )}
            onClick={onClick(`/${CheckTypes.CHECKS}`)}
          >
            Whole table checks
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith(`/${CheckTypes.TIME_PARTITIONED}`) ? 'font-bold' : '' )}
            onClick={onClick(`/${CheckTypes.TIME_PARTITIONED}`)}
          >
            Time period checks
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname === '/dashboards' ? 'font-bold' : '' )}
            onClick={onClick('/dashboards')}
          >
            Data Quality Dashboards
          </div>
        </div>
      </div>
      <div>
        <HelpMenu />
        <NotificationMenu />
      </div>
    </div>
  );
};

export default Header;
