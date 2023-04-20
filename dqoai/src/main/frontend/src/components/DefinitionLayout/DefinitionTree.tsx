import React from "react";
import { useEffect } from "react";
import { SensorsApi } from "../../services/apiClient";
import { useDispatch, useSelector } from "react-redux";
import { getSensorFolderTree, toggleSensorFolderTree } from "../../redux/actions/sensor.actions";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { IRootState } from "../../redux/reducers";
import { SensorBasicFolderModel } from "../../api";
import SvgIcon from "../SvgIcon";

export const DefinitionTree = () => {
  const dispatch = useActionDispatch();
  const { sensorFolderTree, sensorState } = useSelector((state: IRootState) => state.sensor);

  useEffect(() => {
    dispatch(getSensorFolderTree());
  }, []);

  const toggleSensorFolder = (key: string) => {
    dispatch(toggleSensorFolderTree(key));
  };

  const renderSensorFolderTree = (folder?: SensorBasicFolderModel) => {
    if (!folder) return null;

    return (
      <div className="text-sm">
        {folder.folders && Object.keys(folder.folders).map((key, index) => {
          return (
            <div key={index}>
              <div
                className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer"
                onClick={() => toggleSensorFolder(key)}
              >
                <SvgIcon name={sensorState[key] ? "folder" : "closed-folder"} className="w-4 h-4 min-w-4" />
                <div className="text-[13px] leading-1.5 truncate">{key}</div>
              </div>
              {sensorState[key] && (
                <div className="ml-2">
                  {folder?.folders && renderSensorFolderTree(folder?.folders[key])}
                </div>
              )}
            </div>
          )
        })}
        <div className="ml-2">
          {folder.sensors?.map((sensor) => (
            <div
              key={sensor.full_sensor_name}
              className="cursor-pointer flex space-x-1.5 items-center mb-1 h-5"
            >
              <SvgIcon name="grid" className="w-4 h-4 min-w-4 shrink-0" />
              <div className="text-[13px] leading-1.5 whitespace-nowrap">{sensor.sensor_name}</div>
            </div>
          ))}
        </div>
      </div>
    )
  };

  return (
    <div className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white">
      {renderSensorFolderTree(sensorFolderTree)}
    </div>
  );
};

export default DefinitionTree;
