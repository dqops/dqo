import React, { useEffect, useState } from 'react';
import { AxiosResponse } from 'axios';
import { TableApiClient } from '../../../services/apiClient';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';
import LabelItem from '../ConnectionView/LabelItem';

interface ILabelsTabProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const LabelsTab = ({
  connectionName,
  schemaName,
  tableName
}: ILabelsTabProps) => {
  const [labels, setLabels] = useState<string[]>([]);
  const [text, setText] = useState('');

  const fetchLabels = async () => {
    try {
      const res: AxiosResponse<string[]> = await TableApiClient.getTableLabels(
        connectionName,
        schemaName,
        tableName
      );

      setLabels(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchLabels().then();
  }, [connectionName]);

  const onAdd = () => {
    setLabels([...labels, text]);
    setText('');
  };

  const onChangeLabel = (key: number, value: string) => {
    setLabels(labels.map((label, index) => (key === index ? value : label)));
  };

  const onRemoveLabel = (key: number) => {
    setLabels(labels.filter((item, index) => index !== key));
  };

  return (
    <div className="p-4">
      <table className="my-6 w-full">
        <thead>
          <th className="text-left min-w-40 w-full pr-4 py-2">Label</th>
          <th className="px-8 min-w-40 py-2">Action</th>
        </thead>
        <tbody>
          {labels &&
            labels.map((label, index) => (
              <LabelItem
                label={label}
                key={index}
                idx={index}
                onChange={onChangeLabel}
                onRemove={onRemoveLabel}
              />
            ))}
        </tbody>
      </table>
      <div className="flex items-center space-x-4">
        <div className="flex-1">
          <Input
            className="h-10"
            value={text}
            onChange={(e) => setText(e.target.value)}
          />
        </div>
        <IconButton className="w-10 h-10" onClick={onAdd}>
          <SvgIcon name="add" className="w-6" />
        </IconButton>
      </div>
    </div>
  );
};

export default LabelsTab;
