import React from "react";
import { IncidentCountsModel, IncidentSeverityLevelCountsModel, TopIncidentsModel } from "../../../api";
import SectionWrapper from "../../../components/Dashboard/SectionWrapper";
import moment from 'moment';
import Button from "../../../components/Button";
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
  severity: string,
  status: string
}) {
  const history = useHistory();
  
  const getMonthName = (date: string) => {
    const formattedDate = moment(date).format("MMMM");
    return formattedDate;
  }

  const goToIncidents = (severity: string, status: string) => {
    history.push(
      ROUTES.INCIDENT_CONNECTION(
        `*?severity=${severity}&status=${status}`
      )
    );
  };

  return (
    <SectionWrapper 
      className={"flex justify-center border-2 my-4 mx-4 lg:mx-10 xl:mx-14 2xl:mx-4 screen1900:mx-14 screen3000:mx-34 " + colorClassName}
      title={title}
      // titleIcon={<div className={"w-4 h-4 border " + colorClassName}></div>}
    >
      <div 
        className="w-[150px]"
        onClick={() => goToIncidents(severity, status)}
      >
        <div className="text-center pt-1 pb-3 text-2xl ">
          {incidentCounts?.currentMonthCount && incidentCounts?.previousMonthCount && incidentCounts?.currentMonthCount + incidentCounts?.previousMonthCount}
        </div>
        <div className="flex justify-between text-xs px-5">
          <div className="">
            Last 24h
          </div>
          <div className="">
            {(incidentCounts?.countFromLast24h !== undefined 
              && incidentCounts?.countFromLast24h > 0) && "+"} 
              {incidentCounts?.countFromLast24h}
          </div>
        </div>
        <div className="flex justify-between text-xs px-5">
          <div className="">
            Last 7 days
          </div>
          <div className="text-right">
            {(incidentCounts?.countFromLast7days !== undefined 
              && incidentCounts?.countFromLast7days > 0) && "+"} 
              {incidentCounts?.countFromLast7days}
          </div>
        </div>
        <div className="flex justify-between text-xs px-5">
          <div className="">
            {incidentCounts?.currentMonthDate && getMonthName(incidentCounts?.currentMonthDate)}
          </div>
          <div className="text-right">
          {(incidentCounts?.currentMonthCount !== undefined 
              && incidentCounts?.currentMonthCount > 0) && "+"} 
              {incidentCounts?.currentMonthCount}
          </div>
        </div>
        <div className="flex justify-between text-xs px-5">
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
    </SectionWrapper>
  );

}