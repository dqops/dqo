import React, { useEffect, useState } from 'react';
import { TopIncidentsModel } from '../../api';
import RadioButton from '../../components/RadioButton';
import { IncidentsApi } from '../../services/apiClient';
import GlobalIncidentsDashboard from './GlobalIncidentDashboard/GlobalIncidentsDashboard';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import clsx from 'clsx';

export default function index() {
  const [incidents, setIncidents] = useState<TopIncidentsModel>({});
  const [groupingField, setGroupingField] = useState<
    'category' | 'dimension'
  >('category');
  const [loading, setLoading] = useState<boolean>(false);

  const getIncidents = async (status?: string, groupBy?: string) => {
    setLoading(true);
    const response = await IncidentsApi.findTopIncidentsGrouped(undefined, groupingField)
    .then( (response) => { setIncidents(response.data); } )
    .finally(() => setLoading(false));
  }

  useEffect(() => {
    getIncidents(undefined, groupingField);
  }, [groupingField]);

  return (
    <div>
      <div className='flex items-center justify-between'>
        <div className="flex pb-2 pt-6 pl-4 gap-x-5 items-center">
          <div className="text-sm pl-1">Group incidents by: </div>
          <RadioButton
            checked={groupingField === 'category'}
            label="category"
            fontClassName="text-sm"
            onClick={() => setGroupingField('category')}
          />
          <RadioButton
            checked={groupingField === 'dimension'}
            label="quality dimension"
            fontClassName="text-sm"
            onClick={() => setGroupingField('dimension')}
          />
        </div>
        <Button
          color="primary"
          leftIcon={
            <SvgIcon
              name="sync"
              className={clsx('w-4 h-4 mr-3', loading ? 'animate-spin' : '')}
            />
          }
          className="mr-8 pr-0 pl-3 mt-3"
          onClick={() =>
            getIncidents(undefined, groupingField)
          }
        />
      </div>
      <GlobalIncidentsDashboard
        incidents={incidents}
        groupBy={groupingField}
      />
    </div>
  );
}
