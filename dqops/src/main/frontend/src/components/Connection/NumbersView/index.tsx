import React, { useEffect, useState } from 'react';
import NumberItem from './NumberItem';

interface INumbersViewProps {
  values: number[];
  onChange: (labels: number[]) => void;
}

const NumbersView = ({ values, onChange }: INumbersViewProps) => {
  const [text, setText] = useState<number>();

  useEffect(() => {
    if (text !== undefined) {
      onChange([...values, text]);
      setText(undefined);
    }
  }, [text]);

  const onChangeLabel = (key: number, value: number) => {
    if (key === values.length) {
      onChange([...values, value]);
    } else {
      onChange(values.map((item, index) => (key === index ? value : item)));
    }
  };

  const onRemoveLabel = (key: number) => {
    onChange(values.filter((item, index) => index !== key));
  };

  const data = [...(values || []), undefined];

  return (
    <div className="p-4">
      <table className="my-3 w-full">
        <thead>
          <tr>
            <th className="text-left min-w-40 w-full pr-4 py-2">Integer Value</th>
            <th className="px-8 min-w-40 text-left py-2">Action</th>
          </tr>
        </thead>
        <tbody>
          {data.map((label, index) => (
            <NumberItem
              integer={label}
              key={index}
              idx={index}
              isLast={index === values.length}
              onChange={onChangeLabel}
              onRemove={onRemoveLabel}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default NumbersView;
