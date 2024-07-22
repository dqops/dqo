import React from "react";
import { IncidentCountsModel, IncidentSeverityLevelCountsModel, TopIncidentsModel } from "../../../api";
import SectionWrapper from "../../../components/Dashboard/SectionWrapper";
import moment from 'moment';
import { ROUTES } from "../../../shared/routes";
import { useHistory } from 'react-router-dom';

export default function Gauge({
  incidentCounts,
  colorClassName,
  title,
  severity,
  status
}: {
  incidentCounts: IncidentCountsModel | undefined,
  colorClassName: string,
  title: string,
  severity: number,
  status: string
}) {
  const history = useHistory();
  
  const getMonthName = (date: string) => {
    const formattedDate = moment(date).format("MMMM");
    return formattedDate;
  }

  const goToIncidents = (severity: number, status: string) => {
    history.push(
      ROUTES.INCIDENT_CONNECTION(
        `*?severity=${severity}&${status}=true`
      )
    );
  };

  return (
    <div className={"flex flex-col justify-center mx-1 lg:mx-4 lg:my-2 pb-2 cursor-pointer border-2 " + colorClassName}>
      <div
        className="w-full min-w-[110px] max-w-[160px] mx-auto"
        onClick={() => goToIncidents(severity, status)}
      >
        <div className="text-center pt-1 text-2xl w-full">
          {incidentCounts?.currentMonthCount && incidentCounts?.previousMonthCount && incidentCounts?.currentMonthCount + incidentCounts?.previousMonthCount}
        </div>
        <div className="text-center pb-3 text-2xl text-xs">
          {title}
        </div>
        <div className="flex justify-between text-xs px-1">
          <div className="">
            Last 24h
          </div>
          <div className="">
            {(incidentCounts?.countFromLast24h !== undefined 
              && incidentCounts?.countFromLast24h > 0) && "+"} 
              {incidentCounts?.countFromLast24h}
          </div>
        </div>
        <div className="flex justify-between text-xs px-1">
          <div className="">
            Last 7 days
          </div>
          <div className="text-right">
            {(incidentCounts?.countFromLast7days !== undefined 
              && incidentCounts?.countFromLast7days > 0) && "+"} 
              {incidentCounts?.countFromLast7days}
          </div>
        </div>
        <div className="flex justify-between text-xs px-1">
          <div className="">
            {incidentCounts?.currentMonthDate && getMonthName(incidentCounts?.currentMonthDate)}
          </div>
          <div className="text-right">
          {(incidentCounts?.currentMonthCount !== undefined 
              && incidentCounts?.currentMonthCount > 0) && "+"} 
              {incidentCounts?.currentMonthCount}
          </div>
        </div>
        <div className="flex justify-between text-xs px-1">
          <div className="">
            {incidentCounts?.previousMonthDate && getMonthName(incidentCounts?.previousMonthDate)}
          </div>
          <div className="">
            {(incidentCounts?.previousMonthCount !== undefined 
              && incidentCounts?.previousMonthCount > 0) && "+"} 
              {incidentCounts?.previousMonthCount}
          </div>
        </div>
      </div>
    </div>
  );

}