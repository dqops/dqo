import React, { useCallback, useEffect, useState } from 'react';

import { Dialog, Tooltip } from '@material-tailwind/react';
import { DataDictionaryApiClient } from '../../services/apiClient';
import Button from '../Button';
import LabelsView from '../Connection/LabelsView';
import Input from '../Input';
import SvgIcon from '../SvgIcon';

interface IStringListFieldProps {
  className?: string;
  label?: string;
  value: string[];
  tooltipText?: string;
  onChange: (value: string[]) => void;
  onSave?: () => void;
  disabled?: boolean;
}

const StringListField = ({
  label,
  value,
  tooltipText,
  onChange,
  disabled
}: IStringListFieldProps) => {
  const [open, setOpen] = useState(false);
  const [labels, setLabels] = useState<string[]>([]);
  const [convertToDictionary, setConvertToDictionary] = useState(false);
  const [dictionary, setDictionary] = useState<string | null>(null);
  const [dictionaryExistError, setDictionaryExistError] = useState(false);

  useEffect(() => {
    if (open) {
      setLabels(value);
    }
  }, [value, open]);

  const handleSave = () => {
    if (convertToDictionary) {
      saveDictionary();
      onChange([
        '${dictionary://' +
          (dictionary?.includes('.csv') ? dictionary : dictionary + '.csv') +
          '}'
      ]);

      setConvertToDictionary(false);
      setDictionaryExistError(false);
      setDictionary(null);
    } else {
      onChange(labels);
    }
    setOpen(false);
  };

  const handleChange = useCallback((values: string[]) => {
    setLabels(values);
  }, []);

  const saveDictionary = async () => {
    await DataDictionaryApiClient.createDictionary({
      dictionary_name: dictionary!,
      file_content: labels.filter((label) => label !== '').join('\n')
    })
      .then(() => {
        setOpen(false);
      })
      .catch((err) => {
        if (err.response?.status === 409) {
          setDictionaryExistError(true);
        }
        console.error(err);
      });
  };

  return (
    <div>
      <div className="flex space-x-1">
        {label && (
          <label className="block font-regular text-gray-700 mb-1 text-sm flex space-x-1">
            <span>{label}</span>
            {!!tooltipText && (
              <Tooltip
                content={tooltipText}
                className="max-w-80 py-2 px-2 bg-gray-800"
              >
                <div className="!min-w-4 !w-4">
                  <SvgIcon
                    name="info"
                    className="!w-4 !h-4 z-10 text-gray-700 cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
          </label>
        )}
      </div>
      <div className="flex space-x-2 items-center">
        <div className="relative text-sm leading-1">
          {value?.join(', ')?.slice(0, 200)}
          {value.join(', ').length > 200 && '...'}
        </div>
        <div className="!min-w-4 !w-4 ">
          <SvgIcon
            name="edit"
            className="w-4 h-4 text-gray-700 cursor-pointer"
            onClick={() => !disabled && setOpen(true)}
          />
        </div>
      </div>
      <Dialog open={open} handler={() => setOpen(false)} className="">
        <div className="p-4 !w-full max-h-[50vh] text-sm flex flex-col overflow-y-auto">
          <LabelsView
            labels={labels}
            onChange={handleChange}
            hasAdd
            title="Text values"
            className="text-sm flex flex-col"
            hasEdit={
              labels.length === 1 &&
              labels[0].startsWith('${dictionary://') &&
              labels[0].endsWith('}')
            }
          />
          <div className="flex space-x-4 p-4 justify-between">
            <div>
              {!convertToDictionary ? (
                <Button
                  label="Convert to dictionary"
                  onClick={() => setConvertToDictionary(true)}
                  color="primary"
                />
              ) : (
                <Input
                  label="Dictionary name"
                  value={dictionary || ''}
                  onChange={(e) => setDictionary(e.target.value)}
                  containerClassName="mb-5"
                />
              )}
            </div>
            <div className="flex items-center gap-x-4">
              <Button
                color="primary"
                variant="outlined"
                label="Cancel"
                className="w-40"
                onClick={() => setOpen(false)}
              />
              <Button
                variant="contained"
                label="Save"
                color="primary"
                className="w-40"
                onClick={handleSave}
              />
            </div>
          </div>
          {dictionaryExistError && (
            <div className="text-red-500 text-sm">
              Dictionary name is in use by another dictionary
            </div>
          )}
        </div>
      </Dialog>
    </div>
  );
};

export default StringListField;
