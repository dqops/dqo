import Button from '../Button';
import React from 'react';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { updateSensor } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';

type SensorActionGroupProps = {
  onSave: () => void;
  onCopy?: () => void;
};

export const SensorActionGroup = ({ onSave, onCopy }: SensorActionGroupProps) => {
  const {
    full_sensor_name,
    sensorDetail,
    isUpdatedSensorDetail,
    isUpdating,
    type
  } = useSelector(getFirstLevelSensorState);
  const dispatch = useActionDispatch();
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const handleSave = () => {
    if (type === 'create') {
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
        disabled={!isUpdatedSensorDetail || userProfile.can_manage_definitions !== true}
        onClick={handleSave}
        loading={isUpdating}
      />
    </div>
  );
};
