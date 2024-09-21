import React from 'react';
import { useSelector } from 'react-redux';
import Button from '../../components/Button';
import { IRootState } from '../../redux/reducers';

export default function DataDictionaryConfigurationHeader({
  addDictionary
}: {
  addDictionary: () => void;
}) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  return (
    <thead className="border-b w-full border-b-gray-400 relative flex items-center text-sm">
      <th className="px-6 py-4 text-left block w-100">Dictionary file name</th>
      <th className="px-6 py-4 text-left block w-100">Dictionary reference</th>
      {/* <th className="px-6 py-4 text-left block w-100">Credential type</th> */}
      <Button
        label="Add dictionary"
        color="primary"
        variant="contained"
        className="absolute right-2 top-2 w-40"
        onClick={addDictionary}
        disabled={userProfile.can_manage_definitions !== true}
      />
    </thead>
  );
}
