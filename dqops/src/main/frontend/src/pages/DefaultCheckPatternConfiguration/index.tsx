import React from 'react';

import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import CreateCheckPattern from './CreateCheckPattern';
import EditCheckPattern from './EditCheckPattern';

export default function index() {
  const {
    type,
    pattern_name,
    create
  }: { type: 'table' | 'column'; pattern_name: string; create: boolean } =
    useSelector(getFirstLevelSensorState);

  return (
    <>
      {create === true ? (
        <CreateCheckPattern type={type} />
      ) : (
        <EditCheckPattern
          type={type}
          pattern_name={pattern_name}
          create={create}
        />
      )}
    </>
  );
}
