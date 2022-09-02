import React from 'react';
import SectionWrapper from '../SectionWrapper';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';

const MainTab = () => {
  return (
    <div className="text-sm mb-8 leading-1.5 bg-white py-4 px-2 border border-gray-300">
      <SectionWrapper title="Connection" className="mb-4">
        <div className="flex items-center mb-3">
          <div className="w-40">Project:</div>
          <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" />
        </div>
        <div className="flex items-center">
          <div className="w-40">Additional project(s):</div>
          <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" />
        </div>
      </SectionWrapper>
    
      <SectionWrapper title="Server info" className="mb-4">
        <div className="flex space-x-4">
          <div className="flex items-center space-x-2 flex-1">
            <div className="">Host:</div>
            <input
              className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200"
              defaultValue="https://www.googleapis.com/bigquery/v2"
            />
          </div>
          <div className="flex items-center space-x-2">
            <div className="">Port:</div>
            <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" type="number" defaultValue={443} />
          </div>
        </div>
      </SectionWrapper>
    
      <SectionWrapper title="Authentication (Google Cloud Auth)" className="mb-8">
        <div className="flex items-center mb-3">
          <div className="w-24">OAuth type:</div>
          <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" />
        </div>
        <div className="flex items-center">
          <div className="w-24">Key path:</div>
          <input className="px-2 py-1 flex-auto focus:outline-none border text-sm border-gray-200" />
        </div>
      </SectionWrapper>
    
      <div className="flex space-x-2 items-center border-b pb-2 border-gray-200 mb-3">
        <SvgIcon name="info" className="w-4 text-blue-700" />
        <div>You can use variables in connection parameters</div>
      </div>
    
      <div className="flex justify-between">
        <div className="flex space-x-3 text-sm">
          <span>Driver name:</span>
          <span>Google BigQuery</span>
        </div>
        <Button label="Edit Driver Settings" color="secondary" className="py-2 !text-sm !text-black rounded-md" />
      </div>
    </div>
  );
};

export default MainTab;
