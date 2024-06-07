import React, { useEffect, useState } from 'react';
import { TopIncidentsModel } from '../../api';
import { IncidentsApi } from '../../services/apiClient';
import GlobalIncidentsDashboard from './GlobalIncidentDashboard/GlobalIncidentsDashboard';

type GlobalIncidentsProps = {
  groupBy: 'dimension' | 'category' | 'connection' | undefined;
};
export default function index({ groupBy }: GlobalIncidentsProps) {
  const [incidents, setIncidents] = useState<TopIncidentsModel>({});

  useEffect(() => {
    const fetchIncidents = async () => {
      IncidentsApi.findTopIncidentsGrouped(undefined, groupBy).then(
        (response) => {
          setIncidents(response.data);
        }
      );
    };
    fetchIncidents();
  }, []);

  console.log(incidents);

  return (
    <div>
      <GlobalIncidentsDashboard incidents={incidents} />
    </div>
  );
}
