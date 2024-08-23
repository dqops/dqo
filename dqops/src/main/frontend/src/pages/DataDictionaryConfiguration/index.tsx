import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateTabLabel } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import DataDictionaryConfigurationHeader from './DataDictionaryConfigurationHeader';
import DataDictionaryConfigurationTable from './DataDictionaryConfigurationTable';
import DataDictionaryItemOverview from './DataDictionaryItemOverview';

export default function DataDictionary() {
  const [dictionaryToEdit, setDictionaryToEdit] = React.useState<string | null>(
    null
  );
  const [createDictionary, setCreateDictionary] =
    React.useState<boolean>(false);
  const { activeTab } = useSelector((state: IRootState) => state.definition);

  const dispatch = useDispatch();

  const onBack = () => {
    setDictionaryToEdit(null);
    setCreateDictionary(false);
    dispatch(updateTabLabel('Data dictionaries', activeTab ?? ''));
  };

  return (
    <>
      {dictionaryToEdit || createDictionary ? (
        <DataDictionaryItemOverview
          dictionary_name={dictionaryToEdit ?? ''}
          onBack={onBack}
        />
      ) : (
        <table className="w-full ">
          <DataDictionaryConfigurationHeader
            addDictionary={() => setCreateDictionary(true)}
          />
          <DataDictionaryConfigurationTable
            setDictionaryToEdit={setDictionaryToEdit}
          />
        </table>
      )}
    </>
  );
}
