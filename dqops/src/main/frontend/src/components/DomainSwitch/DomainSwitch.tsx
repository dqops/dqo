import { Tooltip } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { LocalDataDomainModel } from '../../api';
import { IRootState } from '../../redux/reducers';
import { DataDomainApiClient } from '../../services/apiClient';
import Select, { Option } from '../Select';

export default function DomainSwitch() {
  const { userProfile } = useSelector((state: IRootState) => state.job);

  const [domains, setDomains] = useState<LocalDataDomainModel[]>([]);
  const [activeDomain, setActiveDomain] = useState<string>('');

  useEffect(() => {
    DataDomainApiClient.getLocalDataDomains().then((response) => {
      setDomains(response.data);
    });
  }, []);

  const switchDomain = (domainId: string) => {
    window.location.href = `/api/domains/${domainId}/switch`;
  };

  return (
    <Tooltip
      content={
        !userProfile || !userProfile.can_use_data_domains
          ? 'Data domains are supported in the ENTERPRISE version of DQOps.'
          : 'Switch to a different data domain.'
      }
    >
      <div className="flex items-center">
        <Select
          options={
            domains.map((domain) => ({
              label: domain.display_name,
              value: domain.domain_name
            })) as Option[]
          }
          value={activeDomain}
          onChange={(value) => {
            switchDomain(value);
          }}
          disabled={!userProfile || !userProfile.can_use_data_domains}
        />
      </div>
    </Tooltip>
  );
}
