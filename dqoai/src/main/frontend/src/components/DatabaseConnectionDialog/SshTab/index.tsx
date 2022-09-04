import React from 'react';
import SectionWrapper from '../SectionWrapper';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Checkbox from '../../Checkbox';
import Select from '../../Select';

const SshTab = () => {
  return (
    <div className="text-sm mb-8 leading-1.5 bg-white py-4 px-2 border border-gray-300">
      <div className="mb-4 flex justify-between">
        <div>
          <Checkbox label="Use SSH Tunnel" />
        </div>
        
        <Select options={[]} label="Profile:" placeholder="" />
      </div>
      <SectionWrapper title="Settings" className="mb-4">
        <div className="flex items-center mb-3">
          <div className="w-40">Host/IP:</div>
          <div className="flex flex-auto space-x-2">
            <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" />
            <div className="flex items-center space-x-2">
              <div className="">Port:</div>
              <input className="px-2 py-1 focus:outline-none border text-sm border-gray-200" type="number" defaultValue={22} />
            </div>
          </div>
        </div>
        <div className="flex items-center mb-3">
          <div className="w-40">Username:</div>
          <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" />
        </div>
        <div className="flex items-center mb-3">
          <div className="w-40">Authentication Method:</div>
          <Select options={[]} placeholder="Password" />
        </div>
        <div className="flex items-center">
          <div className="w-40">Password:</div>
          <div className="flex-auto flex items-center space-x-2">
            <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" />
            <div>
              <Checkbox label="Save Password" />
            </div>
          </div>
        </div>
      </SectionWrapper>
    
      <div className="flex justify-between items-center mt-8">
        <div className="flex space-x-3 items-center text-sm">
          <Button label="Test tunnel configuration" color="secondary" className="py-2 px-2 !text-sm !text-black rounded-md" />
  
          <div className="flex space-x-2 items-center">
            <SvgIcon name="info" className="w-4 text-blue-500" />
            <div>You can use variables in SSH parameters</div>
          </div>
        </div>
        <a className="text-blue-700" href="https://dbeaver.com/docs/wiki/SSH-Configuration/" target="_blank">SSH Documentation</a>
      </div>
    </div>
  );
};

export default SshTab;
