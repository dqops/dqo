import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionLabels,
  setIsUpdatedLabels,
  setUpdatedLabels,
  updateConnectionLabels
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import LabelsView from '../LabelsView';
import ConnectionActionGroup from './ConnectionActionGroup';

interface IConnectionLabelsViewProps {
  connectionName: string;
}

const ConnectionLabelsView = ({
  connectionName
}: IConnectionLabelsViewProps) => {
  const { isUpdating, updatedLabels, isUpdatedLabels } = useSelector(
    (state: IRootState) => state.connection
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (!updatedLabels) {
      dispatch(getConnectionLabels(connectionName));
    }
  }, [connectionName]);

  const onUpdate = async () => {
    await dispatch(updateConnectionLabels(connectionName, updatedLabels || []));
    await dispatch(getConnectionLabels(connectionName));
  };

  const handleChange = (value: string[]) => {
    dispatch(setUpdatedLabels(value));
    dispatch(setIsUpdatedLabels(true));
  };

  return (
    <div>
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedLabels}
        isUpdating={isUpdating}
      />
      <LabelsView labels={updatedLabels || []} onChange={handleChange} />
    </div>
  );
};

export default ConnectionLabelsView;
