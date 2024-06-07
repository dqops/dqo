import React from 'react';
import { TopIncidentsModel } from '../../../api';
import GlobalIncidentsDashboardTable from './GlobalIncidentsDashboardTable';

export default function GlobalIncidentsDashboard({
  incidents
}: {
  incidents: TopIncidentsModel;
}) {
  const groups = incidents.topIncidents || {};
  return (
    <div>
      {Object.entries(groups).map(([key, value]) => {
        return (
          <GlobalIncidentsDashboardTable
            key={key}
            group={key}
            incidents={value}
          />
        );
      })}
    </div>
  );
}
