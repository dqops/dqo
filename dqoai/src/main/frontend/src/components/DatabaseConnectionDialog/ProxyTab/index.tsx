import React from 'react';
import SectionWrapper from '../SectionWrapper';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import {Checkbox} from '../../Checkbox';
import Select from '../../Select';

const SshTab = () => {
  return (
    <div className="text-sm mb-8 leading-1.5 bg-white py-4 px-2 border border-gray-300">
      <div className="mb-4 flex justify-between">
        <div>
          <Checkbox label="Use Proxy" />
        </div>
        
        <Select options={[]} label="Profile:" placeholder="" />
      </div>
      <SectionWrapper title="SOCKS" className="mb-4">
        <div className="flex items-center mb-3 space-x-4">
          <div className="flex flex-1 space-x-2">
            <div className="w-20">Host/IP:</div>
            <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" />
          </div>
          <div className="flex flex-1 space-x-2">
            <div className="">Port:</div>
            <input className="px-2 py-1 focus:outline-none border text-sm border-gray-200" type="number" defaultValue={22} />
          </div>
        </div>
        <div className="flex items-start space-x-4">
          <div className="flex flex-1 space-x-2">
            <div className="w-20">Username:</div>
            <div className="flex-auto">
              <input className="px-2 py-1 w-full focus:outline-none border text-sm border-gray-200 flex-auto" />
              <div className="mt-2">
                <Checkbox label="Save Password" />
              </div>
            </div>
          </div>
          <div className="flex flex-1 space-x-2">
            <div className="">Password:</div>
            <input className="px-2 py-1 focus:outline-none border text-sm border-gray-200 flex-auto" />
          </div>
        </div>
      </SectionWrapper>
  
      <a className="text-blue-700" href="https://dbeaver.com/docs/wiki/SSH-Configuration/" target="_blank">Open global network preferences</a>
    </div>
  );
};

export default SshTab;
