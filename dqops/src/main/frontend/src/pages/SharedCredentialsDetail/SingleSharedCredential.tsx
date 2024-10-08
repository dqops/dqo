import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  SharedCredentialModel,
  SharedCredentialModelTypeEnum
} from '../../api';
import Button from '../../components/Button';
import Input from '../../components/Input';
import Loader from '../../components/Loader';
import RadioButton from '../../components/RadioButton';
import SvgIcon from '../../components/SvgIcon';
import TextArea from '../../components/TextArea';
import { IRootState } from '../../redux/reducers';
import { SharedCredentialsApi } from '../../services/apiClient';

export default function SingleSharedCredential({
  credential_name = '',
  onBack
}: {
  credential_name?: string;
  onBack: () => void;
}) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const [credentialName, setCredentialName] = useState('');
  const [editingCredential, setEditingCredential] =
    useState<SharedCredentialModel>();
  const [type, setType] = useState<SharedCredentialModelTypeEnum>('text');
  const [textAreaValue, setTextAreaValue] = useState<string>('');
  const [incorrectBinaryText, setIncorrectBinaryText] = useState(false);
  const [loading, setLoading] = useState(false);

  const addSharedCredential = async () => {
    if (type === 'text') {
      await SharedCredentialsApi.createSharedCredential({
        credential_name: credentialName,
        type,
        text_value: textAreaValue
      }).catch((err) => console.error(err));
    } else {
      await SharedCredentialsApi.createSharedCredential({
        credential_name: credentialName,
        type,
        binary_value: textAreaValue
      }).catch((err) => console.error(err));
    }
    onBack();
  };

  const editSharedCredential = async () => {
    if (type === 'text') {
      await SharedCredentialsApi.updateSharedCredential(credential_name, {
        credential_name: credential_name,
        type: type,
        text_value: textAreaValue
      }).catch((err) => console.error(err));
    } else {
      await SharedCredentialsApi.updateSharedCredential(credential_name, {
        credential_name,
        type,
        binary_value: textAreaValue
      }).catch((err) => console.error(err));
    }
    onBack();
  };

  const getSharedCredential = async () => {
    setLoading(true);
    await SharedCredentialsApi.getSharedCredential(credential_name)
      .then((res) => setEditingCredential(res.data))
      .catch((err) => console.error(err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    if (credential_name) {
      getSharedCredential();
    }
  }, [credential_name]);

  useEffect(() => {
    if (editingCredential) {
      setCredentialName(credential_name);
      setType(editingCredential.type ?? 'text');
      setTextAreaValue(
        (editingCredential.type === 'text'
          ? editingCredential.text_value
          : editingCredential.binary_value) ?? ''
      );
    }
  }, [credential_name, editingCredential]);

  useEffect(() => {
    isBinaryString(textAreaValue);
  }, [textAreaValue, type]);

  const isBinaryString = (input: string) => {
    const binaryPattern =
      /^([0-9a-zA-Z+/]{4})*(([0-9a-zA-Z+/]{2}==)|([0-9a-zA-Z+/]{3}=))?$/;
    if (binaryPattern.test(input) === false && type === 'binary') {
      setIncorrectBinaryText(true);
    } else {
      setIncorrectBinaryText(false);
    }
  };

  if (loading) {
    return (
      <>
        <div className="w-full h-screen flex items-center justify-center">
          <Loader isFull={false} className="w-8 h-8 fill-green-700" />
        </div>
      </>
    );
  }

  return (
    <>
      {userProfile.can_manage_and_view_shared_credentials === true ? (
        <>
          <div className="w-full border-b border-b-gray-400 flex justify-between ">
            <div className="text-lg font-semibold truncate flex items-center pl-5 space-x-2">
              <div>Shared credential:</div>
              <Input
                value={credentialName}
                onChange={(e) => setCredentialName(e.target.value)}
                disabled={!!credential_name.length}
              />
            </div>
            <div className="flex items-center justify-center space-x-1 pr-5 overflow-hidden">
              <Button
                label="Back"
                color="primary"
                variant="text"
                className="px-0 mr-2"
                leftIcon={
                  <SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />
                }
                onClick={onBack}
              />
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
                label={credential_name ? 'Save' : 'Add shared credential'}
                color="primary"
                variant="contained"
                className={
                  credential_name ? 'w-30 mr-10 my-3' : 'w-55 mr-10 my-3'
                }
                onClick={
                  credential_name ? editSharedCredential : addSharedCredential
                }
                disabled={incorrectBinaryText === true}
              />
            </div>
          </div>
          <div className="w-100 px-5 ">
            <div className="text-sm py-3">Type of credential:</div>
            <div className="flex items-center space-x-4">
              <RadioButton
                label="Text"
                checked={type === 'text'}
                onClick={() => setType('text')}
              />
              <RadioButton
                label="Binary"
                checked={type === 'binary'}
                onClick={() => setType('binary')}
              />
            </div>
          </div>
          {incorrectBinaryText ? (
            <div className="text-red-500 pt-5 text-lg px-5">
              Invalid base64 text
            </div>
          ) : null}
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
    </>
  );
}
