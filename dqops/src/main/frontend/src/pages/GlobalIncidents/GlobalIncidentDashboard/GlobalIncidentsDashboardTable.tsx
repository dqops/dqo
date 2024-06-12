import { Tooltip } from '@material-tailwind/react';
import moment from 'moment';
import React from 'react';
import { useHistory } from 'react-router-dom';
import { IncidentModel } from '../../../api';
import { ROUTES } from '../../../shared/routes';

type GlobalIncidentsDashboardTableProps = {
  group: string;
  incidents: IncidentModel[];
  groupBy: 'dimension' | 'category' | 'connection' | undefined;
  maxHeight?: number;
};
export default function GlobalIncidentsDashboardTable({
  group,
  incidents,
  groupBy,
  maxHeight
}: GlobalIncidentsDashboardTableProps) {
  const history = useHistory();
  const goToIncidents = (incident: IncidentModel) => {
    const url = ROUTES.INCIDENT_DETAIL(
      incident.connection ?? '',
      incident.year ?? 0,
      incident.month ?? 0,
      incident.incidentId ?? ''
    );
    history.push(url);
  };

  if (incidents.length === 0) {
    return null;
  }

  const getWidth = () => {
    if (window.innerWidth < 2060) {
      return '100%';
    } else {
      const amount = Math.floor(window.innerWidth / 1030);
      return `${window.innerWidth / amount} px`;
    }
  };

  const showMore = () => {
    // history.push(ROUTES.INCIDENT_CONNECTION);
  };

  return (
    <div
      className="border border-gray-150 p-2 rounded-md text-xs"
      style={{ width: getWidth(), height: `${maxHeight}px` }}
    >
      <div className="flex items-center justify-between pl-4 py-2 border-b border-gray-300 mb-2 text-md font-semibold">
        <div>
          {groupBy === 'dimension'
            ? incidents[0].qualityDimension
            : incidents[0].checkCategory}
        </div>
        <div>
          {/* <Button label="Show more" color="primary" className="text-sm" /> */}
        </div>
      </div>
      <div className="overflow-auto w-full">
        <table className="w-full">
          <thead>
            <tr>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                Connection
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                Schema
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                Table
              </th>
              {groupBy === 'dimension' ? (
                <th
                  className="py-2 px-4 text-left whitespace-nowrap"
                  style={{ width: '12.5%' }}
                >
                  Check category
                </th>
              ) : (
                <th
                  className="py-2 px-4 text-left whitespace-nowrap"
                  style={{ width: '12.5%' }}
                >
                  Quality dimension
                </th>
              )}

              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                First seen
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                Last seen
              </th>
              {/* <th className="py-2 px-4 text-left whitespace-nowrap">
            Incident id
          </th> */}
            </tr>
          </thead>
          <tbody>
            {incidents.map((incident) => (
              <tr
                key={incident.incidentId}
                className="py-2 border-b border-gray-300"
              >
                <td className="py-2 px-4 truncate">
                  <Tooltip
                    content={incident.connection}
                    placement="top"
                    color="light"
                  >
                    {incident.connection}
                  </Tooltip>
                </td>
                <td className="py-2 px-4 truncate">
                  <Tooltip
                    content={incident.schema}
                    placement="top"
                    color="light"
                  >
                    {incident.schema}
                  </Tooltip>
                </td>
                <td
                  className="py-2 px-4 underline cursor-pointer truncate"
                  onClick={() => goToIncidents(incident)}
                >
                  <Tooltip
                    content={incident.table}
                    placement="top"
                    color="light"
                  >
                    {incident.table}
                  </Tooltip>
                </td>
                {groupBy === 'dimension' ? (
                  <td className="py-2 px-4 truncate">
                    {incident.checkCategory}
                  </td>
                ) : (
                  <td className="py-2 px-4 truncate">
                    {incident.qualityDimension}
                  </td>
                )}
                <td className="py-2 px-4 truncate">
                  {moment(incident.firstSeen).format('YYYY-MM-DD')}
                </td>
                <td className="py-2 px-4 truncate">
                  {moment(incident.lastSeen).format('YYYY-MM-DD')}
                </td>
                {/* <td className="py-2 px-4">{incident.incidentId}</td> */}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
