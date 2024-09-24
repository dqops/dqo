import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { LocalDataDomainModel } from '../../api';
import { getCurrentDataDomainName } from '../../redux/localStorage';
import { IRootState } from '../../redux/reducers';
import { DataDomainApiClient } from '../../services/apiClient';
import { Option } from '../Select';
import SelectTailwind from '../Select/SelectTailwind';

export default function DomainSwitch() {
  const { userProfile } = useSelector((state: IRootState) => state.job);

  const [domains, setDomains] = useState<LocalDataDomainModel[]>([]);

  const currentDomain = getCurrentDataDomainName();

  useEffect(() => {
    DataDomainApiClient.getLocalDataDomains().then((response) => {
      setDomains(response.data);
    });
  }, []);

  const switchDomain = (domainId: string) => {
    window.location.href = `/api/domains/${domainId}/switch`;
  };

  return (
    <div className="flex items-center mr-3">
      <SelectTailwind
        options={
          domains.map((domain) => ({
            label: domain.display_name,
            value: domain.domain_name
          })) as Option[]
        }
        value={currentDomain ?? ''}
        onChange={(value) => {
          switchDomain(value);
        }}
        menuClassName="!top-[50px] !z-[101]"
        disabled={!userProfile || !userProfile.can_use_data_domains}
        mainTooltip={
          !userProfile || !userProfile.can_use_data_domains
            ? 'Data domains are supported in the ENTERPRISE version of DQOps'
            : 'Switch to a different data domain'
        }
      />
    </div>
  );
}
