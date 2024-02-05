import React, { useEffect, useState } from 'react';
import { DataDictionaryListModel } from '../../api';
import Button from '../../components/Button';
import SvgIcon from '../../components/SvgIcon';
import { DataDictionaryApiClient } from '../../services/apiClient';
import { useDispatch, useSelector } from 'react-redux';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { ROUTES } from '../../shared/routes';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import { IRootState } from '../../redux/reducers';

export default function DataDictionaryConfigurationTable() {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const [loading, setLoading] = useState(false);
  const [dictionaries, setDictionaries] = useState<DataDictionaryListModel[]>(
    []
  );
  const [selectedDictionaryToDelete, setSelectedDictionaryToDelete] =
    useState('');
  const dispatch = useDispatch();

  const getAllDictionaries = async () => {
    setLoading(true);
    await DataDictionaryApiClient.getAllDictionaries()
      .then((res) => setDictionaries(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false));
  };

  const updateDataDictionary = async (dictionary_name: string) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.DATA_DICTIONARY_DETAIL(dictionary_name),
        value: ROUTES.DATA_DICTIONARY_VALUE(dictionary_name),
        label: 'Edit dictionary',
        state: {
          dictionary_name
        }
      })
    );
  };

  const deleteDictionary = async (dictionary: string) => {
    await DataDictionaryApiClient.deleteDictionary(dictionary)
      .then(() => {
        getAllDictionaries();
      })
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    getAllDictionaries();
  }, []);

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <SvgIcon name="sync" className="w-6 h-6 animate-spin" />
      </div>
    );
  }

  return (
    <tbody>
      {dictionaries?.map((dictionary, index) => (
        <tr key={index} className="flex items-center">
          <td className="px-6 py-2 text-left block w-100">
            {dictionary.dictionary_name}
          </td>
          <td className="px-6 py-2 text-left block w-100 italic">
             {'${dictionary://' + dictionary.dictionary_name + "}"}
          </td>
          <td className="px-6 py-2 text-left block max-w-100">
            <Button
              label="edit"
              variant="text"
              color="primary"
              onClick={() =>
                updateDataDictionary(dictionary.dictionary_name ?? '')
              }
              disabled={userProfile.can_manage_definitions !== true}
            />
          </td>
          <td className="px-6 py-2 text-left block max-w-100">
            <Button
              label="delete"
              variant="text"
              color="primary"
              onClick={() =>
                setSelectedDictionaryToDelete(dictionary.dictionary_name ?? '')
              }
              disabled={userProfile.can_manage_definitions !== true}
            />
          </td>
          <td className="px-6 py-2 text-left block max-w-100">
            <a
              href={`/api/dictionaries/${dictionary.dictionary_name}/download`}
              rel="noreferrer"
              target="_blank"
              className="text-teal-500"
            >
              download
            </a>
          </td>
        </tr>
      ))}
      <ConfirmDialog
        open={selectedDictionaryToDelete?.length !== 0}
        onClose={() => setSelectedDictionaryToDelete('')}
        onConfirm={() => deleteDictionary(selectedDictionaryToDelete)}
        message={`Are you sure you want to delete ${selectedDictionaryToDelete} dictionary?`}
      />
    </tbody>
  );
}
