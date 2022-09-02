import React, {useState} from 'react';

import clsx from 'clsx';

import Portal from '../../hoc/Portal';
import LogoSmall from '../LogoSmall';
import Button from '../Button';
import {Tabs} from '../Tabs';
import MainTab from './MainTab';
import DriverPropertiesTab from './DriverPropertiesTab';
import SshTab from './SshTab';
import ProxyTab from './ProxyTab';

const tabs = [
  {
    label: 'Main',
    value: 'main'
  },
  {
    label: 'Driver Properties',
    value: 'driver'
  },
  {
    label: 'SSH',
    value: 'ssh'
  },
  {
    label: 'Proxy',
    value: 'proxy'
  },
];

interface IDatabaseConnectionDialogProps {
  open: boolean;
  onClose: () => void;
  title: string;
  description: string;
  logo: string;
}

const DatabaseConnectionDialog: React.FC<IDatabaseConnectionDialogProps> = ({
  open,
  onClose,
  title,
  description,
  logo,
}) => {
  const [activeTab, setActiveTab] = useState('main');

  return (
    <>
      {open && (
        <Portal>
          <div
            className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-1000 outline-none focus:outline-none"
            onClick={onClose}
          >
            <div
              className={clsx(
                'relative w-200 my-6 mx-auto rounded-lg bg-white',
              )}
              onClick={(e) => e.stopPropagation()}
            >
              <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full outline-none focus:outline-none">
                <div className="rounded-t p-3 pb-0 border-b">
                  <div className="flex items-center space-x-2">
                    <LogoSmall className="h-4" />
                    <div className="">Connect a database</div>
                  </div>
                  <div className="flex justify-between items-start mb-3">
                    <div className="mt-3">
                      <div className="text-sm font-bold text-black mb-2">{title}</div>
                      <div className="text-base">{description}</div>
                    </div>
                    <img src={logo} className="h-16" alt="db logo" />
                  </div>
                </div>
                <div className="bg-gray-50 p-3 border-b">
                  <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
                  {activeTab === 'main' && <MainTab />}
                  {activeTab === 'driver' && <DriverPropertiesTab />}
                  {activeTab === 'ssh' && <SshTab />}
                  {activeTab === 'proxy' && <ProxyTab />}
                </div>
                <div className="flex items-center justify-end rounded-b space-x-4 p-3">
                  <Button label="Back" color="secondary" className="py-2 !text-sm w-30 rounded-md" />
                  <Button label="Next" color="secondary" className="py-2 !text-sm w-30 rounded-md" />
                  <Button label="Finish" color="secondary" className="py-2 !text-sm w-30 rounded-md" />
                  <Button label="Cancel" color="secondary" className="py-2 !text-sm w-30 rounded-md" />
                </div>
              </div>
            </div>
          </div>
          <div className="opacity-25 fixed inset-0 z-40 bg-black" />
        </Portal>
      )}
    </>
  );
};

export default DatabaseConnectionDialog;
