import React, { useState } from 'react';
import DefinitionLayout from '../../components/DefinitionLayout';
import TextArea from '../../components/TextArea';
import { useDispatch, useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';
import Input from '../../components/Input';
import Button from '../../components/Button';
import { DataDictionaryApiClient } from '../../services/apiClient';
import { closeFirstLevelTab } from '../../redux/actions/definition.actions';

export default function DataDictionaryItemOverview() {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const { credential_name } = useSelector(getFirstLevelSensorState);
  const [dictionaryName, setDictionaryName] = useState('');
  const [textAreaValue, setTextAreaValue] = useState<string>('');

  const dispatch = useDispatch();

  const addDictionary = async () => {
    await DataDictionaryApiClient.createDictionary({
      dictionary_name: dictionaryName,
      file_content: textAreaValue
    }).catch((err) => console.error(err));
    dispatch(closeFirstLevelTab('/definitions/shared-credential/new'));
  };

  const editDictionary = async () => {
    await DataDictionaryApiClient.updateDictionary(dictionaryName, {
      file_content: textAreaValue
    }).catch((err) => console.error(err));

    dispatch(
      closeFirstLevelTab('/definitions/shared-credential/' + credential_name)
    );
  };

  return (
    <DefinitionLayout>
      {userProfile.can_manage_and_view_shared_credentials === true ? (
        <>
          <div className="w-full border-b border-b-gray-400 flex justify-between ">
            <div className="text-xl font-semibold truncate flex items-center pl-5 space-x-2">
              <div>Dictionary:</div>
              <Input
                value={dictionaryName}
                onChange={(e) => setDictionaryName(e.target.value)}
                disabled={credential_name}
              />
            </div>
            <div className="flex items-center justify-center space-x-1 pr-5 overflow-hidden">
              {credential_name ? (
                <a
                  href={`/api/credentials/${credential_name}/download`}
                  rel="noreferrer"
                  target="_blank"
                >
                  <Button
                    label="Download"
                    color="primary"
                    className="w-30"
                    variant="contained"
                  />
                </a>
              ) : null}
              <Button
                label={credential_name ? 'Save' : 'Add dictionary'}
                color="primary"
                variant="contained"
                className={'my-3'}
                onClick={credential_name ? editDictionary : addDictionary}
              />
            </div>
          </div>
          <div className="px-5">
            <TextArea
              className="w-250 min-h-50 mt-4"
              value={textAreaValue}
              onChange={(e) => setTextAreaValue(e.target.value)}
            />
          </div>
        </>
      ) : (
        <div className="w-full h-screen flex items-center justify-center text-red-500 text-2xl">
          Access denied
        </div>
      )}
    </DefinitionLayout>
  );
}
