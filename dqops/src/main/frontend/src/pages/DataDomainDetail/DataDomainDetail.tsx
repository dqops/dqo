import React from 'react';
import { useSelector } from 'react-redux';
import Button from '../../components/Button';
import Input from '../../components/Input';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';

export default function DataDomainDetail() {
  const { userProfile } = useSelector((state: IRootState) => state.job);
  const { data_domain } = useSelector(getFirstLevelSensorState);
  const [dataDomainName, setDataDomainName] = React.useState<string>('');
  const getDataDomain = () => {
    // DataDomainApiClient.
  };
  const updateDataDomain = () => {
    //  DataDomainApiClient.
  };
  return (
    <div>
      <div>
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={getDataDomain}
          label="Save"
        />
      </div>
      <div>
        <Input
          type="text"
          className="w-full border border-gray-300 rounded-md px-4 py-2"
          placeholder="Enter data domain name"
          value={data_domain}
          disabled
        />
        <Input
          type="text"
          className="w-full border border-gray-300 rounded-md px-4 py-2"
          placeholder="Enter data domain name"
          value={dataDomainName}
          onChange={(e) => setDataDomainName(e.target.value)}
        />
      </div>
    </div>
  );
}
