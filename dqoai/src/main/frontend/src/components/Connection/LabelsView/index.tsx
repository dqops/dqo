import React, { useState } from 'react';
import LabelItem from './LabelItem';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';

interface ILabelsViewProps {
  labels: string[];
  onChange: (labels: string[]) => void;
}

const LabelsView = ({ labels, onChange }: ILabelsViewProps) => {
  const [text, setText] = useState('');

  const onAdd = () => {
    onChange([...labels, text]);
    setText('');
  };

  const onChangeLabel = (key: number, value: string) => {
    onChange(labels.map((label, index) => (key === index ? value : label)));
  };

  const onRemoveLabel = (key: number) => {
    onChange(labels.filter((item, index) => index !== key));
  };

  return (
    <div className="p-4">
      <table className="my-3 w-full">
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

export default LabelsView;
