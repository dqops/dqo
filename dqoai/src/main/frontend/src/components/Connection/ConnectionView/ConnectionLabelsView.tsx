import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionLabels,
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
  const { labels, isUpdating } = useSelector(
    (state: IRootState) => state.connection
  );
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const dispatch = useActionDispatch();
  const [isUpdated, setIsUpdated] = useState(false);

  useEffect(() => {
    setUpdatedLabels(labels);
  }, [labels]);

  useEffect(() => {
    dispatch(getConnectionLabels(connectionName));
  }, [connectionName]);

  const onUpdate = async () => {
    await dispatch(updateConnectionLabels(connectionName, updatedLabels));
    await dispatch(getConnectionLabels(connectionName));
  };

  const handleChange = (value: string[]) => {
    setUpdatedLabels(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <LabelsView labels={updatedLabels} onChange={handleChange} />
    </div>
  );
};

export default ConnectionLabelsView;
