import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Button from '../../components/Button';
import Input from '../../components/Input';
import SvgIcon from '../../components/SvgIcon';
import TextArea from '../../components/TextArea';
import { IRootState } from '../../redux/reducers';
import { DataDictionaryApiClient } from '../../services/apiClient';

export default function DataDictionaryItemOverview({
  dictionary_name = '',
  onBack
}: {
  dictionary_name?: string;
  onBack: () => void;
}) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const [dictionaryName, setDictionaryName] = useState('');
  const [textAreaValue, setTextAreaValue] = useState<string>('');

  const dispatch = useDispatch();

  const addDictionary = async () => {
    if (dictionaryName.length === 0) return;
    await DataDictionaryApiClient.createDictionary({
      dictionary_name: dictionaryName,
      file_content: textAreaValue
    }).catch((err) => console.error(err));
    onBack();
  };

  const editDictionary = async () => {
    await DataDictionaryApiClient.updateDictionary(dictionaryName, {
      dictionary_name: dictionaryName,
      file_content: textAreaValue
    }).catch((err) => console.error(err));
    onBack();
  };

  useEffect(() => {
    if (dictionary_name && dictionary_name.length !== 0) {
      const getDictionary = () => {
        DataDictionaryApiClient.getDictionary(dictionary_name).then((res) => {
          setDictionaryName(res?.data?.dictionary_name ?? ''),
            setTextAreaValue(res?.data?.file_content ?? '');
        });
      };
      getDictionary();
    }
  }, [dictionary_name]);

  return (
    <>
      <div className="w-full border-b border-b-gray-400 flex justify-between ">
        <div className="text-sm font-semibold truncate flex items-center pl-5 space-x-2">
          <div>Dictionary:</div>
          <Input
            value={dictionaryName}
            onChange={(e) => setDictionaryName(e.target.value)}
            disabled={
              dictionary_name.length !== 0 ||
              userProfile.can_manage_definitions !== true
            }
          />
        </div>
        <div className="flex items-center justify-center space-x-1 pr-5 overflow-hidden">
          <Button
            label="Back"
            color="primary"
            variant="text"
            className="px-0 mr-4"
            leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
            onClick={onBack}
          />
          {dictionary_name ? (
            <a
              href={`/api/credentials/${dictionary_name}/download`}
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
            label={dictionary_name ? 'Save' : 'Add dictionary'}
            color="primary"
            variant="contained"
            className={'my-3'}
            onClick={dictionary_name ? editDictionary : addDictionary}
            disabled={userProfile.can_manage_definitions !== true}
          />
        </div>
      </div>
      <div className="px-5">
        <TextArea
          className="w-full min-h-120 mt-4"
          value={textAreaValue}
          onChange={(e) => setTextAreaValue(e.target.value)}
          disabled={userProfile.can_manage_definitions !== true}
        />
      </div>
    </>
  );
}
