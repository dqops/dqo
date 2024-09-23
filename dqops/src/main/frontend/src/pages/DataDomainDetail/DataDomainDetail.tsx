import React from 'react';
import { useSelector } from 'react-redux';
import Input from '../../components/Input';
import { getFirstLevelSensorState } from '../../redux/selectors';

export default function DataDomainDetail() {
  const { data_domain, data_domain_name } = useSelector(
    getFirstLevelSensorState
  );

  return (
    <div className="p-4 flex flex-col gap-y-2">
      <Input
        type="text"
        className="w-full border border-gray-300 rounded-md px-4 py-2"
        placeholder="Enter data domain name"
        value={data_domain}
        disabled
        label="Domain id"
      />
      <Input
        type="text"
        className="w-full border border-gray-300 rounded-md px-4 py-2"
        placeholder="Enter data domain name"
        value={data_domain_name}
        label="Domain name"
        disabled
      />
    </div>
  );
}
