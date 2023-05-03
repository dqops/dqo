import React from "react";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { addFirstLevelTab, getSensorFolderTree, toggleSensorFolderTree } from "../../redux/actions/sensor.actions";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { IRootState } from "../../redux/reducers";
import { RuleBasicFolderModel, RuleBasicModel, SensorBasicFolderModel, SensorBasicModel } from "../../api";
import SvgIcon from "../SvgIcon";
import { getRuleFolderTree, toggleRuleFolderTree } from "../../redux/actions/rule.actions";
import clsx from "clsx";
import { ROUTES } from "../../shared/routes";
import SensorContextMenu from "./SensorContextMenu";

export const DefinitionTree = () => {
  const dispatch = useActionDispatch();
  const { sensorFolderTree, sensorState } = useSelector((state: IRootState) => state.sensor);
  const { ruleFolderTree, ruleState } = useSelector((state: IRootState) => state.rule);

  useEffect(() => {
    dispatch(getSensorFolderTree());
    dispatch(getRuleFolderTree());
  }, []);

  const toggleSensorFolder = (key: string) => {
    dispatch(toggleSensorFolderTree(key));
  };

  const toggleRuleFolder = (key: string) => {
    dispatch(toggleRuleFolderTree(key));
  };

  const openSensorFirstLevelTab = (sensor: SensorBasicModel) => {
    dispatch(addFirstLevelTab({
      url: ROUTES.SENSOR_DETAIL(sensor.sensor_name ?? ""),
      value: ROUTES.SENSOR_DETAIL_VALUE(sensor.sensor_name ?? ""),
      state: {
        full_sensor_name: sensor.full_sensor_name
      },
      label: sensor.sensor_name
    }))
  };

  const openRuleFirstLevelTab = (rule: RuleBasicModel) => {
    dispatch(addFirstLevelTab({
      url: ROUTES.RULE_DETAIL(rule.rule_name ?? ""),
      value: ROUTES.RULE_DETAIL_VALUE(rule.rule_name ?? ""),
      state: {
        full_rule_name: rule.full_rule_name
      },
      label: rule.rule_name
    }))
  };

  const renderSensorFolderTree = (folder?: SensorBasicFolderModel, path?: string[]) => {
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
                <SensorContextMenu sensor={folder?.folders?.[key]} path={[...path || [], key]} />
              </div>
              {sensorState[key] && (
                <div className="ml-2">
                  {folder?.folders && renderSensorFolderTree(folder?.folders[key], [...path || [], key])}
                </div>
              )}
            </div>
          )
        })}
        <div className="ml-2">
          {folder.sensors?.map((sensor) => (
            <div
              key={sensor.full_sensor_name}
              className={clsx(
                "cursor-pointer flex space-x-1.5 items-center mb-1 h-5",
                sensor.custom ? "font-bold" : ""
              )}
              onClick={() => openSensorFirstLevelTab(sensor)}
            >
              <SvgIcon name="grid" className="w-4 h-4 min-w-4 shrink-0" />
              <div className="text-[13px] leading-1.5 whitespace-nowrap">{sensor.sensor_name}</div>
            </div>
          ))}
        </div>
      </div>
    )
  };

  const renderRuleFolderTree = (folder?: RuleBasicFolderModel) => {
    if (!folder) return null;

    return (
      <div className="text-sm">
        {folder.folders && Object.keys(folder.folders).map((key, index) => {
          return (
            <div key={index}>
              <div
                className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer"
                onClick={() => toggleRuleFolder(key)}
              >
                <SvgIcon name={ruleState[key] ? "folder" : "closed-folder"} className="w-4 h-4 min-w-4" />
                <div className="text-[13px] leading-1.5 truncate">{key}</div>
              </div>
              {ruleState[key] && (
                <div className="ml-2">
                  {folder?.folders && renderRuleFolderTree(folder?.folders[key])}
                </div>
              )}
            </div>
          )
        })}
        <div className="ml-2">
          {folder.rules?.map((rule) => (
            <div
              key={rule.full_rule_name}
              className={clsx(
                "cursor-pointer flex space-x-1.5 items-center mb-1 h-5",
                rule.custom ? "font-bold" : ""
              )}
              onClick={() => openRuleFirstLevelTab(rule)}
            >
              <SvgIcon name="grid" className="w-4 h-4 min-w-4 shrink-0" />
              <div className="text-[13px] leading-1.5 whitespace-nowrap">{rule.rule_name}</div>
            </div>
          ))}
        </div>
      </div>
    )
  };

  return (
    <div className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white">
      <div className="mb-4">
        <div className="text-sm text-gray-700 font-semibold mb-2">Sensors:</div>
        {renderSensorFolderTree(sensorFolderTree, [])}
      </div>

      <div>
        <div className="text-sm text-gray-700 font-semibold mb-2">Rules:</div>
        {renderRuleFolderTree(ruleFolderTree)}
      </div>
    </div>
  );
};

export default DefinitionTree;
