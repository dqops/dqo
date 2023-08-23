import React, { ChangeEvent, useEffect, useState } from 'react';
import DefinitionLayout from '../../components/DefinitionLayout';
import SvgIcon from '../../components/SvgIcon';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import {
  addFirstLevelTab,
  closeFirstLevelTab
} from '../../redux/actions/definition.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
// import { createRule, getRule, setUpdatedRule } from "../../redux/actions/definition.actions";
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

export const SensorDetail = () => {
  const { fullCheckName, path, type, custom, checkDetail } = useSelector(
    getFirstLevelSensorState
  );

  const dispatch = useActionDispatch();

  const [checkName, setcheckName] = useState('');
  const [selectedSensor, setSelectedSensor] = useState('');
  const [selectedRule, setSelectedRule] = useState('');
  const [isUpdating, setIsUpdating] = useState(false);
  const [isCreating, setIsCreating] = useState(false);
  const [isUpdated, setIsUpdated] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);

  const onChangeSensor = (value: string) => {
    setSelectedSensor(value);
  };

  const onChangeRule = (value: string) => {
    setSelectedRule(value);
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
    dispatch(getCheck(fullCheckName));
  }, [fullCheckName, path, type, custom]);

  useEffect(() => {
    if (fullCheckName !== undefined && type !== 'create') {
      dispatch(getCheck(fullCheckName));
    }
  }, [fullCheckName, type]);

  useEffect(() => {
    setSelectedRule((checkDetail as CheckSpecModel)?.rule_name ?? '');
    setSelectedSensor((checkDetail as CheckSpecModel)?.sensor_name ?? '');
  }, [checkDetail]);

  const onCreateUpdateCheck = async () => {
    const fullName = [...(path || []), checkName].join('/');
    setIsUpdating(true);
    if (type === 'create') {
      await dispatch(
        createCheck(fullName, {
          sensor_name: selectedSensor,
          rule_name: selectedRule
        })
      );
      setIsUpdating(false);
      setIsCreating(false);
      openAddNewCheck();
    } else {
      await dispatch(
        updateCheck(
          fullCheckName
            ? fullCheckName
            : Array.from(path).join('/') + '/' + checkName,
          { sensor_name: selectedSensor, rule_name: selectedRule }
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
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DETAIL([...(path || []), 'new_check'].join('-')),
        value: ROUTES.CHECK_DETAIL_VALUE(
          [...(path || []), 'new_check'].join('-')
        ),
        state: {
          type: 'upgrade',
          path,
          fullCheckName
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
              color="primary"
              variant="outlined"
              label="Delete check"
              className="w-40 !h-10"
              onClick={() => setDialogOpen(true)}
            />
          )}
          <Button
            color="primary"
            variant="contained"
            label={isCreating === true ? 'Create' : 'Update'}
            className="w-40 !h-10"
            disabled={!isUpdated}
            onClick={onCreateUpdateCheck}
            loading={isUpdating}
          />
        </div>
        {isCreating === false ? (
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">
                Check: {fullCheckName || checkName}
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
        {/* <div className="border-b border-gray-300 relative">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
        {activeTab === 'check_editor' && ( */}
        <CheckEditor
          create={type === 'create' ? true : false}
          onChangeRule={onChangeRule}
          onChangeSensor={onChangeSensor}
          selectedRule={selectedRule}
          selectedSensor={selectedSensor}
          setIsUpdated={setIsUpdated}
          custom={custom}
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
