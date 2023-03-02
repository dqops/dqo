import React, { useEffect, useState } from 'react';
import LabelItem from './LabelItem';

interface ILabelsViewProps {
  labels: string[];
  onChange: (labels: string[]) => void;
}

const LabelsView = ({ labels, onChange }: ILabelsViewProps) => {
  const [text, setText] = useState('');

  useEffect(() => {
    if (text.length) {
      onChange([...labels, text]);
      setText('');
    }
  }, [text]);

  const onChangeLabel = (key: number, value: string) => {
    if (key === labels.length) {
      onChange([...labels, value]);
    } else {
      onChange(labels.map((label, index) => (key === index ? value : label)));
    }
  };

  const onRemoveLabel = (key: number) => {
    onChange(labels.filter((item, index) => index !== key));
  };

  const data = [...(labels || []), ''];

  return (
    <div className="p-4">
      <table className="my-3 w-full">
        <thead>
          <th className="text-left min-w-40 w-full pr-4 py-2">Label</th>
          <th className="px-8 min-w-34 max-w-34 py-2">Action</th>
        </thead>
        <tbody>
          {data.map((label, index) => (
            <LabelItem
              label={label}
              key={index}
              idx={index}
              isLast={index === labels.length}
              onChange={onChangeLabel}
              onRemove={onRemoveLabel}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default LabelsView;
