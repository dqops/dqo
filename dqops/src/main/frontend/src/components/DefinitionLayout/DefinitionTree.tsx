import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckDefinitionFolderModel,
  CheckDefinitionListModel,
  RuleFolderModel,
  RuleListModel,
  SensorFolderModel,
  SensorListModel
} from '../../api';
import { useDefinition } from '../../contexts/definitionContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getRuleFolderTree,
  getSensorFolderTree,
  getdataQualityChecksFolderTree,
  toggleFirstLevelFolder
} from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { ROUTES } from '../../shared/routes';
import { urlencodeEncoder } from '../../utils';
import SvgIcon from '../SvgIcon';
import DataQualityContextMenu from './DataQualityContextMenu';
import RuleContextMenu from './RuleContextMenu';
import SensorContextMenu from './SensorContextMenu';

const defaultChecks = [
  'Table-level checks patterns',
  'Column-level checks patterns'
];

export const DefinitionTree = () => {
  const dispatch = useActionDispatch();
  const {
    sensorFolderTree,
    sensorState,
    definitionFirstLevelFolder,
    checksFolderTree,
    dataQualityChecksState,
    ruleFolderTree,
    ruleState,
    tabs,
    activeTab,
    refreshChecksTreeIndicator,
    refreshRulesTreeIndicator,
    refreshSensorsTreeIndicator
  } = useSelector((state: IRootState) => state.definition);

  const {
    openCheckDefaultFirstLevelTab,
    openCheckFirstLevelTab,
    openRuleFirstLevelTab,
    openSensorFirstLevelTab,
    openDefaultChecksPatternsFirstLevelTab,
    toggleTree,
    nodes,
    toggleSensorFolder,
    toggleRuleFolder,
    toggleDataQualityChecksFolder,
    sortItemsTreeAlphabetically
  } = useDefinition();

  useEffect(() => {
    dispatch(getSensorFolderTree());
  }, [refreshSensorsTreeIndicator]);

  useEffect(() => {
    dispatch(getRuleFolderTree());
  }, [refreshRulesTreeIndicator]);

  useEffect(() => {
    dispatch(getdataQualityChecksFolderTree());
  }, [refreshChecksTreeIndicator]);

  useEffect(() => {
    toggleTree(tabs);
  }, [activeTab]);

  const highlightedNode = urlencodeEncoder(
    activeTab?.split('/').at(activeTab?.split('/').length - 1)
  );

  const renderSensorFolderTree = (
    folder?: SensorFolderModel,
    path?: string[],
    previousFolder?: string
  ) => {
    if (!folder) return null;

    return (
      <div className="text-sm">
        {folder.folders &&
          Object.keys(folder.folders).map((key, index) => {
            return (
              <div key={index}>
                <div
                  className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer hover:bg-gray-300"
                  onClick={() =>
                    toggleSensorFolder(
                      previousFolder !== 'undefined'
                        ? previousFolder + '/' + key
                        : key
                    )
                  }
                >
                  <SvgIcon
                    name={
                      sensorState[previousFolder + '/' + key] === true
                        ? 'folder'
                        : 'closed-folder'
                    }
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  <SensorContextMenu
                    folder={folder?.folders?.[key] || {}}
                    path={[...(path || []), key]}
                  />
                </div>
                {sensorState[previousFolder + '/' + key] === true && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderSensorFolderTree(
                        folder?.folders[key],
                        [...(path || []), key],
                        previousFolder ? previousFolder + '/' + key : key
                      )}
                  </div>
                )}
              </div>
            );
          })}
        <div className="ml-2">
          {sortItemsTreeAlphabetically(folder.sensors)?.map(
            (sensor: SensorListModel) => (
              <div
                key={sensor.full_sensor_name}
                className={clsx(
                  'cursor-pointer flex space-x-1.5 items-center mb-1 h-5  hover:bg-gray-300',
                  sensor.custom ? 'font-bold' : '',
                  highlightedNode === sensor.sensor_name ? 'bg-gray-300' : ''
                )}
                onClick={() => {
                  !(
                    sensor.yaml_parsing_error &&
                    sensor.yaml_parsing_error.length > 0
                  )
                    ? openSensorFirstLevelTab(sensor)
                    : undefined;
                }}
              >
                <SvgIcon
                  name="definitionssensors"
                  className="w-4 h-4 min-w-4 shrink-0"
                />
                <div className="text-[13px] leading-1.5 whitespace-nowrap">
                  {sensor.sensor_name ?? ''}
                </div>
                {sensor.yaml_parsing_error &&
                sensor.yaml_parsing_error.length > 0 ? (
                  <div className="text-gray-700 !absolute right-0 w-7 h-7 rounded-full flex items-center justify-center bg-white ">
                    <Tooltip
                      content={sensor.yaml_parsing_error}
                      className="max-w-120 z-50"
                      placement="right-start"
                    >
                      <div
                        style={{
                          position: 'absolute',
                          right: '30px',
                          top: '4px',
                          borderRadius: '3px'
                        }}
                        className="bg-white"
                      >
                        <SvgIcon name="warning" className="w-5 h-5" />
                      </div>
                    </Tooltip>
                  </div>
                ) : null}
                <SensorContextMenu singleSensor={true} sensor={sensor} />
              </div>
            )
          )}
        </div>
      </div>
    );
  };

  const renderRuleFolderTree = (
    folder?: RuleFolderModel,
    path?: string[],
    previousFolder?: string
  ) => {
    if (!folder) return null;

    return (
      <div className="text-sm">
        {folder.folders &&
          Object.keys(folder.folders).map((key, index) => {
            return (
              <div key={index}>
                <div
                  className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer hover:bg-gray-300"
                  onClick={() =>
                    toggleRuleFolder(
                      previousFolder !== 'undefined'
                        ? previousFolder + '/' + key
                        : key
                    )
                  }
                >
                  <SvgIcon
                    name={
                      ruleState[previousFolder + '/' + key] === true
                        ? 'folder'
                        : 'closed-folder'
                    }
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  <RuleContextMenu
                    folder={folder?.folders?.[key] || {}}
                    path={[...(path || []), key]}
                  />
                </div>
                {ruleState[previousFolder + '/' + key] === true && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderRuleFolderTree(
                        folder?.folders[key],
                        [...(path || []), key],
                        previousFolder ? previousFolder + '/' + key : key
                      )}
                  </div>
                )}
              </div>
            );
          })}
        <div className="ml-2 ">
          {sortItemsTreeAlphabetically(folder.rules)?.map(
            (rule: RuleListModel) => (
              <div
                key={rule.full_rule_name}
                className={clsx(
                  'cursor-pointer flex space-x-1.5 items-center mb-1 h-5 hover:bg-gray-300',
                  rule.custom ? 'font-bold ' : '',
                  highlightedNode === rule.rule_name ? 'bg-gray-300' : ''
                )}
                onClick={() => {
                  !(
                    rule.yaml_parsing_error &&
                    rule.yaml_parsing_error.length > 0
                  )
                    ? openRuleFirstLevelTab(rule)
                    : undefined;
                }}
              >
                <SvgIcon
                  name="definitionsrules"
                  className="w-4 h-4 min-w-4 shrink-0"
                />
                <div className="text-[13px] leading-1.5 whitespace-nowrap">
                  {rule.rule_name ?? ''}
                </div>
                {rule.yaml_parsing_error &&
                rule.yaml_parsing_error.length > 0 ? (
                  <div className="text-gray-700 !absolute right-0 w-7 h-7 rounded-full flex items-center justify-center bg-white ">
                    <Tooltip
                      content={rule.yaml_parsing_error}
                      className="max-w-120 z-50"
                      placement="right-start"
                    >
                      <div
                        style={{
                          position: 'absolute',
                          right: '30px',
                          top: '4px',
                          borderRadius: '3px'
                        }}
                        className="bg-white"
                      >
                        <SvgIcon name="warning" className="w-5 h-5" />
                      </div>
                    </Tooltip>
                  </div>
                ) : null}
                <RuleContextMenu singleRule={true} rule={rule} />
              </div>
            )
          )}
        </div>
      </div>
    );
  };

  const renderChecksFolderTree = (
    folder?: CheckDefinitionFolderModel,
    path?: string[],
    previousFolder?: string
  ) => {
    if (!folder) return null;

    return (
      <div className="text-sm">
        {folder.folders &&
          Object.keys(folder.folders).map((key, index) => {
            return (
              <div key={index}>
                <div
                  className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer hover:bg-gray-300"
                  onClick={() =>
                    toggleDataQualityChecksFolder(
                      previousFolder !== 'undefined'
                        ? previousFolder + '/' + key
                        : key
                    )
                  }
                >
                  <SvgIcon
                    name={
                      dataQualityChecksState[previousFolder + '/' + key] ===
                      true
                        ? 'folder'
                        : 'closed-folder'
                    }
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  {folder?.folders?.[key].folders === undefined && (
                    <DataQualityContextMenu
                      folder={folder?.folders?.[key] || {}}
                      path={[...(path || []), key]}
                    />
                  )}
                </div>
                {dataQualityChecksState[previousFolder + '/' + key] ===
                  true && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderChecksFolderTree(
                        folder?.folders[key],
                        [...(path || []), key],
                        previousFolder ? previousFolder + '/' + key : key
                      )}
                  </div>
                )}
              </div>
            );
          })}
        <div className="ml-2">
          {folder.checks &&
            sortItemsTreeAlphabetically(folder?.checks).map(
              (check: CheckDefinitionListModel) => (
                <div key={check.check_name}>
                  <div
                    className={clsx(
                      'cursor-pointer flex w-full space-between items-center mb-1 h-5  hover:bg-gray-300',
                      check.custom ? 'font-bold' : '',
                      highlightedNode === check.check_name ? 'bg-gray-300' : ''
                    )}
                    onClick={() => {
                      !(
                        check.yaml_parsing_error &&
                        check.yaml_parsing_error.length > 0
                      )
                        ? openCheckFirstLevelTab(check)
                        : undefined;
                    }}
                  >
                    <SvgIcon
                      name="definitionssensors"
                      className="w-4 h-4 min-w-4 shrink-0"
                    />
                    <div className="text-[13px] leading-1.5 whitespace-nowrap flex items-center justify-between">
                      {check.check_name ?? ''}
                    </div>
                    {check.yaml_parsing_error &&
                    check.yaml_parsing_error.length > 0 ? (
                      <div className="text-gray-700 !absolute right-0 w-7 h-7 rounded-full flex items-center justify-center bg-white ">
                        <Tooltip
                          content={check.yaml_parsing_error}
                          className="max-w-120 z-50"
                          placement="right-start"
                        >
                          <div
                            style={{
                              position: 'absolute',
                              right: '30px',
                              top: '4px',
                              borderRadius: '3px'
                            }}
                            className="bg-white"
                          >
                            <SvgIcon name="warning" className="w-5 h-5" />
                          </div>
                        </Tooltip>
                      </div>
                    ) : null}
                    <DataQualityContextMenu singleCheck={true} check={check} />
                  </div>
                </div>
              )
            )}
        </div>
      </div>
    );
  };

  const NodeComponent = ({
    onClick,
    icon,
    text
  }: {
    onClick: () => void;
    icon: string;
    text: string;
  }) => (
    <div
      onClick={onClick}
      className={clsx(
        'cursor-pointer flex space-x-1 items-center mb-1 h-4.5 hover:bg-gray-300',
        highlightedNode === text.toLowerCase().replace(' ', '-') &&
          'bg-gray-300'
      )}
    >
      <SvgIcon name={icon} className="w-4 h-4 min-w-4 " />
      <div className="text-[13px] leading-1.5 whitespace-nowrap flex items-center justify-between">
        {text}
      </div>
    </div>
  );

  return (
    <div className="overflow-hidden bg-white">
      {definitionFirstLevelFolder?.map((x, index) => (
        <div key={index} className="text-[13px] cursor-pointer">
          <div
            className="flex items-center mb-1 gap-x-1"
            onClick={() => {
              const updatedRootTree = [...definitionFirstLevelFolder];
              updatedRootTree[index].isOpen = !updatedRootTree[index].isOpen;
              dispatch(toggleFirstLevelFolder(updatedRootTree));
            }}
          >
            <SvgIcon
              name={x.isOpen ? 'folder' : 'closed-folder'}
              className="w-4 h-4 min-w-4"
            />
            {x.category}
          </div>
          {x.category === 'Sensors' && x.isOpen === true && (
            <div className="ml-2">
              {renderSensorFolderTree(sensorFolderTree, [])}
            </div>
          )}
          {x.category === 'Rules' && x.isOpen === true && (
            <div className="ml-2">
              {renderRuleFolderTree(ruleFolderTree, [])}
            </div>
          )}
          {x.category === 'Data quality checks' && x.isOpen === true && (
            <div className="ml-2">
              {renderChecksFolderTree(checksFolderTree, [])}
            </div>
          )}
          {x.category === 'Default checks configuration' &&
            x.isOpen === true && (
              <div>
                {defaultChecks.map((x, index) => (
                  <div key={index}>
                    <div
                      className={clsx(
                        'cursor-pointer flex space-x-1.5 items-center mb-1 h-5 ml-2  hover:bg-gray-300',
                        activeTab === ROUTES.DEFAULT_CHECKS_PATTERNS_VALUE(x)
                          ? 'bg-gray-300'
                          : ''
                        // check.custom ? 'font-bold' : '',
                        // selected == check.check_name ? 'bg-gray-300' : ''
                      )}
                      onClick={() => {
                        openDefaultChecksPatternsFirstLevelTab(
                          x,
                          x.includes('Column') ? 'column' : 'table'
                        );
                      }}
                    >
                      <SvgIcon
                        name="definitionssensors"
                        className="w-4 h-4 min-w-4 shrink-0"
                      />
                      <div className="text-[13px] leading-1.5 whitespace-nowrap flex items-center justify-between">
                        {x}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
        </div>
      ))}
      {(nodes as any[]).map((tab, index) => (
        <NodeComponent key={index} {...tab} />
      ))}
    </div>
  );
};

export default DefinitionTree;
