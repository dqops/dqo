import React, { ChangeEvent, useEffect, useState } from 'react';
import DefinitionLayout from '../../components/DefinitionLayout';
import SvgIcon from '../../components/SvgIcon';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import {
  addFirstLevelTab,
  closeFirstLevelTab,
  getdataQualityChecksFolderTree,
  opendataQualityChecksFolderTree
} from '../../redux/actions/definition.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  createCheck,
  updateCheck,
  deleteCheck,
  getCheck
} from '../../redux/actions/definition.actions';
import Input from '../../components/Input';
import CheckEditor from './CheckEditor';
import Button from '../../components/Button';
import { ROUTES } from '../../shared/routes';
import { CheckSpecModel } from '../../api';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import { IRootState } from '../../redux/reducers';

export const SensorDetail = () => {
  const { fullCheckName, path, type, custom} = useSelector(
    getFirstLevelSensorState
  );
  const {tabs, activeTab } = useSelector(
    (state: IRootState) => state.definition
  );
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const dispatch = useActionDispatch();
  const activeCheckDetail : CheckSpecModel = (tabs.find((x) => x.url === activeTab)?.state?.checkDetail as CheckSpecModel)

  const [checkName, setcheckName] = useState('');
  const [selectedSensor, setSelectedSensor] = useState(activeCheckDetail?.sensor_name ?? "");
  const [selectedRule, setSelectedRule] = useState(activeCheckDetail?.rule_name ?? "");
  const [isUpdating, setIsUpdating] = useState(false);
  const [isCreating, setIsCreating] = useState(false);
  const [isUpdated, setIsUpdated] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [helpText, setHelpText] = useState(activeCheckDetail?.help_text ?? "");


  const onChangeSensor = (value: string) => {
    setSelectedSensor(value);
  };

  const onChangeRule = (value: string) => {
    setSelectedRule(value);
  };

  const onChangeHelpText = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setHelpText(e.target.value);
  };

  useEffect(() => {
    if (type === 'create') {
      setIsCreating(true);
    } else {
      setIsCreating(false);
    }
    if (fullCheckName === undefined) {
      dispatch(closeFirstLevelTab(path));
    } else if ((fullCheckName as string).length === 0) {
      dispatch(closeFirstLevelTab(path));
    }
    if(activeCheckDetail === undefined){
      dispatch(getCheck(fullCheckName));
    }
  }, [fullCheckName, path, type, custom, activeCheckDetail]);

  useEffect(() => {
    if(activeCheckDetail === undefined){
      setSelectedRule('');
      setSelectedSensor('');
      setHelpText('');
      setcheckName('')
    }else{
      setSelectedRule(activeCheckDetail.rule_name ?? "");
      setSelectedSensor(activeCheckDetail.sensor_name ?? "");
      setHelpText(activeCheckDetail.help_text ?? "");
      setcheckName(activeCheckDetail.check_name ?? "")
    }
  }, [activeTab, activeCheckDetail]);

  const onCreateUpdateCheck = async () => {
    const fullName = [...(path || []), checkName].join('/');
    setIsUpdating(true);
    if (type === 'create') {
      await dispatch(
        createCheck(fullName, {
          sensor_name: selectedSensor,
          rule_name: selectedRule,
          help_text: helpText
        })
      );
      setIsUpdating(false);
      setIsCreating(false);
      openAddNewCheck();
      dispatch(getdataQualityChecksFolderTree());
      dispatch(opendataQualityChecksFolderTree(Array.from(path).join("/")))
    } else {
      await dispatch(
        updateCheck(
          fullCheckName
            ? fullCheckName
            : Array.from(path).join('/') + '/' + checkName,
          {
            sensor_name: selectedSensor,
            rule_name: selectedRule,
            help_text: helpText
          }
        )
      );
      setIsUpdating(false);
    }
    setIsUpdated(false);
  };

  const onDeleteCheck = async () => {
    closeFirstLevelTab(path);
    await dispatch(
      deleteCheck(
        fullCheckName
          ? fullCheckName
          : Array.from(path).join('/') + '/' + checkName
      )
    );
  };

  const onChangecheckName = (e: ChangeEvent<HTMLInputElement>) => {
    setIsUpdated(true);
    setcheckName(e.target.value);
  };

  const openAddNewCheck = () => {
    dispatch(closeFirstLevelTab("/definitions/checks/"+Array.from(path).join("-") + "-new_check"))
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DETAIL(checkName ?? ''),
        value: ROUTES.CHECK_DETAIL_VALUE(checkName ?? ''),
        state: {
          fullCheckName: (path !== undefined && (Array.from(path).join('/') + '/' + checkName)),
          custom: true,
          sensor: selectedSensor,
          rule: selectedRule,
          checkName: checkName,
          helpText: helpText
        },
        label: checkName
      })
    );
  };

  return (
    <DefinitionLayout>
      <div className="relative">
        <div className="flex space-x-4 items-center absolute right-2 top-2">
          {custom !== false && isCreating === false && (
            <Button
            color={!(userProfile.can_manage_definitions === false) ? 'primary' : 'secondary'}
            variant={!(userProfile.can_manage_definitions === false) ? "outlined" : "contained"}
              label="Delete check"
              className="w-40 !h-10"
              onClick={() => setDialogOpen(true)}
              disabled={userProfile.can_manage_definitions === false}
            />
          )}
          <Button
           color={!(userProfile.can_manage_definitions === false) ? 'primary' : 'secondary'}
            variant="contained"
            label={isCreating === true ? 'Create' : 'Update'}
            className="w-40 !h-10"
            disabled={!isUpdated || userProfile.can_manage_definitions === false}
            onClick={onCreateUpdateCheck}
            loading={isUpdating}
          />
        </div>
        {isCreating === false ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">
                Check: {fullCheckName || (path !== undefined && (Array.from(path).join('/') + '/' + checkName)) || checkName}
              </div>
            </div>
          </div>
        ) : (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">
                Check: {[...(path || []), ''].join('/')}
              </div>
              <Input
                value={checkName}
                onChange={onChangecheckName}
                error={!checkName}
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
          canEditDefinitions = {userProfile.can_manage_definitions}
        />
        {/* )} */}
      </div>
      <ConfirmDialog
        open={dialogOpen}
        onClose={() => setDialogOpen(false)}
        onConfirm={onDeleteCheck}
        message={`Are you sure you want to delete the check ${fullCheckName}`}
      />
    </DefinitionLayout>
  );
};

export default SensorDetail;
