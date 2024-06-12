import React, { useEffect, useState } from 'react';
import { TopIncidentsModel } from '../../api';
import RadioButton from '../../components/RadioButton';
import { IncidentsApi } from '../../services/apiClient';
import GlobalIncidentsDashboard from './GlobalIncidentDashboard/GlobalIncidentsDashboard';

export default function index() {
  const [incidents, setIncidents] = useState<TopIncidentsModel>({});
  const [categoryDimension, setCategoryDimension] = useState<
    'category' | 'dimension'
  >('category');

  useEffect(() => {
    const fetchIncidents = async () => {
      IncidentsApi.findTopIncidentsGrouped(undefined, categoryDimension).then(
        (response) => {
          setIncidents(response.data);
        }
      );
    };
    fetchIncidents();
  }, []);

  return (
    <div className="">
      <div className="flex pb-2 pt-6 pl-4 gap-x-5 items-center">
        <div className="text-sm pl-1">Group incidents by: </div>
        <RadioButton
          checked={categoryDimension === 'category'}
          label="category"
          fontClassName="text-sm"
          onClick={() => setCategoryDimension('category')}
        />
        <RadioButton
          checked={categoryDimension === 'dimension'}
          label="quality dimension"
          fontClassName="text-sm"
          onClick={() => setCategoryDimension('dimension')}
        />
      </div>
      <GlobalIncidentsDashboard
        incidents={incidents}
        groupBy={categoryDimension}
      />
    </div>
  );
}
