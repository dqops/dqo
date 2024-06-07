import React from 'react';
import { IncidentModel } from '../../../api';
type GlobalIncidentsDashboardTableProps = {
  group: string;
  incidents: IncidentModel[];
};
export default function GlobalIncidentsDashboardTable({
  group,
  incidents
}: GlobalIncidentsDashboardTableProps) {
  if (incidents.length === 0) {
    return null;
  }
  return (
    <div className="border border-gray-150 mt-4">
      <div className="flex justify-between pl-2 py-2 border-b border-gray-300 mb-2 text-lg font-semibold">
        Incident group: {group}
      </div>
      <table>
        <thead>
          <tr>
            <th>Connection</th>
            <th>Last seen</th>
            <th>First seen</th>
          </tr>
        </thead>
        <tbody>
          {incidents.map((incident) => (
            <tr key={incident.incidentId}>
              <td>{incident.connection}</td>
              <td>{incident.lastSeen}</td>
              <td>{incident.firstSeen}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
