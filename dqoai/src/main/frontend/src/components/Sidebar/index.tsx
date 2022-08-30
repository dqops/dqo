import React from 'react';

import { Link, useLocation } from 'react-router-dom';
import clsx from 'clsx';
import Logo from "../Logo";
import SvgIcon from "../SvgIcon";
import Button from "../Button";
import ConnectionsTree from '../ConnectionsTree';

interface ISidebarProps {
}

const Sidebar: React.FC<ISidebarProps> = () => {
  const navItems = [
    {
      path: '/',
      icon: 'chart-bar',
      name: 'Dashboard'
    },
    {
      path: '/customers',
      icon: 'users',
      name: 'Customers'
    },
    {
      path: '/products',
      icon: 'shopping-bag',
      name: 'Products'
    },
    {
      path: '/account',
      icon: 'user',
      name: 'Account'
    },
    {
      path: '/settings',
      icon: 'cog',
      name: 'Settings'
    },
    {
      path: '/login',
      icon: 'lock',
      name: 'Login'
    },
    {
      path: '/register',
      icon: 'user-add',
      name: 'Register'
    },
    {
      path: '/404',
      icon: 'x-circle',
      name: 'Error'
    }
  ];
  const location = useLocation();

  const isActivePath = (path: string) => {
    return path === location.pathname;
  }

  return (
    <div className="fixed top-0 left-0 bg-sidebar h-screen overflow-auto w-70 flex flex-col bg-white">
      <ConnectionsTree />
      {/*<div className="px-4">*/}
      {/*  {navItems.map((item) => (*/}
      {/*    <Link*/}
      {/*      data-testid="side-nav-link"*/}
      {/*      key={item.name}*/}
      {/*      to="#"*/}
      {/*      className={clsx(*/}
      {/*        'px-4 py-2 hover:bg-opacity-8 hover:bg-white cursor-pointer rounded-lg mb-1 flex items-center font-semibold text-sm leading-1.5',*/}
      {/*        isActivePath(item.path) ? 'bg-opacity-8 bg-white text-green-500' : 'text-gray-100'*/}
      {/*      )}*/}
      {/*    >*/}
      {/*      <div className="flex items-center space-x-2">*/}
      {/*        <SvgIcon name={item.icon} className="w-5 h-5" />*/}
      {/*        <div className="leading-2">{item.name}</div>*/}
      {/*      </div>*/}
      {/*    </Link>*/}
      {/*  ))}*/}
      {/*</div>*/}
      
      {/*<div className="border-gray-700 border-b w-full" />*/}
    </div>
  );
};

export default Sidebar;
