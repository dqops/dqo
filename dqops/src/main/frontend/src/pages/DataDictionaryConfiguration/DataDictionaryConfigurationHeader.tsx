import React from 'react';
import { useSelector } from 'react-redux';
import Button from '../../components/Button';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { ROUTES } from '../../shared/routes';

export default function DataDictionaryConfigurationHeader() {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const dispatch = useActionDispatch();
  const addDictionary = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.DATA_DICTIONARY_DETAIL('new'),
        value: ROUTES.DATA_DICTIONARY_VALUE('new'),
        label: 'Add data dictionary'
      })
    );
  };

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
