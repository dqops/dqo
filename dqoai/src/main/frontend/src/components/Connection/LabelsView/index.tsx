import React, { useEffect, useState, KeyboardEvent } from 'react';
import LabelItem from './LabelItem';
import Input from "../../Input";

interface ILabelsViewProps {
  labels: string[];
  onChange: (labels: string[]) => void;
}

const LabelsView = ({ labels, onChange }: ILabelsViewProps) => {
  const [text, setText] = useState('');

  useEffect(() => {
    // if (text.length) {
    //   onChange([...labels, text]);
    //   setText('');
    // }
  }, [text]);

  const onChangeLabel = (key: number, value: string) => {
    onChange(labels.map((label, index) => (key === index ? value : label)));
  };

  const onRemoveLabel = (key: number) => {
    onChange(labels.filter((item, index) => index !== key));
  };

  const onKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      onChange([...labels, text]);
      setText('');
    }
  }

  return (
    <div className="p-4">
      <table className="w-full">
        <thead>
          <th className="text-left min-w-40 w-full pr-4 py-2">Label</th>
          <th className="px-8 min-w-34 max-w-34 py-2">Action</th>
        </thead>
        <tbody>
          {labels.map((label, index) => (
            <LabelItem
              label={label}
              key={index}
              idx={index}
              onChange={onChangeLabel}
              onRemove={onRemoveLabel}
            />
          ))}
          <tr>
            <td className="pr-4 min-w-40 py-2">
              <Input
                className="focus:!ring-0 focus:!border"
                value={text}
                onChange={(e) => setText(e.target.value)}
                onKeyDown={onKeyDown}
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default LabelsView;
