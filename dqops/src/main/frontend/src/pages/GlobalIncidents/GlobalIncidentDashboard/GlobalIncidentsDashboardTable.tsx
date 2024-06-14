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

const tableHeaderClassName = "py-2 px-2 text-left whitespace-nowrap ";
const tableRowClassName = "px-2 truncate ";
const tableMinWidth = '960px'

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

  const showMore = () => {
    // history.push(ROUTES.INCIDENT_CONNECTION);
  };

  return (
    <div
      className="border border-gray-150 p-2 rounded-md text-xs"
      style={{ height: `${maxHeight}px` }}
    >
      <div className="flex items-center justify-between pl-2 py-2 border-b border-gray-300 mb-2 text-md font-semibold">
        <div>
          {groupBy === 'dimension'
            ? incidents[0].qualityDimension
            : incidents[0].checkCategory}
        </div>
      </div>
      <div className="">
        <div className='grid grid-cols-24 font-bold' style={{ minWidth: tableMinWidth}}>
          <div className={tableHeaderClassName + ' col-span-3'}>
            Connection
          </div>
          <div className={tableHeaderClassName + ' col-span-4'}>
            Schema
          </div>
          <div className={tableHeaderClassName + ' col-span-10'}>
            Table
          </div>
          <div className={tableHeaderClassName + ' col-span-3'}>
            {groupBy === 'dimension' ? (
              <p>Check category</p>
            ) : (
              <p>Quality dimension</p>
            )}
            </div>

          <div className={tableHeaderClassName + 'col-span-2'}>
            First seen
          </div>
          <div className={tableHeaderClassName + 'col-span-2'}>
            Last seen
          </div>
        </div>
        <div style={{ minWidth: tableMinWidth}}>
          {incidents.map((incident) => (
            <div
              key={incident.incidentId}
              className="py-2 border-b border-gray-300 grid grid-cols-24"
            >
              <div className={tableRowClassName + "col-span-3"}>
                <Tooltip
                  content={incident.connection}
                  placement="top"
                  color="light"
                >
                  {incident.connection}
                </Tooltip>
              </div>
              <div className={tableRowClassName + "col-span-4"}>
                <Tooltip
                  content={incident.schema}
                  placement="top"
                  color="light"
                >
                  {incident.schema}
                </Tooltip>
              </div>
              <div
                className={tableRowClassName + "underline cursor-pointer col-span-10"}
                onClick={() => goToIncidents(incident)}
              >
                <Tooltip
                  content={incident.table}
                  placement="top"
                  color="light"
                >
                  {incident.table}
                </Tooltip>
              </div>
              <div className={tableRowClassName + "col-span-3"}>
                {groupBy === 'dimension' ? (
                  <>{incident.checkCategory}</>
                ) : (
                  <>{incident.qualityDimension}</>
                )}
              </div>
              <div className={tableRowClassName + "col-span-2"}>
                {moment(incident.firstSeen).format('YYYY-MM-DD')}
              </div>
              <div className={tableRowClassName + "col-span-2"}> 
                {moment(incident.lastSeen).format('YYYY-MM-DD')}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
