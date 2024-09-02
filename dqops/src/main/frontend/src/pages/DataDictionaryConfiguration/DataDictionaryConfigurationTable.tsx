import { IconButton } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { DataDictionaryListModel } from '../../api';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import ClientSidePagination from '../../components/Pagination/ClientSidePagination';
import SvgIcon from '../../components/SvgIcon';
import { updateTabLabel } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { DataDictionaryApiClient } from '../../services/apiClient';
import Loader from '../../components/Loader';

export default function DataDictionaryConfigurationTable({
  setDictionaryToEdit
}: {
  setDictionaryToEdit: (value: string) => void;
}) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const [loading, setLoading] = useState(false);
  const [dictionaries, setDictionaries] = useState<DataDictionaryListModel[]>(
    []
  );
  const [displayedDictionaries, setDisplayedDictionaries] = useState<
    DataDictionaryListModel[]
  >([]);
  const [selectedDictionaryToDelete, setSelectedDictionaryToDelete] =
    useState('');

  const { activeTab } = useSelector((state: IRootState) => state.definition);

  const dispatch = useDispatch();

  const getAllDictionaries = async () => {
    setLoading(true);
    await DataDictionaryApiClient.getAllDictionaries()
      .then((res) => setDictionaries(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false));
  };

  const updateDataDictionary = async (dictionary_name: string) => {
    dispatch(updateTabLabel(dictionary_name, activeTab ?? ''));
    setDictionaryToEdit(dictionary_name);
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
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  return (
    <>
      <table>
        <tbody>
          {displayedDictionaries?.map((dictionary, index) => (
            <tr key={index} className="flex items-center text-sm">
              <td
                className="px-6 py-2 text-left block w-100 text-teal-500 cursor-pointer"
                onClick={() =>
                  updateDataDictionary(dictionary.dictionary_name ?? '')
                }
              >
                {dictionary.dictionary_name}
              </td>
              <td className="px-6 py-2 text-left block w-100 italic">
                {'${dictionary://' + dictionary.dictionary_name + '}'}
              </td>
              <td className="px-2 py-2 text-left block max-w-100">
                <IconButton
                  size="sm"
                  onClick={() =>
                    updateDataDictionary(dictionary.dictionary_name ?? '')
                  }
                  ripple={false}
                  color="teal"
                  className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                  disabled={userProfile.can_manage_definitions !== true}
                >
                  <SvgIcon name="edit" className="w-4" />
                </IconButton>
              </td>
              <td className="px-2 py-2 text-left block max-w-100">
                <IconButton
                  size="sm"
                  onClick={() =>
                    setSelectedDictionaryToDelete(
                      dictionary.dictionary_name ?? ''
                    )
                  }
                  ripple={false}
                  disabled={userProfile.can_manage_definitions !== true}
                  color="teal"
                  className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                >
                  <SvgIcon name="delete" className="w-4" />
                </IconButton>
              </td>
              <td className="px-2 py-2 text-left block max-w-100">
                <a
                  href={`/api/dictionaries/${dictionary.dictionary_name}/download`}
                  rel="noreferrer"
                  target="_blank"
                  className="text-teal-500"
                >
                  <IconButton
                    size="sm"
                    disabled={userProfile.can_manage_definitions !== true}
                    color="teal"
                    className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                    ripple={false}
                  >
                    <SvgIcon name="download" className="w-4" />
                  </IconButton>
                </a>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <ClientSidePagination
        items={dictionaries}
        onChangeItems={setDisplayedDictionaries} // Update displayed items
      />
      <ConfirmDialog
        open={selectedDictionaryToDelete?.length !== 0}
        onClose={() => setSelectedDictionaryToDelete('')}
        onConfirm={() => deleteDictionary(selectedDictionaryToDelete)}
        message={`Are you sure you want to delete ${selectedDictionaryToDelete} dictionary?`}
      />
    </>
  );
}
