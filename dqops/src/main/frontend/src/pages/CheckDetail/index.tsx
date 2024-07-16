import React, { ChangeEvent, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckDefinitionModel,
  CheckDefinitionModelDefaultSeverityEnum
} from '../../api';
import Button from '../../components/Button';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import Input from '../../components/Input';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  closeFirstLevelTab,
  createCheck,
  deleteCheck,
  getCheck,
  getdataQualityChecksFolderTree,
  opendataQualityChecksFolderTree,
  refreshChecksFolderTree,
  updateCheck
} from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { ROUTES } from '../../shared/routes';
import CheckEditor from './CheckEditor';

export const SensorDetail = () => {
  const { full_check_name, path, type, custom, copied } = useSelector(
    getFirstLevelSensorState
  );
  const { tabs, activeTab, refreshChecksTreeIndicator } = useSelector(
    (state: IRootState) => state.definition
  );
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const dispatch = useActionDispatch();
  const activeCheckDetail: CheckDefinitionModel = tabs.find(
    (x) => x.url === activeTab
  )?.state?.checkDetail as CheckDefinitionModel;

  const [checkName, setcheckName] = useState('');
  const [selectedSensor, setSelectedSensor] = useState(
    activeCheckDetail?.sensor_name ?? ''
  );
  const [selectedRule, setSelectedRule] = useState(
    activeCheckDetail?.rule_name ?? ''
  );
  const [isUpdating, setIsUpdating] = useState(false);
  const [isCreating, setIsCreating] = useState(false);
  const [isUpdated, setIsUpdated] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [helpText, setHelpText] = useState(activeCheckDetail?.help_text ?? '');
  const [friendlyName, setFriendlyName] = useState(
    activeCheckDetail?.friendly_name ?? ''
  );
  const [standard, setStandard] = useState(
    activeCheckDetail?.standard ?? false
  );
  const [defaultSeverity, setDefaultSeverity] = useState<
    CheckDefinitionModelDefaultSeverityEnum | undefined
  >(activeCheckDetail?.default_severity);

  const onChangeSensor = (value: string) => {
    setSelectedSensor(value);
  };

  const onChangeRule = (value: string) => {
    setSelectedRule(value);
  };

  const onChangeHelpText = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setHelpText(e.target.value);
  };

  const onChangeFriendlyName = (e: ChangeEvent<HTMLInputElement>) => {
    setFriendlyName(e.target.value);
  };

  const onChangeStandard = (value: boolean) => {
    setStandard(value);
  };

  const onChangeDefaultSeverity = (
    value: CheckDefinitionModelDefaultSeverityEnum | undefined
  ) => {
    setDefaultSeverity(value);
  };

  useEffect(() => {
    if (type === 'create') {
      setIsCreating(true);
    } else {
      setIsCreating(false);
    }
    if (full_check_name === undefined) {
      dispatch(closeFirstLevelTab(path));
    } else if ((full_check_name as string).length === 0) {
      dispatch(closeFirstLevelTab(path));
    }
    if (activeCheckDetail === undefined) {
      dispatch(getCheck(full_check_name));
    }
    setIsUpdated(false);
  }, [full_check_name, path, type, custom, activeCheckDetail]);

  useEffect(() => {
    if (activeCheckDetail === undefined) {
      setSelectedRule('');
      setSelectedSensor('');
      setHelpText('');
      setFriendlyName('');
      setStandard(false);
      setcheckName('');
      setDefaultSeverity(undefined);
    } else {
      setSelectedRule(activeCheckDetail.rule_name ?? '');
      setSelectedSensor(activeCheckDetail.sensor_name ?? '');
      setHelpText(activeCheckDetail.help_text ?? '');
      setFriendlyName(activeCheckDetail.friendly_name ?? '');
      setStandard(activeCheckDetail.standard ?? false);
      setcheckName(
        String(activeCheckDetail.check_name).split('/')[
          String(activeCheckDetail.check_name).split('/').length - 1
        ] + '_copy'
      );
      setDefaultSeverity(activeCheckDetail.default_severity);
    }
  }, [activeTab, activeCheckDetail]);

  const onCreateUpdateCheck = async () => {
    const fullName = [...(path || []), checkName].join('/');
    if (!checkName.length) return;
    setIsUpdating(true);
    if (type === 'create') {
      if (copied === true) {
        await dispatch(
          createCheck(
            String(full_check_name).replace(/\/[^/]*$/, '/') + checkName,
            {
              sensor_name: selectedSensor,
              rule_name: selectedRule,
              help_text: helpText,
              friendly_name: friendlyName,
              standard: standard,
              default_severity: defaultSeverity
            }
          )
        );
      } else {
        await dispatch(
          createCheck(fullName, {
            sensor_name: selectedSensor,
            rule_name: selectedRule,
            help_text: helpText,
            friendly_name: friendlyName,
            standard: standard,
            default_severity: defaultSeverity
          })
        );
      }
      setIsUpdating(false);
      setIsCreating(false);
      openAddNewCheck();
      dispatch(getdataQualityChecksFolderTree());
      if (path) {
        dispatch(opendataQualityChecksFolderTree(Array.from(path).join('/')));
      } else {
        dispatch(opendataQualityChecksFolderTree(full_check_name));
      }
    } else {
      await dispatch(
        updateCheck(
          full_check_name
            ? full_check_name
            : Array.from(path).join('/') + '/' + checkName,
          {
            sensor_name: selectedSensor,
            rule_name: selectedRule,
            help_text: helpText,
            friendly_name: friendlyName,
            standard: standard,
            default_severity: defaultSeverity
          }
        )
      );
      setIsUpdating(false);
    }
    setIsUpdated(false);
  };

  const onDeleteCheck = async () => {
    await dispatch(
      deleteCheck(
        full_check_name
          ? full_check_name
          : Array.from(path).join('/') + '/' + checkName
      )
    );
    dispatch(
      refreshChecksFolderTree(refreshChecksTreeIndicator ? false : true)
    );
    dispatch(
      closeFirstLevelTab(
        '/definitions/checks/' +
          String(full_check_name).split('/')[
            String(full_check_name).split('/').length - 1
          ]
      )
    );
  };
  const onChangecheckName = (e: ChangeEvent<HTMLInputElement>) => {
    setIsUpdated(true);
    setcheckName(e.target.value);
  };

  const openAddNewCheck = () => {
    if (path) {
      dispatch(
        closeFirstLevelTab(
          '/definitions/checks/' + Array.from(path).join('-') + '-new_check'
        )
      );
    } else {
      dispatch(
        closeFirstLevelTab(
          '/definitions/checks/' +
            String(full_check_name).split('/')[
              String(full_check_name).split('/').length - 1
            ] +
            '_copy'
        )
      );
    }
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DETAIL(checkName ?? ''),
        value: ROUTES.CHECK_DETAIL_VALUE(checkName ?? ''),
        state: {
          full_check_name: path
            ? Array.from(path).join('/') + '/' + checkName
            : String(full_check_name).replace(/\/[^/]*$/, '/') + checkName,
          custom: true,
          sensor: selectedSensor,
          rule: selectedRule,
          checkName: checkName,
          helpText: helpText,
          friendlyName: friendlyName,
          standard: standard,
          defaultSeverity: defaultSeverity
        },
        label: checkName
      })
    );
  };
  const onCopy = (): void => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DETAIL(
          String(full_check_name).split('/')[
            String(full_check_name).split('/').length - 1
          ] + '_copy' ?? ''
        ),
        value: ROUTES.CHECK_DETAIL_VALUE(
          String(full_check_name).split('/')[
            String(full_check_name).split('/').length - 1
          ] + '_copy' ?? ''
        ),
        state: {
          full_check_name: full_check_name,
          copied: true,
          path: path,
          checkDetail: {
            ...activeCheckDetail,
            full_check_name: full_check_name + '_copy',
            custom: true,
            built_in: false,
            can_edit: true
          },
          type: 'create'
        },
        label: `${
          String(full_check_name).split('/')[
            String(full_check_name).split('/').length - 1
          ]
        }_copy`
      })
    );
  };

  return (
    <>
      <div className="relative">
        <div className="flex space-x-4 items-center absolute right-2 top-2">
          {custom !== false && isCreating === false && (
            <Button
              color={
                !(userProfile.can_manage_definitions !== true)
                  ? 'primary'
                  : 'secondary'
              }
              variant={
                !(userProfile.can_manage_definitions !== true)
                  ? 'outlined'
                  : 'contained'
              }
              label="Delete check"
              className="w-40 !h-10"
              onClick={() => setDialogOpen(true)}
              disabled={userProfile.can_manage_definitions !== true}
            />
          )}
          <Button
            color="primary"
            variant="outlined"
            label="Copy"
            className="w-40 !h-10"
            disabled={userProfile.can_manage_definitions !== true}
            onClick={onCopy}
          />
          <Button
            color={
              !(userProfile.can_manage_definitions !== true)
                ? 'primary'
                : 'secondary'
            }
            variant="contained"
            label={'Save'}
            className="w-40 !h-10"
            disabled={
              (!isUpdated && !copied) ||
              userProfile.can_manage_definitions !== true
            }
            onClick={onCreateUpdateCheck}
            loading={isUpdating}
          />
        </div>
        {isCreating === false ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-lg font-semibold truncate">
                Check:{' '}
                {full_check_name ||
                  (path !== undefined &&
                    Array.from(path).join('/') + '/' + checkName) ||
                  checkName}
              </div>
            </div>
          </div>
        ) : (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="definitionssensors" className="w-5 h-5 shrink-0" />
              <div className="text-lg font-semibold truncate">
                Check:{' '}
                {path
                  ? [...(path || []), ''].join('/')
                  : String(full_check_name).replace(/\/[^/]*$/, '/')}
              </div>
              <Input
                value={checkName}
                onChange={onChangecheckName}
                error={!checkName}
                className="min-w-64"
              />
            </div>
          </div>
        )}
        <CheckEditor
          create={type === 'create' ? true : false}
          onChangeRule={onChangeRule}
          onChangeSensor={onChangeSensor}
          selectedRule={selectedRule}
          selectedSensor={selectedSensor}
          setIsUpdated={setIsUpdated}
          custom={custom}
          helpText={helpText}
          onChangeHelpText={onChangeHelpText}
          friendlyName={friendlyName}
          onChangeFriendlyName={onChangeFriendlyName}
          standard={standard}
          onChangeStandard={onChangeStandard}
          canEditDefinitions={userProfile.can_manage_definitions}
          defaultSeverity={defaultSeverity}
          onChangeDefaultSeverity={onChangeDefaultSeverity}
        />
        {/* )} */}
      </div>
      <ConfirmDialog
        open={dialogOpen}
        onClose={() => setDialogOpen(false)}
        onConfirm={onDeleteCheck}
        message={`Are you sure you want to delete the check ${full_check_name}`}
      />
    </>
  );
};

export default SensorDetail;
