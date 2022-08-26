import React from 'react';

import { Link, useLocation } from 'react-router-dom';
import clsx from 'clsx';
import Logo from "../Logo";
import SvgIcon from "../SvgIcon";
import Button from "../Button";

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
      <div className="p-6">
        <Logo />
      </div>
      <div className="px-4">
        <div
          className="flex items-center justify-between cursor-pointer px-6 py-3 rounded bg-white bg-opacity-4"
        >
          <div>
            <div className="text-gray-100 text-sm font-semibold mb-1">Acme Inc</div>
            <div className="text-gray-200 text-xs">
              Your tier
              {' '}
              : Premium
            </div>
          </div>
          <SvgIcon name="selector" className="w-3.5 h-3.5 text-gray-200" />
        </div>
      </div>

      <div className="my-6 border-gray-700 border-b w-full" />

      <div className="px-4">
        {navItems.map((item) => (
          <Link
            data-testid="side-nav-link"
            key={item.name}
            to="#"
            className={clsx(
              'px-4 py-2 hover:bg-opacity-8 hover:bg-white cursor-pointer rounded-lg mb-1 flex items-center font-semibold text-sm leading-1.5',
              isActivePath(item.path) ? 'bg-opacity-8 bg-white text-green-500' : 'text-gray-100'
            )}
          >
            <div className="flex items-center space-x-2">
              <SvgIcon name={item.icon} className="w-5 h-5" />
              <div className="leading-2">{item.name}</div>
            </div>
          </Link>
        ))}
      </div>

      <div className="border-gray-700 border-b w-full" />

      <div className="px-4 py-6">
        <div className="text-sm text-gray-50 mb-2">
          Need more features?
        </div>
        <div className="text-xs text-gray-500 leading-1.5">
          Check out our Pro solution template.
        </div>
        <div
          className="flex mt-4 mx-auto w-40"
        >
          <img
            alt="Go to pro"
            src="/images/sidebar_pro.png"
            className="w-full"
          />
        </div>
        <Link
          to="https://material-kit-pro-react.devias.io/"
        >
          <Button
            label="Pro Live Preview"
            variant="contained"
            color="secondary"
            className="w-full mt-4"
            textSize="sm"
          />
        </Link>
      </div>

    </div>
  );
};

export default Sidebar;
