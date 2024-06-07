import moment from 'moment';
import React from 'react';
import { IncidentModel } from '../../../api';
import Button from '../../../components/Button';

type GlobalIncidentsDashboardTableProps = {
  group: string;
  incidents: IncidentModel[];
};
export default function GlobalIncidentsDashboardTable({
  group,
  incidents
}: GlobalIncidentsDashboardTableProps) {
  const goToIncidents = (group: string) => {};

  if (incidents.length === 0) {
    return null;
  }

  return (
    <div className="border border-gray-150 p-2 rounded-md">
      <div className="flex items-center justify-between pl-2 py-2 border-b border-gray-300 mb-2 text-lg font-semibold">
        <div>{group}</div>
        <div>
          <Button
            label="Show more"
            color="primary"
            className="text-sm"
            onClick={() => goToIncidents(group)}
          />
        </div>
      </div>
      <table>
        <thead>
          <tr>
            <th className="py-2 px-4 text-left whitespace-nowrap">
              Connection
            </th>
            <th className="py-2 px-4 text-left whitespace-nowrap">Schema</th>
            <th className="py-2 px-4 text-left whitespace-nowrap">Table</th>
            <th className="py-2 px-4 text-left whitespace-nowrap">
              Quality dimension
            </th>
            <th className="py-2 px-4 text-left whitespace-nowrap">
              Check category
            </th>
            <th className="py-2 px-4  text-left whitespace-nowrap">
              First seen
            </th>
            <th className="py-2 px-4 text-left whitespace-nowrap">Last seen</th>
            <th className="py-2 px-4 text-left whitespace-nowrap">
              Incident id
            </th>
          </tr>
        </thead>
        <tbody>
          {incidents.map((incident) => (
            <tr
              key={incident.incidentId}
              className="py-2 border-b border-gray-300 pl-2"
            >
              <td className="py-2 px-4">{incident.connection}</td>
              <td className="py-2 px-4">{incident.schema}</td>
              <td className="py-2 px-4">{incident.table}</td>
              <td className="py-2 px-4">{incident.qualityDimension}</td>
              <td className="py-2 px-4">{incident.checkCategory}</td>
              <td className="py-2 px-4">
                {moment(incident.firstSeen).format('YYYY-MM-DD')}
              </td>
              <td className="py-2 px-4">
                {moment(incident.lastSeen).format('YYYY-MM-DD')}
              </td>
              <td className="py-2 px-4">{incident.incidentId}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
