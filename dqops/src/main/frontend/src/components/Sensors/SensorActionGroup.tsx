import React from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { updateSensor } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';
import Button from '../Button';

type SensorActionGroupProps = {
  onSave: () => void;
  onCopy?: () => void;
  onDelete?: () => void;
};

export const SensorActionGroup = ({
  onSave,
  onCopy,
  onDelete
}: SensorActionGroupProps) => {
  const {
    full_sensor_name,
    sensorDetail,
    isUpdatedSensorDetail,
    isUpdating,
    type,
    copied
  } = useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const handleSave = () => {
    if (type === 'create' || copied === true) {
      onSave();
      return;
    }
    if (full_sensor_name) {
      dispatch(updateSensor(full_sensor_name, sensorDetail));
    }
  };
  return (
    <div className="flex space-x-4 items-center absolute right-2 top-2">
      {sensorDetail?.custom && (
        <Button
          color="primary"
          variant="outlined"
          label="Delete Sensor"
          className="w-40 !h-10"
          disabled={userProfile.can_manage_definitions !== true}
          onClick={onDelete}
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
        color="primary"
        variant="contained"
        label="Save"
        className="w-40 !h-10"
        disabled={
          (!isUpdatedSensorDetail && !copied) ||
          userProfile.can_manage_definitions !== true
        }
        onClick={handleSave}
        loading={isUpdating}
      />
    </div>
  );
};
