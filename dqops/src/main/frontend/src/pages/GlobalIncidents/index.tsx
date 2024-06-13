import React, { useEffect, useState } from 'react';
import { TopIncidentsModel } from '../../api';
import RadioButton from '../../components/RadioButton';
import { IncidentsApi } from '../../services/apiClient';
import GlobalIncidentsDashboard from './GlobalIncidentDashboard/GlobalIncidentsDashboard';

export default function index() {
  const [incidents, setIncidents] = useState<TopIncidentsModel>({});
  const [groupingField, setGroupingField] = useState<
    'category' | 'dimension'
  >('category');

  useEffect(() => {
    const fetchIncidents = async () => {
      IncidentsApi.findTopIncidentsGrouped(undefined, groupingField).then(
        (response) => {
          setIncidents(response.data);
        }
      );
    };
    fetchIncidents();
  }, [groupingField]);

  return (
    <div>
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
      <GlobalIncidentsDashboard
        incidents={incidents}
        groupBy={groupingField}
      />
    </div>
  );
}
