import React from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';

export default function DataDomainDetail() {
  const { userProfile } = useSelector((state: IRootState) => state.job);
  const { data_domain } = useSelector(getFirstLevelSensorState);
  return <div></div>;
}
